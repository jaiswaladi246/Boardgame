pipeline {
    agent any

    tools {
        maven 'Maven3'     // Ensure this matches Jenkins config
        jdk 'Java 8'       // Ensure this is defined in Jenkins
    }

    environment {
        registry = "https://jfrog.io"  // Replace with your actual JFrog base URL (without /artifactory)
        IMAGE_NAME = "Image Name"  // replace with your Docker image name 
        IMAGE_TAG = "latest"
        PATH = "/opt/sonar-scanner/bin:$PATH"   // Replace with your Image path 
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning repository...'
                git url: 'https://github.com.git', branch: 'main'    // Replace with your git repo URl
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling the application...'
                sh 'mvn compile'
            }
        }

        stage('File scan by Trivy') {
            steps {
                echo 'Running Trivy scan...'
                sh 'trivy fs --format table --output trivy-filescanproject-output.txt .'
            }
        }

        stage('Check Scanner') {
            steps {
                echo 'Checking sonar-scanner path and version...'
                sh 'echo $PATH'
                sh 'which sonar-scanner || echo "sonar-scanner not found!"'    // jenkins Sonar scanner Creds
                sh 'sonar-scanner --version || echo "Version check failed!"'
            }
        }

        stage('Sonar Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('sonarqube') {          // Replace with your Jenkins sonarqube creds name
                    sh '''
                        sonar-scanner \
                        -Dsonar.projectKey=Boardgame \
                        -Dsonar.java.binaries=. \
                        -Dsonar.exclusions=**/trivy-filescanproject-output.txt
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Waiting for SonarQube Quality Gate...'
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Maven Package') {
            steps {
                echo 'Maven packaging started...'
                sh 'mvn package'
            }
        }

        stage('Jar Publish') {
            steps {
                script {
                    echo '<-------------- Jar Publish Started -------------->'

                    def server = Artifactory.newServer(
                        url: "${registry}/artifactory",
                        credentialsId: "jfrogaccess"
                    )

                    def properties = "buildid=${env.BUILD_ID},commitid=${env.GIT_COMMIT}"

                    def uploadSpec = """{
                        "files": [
                            {
                                "pattern": "target/database_service_project.jar",
                                "target": "boardgame-libs-release/",
                                "flat": false,
                                "props": "${properties}",
                                "exclusions": ["*.sha1", "*.md5"]
                            }
                        ]
                    }"""

                    def buildInfo = server.upload(uploadSpec)
                    buildInfo.env.collect()
                    server.publishBuildInfo(buildInfo)

                    echo '<-------------- Jar Publish Ended -------------->'
                }
            }
        }

        stage('Docker Build and Tag') {
            steps {
                script {
                    echo 'Docker Build and Tag started'
                    echo "Building Docker image ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Trivy Docker Image Scan') {
            steps {
                echo 'Trivy Docker image scan started'
                sh "trivy image --format table --output trivy-image-scan.txt ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }

        stage('Image Push to Docker Hub') {
            steps {
                script {
                    echo 'Docker Push to Docker Hub started'
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                            echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                            docker push ${IMAGE_NAME}:${IMAGE_TAG}
                            docker logout
                        '''
                    }
                }
            }
        }

        stage('Image Push to Azure Container Registry') {
            steps {
                script {
                    echo 'Docker Push to Azure Container Registry started'

                    def acrRegistry = ''          // Place your registry name here 
                    def acrImage = "${acrRegistry}/to-do-app:${IMAGE_TAG}"

                    sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${acrImage}"

                    withCredentials([usernamePassword(credentialsId: 'acr-cred', usernameVariable: 'ACR_USERNAME', passwordVariable: 'ACR_PASSWORD')]) {
                        sh """
                            echo "$ACR_PASSWORD" | docker login ${acrRegistry} -u "$ACR_USERNAME" --password-stdin
                            docker push ${acrImage}
                            docker logout ${acrRegistry}
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo 'Deploying to Kubernetes cluster'

                    withCredentials([file(credentialsId: 'K8-cred', variable: 'KUBECONFIG_FILE')]) {
                        sh '''
                            export KUBECONFIG=$KUBECONFIG_FILE
                            kubectl apply -f deployment-service.yml
                            kubectl rollout status deployment/to-do-app
                        '''
                    }
                }
            }
        }
    }
}
