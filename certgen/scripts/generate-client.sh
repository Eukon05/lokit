mkdir -p output/client
cd output/client || exit
echo -e "GENERATING CLIENT CERTS\n"

for x in role room device decision
do
  service="$x-service"

  mkdir -p "$x"
  cd "$x" || exit
  echo -e "Generating $service client private key..."
  openssl genrsa -out "$x-client-key.pem" 4096

  echo -e "Generating $service client CSR..."
  openssl req -new -key "$x-client-key.pem" -out "$x-client.csr" -subj "/CN=$service-client"

  cat > "$x-client.ext" <<EOF
basicConstraints=CA:FALSE
keyUsage=critical,digitalSignature,keyEncipherment
extendedKeyUsage=clientAuth
EOF

  echo -e "Signing $service client certificate..."
  openssl x509 -req -days 365 -in "$x-client.csr" \
    -CA ../../ca/ca-cert.pem \
    -CAkey ../../ca/ca-key.pem \
    -CAcreateserial \
    -out "$x-client-cert.pem" \
    -extfile "$x-client.ext"

  echo -e "$service CLIENT CERT DONE!\n"
  cd ..
done

echo -e "ALL CLIENT CERTS DONE!"

