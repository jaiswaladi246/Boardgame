pipeline {
    agent any
    
    tools {
        maven 'Maven3'
        dockerTool 'Docker'
    }
     environment {
        DOCKER_HUB_USER = credentials('docker-user') 
        DOCKER_HUB_PASS = credentials('docker-pass') 
    }

    stages {
        stage('Git checkout') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/sanciajerin/Boardgame.git'
            }
        }
        stage('Build Project') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Code check') {
            steps {
              withSonarQubeEnv('sonar-scanner') {
                    sh '''mvn clean verify sonar:sonar -Dsonar.projectKey=Project1 -Dsonar.projectName='Project1' -Dsonar.host.url=mention the url of sonarqube'''
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
        stage('Upload to dockerhub') {
            steps {
                script {
                   sh 'docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASS}'
                   sh 'docker build -t boardgame .'
                   sh 'docker tag boardgame jerinvarghese1993/boardgame'
                   sh 'docker push jerinvarghese1993/boardgame'
                }
            }
        }
    }
}
