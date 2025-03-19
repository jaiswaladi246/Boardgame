pipeline {
    agent any
    
    tools{
        jdk 'jdk11'
        maven 'maven3'
    }

    stages {
        stage('GIT Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AKASH-GIT-2998/Boardgame.git'
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
        
        stage('Build') {
            steps {
                sh "mvn package"
            }
        }
    }
}
