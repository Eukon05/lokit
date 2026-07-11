STOREPASS="changeit"
CA_CERT="output/ca/ca-cert.pem"

echo -e "GENERATING TRUSTSTORES"
echo -e "Truststore password = $STOREPASS"

for x in room device decision
do
  echo -e "\nGenerating $x client truststore..."
  rm -f "output/client/$x/$x-client-truststore.p12"

  keytool -importcert \
    -file "$CA_CERT" \
    -keystore "output/client/$x/$x-client-truststore.p12" \
    -storetype PKCS12 \
    -storepass "$STOREPASS" \
    -alias lokit-ca \
    -noprompt \
    -trustcacerts
done

for x in role room card device
do
  echo -e "\nGenerating $x server truststore..."
  rm -f "output/server/$x/$x-server-truststore.p12"

  keytool -importcert \
    -file "$CA_CERT" \
    -keystore "output/server/$x/$x-server-truststore.p12" \
    -storetype PKCS12 \
    -storepass "$STOREPASS" \
    -alias lokit-ca \
    -noprompt \
    -trustcacerts
done

echo -e "\nALL TRUSTSTORES DONE!"
