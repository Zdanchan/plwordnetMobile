sed -i "s/MY_TOKEN/$1/g" 'trigger_buid.sh'
sed -i "s/MY_TOKEN/$1/g" 'docker-jenkins/plwordnetmobile-service.xml'
sed -i "s/MY_TOKEN/$1/g" '../scm_scanner'