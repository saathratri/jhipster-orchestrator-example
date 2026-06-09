# Before running this script, install ttab by running the command: npm -g install ttab
# Since Saathratri Orchestrator has a startup/scheduled task that depends on other microservices like Organizations Service,
# we start/deploy the orchestrator separately once all other services have been deployed.

CURRENT_FOLDER="$PWD"

cd orchestratorgateway

echo "Deploying Authorization Server..."
docker compose -f src/main/docker/keycloak.yml up -d

echo "Deploying Registry..."
docker compose -f src/main/docker/jhipster-registry.yml up -d

cd ..

sh deploy-saathratri-mac-dev-postgresql.sh

sh deploy-saathratri-mac-dev-cassandra.sh

echo "Deploying Saathratri Gateway..."
ttab -t orchestratorgateway -d "$CURRENT_FOLDER/orchestratorgateway" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8001"

echo "Check opened tabs to see deployment status of Saathratri Gateway and all its microservices."
