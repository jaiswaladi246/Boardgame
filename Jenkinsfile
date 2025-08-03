pipeline {
    agent any

    tools {
        maven 'Maven3'     // Ensure this matches Jenkins config
        jdk 'Java 8'       // Ensure this is defined in Jenkins
        sonarQubeScanner 'SonarScanner'  // Must match Global Tool Configuration name
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
                        sonar-scanner \
                          -Dsonar.projectKey=Boardgame \
                          -Dsonar.java.binaries=. \
                          -Dsonar.exclusions=**/trivy-filescanproject-output.txt
                    '''
                }
            }
        }
    }
}
