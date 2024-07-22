pipeline {
    agent any
    tools {
        maven 'Maven 3.9.8'  // Make sure this version is configured in Jenkins
    }
    environment {
        MAVEN_HOME = tool 'Maven 3.9.8'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/ToanNgo2709/java-web-dev-pro4-udacity-johnngo.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                dir('starter_code') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                dir('starter_code') {
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy') {
            steps {
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