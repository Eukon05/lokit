mkdir -p output/web
cd output/web || exit
echo -e "GENERATING NGINX AND KEYCLOAK CERTS\n"

DNS_NAMES=()
IP_ADDRESSES=()

while getopts ":q::i:w" opt; do
  case $opt in
    q) for i in $OPTARG; do DNS_NAMES+=("$i"); done ;;
    i) for i in $OPTARG; do IP_ADDRESSES+=("$i"); done;;
    w) ;;
    :) echo "Parameters are missing their values!"; exit 1;;
    *) echo "Unknown option -$OPTARG";;
  esac
done

if (( !${#DNS_NAMES[@]} )); then
  DNS_NAMES+=("localhost");
fi;

if (( !${#IP_ADDRESSES[@]} )); then
  for i in $(hostname -I); do IP_ADDRESSES+=("$i"); done;
fi;

SAN=""
for dns in "${DNS_NAMES[@]}"; do
  [ -n "$SAN" ] && SAN+=","
  SAN+="DNS:$dns"
done
for ip in "${IP_ADDRESSES[@]}"; do
  [ -n "$SAN" ] && SAN+=","
  SAN+="IP:$ip"
done

echo -e "Generating web server private key..."
openssl genrsa -out "web-server-key.pem" 4096

echo -e "Generating web server CSR..."
openssl req -new -noenc -key "web-server-key.pem" -out "web-server.csr" -subj "/CN=${DNS_NAMES[0]}"

cat > "web-server.ext" <<EOF
basicConstraints=CA:FALSE
keyUsage=critical,digitalSignature,keyEncipherment
extendedKeyUsage=serverAuth
subjectAltName=$SAN
EOF

echo -e "Signing web server certificate..."
openssl x509 -req -days 365 -in "web-server.csr" \
  -CA ../ca/ca-cert.pem \
  -CAkey ../ca/ca-key.pem \
  -CAcreateserial \
  -out "web-server-cert.pem" \
  -extfile "web-server.ext"

echo -e "WEB SERVER CERT DONE!\n"
