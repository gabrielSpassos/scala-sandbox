#!/usr/bin/env bash

set -e

COMPOSE_FILE="docker-compose.yml"
NETWORK_NAME="kafka-net"

echo "🛑 Stopping Kafka Compose Stack..."
docker compose -f $COMPOSE_FILE down --remove-orphans

echo "🧹 Removing containers..."
docker rm -f zookeeper kafka kafdrop 2>/dev/null || true

echo "🧼 Removing custom Docker network ($NETWORK_NAME)..."
docker network rm $NETWORK_NAME 2>/dev/null || true

echo "💾 Removing dangling volumes..."
docker volume prune -f

echo "✅ Cleanup complete!"
