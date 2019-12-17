OLD="MY_TOKEN"

if [! -z "$1" ]
then

	if [ -z "$2" ]
	then
      		OLD=$2
	fi

	echo "[INFO] Replacing old token $OLD with new $1" 

	sed -i "s/$OLD/$1/g" 'trigger_buid.sh'
	sed -i "s/$OLD/$1/g" 'docker-jenkins/plwordnetmobile-service.xml'
	sed -i "s/$OLD/$1/g" '../scm_scanner'
else
	echo "[ERROR] Token cannot be null"
fi