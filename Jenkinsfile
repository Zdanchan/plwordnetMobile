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
                    label 'plwordnetmobile-jenkins-builder'
                }
            }
            steps {
                sh 'gradle clean build'
                stash includes: 'target/*.jar', name: 'targetfiles'
            }     
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:jdk8'
                    label 'plwordnetmobile-jenkins-tester'
                }
            }
            steps {
                sh 'gradle test'
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