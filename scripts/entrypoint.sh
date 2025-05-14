#!/bin/bash
set -e

# Print environment information
echo "Starting Task Manager application with profile: $SPRING_PROFILES_ACTIVE"
echo "Server port: $SERVER_PORT"
echo "Context path: $SERVER_SERVLET_CONTEXT_PATH"

db_host=${POSTGRES_HOST:-db}
db_port=${POSTGRES_PORT:-5432}
db_user=${POSTGRES_USER:-postgres}

# Wait for PostgreSQL to be ready
max_retries=30
retry=0
until PGPASSWORD="$POSTGRES_PASSWORD" psql -h "$db_host" -U "$db_user" -p "$db_port" -c '\q' 2>/dev/null; do
    retry=$((retry + 1))
    if [ $retry -ge $max_retries ]; then
        echo "PostgreSQL is still not available after $max_retries attempts. Exiting."
        exit 1
    fi
    echo "Waiting for PostgreSQL at $db_host:$db_port... ($retry/$max_retries)"
    sleep 2
done

echo "PostgreSQL is up!"

# Execute the CMD
exec "$@"
