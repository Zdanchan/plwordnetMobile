echo "[INFO] stopping all current docker plwordnetMobile containers"
./stop_all.sh
echo "[INFO] deleting all current docker plwordnetMobile containers and images"
./remove_all.sh

echo "[INFO] starting docker-compose script"
docker-compose up --build