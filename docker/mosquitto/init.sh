#!/bin/sh
set -e

CONFIG=/mosquitto/data/dynamic-security.json

if [ -f "$CONFIG" ]; then
  echo "Dynamic security already initialized, skipping."
else
  ADMIN_USERNAME=${LOKIT_MOSQUITTO_ADMIN_USERNAME:-admin}
  ADMIN_PASSWORD=${LOKIT_MOSQUITTO_ADMIN_PASSWORD:-admin}

  mosquitto_ctrl dynsec init "$CONFIG" "$ADMIN_USERNAME" "$ADMIN_PASSWORD"
fi