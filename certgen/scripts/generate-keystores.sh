STOREPASS="changeit"
CA_CERT="output/ca/ca-cert.pem"

echo -e "GENERATING KEYSTORES"
echo -e "Keystore password = $STOREPASS\n"

for x in role room card device
do
  echo -e "Generating $x server keystore..."
  rm -f "output/server/$x/$x-server-keystore.p12"

  openssl pkcs12 -export \
    -in "output/server/$x/$x-server-cert.pem" \
    -inkey "output/server/$x/$x-server-key.pem" \
    -out "output/server/$x/$x-server-keystore.p12" \
    -name "$x-server" \
    -password pass:$STOREPASS \
    -certfile "$CA_CERT"
done

for x in room device decision
do
  echo -e "Generating $x client keystore..."
  rm -f "output/client/$x/$x-client-keystore.p12"

  openssl pkcs12 -export \
    -in "output/client/$x/$x-client-cert.pem" \
    -inkey "output/client/$x/$x-client-key.pem" \
    -out "output/client/$x/$x-client-keystore.p12" \
    -name "$x-client" \
    -password pass:$STOREPASS \
    -certfile "$CA_CERT"
done

echo -e "\nALL KEYSTORES DONE!"
