pipeline {
    agent any

    tools {
        maven 'Maven3'     // Ensure this matches Jenkins config
        jdk 'Java 8'       // Ensure this is defined in Jenkins
    }

    environment {
        registry = "https://your-jfrog-url"  // Replace with your actual JFrog base URL (without /artifactory)
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning repository...'
                git url: 'https://github.com/Savirean07/Boardgame.git', branch: 'main'
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
                sh 'which sonar-scanner || echo "sonar-scanner not found!"'
                sh 'sonar-scanner --version || echo "Version check failed!"'
            }
        }

        stage('Sonar Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('sonarqube') {
                    sh '''
                        /opt/sonar-scanner/bin/sonar-scanner \
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
    }
}
