# Before running this script, install ttab by running the command: npm -g install ttab
# Since Saathratri Orchestrator has a startup/scheduled task that depends on other microservices like Organizations Service,
# we start/deploy the orchestrator separately once all other services have been deployed.

CURRENT_FOLDER="$PWD"

echo "Deploying Cassandrablog Service..."
ttab -t cassandrablog -d "$CURRENT_FOLDER/cassandrablog" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,no-liquibase -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8008"

echo "Deploying TAJ Vote Service..."
ttab -t cassandrastore -d "$CURRENT_FOLDER/cassandrastore" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,no-liquibase -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8004"

echo "Check opened tabs to see deployment status of Cassandra microservices."
