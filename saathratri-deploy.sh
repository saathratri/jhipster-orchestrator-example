# Deploy the full orchestrator example stack on macOS, each service in its own
# terminal tab. Requires ttab: `npm -g install ttab`. On Windows use
# .\saathratri-deploy.bat (same flow, Windows Terminal tabs).
#
# The three stale deploy-saathratri-mac-dev*.sh scripts this replaces referenced
# the main saathratri app's services, not this example's apps.

cd orchestratorgateway
# Keycloak must be healthy before the JHipster Registry starts (the registry's
# oauth2 profile resolves the OIDC issuer eagerly at boot and exits(1) if
# Keycloak isn't serving), so run them sequentially in one tab.
ttab "docker compose -f src/main/docker/keycloak.yml up -d --wait && docker compose -f src/main/docker/jhipster-registry.yml up -d"
cd ..

for app in orchestratorgateway psqlblog psqlstore cassandrablog cassandrastore; do
  cd "$app"
  npm run docker:db:up
  ttab -t "$app" -d "$PWD" ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
  cd ..
done

echo "Check the opened tabs for deployment status. Gateway: http://localhost:8080  Registry: http://localhost:8761"
