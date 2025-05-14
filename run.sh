#!/bin/bash

# get services from command line arguments
SERVICES=${@:-""}

ENV_FILE=${ENV_FILE:=./env/.env}

docker network inspect reverse-proxy-network &>/dev/null || docker network create reverse-proxy-network

# echo back commands
set -x

docker-compose --env-file $ENV_FILE up --build --force-recreate --remove-orphans $SERVICES

docker image prune -f &>/dev/null
