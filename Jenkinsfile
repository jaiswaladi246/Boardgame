pipeline {
    agent any
    
    tools {
        jdk 'java17'
        maven 'maven3'
    }

    stages {
        stage('git checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Afsarali1803/Boardgame.git'
            }
        }
        
        stage('compile') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
