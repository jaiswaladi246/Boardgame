pipeline {
    agent any

    tools {
        maven 'Maven3'     // Ensure this name matches your Jenkins tool configuration
        jdk 'Java 8'        // Ensure JDK 17 is installed and labeled as 'jdk17' in Jenkins
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
        stage('Sonar Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') { // Ensure 'SonarQube' matches your SonarQube server configuration in Jenkins
                    sh ''' 
                        ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                        -Dsonar.projectKey=Boardgame \                       
                        -Dsonar.java.binaries=. \
                        -Dsonar.exclusions=**/trivy-filescanproject-output.txt 
                    '''
                }
            }
        }
    }
}
