pipeline {
    agent any
    environment {
        registry = "plwordnetmobile-service"
        dockerImage = ''
    }
    stages {
        stage('build'){
            agent {
                docker {
                    image 'gradle:jdk8'
                }
            }
            steps {
                cd 'plwordnetMobileService'
                sh 'gradle clean build'
                stash includes: 'target/*.jar', name: 'targetfiles'
                cd '..'
            }     
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:jdk8'
                }
            }
            steps {
                cd 'plwordnetMobileService'
                sh 'gradle test'
                cd '..'
            }
        }
        stage('Remove Unused docker image') {
            steps{
                script {
                    sh "docker rmi -f $registry:$BUILD_NUMBER"
                }
            }
        }
        stage('Building image') {
            steps{
                script {
                    unstash 'targetfiles'
                    sh 'ls -l -R'
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy Image') {
            steps{
                script {
                    dockerImage.withRun('-p 8080:8080')
                }
            }
        }
    }
}