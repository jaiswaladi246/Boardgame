pipeline {
    agent any

 tools {
     jdk 'java17'
     maven 'maven3.6'
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
