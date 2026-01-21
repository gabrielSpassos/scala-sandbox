#!/usr/bin/env bash
set -euo pipefail

COMPOSE_FILE="docker-compose.yml"

echo "🧹 Cleaning up Docker Compose resources..."

# Stop and remove containers, networks, volumes defined in the compose file
docker compose -f "$COMPOSE_FILE" down --volumes --remove-orphans

# Optionally, remove named volumes (in case they persist)
echo "🔍 Checking for leftover volume 'postgres_18_data'..."
if docker volume inspect postgres_18_data &>/dev/null; then
  echo "🗑️ Removing volume 'postgres_18_data'..."
  docker volume rm postgres_18_data || true
fi

# Optionally, remove network if it still exists
echo "🔍 Checking for leftover network 'database-migration-poc'..."
if docker network inspect database-migration-poc &>/dev/null; then
  echo "🗑️ Removing network 'database-migration-poc'..."
  docker network rm database-migration-poc || true
fi

echo "✅ Cleanup complete."
