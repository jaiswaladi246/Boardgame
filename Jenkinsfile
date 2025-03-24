pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'jdk17'
    }

    stages {     
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
