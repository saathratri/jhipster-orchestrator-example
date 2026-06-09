#!/usr/bin/env bash
# Reset the generated orchestrator example back to its sources (JDL, scripts, docs).
# The current saathratri-apps-orchestrator-mf.jdl generates 5 apps + 4 DTO Maven modules
# (the merged union of the ai-postgresql and cassandra base examples).
set -u

APPS="orchestratorgateway psqlblog psqlstore cassandrablog cassandrastore"
DTOS="psqlblogdto psqlstoredto cassandrablogdto cassandrastoredto"

echo "Deleting generated apps + DTO modules..."
for d in $APPS $DTOS; do rm -rf "$d"; done

echo "Deleting root build/config artifacts..."
rm -rf node_modules .yo-repository .jhipster target docker-compose kubernetes
rm -f package.json package-lock.json .yo-rc.json .npmrc last-used-port.json last-used-ports.json

echo "Cleanup complete."
