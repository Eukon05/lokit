# https://ziohttp.com/guides/implementing-mutual-tls/
echo -e '==== LOKIT CERT GEN TOOL ====\n'

WEB_CERT_ONLY=false;

while getopts "w" opt; do
  case $opt in
    w) echo "Only web cert will be generated!"; WEB_CERT_ONLY=true;;
    :) echo "Parameters are missing their values!"; exit 1;;
    *) echo "Unknown option -$OPTARG";;
  esac
done

if [ ! "$WEB_CERT_ONLY" ]; then
  ./scripts/generate-ca.sh
  ./scripts/generate-server.sh
  ./scripts/generate-client.sh
  ./scripts/generate-keystores.sh
  ./scripts/generate-truststores.sh
fi

./scripts/generate-web.sh "$@"
echo -e '\nALL DONE :)'
