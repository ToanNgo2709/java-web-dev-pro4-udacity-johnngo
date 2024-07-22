pipeline {
    agent any
    environment {
        MAVEN_VERSION = '3.9.8'
        MAVEN_HOME = "/opt/maven"
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Diagnostic') {
            steps {
                sh 'pwd'
                sh 'ls -la'
                sh 'git --version'
                sh 'which git'
                sh 'env'
                sh 'which curl || echo "curl not found"'
                sh 'which wget || echo "wget not found"'
            }
        }

        stage('Checkout') {
            steps {
                deleteDir()
                git url: 'https://github.com/ToanNgo2709/java-web-dev-pro4-udacity-johnngo.git', branch: 'main'
            }
        }

        stage('Clean Maven Repo') {
            steps {
                sh 'rm -rf /root/.m2/repository'
            }
        }

        stage('Install Maven') {
            steps {
                sh """
                # Remove existing Maven installation if it exists
                rm -rf ${MAVEN_HOME}

                # Download and extract Maven
                curl -fsSL https://archive.apache.org/dist/maven/maven-3/\${MAVEN_VERSION}/binaries/apache-maven-\${MAVEN_VERSION}-bin.tar.gz | tar xzf - -C /opt

                # Rename the extracted directory to MAVEN_HOME
                mv /opt/apache-maven-\${MAVEN_VERSION} ${MAVEN_HOME}

                # Verify Maven installation
                ${MAVEN_HOME}/bin/mvn --version
                """
            }
        }

        stage('Debug POM') {
            steps {
                dir('starter_code') {
                    sh '''
                    if command -v curl >/dev/null 2>&1; then
                        curl -s https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.5.5/spring-boot-starter-parent-2.5.5.pom -o test.pom
                    elif command -v wget >/dev/null 2>&1; then
                        wget -q https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.5.5/spring-boot-starter-parent-2.5.5.pom -O test.pom
                    else
                        echo "Neither curl nor wget is available. Cannot download POM."
                        exit 1
                    fi
                    cat test.pom
                    cat pom.xml
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                dir('starter_code') {
                    sh "${MAVEN_HOME}/bin/mvn clean package -X"
                }
            }
        }

        stage('Test') {
            steps {
                dir('starter_code') {
                    sh "${MAVEN_HOME}/bin/mvn test"
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
        always {
            echo 'Cleaning up workspace'
            deleteDir()
        }
    }
}