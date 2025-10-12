pipeline {
    agent any

 tools {
     // Update to JDK 21; ensure your Jenkins tool configuration has an entry named 'java21'
     // Ensure your Jenkins has tools named 'java21' and 'maven3.9' (or change these names to match your Jenkins tool config)
     jdk 'java21'
     maven 'maven3.9'
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
