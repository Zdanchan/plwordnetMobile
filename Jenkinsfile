pipeline {
    agent any
    environment {
        registry = "plwordnetmobile-service"
        dockerImage = ''
	mysql_ip = 'localhost'
    }
    stages {
        stage('build'){
            agent {
                docker {
                    image 'gradle:4.6-jdk8'
                }
            }
            steps {
		script {
		    mysql_ip = sh "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' plwordnetmobile-mysql"
		    replace_regex = 's/localhost/' + mysql_ip + '/g'
		    sh "sed -i $replace_regex plwordnetMobileService/src/main/resources/application.properties"
                    sh 'gradle -p plwordnetMobileService/ clean build'
                    stash includes: 'plwordnetMobileService/build/libs/plwordnetmobile-service.jar', name: 'targetfiles'
		}
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
		    sh "docker stop $registry || true"
		    sh "docker rm -f $registry || true"
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
		    sh "docker rmi -f $registry-backup:latest || true"
                    dockerImageBackup = docker.build registry + "-backup:latest"
                }
            }
        }
    }
}