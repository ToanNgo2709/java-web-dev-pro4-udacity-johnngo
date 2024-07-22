pipeline {
    agent any
        environment {
            MAVEN_VERSION = '3.9.8'
            MAVEN_HOME = "/opt/maven"
            PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        }
    stages {
        stage('Checkout') {
            steps {
                // Clean workspace before cloning
                deleteDir()
                // Clone the repository
                git url: 'https://github.com/ToanNgo2709/java-web-dev-pro4-udacity-johnngo.git', branch: 'main'
            }
        }
        stage('Install Maven') {
            steps {
                sh """
                curl -fsSL https://archive.apache.org/dist/maven/maven-3/\${MAVEN_VERSION}/binaries/apache-maven-\${MAVEN_VERSION}-bin.tar.gz | tar xzf - -C /opt
                mv /opt/apache-maven-\${MAVEN_VERSION} ${MAVEN_HOME}
                """
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