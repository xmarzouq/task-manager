#!/bin/bash

# list of backend services to run
SERVICES=(
    "traefik"
    "backend"
    "db"
)

# feed services to run.sh
./run.sh ${SERVICES[@]}
