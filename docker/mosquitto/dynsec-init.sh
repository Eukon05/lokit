#!/bin/sh
set -e

ADMIN_USERNAME=${LOKIT_MOSQUITTO_ADMIN_USERNAME:-admin}
ADMIN_PASSWORD=${LOKIT_MOSQUITTO_ADMIN_PASSWORD:-admin}

SERVICE_CLIENT_ID=${LOKIT_DEVICE_MQTT_CLIENT_ID:-device}
SERVICE_USERNAME=${LOKIT_DEVICE_MQTT_CLIENT_ID:-device}
SERVICE_PASSWORD=${LOKIT_DEVICE_MQTT_PASSWORD:-device}

INIT_CHECK="$(mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec listClients 2> /dev/null | grep -o "$SERVICE_USERNAME" | wc -w)"

if [ "$INIT_CHECK" = "1" ]; then
  echo "Dynamic security already configured, skipping."
else
  #Create device-service client
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec createClient "$SERVICE_USERNAME" -i "$SERVICE_CLIENT_ID"
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec setClientPassword "$SERVICE_USERNAME" "$SERVICE_PASSWORD"

  #Create dynsec-admin role
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec createRole dynsec-admin
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin publishClientSend '$CONTROL/dynamic-security/#' allow -1
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin publishClientReceive '$CONTROL/dynamic-security/#' allow -1
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin subscribeLiteral '$CONTROL/dynamic-security/#' allow -1
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin subscribePattern '$CONTROL/dynamic-security/#' allow -1
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin unsubscribeLiteral '$CONTROL/dynamic-security/#' allow -1
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL dynsec-admin unsubscribePattern '$CONTROL/dynamic-security/#' allow -1

  #Assign dynsec-admin to device-service
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addClientRole "$SERVICE_USERNAME" dynsec-admin 5

  #Create lokit-device role
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec createRole lokit-device
  mosquitto_ctrl -u "$ADMIN_USERNAME" -P "$ADMIN_PASSWORD" dynsec addRoleACL lokit-device publishClientSend 'lokit/devices/heartbeat' allow -1

  echo "Dynamic security setup complete"
fi