pipeline {
    agent { docker { image 'maven:3.9.8' } }
    stages {
        stage('Checkout') {
            steps {
                // Checkout source code from Git repository
                git url: 'https://github.com/ToanNgo2709/java-web-dev-pro4-udacity-johnngo.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                // Change directory and build the project using Maven
                sh 'cd starter_code && mvn clean package'
            }
        }
        stage('Test') {
            steps {
                // Run tests
                sh 'cd starter_code && mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Deploy or archive build artifacts
                archiveArtifacts artifacts: 'starter_code/target/*.jar', allowEmptyArchive: true
            }
        }
    }
    post {
        success {
            echo 'Build and tests were successful!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}
