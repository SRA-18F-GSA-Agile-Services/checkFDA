#!/bin/bash
set -e

echo "Configuring Search-FDA..."

# Grab the template and then run it thru the environment variable replacer, and save it.

envsubst < /tmp/ServerConfig-template.groovy > /usr/local/tomcat/webapps/ServerConfig.groovy

exec "$@"