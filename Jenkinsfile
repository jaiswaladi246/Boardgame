pipeline {
     agent any
    
    tools{
        maven 'maven'
    }

    stages {
        
        stage('Git') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Devangam/Boardgame.git']])
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
