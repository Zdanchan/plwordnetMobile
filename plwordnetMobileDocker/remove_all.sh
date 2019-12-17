containers=$(docker ps -a | grep plwordnetmobile)
images=$(docker image list | grep plwordnetmobile)

if [ containers!='' ]; then
	echo "[WARN] removing plwordnetmobile containers"
	docker ps -a | grep plwordnetmobile | awk '{print $1}' | while read -r line; do docker rm $line; done
fi

if [ images!='' ]; then
	echo "[WARN] removing plwordnetmobile images"
	docker image list | grep plwordnetmobile | awk '{print $1}' | while read -r line; do docker rmi $line; done
fi