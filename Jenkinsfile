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
                stash includes: 'plwordnetMobileService/build/libs/plwordnetmobile-service.jar', name: 'targetfiles'
            }     
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:4.6-jdk8'
                }
            }
            steps {
                sh 'gradle -p plwordnetMobileService/ test'
            }
        }
	stage('Stop old container') {
	    steps{
		script {
		    sh "docker stop $registry"
		    sh "docker rm -f $registry"
		}
	    }
	}
        stage('Remove Unused docker image') {
            steps{
                script {
                    sh "docker rmi -f $registry:latest"
                }
            }
        }
        stage('Building image') {
            steps{
                script {
                    unstash 'targetfiles'
                    sh 'ls -l -R'
                    dockerImage = docker.build registry + ":latest"
                }
            }
        }
	stage('Deploy container') {
	    steps{
	        script {
		    sh "docker run --name plwordnetmobile-service -p 8080:8080 $registry:latest"
		}
	    }
	}
        stage('Building stable backup image') {
            steps{
                script {
                    dockerImageBackup = docker.build registry + "-backup:latest"
                }
            }
        }
    }
}