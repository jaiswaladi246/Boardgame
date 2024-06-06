pipeline {
    agent any

    tools {
        // Define the Maven and JDK tools to be used in the pipeline
        maven "maven3"
        jdk "jdk17"
    }

    stages {
        stage('Compile') {
            steps {
                // Compile the Maven project
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                // Run tests using Maven
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {
                // Build the Maven project (assuming you meant 'mvn package' for packaging the project)
                sh 'mvn package'
            }
        }
    }
}
