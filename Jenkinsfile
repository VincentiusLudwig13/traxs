pipeline {
    agent any

    tools {
        maven 'default'
        dockerTool 'default'
    }

    environment {
        DOCKER_IMAGE = "luviana/traxs"
        DOCKER_TAG = "latest"
    }

    stages {

        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-creds') {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    string(credentialsId: 'db-url', variable: 'DB_URL'),
                    usernamePassword(credentialsId: 'db-creds', usernameVariable: 'DB_USERNAME', passwordVariable: 'DB_PASSWORD')
                ]) {
                    sh """
                    docker stop traxs || true
                    docker rm traxs || true

                    docker run -d \
                      -p 8086:8086 \
                      --name traxs \
                      -e DB_URL=$DB_URL \
                      -e DB_USERNAME=$DB_USERNAME \
                      -e DB_PASSWORD=$DB_PASSWORD \
                      -e SPRING_PROFILES_ACTIVE=prod \
                      ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                }
            }
        }
    }
}