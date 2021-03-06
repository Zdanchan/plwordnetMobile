pipeline {
    agent any
    environment {
        registry = "plwordnetmobile-service"
        dockerImage = ''
	mysql_ip = 'localhost'
    }
    stages {
        stage('Build'){
            agent {
                docker {
                    image 'gradle:4.6-jdk8'
                }
            }
            steps {
		script {
		    sh "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' plwordnetmobile-mysql > mysql_ip"
		    mysql_ip =readFile('mysql_ip').trim()
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
        stage('Remove unused docker image') {
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
		    sh "docker run -d -t --network=plwordnetmobiledocker_default --name plwordnetmobile-service -p 8080:8080 $registry:latest"
		}
	    }
	}
    }
}