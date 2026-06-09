#!/usr/bin/env bash
# =============================================================================
# run-all-tests.sh — reliable one-shot full test run for the ORCHESTRATOR example
#
# The orchestrator example is the merged union of the two upstream blueprint examples
# (generated from saathratri-apps-orchestrator-mf.jdl). Five apps under one gateway:
#   orchestratorgateway (gateway, :8080, microfrontend shell)
#   psqlblog   (SQL/pgvector microservice, :8081; PsqlBlog/Post/Tag/TajUser)
#   psqlstore  (SQL microservice,          :8082; PsqlProduct/Report)
#   cassandrablog  (Cassandra microservice, :8083; 12 entities incl. composite/TimeUUID)
#   cassandrastore (Cassandra microservice, :8084; CassProduct/Report)
#
# Plus four DTO Maven modules (the orchestrator's modularization): psqlblogdto, psqlstoredto,
# cassandrablogdto, cassandrastoredto — the services depend on these JARs, so they are
# `mvn install`-ed FIRST.
#
# Runs, in order, for all apps:
#   1. (optional) regen from the blueprint                      --regen
#   2. mvn install the 4 DTO modules (services depend on them)
#   3. npm install per app when node_modules is missing (NON-monorepo: per-app deps)
#   4. backend unit + integration (mvnw verify; Postgres / Cassandra Testcontainers)
#   5. frontend (vitest, ng test)
#   6. webapp build into target/classes/static (required for e2e index.html)
#   7. bring up the dev docker stack (Keycloak -> Registry -> Postgres x3 + Cassandra x2)
#   8. launch the 5 backends once, wait for a REAL readiness gate
#   9. e2e (Cypress) per app, judged by EXIT CODE (no retry)
#  10. tear down the backends (Docker stack left running)
#
# Reliability notes (shared with the base examples' harnesses):
#   - Free 8080-8084 and BLOCK until clear before every backend launch.
#   - Self-contained infra with a REAL readiness gate: the JHipster Registry does eager OIDC
#     discovery against Keycloak at boot and exits(1) if Keycloak isn't serving yet, so we gate
#     Keycloak's OIDC endpoint BEFORE starting the Registry, then gate the Registry before backends.
#   - e2e pass/fail = cypress EXIT CODE, never a log grep.
#
# Usage:
#   sh run-all-tests.sh                 # full run, no regen (apps already generated)
#   sh run-all-tests.sh --regen         # regen (prepare + jhipster jdl --force) + full run
#   sh run-all-tests.sh --skip-backend --skip-frontend   # e2e only
#   sh run-all-tests.sh --no-e2e        # backend + frontend only
#
# Requires: Docker daemon running, JDK 21, Node 22+, the orchestrator blueprint npm-linked
# (and its two base blueprints, for --regen).
# =============================================================================
set -u

APPS="orchestratorgateway psqlblog psqlstore cassandrablog cassandrastore"
REMOTES="psqlblog psqlstore cassandrablog cassandrastore"
DTOS="psqlblogdto psqlstoredto cassandrablogdto cassandrastoredto"
declare -A PORT=( [orchestratorgateway]=8080 [psqlblog]=8081 [psqlstore]=8082 [cassandrablog]=8083 [cassandrastore]=8084 )

export NODE_OPTIONS=--use-system-ca
export MAVEN_OPTS="-Djavax.net.ssl.trustStoreType=Windows-ROOT"

ROOT="$(cd "$(dirname "$0")" && pwd)"
LOG="$ROOT/.test-logs"; mkdir -p "$LOG"
cd "$ROOT"

REGEN=0; SKIP_BE=0; SKIP_FE=0; DO_E2E=1
for a in "$@"; do case "$a" in
  --regen) REGEN=1;; --skip-backend) SKIP_BE=1;; --skip-frontend) SKIP_FE=1;;
  --no-e2e) DO_E2E=0;; *) echo "unknown arg: $a"; exit 2;; esac; done

declare -A R_BE_UNIT R_BE_IT R_FE R_E2E
FAIL=0
hr(){ printf '%.0s─' $(seq 1 70); echo; }
say(){ echo; hr; echo ">>> $*"; hr; }
http(){ curl -s -o /dev/null -w '%{http_code}' --max-time 6 "$1" 2>/dev/null; }

kill_backends() {
  powershell.exe -NoProfile -Command "
    Get-CimInstance Win32_Process -Filter \"Name='java.exe'\" |
      Where-Object { \$_.CommandLine -match 'jhipster-orchestrator' -or \$_.CommandLine -match '(Orchestratorgateway|Psqlblog|Psqlstore|Cassandrablog|Cassandrastore)App' } |
      ForEach-Object { try { Stop-Process -Id \$_.ProcessId -Force -ErrorAction Stop } catch {} }
    foreach (\$p in 8080,8081,8082,8083,8084) {
      Get-NetTCPConnection -State Listen -LocalPort \$p -ErrorAction SilentlyContinue |
        ForEach-Object { try { Stop-Process -Id \$_.OwningProcess -Force -ErrorAction Stop } catch {} }
    }" >/dev/null 2>&1 || true
}

wait_ports_free() {
  for i in $(seq 1 30); do
    busy=$(powershell.exe -NoProfile -Command "(Get-NetTCPConnection -State Listen -LocalPort 8080,8081,8082,8083,8084 -ErrorAction SilentlyContinue | Measure-Object).Count" 2>/dev/null | tr -dc '0-9')
    [ "${busy:-0}" = "0" ] && return 0
    sleep 2
  done
  echo "  ⚠ ports still busy after wait: ${busy:-?} listener(s)"; return 1
}

ensure_npm() {
  # NON-monorepo: each app carries its own node_modules.
  for app in $APPS; do
    if [ ! -d "$app/node_modules" ]; then
      say "npm install — $app (node_modules missing)"
      ( cd "$app" && npm install ) > "$LOG/npm-install-$app.log" 2>&1 || { echo "  ✖ $app npm install FAILED (see $LOG/npm-install-$app.log)"; FAIL=1; }
    fi
  done
}

# ---- 0. clean slate -------------------------------------------------------
say "Clean slate: stopping any running example backends + freeing ports"
kill_backends
wait_ports_free || { echo "  could not free 8080-8084 — aborting"; exit 1; }
echo "  ports 8080-8084 free"

# ---- 1. regen (optional) --------------------------------------------------
if [ "$REGEN" = 1 ]; then
  say "Regenerating from blueprint (orchestrator: prepare + jhipster jdl --force)"
  sh saathratri-generate-code-dev-orchestrator-mf.sh > "$LOG/regen.log" 2>&1
  if ! grep -qa "execution is complete" "$LOG/regen.log"; then
    echo "REGEN FAILED — see $LOG/regen.log"; tail -20 "$LOG/regen.log"; exit 1
  fi
  echo "regen OK (code generation complete; npm install is handled below)"
fi

# ---- 2. DTO modules: mvn install (services depend on these JARs) -----------
if [ "$SKIP_BE" = 0 ]; then
  for dto in $DTOS; do
    say "DTO install: $dto"
    ( cd "$dto" && ./mvnw -ntp -q clean install -DskipTests ) > "$LOG/dto-$dto.log" 2>&1
    if [ $? -ne 0 ]; then echo "  ✖ $dto install FAILED (see $LOG/dto-$dto.log)"; FAIL=1; else echo "  ✔ $dto installed"; fi
  done
fi

# ---- 3. npm install per app if needed -------------------------------------
if [ "$SKIP_FE" = 0 ] || [ "$DO_E2E" = 1 ]; then ensure_npm; fi

# ---- 4. backend unit + IT -------------------------------------------------
if [ "$SKIP_BE" = 0 ]; then
  for app in psqlblog psqlstore cassandrastore cassandrablog orchestratorgateway; do
    say "Backend (unit + IT): $app"
    ( cd "$app" && ./mvnw -ntp -Dskip.npm clean verify ) > "$LOG/be-$app.log" 2>&1
    ok=$?
    mapfile -t totals < <(grep -aE "^\[INFO\] Tests run: [0-9]+, Failures: [0-9]+, Errors: [0-9]+, Skipped: [0-9]+$" "$LOG/be-$app.log")
    R_BE_UNIT[$app]="${totals[0]:-(none)}"
    R_BE_IT[$app]="${totals[1]:-(none)}"
    if [ $ok -ne 0 ] || ! grep -qa "BUILD SUCCESS" "$LOG/be-$app.log"; then
      echo "  ✖ $app backend FAILED (see $LOG/be-$app.log)"; FAIL=1
    else echo "  ✔ $app backend OK"; fi
  done
fi

# ---- 5. frontend (vitest) -------------------------------------------------
if [ "$SKIP_FE" = 0 ]; then
  for app in $APPS; do
    say "Frontend (vitest): $app"
    ( cd "$app" && npx ng test --watch=false ) > "$LOG/fe-$app.log" 2>&1
    ok=$?
    R_FE[$app]="$(sed -E 's/\x1b\[[0-9;]*m//g' "$LOG/fe-$app.log" | grep -aE 'Tests +[0-9]+ (passed|failed)' | tail -1 | sed 's/^[[:space:]]*//')"
    if [ $ok -ne 0 ]; then echo "  ✖ $app frontend FAILED (see $LOG/fe-$app.log)"; FAIL=1
    else echo "  ✔ $app frontend OK"; fi
  done
fi

# ---- 6-9. e2e -------------------------------------------------------------
if [ "$DO_E2E" = 1 ]; then
  say "Building webapps into target/classes/static (required for e2e)"
  for app in $APPS; do
    ( cd "$app" && npm run webapp:build ) > "$LOG/web-$app.log" 2>&1 || { echo "  ✖ $app webapp build FAILED"; FAIL=1; }
    [ -f "$app/target/classes/static/index.html" ] && echo "  ✔ $app index.html" || { echo "  ✖ $app index.html MISSING"; FAIL=1; }
  done

  # Order matters: the Registry is an OAuth2 client that does eager OIDC discovery against
  # Keycloak at boot and exits(1) if Keycloak isn't serving yet. Bring up Keycloak + the
  # databases, GATE on Keycloak's OIDC endpoint, then start the Registry and gate it.
  say "Bringing up Keycloak + databases (Postgres x3 + Cassandra x2)"
  ( cd orchestratorgateway && docker compose -f src/main/docker/keycloak.yml up -d ) > "$LOG/docker-keycloak.log" 2>&1 || { echo "  ✖ keycloak up failed"; FAIL=1; }
  for app in $APPS; do
    ( cd "$app" && npm run docker:db:up ) > "$LOG/docker-db-$app.log" 2>&1 || { echo "  ✖ $app db up failed"; FAIL=1; }
  done

  printf "  waiting Keycloak OIDC (:9080) "
  oidc="http://localhost:9080/realms/jhipster/.well-known/openid-configuration"
  for i in $(seq 1 60); do [ "$(http "$oidc")" = 200 ] && break; sleep 3; printf '.'; done
  kc=$(http "$oidc"); echo " $kc"
  [ "$kc" = 200 ] || { echo "  ✖ Keycloak never served OIDC — registry would crash; aborting e2e"; FAIL=1; DO_E2E=0; }
fi

if [ "$DO_E2E" = 1 ]; then
  ( cd orchestratorgateway && docker compose -f src/main/docker/jhipster-registry.yml up -d ) > "$LOG/docker-registry.log" 2>&1 || { echo "  ✖ registry up failed"; FAIL=1; }
  printf "  waiting JHipster Registry (:8761) "
  for i in $(seq 1 60); do c=$(http http://localhost:8761/management/health); case "$c" in 200|401) break;; esac; sleep 3; printf '.'; done
  rg=$(http http://localhost:8761/management/health); echo " $rg"
  case "$rg" in 200|401) ;; *) echo "  ✖ Registry not live on :8761 ($rg) — remotes can't register; aborting e2e"; FAIL=1; DO_E2E=0;; esac
fi

if [ "$DO_E2E" = 1 ]; then
  say "Ensuring ports free before launching backends"
  kill_backends; wait_ports_free || { echo "  ports busy — aborting e2e"; FAIL=1; }

  say "Launching 5 backends (single job)"
  for app in $APPS; do
    ( cd "$app" && ./mvnw -ntp -Dskip.npm spring-boot:run -Dspring-boot.run.profiles=dev > "$LOG/run-$app.log" 2>&1 ) &
  done

  say "Readiness gate: app health (808x) + gateway->remote routes"
  for app in $APPS; do
    p=${PORT[$app]}
    printf "  waiting %s health :%s " "$app" "$p"
    for i in $(seq 1 90); do [ "$(http http://localhost:$p/management/health)" = 200 ] && break; sleep 5; printf '.'; done
    code=$(http http://localhost:$p/management/health)
    echo " $code"; [ "$code" = 200 ] || { echo "  ✖ $app never became healthy"; FAIL=1; }
  done
  printf "  gateway root (index.html): "; echo "$(http http://localhost:8080/)"
  for app in $REMOTES; do
    probe="http://localhost:8080/services/$app/management/health"
    printf "  waiting gateway->%s route " "$app"
    for i in $(seq 1 60); do c=$(http "$probe"); case "$c" in 200|301|302|401) break;; esac; sleep 5; printf '.'; done
    c=$(http "$probe"); echo " $c"
    case "$c" in 200|301|302|401) ;; *) echo "  ✖ gateway->$app route not live ($c)"; FAIL=1;; esac
  done
  echo "  letting federation settle 20s"; sleep 20

  for app in $APPS; do
    say "E2E (Cypress): $app"
    ( cd "$app" && npm run e2e:cypress ) > "$LOG/e2e-$app.log" 2>&1
    rc=$?
    summary="$(grep -aE 'All specs passed|[0-9]+ of [0-9]+ failed' "$LOG/e2e-$app.log" | tr -d '\033' | sed -E 's/\x1b\[[0-9;]*m//g; s/^[[:space:]]*//' | tail -1)"
    if [ $rc -eq 0 ]; then
      R_E2E[$app]="PASS — ${summary:-(no summary line)}"; echo "  ✔ $app e2e passed"
    else
      R_E2E[$app]="FAIL(rc=$rc) — ${summary:-(no cypress summary; see log)}"; echo "  ✖ $app e2e FAILED (rc=$rc, see $LOG/e2e-$app.log)"; FAIL=1
    fi
  done

  say "Tearing down backends (Docker stack left running)"
  kill_backends
fi

# ---- report ---------------------------------------------------------------
say "REPORT"
printf "%-20s | %-34s | %-34s | %-26s | %s\n" "APP" "BACKEND unit" "BACKEND IT" "FRONTEND" "E2E"
for app in $APPS; do
  printf "%-20s | %-34s | %-34s | %-26s | %s\n" "$app" \
    "${R_BE_UNIT[$app]:-skipped}" "${R_BE_IT[$app]:-skipped}" "${R_FE[$app]:-skipped}" "${R_E2E[$app]:-skipped}"
done
echo
if [ "$FAIL" = 0 ]; then echo "✅ ALL GREEN"; else echo "❌ FAILURES — inspect $LOG/*.log"; fi
echo "logs: $LOG"
exit $FAIL
