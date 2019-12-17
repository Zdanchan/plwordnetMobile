#JENKINS_URL=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' plwordnetmobile-jenkins)
JENKINS_URL="localhost"
JENKINS_PORT=$(docker port plwordnetmobile-jenkins | grep "8080" | sed 's/.*://')
echo "sending build request on http://$JENKINS_URL:$JENKINS_PORT/job/plwordnetmobile-service/build"
curl --silent -I -u admin:admin http://$JENKINS_URL:$JENKINS_PORT/job/plwordnetmobile-service/build?token=MY_TOKEN