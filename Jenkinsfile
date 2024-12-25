pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Wurie232/Boardgame.git'
            }
        }
        
        
        stage('Compile') {
            steps {
                sh "mvn compile"
            }
        }
        
        
        stage('test') {
            steps {
                sh "mvn test"
            }
        }
        
        
        stage('build') {
            steps {
                sh "mvn package"
            }
        }
    }
}
