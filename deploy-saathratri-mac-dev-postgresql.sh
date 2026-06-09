# Before running this script, install ttab by running the command: npm -g install ttab
# Since Saathratri Orchestrator has a startup/scheduled task that depends on other microservices like Organizations Service,
# we start/deploy the orchestrator separately once all other services have been deployed.

CURRENT_FOLDER="$PWD"

echo "Deploying Organizations Service..."
ttab -t psqlblog -d "$CURRENT_FOLDER/psqlblog" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8005"

echo "Deploying Saathratri Maintenance Service..."
ttab -t maintenanceservice -d "$CURRENT_FOLDER/psqlstore" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8007"

echo "Check opened tabs to see deployment status of Postgresql microservices."
