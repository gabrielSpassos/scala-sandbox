#!/usr/bin/env bash
set -e

echo "🧹 Stopping and removing containers..."
docker compose -f docker-compose.yml down --remove-orphans

echo "🧹 Removing named volumes..."
docker volume rm -f scala-bitonic-poc_scala_bitonic_postgres_data || true
docker volume rm -f scala-bitonic-poc_scala_bitonic_redis_data || true

echo "🧹 Removing custom network..."
docker network rm scala-bitonic-poc_scala-bitonic-poc || true

echo "✅ Cleanup complete."