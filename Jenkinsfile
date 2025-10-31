pipeline {
    agent any

 tools {
     jdk 'jdk17'
     maven 'maven3'
 }
    
    stages {
        
        stage('Compile') {
            steps {
            sh  'mvn compile'
            }
        }
        
        stage('Testing') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Building The Project') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
