echo -e "INSTALLING SERVER STORES\n"

echo -e "Removing old stores..."
rm -f *-service/src/main/resources/*.p12

for x in "room" "device" "card" "role"
do
  echo "Installing $x server stores..."
  cp -rf output/server/$x/*.p12 ../$x-service/src/main/resources
done

echo -e "\nINSTALLING CLIENT STORES\n"

for x in "room" "device" "decision"
do
  echo "Installing $x client stores..."
    cp -rf output/client/$x/*.p12 ../$x-service/src/main/resources
done

echo -e "\nALL STORES INSTALLED!"