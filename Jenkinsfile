pipeline {
    agent any

    tools {
        jdk "jdk17"
        maven "maven3"
        // Add 'sonar-scanner' as a tool if not already configured in Jenkins Global Tool Configuration
        // sonar "sonar-scanner"
    }
    
    environment {
        SCANNER_HOME = tool 'sonar-scanner'
    }
   
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Gowthamsubramani12/Boardgame.git'
            }
        }
        
        stage('Compile') {
            steps {
                sh "mvn compile"
            }
        }
        
        stage('Test') {
            steps {
                sh "mvn test"
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh '''$SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=BoardGame -Dsonar.projectKey=BoardGame \
                        -Dsonar.java.binaries=. '''
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                script {
                    // waitForQualityGate is deprecated, consider using waitForQualityGate() method
                    // The credentialsId 'sonar' should be the ID of your SonarQube server in Jenkins Credentials
                    waitForQualityGate(credentialsId: 'sonar')
                }
            }
        }
        
        stage('Build') {
            steps {
                sh "mvn package"
            }
        }
    
        stage('Build & Tag Docker Image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker build -t gowtham12345/boardshack:latest ."
                    }
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker push gowtham12345/boardshack:latest"
                    }
                }
            }
        }
    }
}