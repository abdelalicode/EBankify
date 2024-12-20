pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        DOCKER_IMAGE = 'ebankify'
        DOCKER_TAG = "${BUILD_NUMBER}"
        SONAR_TOKEN = credentials('sonar-token')
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    deleteDir()
                    echo "Cloning Git repository..."
                    sh '''
                        git clone -b main https://github.com/abdelalicode/EBankify .
                        echo "Repository cloned successfully."
                    '''
                }
            }
        }

        stage('Environment Check') {
            steps {
                sh '''
                    echo "Git version:"
                    git --version
                    echo "Current Git branch:"
                    git branch --show-current
                    echo "Git status:"
                    git status
                    echo "Java version:"
                    java -version
                    echo "Maven version:"
                    mvn --version
                    echo "Working directory contents:"
                    pwd
                    ls -la
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    mvn clean package -DskipTests --batch-mode --errors
                '''
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test --batch-mode'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                            execPattern: '**/target/*.exec',
                            classPattern: '**/target/classes',
                            sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=ebankify \
                        -Dsonar.projectName=Ebankify \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }



        stage('Deploy') {
            steps {
                script {
                    docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").run('-p 8082:8080')
                }
            }
        }

    }

    post {
        always {
            cleanWs()
        }
        success {
            script {
                emailext(
                        subject: "Pipeline Successful: ${currentBuild.fullDisplayName}",
                        body: """
                        Pipeline execution completed successfully!
                        
                        Job: ${env.JOB_NAME}
                        Build Number: ${env.BUILD_NUMBER}
                        Build URL: ${env.BUILD_URL}
                        
                        Changes:
                        ${currentBuild.changeSets.collect { cs -> cs.items.collect { entry -> "- ${entry.author}: ${entry.msg}" }.join('\n') }.join('\n')}
                    """,
                        to: "abdeltahaali@gmail.com",
                        attachLog: true
                )
            }
            echo 'Pipeline executed successfully!'
        }
        failure {
            script {
                emailext(
                        subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
                        body: """
                        Pipeline execution failed!
                        
                        Job: ${env.JOB_NAME}
                        Build Number: ${env.BUILD_NUMBER}
                        Build URL: ${env.BUILD_URL}
                        
                        Please check the build logs for more details.
                    """,
                        to: "baihmohamedamine@gmail.com",
                        attachLog: true
                )
            }
            echo 'Pipeline execution failed!'
        }
    }
}