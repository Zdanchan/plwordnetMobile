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
                    image 'gradle:4.6-jdk8'
                }
            }
            steps {
                sh 'gradle -p plwordnetMobileService/ clean build'
                stash includes: '/build/libs/*.jar', name: 'targetfiles'
            }     
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:4.6-jdk8'
                }
            }
            steps {
                cd 'plwordnetMobileService'
                sh 'gradle -p plwordnetMobileService/ test'
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