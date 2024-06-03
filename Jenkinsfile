pipeline {
    agent any
    
    tools {
        jdk 'java11'
        maven 'maven3'
    }

    stages {       
        stage('maven compile') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('maven test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('maven package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
