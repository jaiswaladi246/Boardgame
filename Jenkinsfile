pipeline {
    agent any

    tools {
        maven 'Maven3'     // Ensure this name matches your Jenkins tool configuration
        jdk 'jdk17'        // Ensure JDK 17 is installed and labeled as 'jdk17' in Jenkins
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning repository...'
                git url: 'https://github.com/Savirean07/Boardgame.git', branch: 'main'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling the application...'
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package'
            }
        }

        stage('Hello') {
            steps {
                echo 'Hello World!'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Please check the logs.'
        }
    }
}
