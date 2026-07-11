# https://ziohttp.com/guides/implementing-mutual-tls/
echo -e '==== LOKIT CERT GEN TOOL ====\n'

./scripts/generate-ca.sh
./scripts/generate-server.sh
./scripts/generate-client.sh
./scripts/generate-keystores.sh
./scripts/generate-truststores.sh
./scripts/install-stores.sh

echo -e '\nALL DONE :)'
