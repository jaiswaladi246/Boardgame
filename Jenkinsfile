pipeline {
    agent any
    
            tools{
                 maven 'maven3'
                }

     stages {
                stage('Git Checkout') {
                  steps {
                    git branch: 'main', url: 'https://github.com/Akinseye-oludayo/Boardgame.git'
                    }
                    }
        
             stage('Compile') {
                steps {
                    sh 'mvn compile'
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
