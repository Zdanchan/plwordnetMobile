containers=$(docker ps -a | grep plwordnetmobile)

if [ containers!='' ]; then
	echo "[INFO] stopping plwordnetmobile containers"
	docker ps -a | grep plwordnetmobile | awk '{print $1}' | while read -r line; do docker stop $line; done
fi