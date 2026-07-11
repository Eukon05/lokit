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

echo -e "\nGenerating role server truststore..."
rm -f output/server/role/role-server-truststore.p12
for x in room decision
do
  keytool -importcert \
    -file "output/client/$x/$x-client-cert.pem" \
    -keystore output/server/role/role-server-truststore.p12 \
    -storetype PKCS12 \
    -storepass "$STOREPASS" \
    -alias "$x-client" \
    -noprompt \
    -trustcacerts
done

echo -e "\nGenerating room server truststore..."
rm -f output/server/room/room-server-truststore.p12
for x in device decision
do
  keytool -importcert \
    -file "output/client/$x/$x-client-cert.pem" \
    -keystore output/server/room/room-server-truststore.p12 \
    -storetype PKCS12 \
    -storepass "$STOREPASS" \
    -alias "$x-client" \
    -noprompt \
    -trustcacerts
done

echo -e "\nGenerating card server truststore..."
rm -f output/server/card/card-server-truststore.p12
keytool -importcert \
  -file output/client/decision/decision-client-cert.pem \
  -keystore output/server/card/card-server-truststore.p12 \
  -storetype PKCS12 \
  -storepass "$STOREPASS" \
  -alias decision-client \
  -noprompt \
  -trustcacerts

echo -e "\nGenerating device server truststore..."
rm -f output/server/device/device-server-truststore.p12
keytool -importcert \
  -file output/client/decision/decision-client-cert.pem \
  -keystore output/server/device/device-server-truststore.p12 \
  -storetype PKCS12 \
  -storepass "$STOREPASS" \
  -alias decision-client \
  -noprompt \
  -trustcacerts

echo -e "\nALL TRUSTSTORES DONE!"
