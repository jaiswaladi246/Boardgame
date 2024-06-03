pipeline {
    agent any
    
    tools {
        jdk 'java11'
        maven 'maven3'
    }

    stages {
        stage('git checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Afsarali1803/Boardgame.git'
            }
        }
        
        stage('Maven compile') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('maven test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('maven build') {
            steps {
                sh 'mvn package'
            }
        }
        
        
    }
}
