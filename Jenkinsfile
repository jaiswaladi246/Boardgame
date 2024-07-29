pipeline {    
    agent any 
    tools {



        
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {   
        stage('Compile') {
            steps {
                git branch: 'develop', url: 'https://github.com/zurry8474/Boardgame.git'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
