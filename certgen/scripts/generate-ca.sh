mkdir -p output/ca
cd output/ca || exit

echo -e 'Generating CA private key...'
openssl genrsa -out ca-key.pem 4096

echo -e 'Generating CA certificate...'
openssl req -new -x509 -days 3650 -key ca-key.pem -out ca-cert.pem -subj "/CN=LokitCA" \
  -addext "basicConstraints=critical,CA:TRUE" \
  -addext "keyUsage=critical,keyCertSign,cRLSign"

echo -e 'CA CERT DONE!\n'
