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
                  sh '''mvn clean verify sonar:sonar -Dsonar.projectKey=Project1 -Dsonar.host.url=http://98.81.146.83:9000 -Dsonar.login=sqp_97ed9b2153e7d71f3c99bf83cb221e6e25156868'''
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
