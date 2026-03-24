pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "luviana/traxs-app"
        DOCKER_TAG = "latest"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
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
                sh """
                docker stop traxs-app || true
                docker rm traxs-app || true
                docker run -d -p 8086:8086 --name traxs-app ${DOCKER_IMAGE}:${DOCKER_TAG}
                """
            }
        }

        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}