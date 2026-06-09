# Testing & Debugging Guide — Saathratri Orchestrator Example (workspace)

How to test the **generated** Saathratri services in this workspace, and where each fix belongs.
This is the *example* side of the orchestrator blueprint: the apps here are generated output and are
overwritten on every regen. The blueprint's **own** unit tests live in the sibling generator repo —
see [`../generator-jhipster-orchestrator/TESTING.md`](../generator-jhipster-orchestrator/TESTING.md).

> **Companion docs:**
> [`generator-jhipster-cassandra/TESTING.md`](https://github.com/amarpatel-xx/generator-jhipster-cassandra/blob/main/TESTING.md)
> (composite-key backend + Angular catalogues) and
> [`generator-jhipster-ai-postgresql/TESTING.md`](https://github.com/amarpatel-xx/generator-jhipster-ai-postgresql/blob/main/TESTING.md)
> (SQL/pgvector). The generated SQL and Cassandra services are the same output those blueprints
> produce, so their bug patterns apply verbatim.

---

## 0. The one rule: fix templates, never the generated code

Every regen overwrites the generated services. Find the bug by running a generated service's tests,
then fix the **source template** and regenerate — never hand-edit the generated `*.java`/`*.ts`.
Where the template lives depends on which half of the blueprint produced the code:

| Generated code | Fix in |
| --- | --- |
| SQL service (`orchestratorgateway`, `psqlblog`, `psqlstore`) | `generator-jhipster-ai-postgresql` (`sql-*` templates) |
| Cassandra service (`cassandrablog`, `cassandrastore`) | `generator-jhipster-cassandra` (`cassandra-*` templates) |
| Orchestrator infra (Heroku artifacts, `…dto/` module, dev CORS/logback, pgvector changelogs, Keycloak service-account client, db dispatch) | the matching `*-orchestrator` submodule of `generator-jhipster-orchestrator` |

⚠️ Do **not** edit the orchestrator's `sql-*` / `cassandra-*` directories — the prepare step
overwrites them from the base repos on every regen.

---

## 1. Environment (once)

```bash
export NODE_OPTIONS=--use-system-ca                              # npm / ng / vitest / eslint
export MAVEN_OPTS="-Djavax.net.ssl.trustStoreType=Windows-ROOT"  # Windows; macOS: KeychainStore
```

Integration tests need a **running Docker daemon** (Testcontainers starts PostgreSQL/pgvector and
Cassandra 5). AI-search/embedding generation needs `OPENAI_API_KEY` at *runtime* only — tests pass
without it (AI search is simply disabled).

---

## 2. Regenerate the workspace (the tight loop)

```bash
sh saathratri-generate-code-dev-orchestrator.sh   # cleanup → assemble blueprint + run JDL → sync custom files
```

After editing a template in a base repo or an `*-orchestrator` submodule, re-run this, then re-run
the affected service's test layer below.

---

## 3. Layer 1 — the blueprint's own unit tests

These do **not** live here — run them in the generator repo:

```bash
cd ../generator-jhipster-orchestrator
NODE_OPTIONS=--use-system-ca npx vitest run        # 21 files / 45 tests
```

They cover the orchestrator's assembled file-write state (snapshots) and the **behavioral content**
of every orchestrator-specific customization (Heroku profile, CORS/logback patches, DTO module,
pgvector changelogs, Keycloak service-account client, db dispatch). The runbook, the namespace rule,
and the `maven-orchestrator` `jvm.config` **known gap** are documented in that repo's
[`TESTING.md`](../generator-jhipster-orchestrator/TESTING.md) §2.4.

---

## 4. Layer 2 — generated backend (Java), per service

From a service directory (e.g. `psqlblog/`, `cassandrablog/`):

```bash
./mvnw -ntp -DskipTests package    # compile only — fast sanity check, no Docker
./mvnw -ntp verify                 # unit + integration (Testcontainers; Docker required)
```

Build the sibling `…dto/` module first (`cd <svc>dto && ./mvnw clean install`) — services depend on
that shared JAR.

> **Grep, don't dump:** `grep -iE "Tests run:.*Failures|<<< (FAILURE|ERROR)|expected:|but was:|BUILD (SUCCESS|FAILURE)"`.
> Run a single IT while iterating: `./mvnw -ntp -Dsurefire.skip=true -Dit.test=FooResourceIT verify -DfailIfNoTests=false`.

- **Cassandra services** carry composite-key REST/CRUD and nested-`compositeId` behavior — bug
  patterns in `generator-jhipster-cassandra/TESTING.md` §4.
- **SQL services** behave like base JHipster plus the blueprint's additions (human-readable FKs,
  pgvector columns) — see `generator-jhipster-ai-postgresql/TESTING.md` §4.

---

## 5. Layer 3 — generated frontend (Angular)

For the gateway / any app with a client:

```bash
NODE_OPTIONS=--use-system-ca npm install     # once (slow; needs the proxy CA)
NODE_OPTIONS=--use-system-ca npm test        # = eslint pretest THEN ng test (Vitest)
```

`npm test` runs `eslint .` first; a lint **error** (not warning) blocks Vitest. Bypass the gate with
`npx ng test --coverage` while debugging. Frontend bug catalogues (entity-navbar-items, microfrontend
member-ordering, composite-key spec overrides) are in the two base guides' §5.

---

## 6. Layer 4 — generated E2E (Cypress)

Cypress drives a **running** app, so bring the datastore + Keycloak up first (e.g. `docker compose`
the app's `src/main/docker` services). From a generated app dir:

```bash
NODE_OPTIONS=--use-system-ca npm install     # once
npm run e2e:devserver                        # starts backend + frontend, waits, runs cypress
npm run e2e                                  # cypress run against an already-running app
```

The Cypress passes (composite-key body injection, FK-label assertions, widget round-trips, etc.) are
post-processing steps in the base repos' `generators/cypress/generator.js` — they flow into the
generated services automatically and are fixed in the base repos, never here. Catalogues:
`generator-jhipster-cassandra/TESTING.md` §5.2 and `generator-jhipster-ai-postgresql/TESTING.md` §5.2.

---

## 7. One-shot: the full test gamut (`run-all-tests.sh`)

`run-all-tests.sh` runs every layer above across all 5 apps in one reliable pass — the merged
equivalent of the two base examples' harnesses, plus the orchestrator's DTO-module install. It is
the fastest way to confirm a regen is fully green.

```bash
sh run-all-tests.sh                 # full run, no regen (apps already generated)
sh run-all-tests.sh --regen         # prepare + jhipster jdl --force, then the full run
sh run-all-tests.sh --no-e2e        # backend + frontend only (no infra/Cypress)
sh run-all-tests.sh --skip-backend --skip-frontend   # e2e only
```

What it does, in order: free ports 8080–8084 → (optional) regen → `mvn install` the 4 DTO modules
(`psqlblogdto`/`psqlstoredto`/`cassandrablogdto`/`cassandrastoredto`, which the services depend on) →
per-app `npm install` (this example is **non-monorepo** — each app owns its `node_modules`) → backend
unit+IT per app (Postgres Testcontainers for `psql*`/gateway, Cassandra 5 for `cassandra*`) → frontend
Vitest per app → webapp build → bring up the dev stack and **gate Keycloak's OIDC endpoint before
starting the Registry** (the Registry does eager OIDC discovery at boot and exits(1) otherwise) → gate
the Registry on :8761 → launch all 5 backends → wait for app health + `gateway→remote` routes →
Cypress per app (pass/fail = exit code) → tear down backends (Docker stack left up). A final table
reports unit / IT / frontend / e2e per app and `✅ ALL GREEN` or `❌ FAILURES` with log pointers in
`.test-logs/`.

| App | DB | Port | e2e |
| --- | --- | --- | --- |
| orchestratorgateway | postgres | 8080 | account/admin/authority |
| psqlblog / psqlstore | postgres/pgvector | 8081 / 8082 | entity CRUD + FK-label + AI-search |
| cassandrablog / cassandrastore | Cassandra 5 | 8083 / 8084 | composite-key CRUD + search + widgets |

Ports/DBs come from `saathratri-apps-orchestrator-mf.jdl`. The harness only targets these 5 JDL apps.

### 7.1 Resolved — cassandrablog menu-nav → `/error` (NG0919 from federated local-module sharing)

Three of cassandrablog's entity e2e specs (`cass-post`, `cass-saathratri-entity-2`,
`cass-add-ons-selected-by-organization`) used to fail their **navbar-navigation** tests with a redirect
to `/error` (the list GET never fired). Every CRUD/widget/search test passed (those `cy.visit()` the URL
directly), so the entities were always functional — only cold in-app SPA navigation through the gateway
broke. **Fixed**; cassandrablog e2e is now 150/150 and the full one-shot is green.

- **Captured root cause (NG0919)**: a console-capturing diagnostic pinned the real error —
  `NG0919: Cannot read @Component metadata` thrown from `ComponentFactory.createComponentRef` in the
  shared `@angular/core` chunks. Angular couldn't read the entity component's `@Component` def at
  instantiation, so router activation threw and fell to the `/error` wildcard.
- **The decisive experiment**: bring up **only** gateway + cassandrablog → cass-post nav **succeeds**
  (0 errors); bring up **all 4 remotes** → it **fails** with NG0919. So it scaled with remote count, not
  entity shape (the failing components are byte-identical to passing ones, madge finds no source cycle,
  and full-reload `cy.visit` always works).
- **Why**: JHipster core's `webpack/webpack.microfrontend.js` shares the **local workspace modules**
  (`app/shared`, `app/core/*`, `app/config/*`) as singletons across remotes (the `shareMappings(...)`).
  Fine for 1–2 remotes; with **four** separately-built remotes federated under one gateway, a
  lazily-loaded entity component can be instantiated against another remote's copy of a shared local
  directive whose `@Component` metadata doesn't line up → NG0919. (The npm `@angular/*` singletons are
  correct and identical — not the problem.)
- **Fix (orchestrator layer; base repos untouched)**: the preserved
  `generator-jhipster-orchestrator/generators/angular` `POST_WRITING` now `editFile`s
  `webpack.microfrontend.js` to neutralize `shareMappings` (`const shareMappings = (...mappings) => ({})`),
  so each remote bundles its own copy of the local `app/*` modules while npm `@angular/*` stays shared.
  Verified: the 3 specs went to 11/11, 9/9, 21/21 with the two controls (cass-blog, cass-add-ons-available)
  still green, then a full `--regen` one-shot came back **all green** (cassandrablog e2e 150/150).

---

## 8. Quick reference

```bash
# ----- one-shot full gamut (all 5 apps) -----
sh run-all-tests.sh [--regen] [--no-e2e]

# ----- regenerate this workspace -----
sh saathratri-generate-code-dev-orchestrator.sh

# ----- blueprint unit tests (in the generator repo) -----
cd ../generator-jhipster-orchestrator && NODE_OPTIONS=--use-system-ca npx vitest run   # 21 files / 45 tests

# ----- generated backend (per service) -----
cd <svc>dto && ./mvnw clean install            # shared DTO JAR first
cd ../<svc>  && ./mvnw -ntp verify             # + Testcontainers ITs

# ----- generated frontend / E2E (per app) -----
NODE_OPTIONS=--use-system-ca npm test          # eslint pretest + Vitest
npm run e2e:devserver                          # Cypress against a self-started stack
```
