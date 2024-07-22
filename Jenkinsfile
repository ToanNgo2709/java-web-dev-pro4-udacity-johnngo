pipeline {
    agent any
    stages {
         stage('Install Maven') {
            steps {
                // Install Maven
                sh '''
                MAVEN_VERSION=3.9.8
                curl -O https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
                tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz
                sudo mv apache-maven-${MAVEN_VERSION} /usr/local/apache-maven
                export M2_HOME=/usr/local/apache-maven
                export PATH=$M2_HOME/bin:$PATH
                '''
            }
        }

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
