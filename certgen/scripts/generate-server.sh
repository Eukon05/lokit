mkdir -p output/server
cd output/server || exit
echo -e "GENERATING SERVER CERTS\n"

for x in room card device role
do
  service="$x-service"

  mkdir -p "$x"
  cd "$x" || exit
  echo -e "Generating $service server private key..."
  openssl genrsa -out "$x-server-key.pem" 4096

  echo -e "Generating $service server CSR..."
  openssl req -new -key "$x-server-key.pem" -out "$x-server.csr" -subj "/CN=$service"

  cat > "$x-server.ext" <<EOF
basicConstraints=CA:FALSE
keyUsage=critical,digitalSignature,keyEncipherment
extendedKeyUsage=serverAuth
subjectAltName=DNS:$service,DNS:localhost,IP:127.0.0.1
EOF

  echo -e "Signing $service server certificate..."
  openssl x509 -req -days 365 -in "$x-server.csr" \
    -CA ../../ca/ca-cert.pem \
    -CAkey ../../ca/ca-key.pem \
    -CAcreateserial \
    -out "$x-server-cert.pem" \
    -extfile "$x-server.ext"

  echo -e "$service SERVER CERT DONE!\n"
  cd ..
done

echo -e "ALL SERVER CERTS DONE!"
