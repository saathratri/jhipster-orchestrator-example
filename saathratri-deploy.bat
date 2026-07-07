@echo off
REM Bring up the full orchestrator example stack, each service in its own
REM Windows Terminal tab (mirrors saathratri-deploy.sh, which uses ttab on macOS).
REM Requires Windows Terminal (wt.exe) -- installed by default on Windows 11.

cd orchestratorgateway
REM Keycloak must be healthy before the JHipster Registry starts (the registry
REM resolves the OIDC issuer eagerly at boot), so run them sequentially in one tab.
wt -w 0 new-tab --title "Keycloak + Registry" -d "%cd%" cmd /k "docker compose -f src\main\docker\keycloak.yml up -d --wait && docker compose -f src\main\docker\jhipster-registry.yml up -d"
call npm run docker:db:up
wt -w 0 new-tab --title "orchestratorgateway" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd psqlblog
call npm run docker:db:up
wt -w 0 new-tab --title "psqlblog" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd psqlstore
call npm run docker:db:up
wt -w 0 new-tab --title "psqlstore" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd cassandrablog
call npm run docker:db:up
wt -w 0 new-tab --title "cassandrablog" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd cassandrastore
call npm run docker:db:up
wt -w 0 new-tab --title "cassandrastore" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

echo Check the opened tabs for deployment status. Gateway: http://localhost:8080  Registry: http://localhost:8761
