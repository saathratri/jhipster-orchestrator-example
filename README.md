# JHipster Orchestrator Example — Federated SQL + Cassandra Microservices (Saathratri)

### About this JHipster Example

This code was generated using the JHipster blueprint `generator-jhipster-orchestrator`.
The source code for the underlying JHipster blueprint is available at: https://github.com/amarpatel-xx/generator-jhipster-orchestrator

The `generator-jhipster-orchestrator` blueprint is an **assembled** blueprint — it does not
re-implement microservice generation from scratch. Instead it composes the two open-source base
blueprints by `Amar Premsaran Patel` and adds the cross-cutting infrastructure needed to run them as
**one federated stack** behind a single gateway:

- **[`generator-jhipster-ai-postgresql`](https://github.com/amarpatel-xx/generator-jhipster-ai-postgresql)** — SQL services with **human-readable foreign keys** and **pgvector AI semantic search** (the `sql-*` sub-generators).
- **[`generator-jhipster-cassandra`](https://github.com/amarpatel-xx/generator-jhipster-cassandra)** — Cassandra services with **composite primary keys**, `Set`/`Map` columns, and Cassandra vector search (the `cassandra-*` sub-generators).

On top of those, the orchestrator layer adds what neither base blueprint provides on its own: a
sibling **DTO Maven module** per service (published as a shared JAR), **Heroku** deployment
artifacts, dev **CORS / file-logging** patches, **pgvector** Liquibase changelogs, a **Keycloak**
realm with a **service-to-service OAuth2** client, and the database-dispatch wiring that lets a
single gateway front both a PostgreSQL and a Cassandra back end at the same time.

This example demonstrates that the two capability demos Matt Raible popularized — the **blog** and
**store** examples — can be generated side by side, in the same JDL, against two different
databases, and assembled into one micro frontend shell.

> The blueprint source — and its own unit-test suite — lives in the sibling
> **`generator-jhipster-orchestrator`** repo. See its
> [`README.md`](../generator-jhipster-orchestrator/README.md) and
> [`TESTING.md`](../generator-jhipster-orchestrator/TESTING.md).

## Architecture

A single OAuth2 gateway (`orchestratorgateway`) hosts an Angular **micro frontend** shell and routes
to four independently-deployed microservices through a Eureka service registry. Two services are
backed by **PostgreSQL** (with `pgvector` for AI search), two by **Cassandra 5**.

| App | Database | Blueprint half | Port | DTO module |
| --- | --- | --- | --- | --- |
| `orchestratorgateway` | PostgreSQL | `sql-*` (gateway shell) | 8080 | — |
| `psqlblog` | PostgreSQL + pgvector | `sql-*` | 8081 | `psqlblogdto` |
| `psqlstore` | PostgreSQL | `sql-*` | 8082 | `psqlstoredto` |
| `cassandrablog` | Cassandra 5 | `cassandra-*` | 8083 | `cassandrablogdto` |
| `cassandrastore` | Cassandra 5 | `cassandra-*` | 8084 | `cassandrastoredto` |

Infrastructure: **Keycloak** (OAuth2 / OIDC, realm `admin`/`admin`) and the **JHipster Registry**
(Eureka + Spring Cloud Config) on port `8761`.

## What gets generated

Everything is generated from a single multi-app JDL, [`saathratri-apps-orchestrator-mf.jdl`](saathratri-apps-orchestrator-mf.jdl),
which is a merge of the two upstream blueprint examples. Because JDL entity names must be globally
unique within one file, the SQL example's entities are prefixed **`Psql`** and the Cassandra
example's **`Cass`**:

- `jhipster-ai-postgresql-example/non-reactive-mf.jdl` → the `Psql*` entities (relationships,
  human-readable FK annotations, and pgvector embedding fields).
- `jhipster-cassandra-example/saathratri-apps-cassandra-mf.jdl` → the `Cass*` entities (composite
  primary keys, `Set`/`Map` columns, and Cassandra vector fields).

Each microservice's entity DTOs are copied into a sibling `…dto/` Maven module so they can be
published as a shared JAR and consumed by other services (see the blueprint's
`spring-boot-orchestrator` sub-generator).

### SQL / pgvector entities (the `Psql*` half)

The SQL services carry the `generator-jhipster-ai-postgresql` features. Two annotations drive them:

- `@customAnnotation("DISPLAY_IN_GUI_RELATIONSHIP_LINK")` — replace an entity's UUID with one or more
  **human-readable fields** wherever it appears on the other side of a relationship in the Angular UI.
  A second `@customAnnotation("<delimiter>")` joins multiple display fields (e.g. `name-handle`).
- `@customAnnotation("VECTOR")` + `@customAnnotation("<dimension>")` — mark a field as a **pgvector**
  embedding column. The blueprint converts it from `Blob` to `float[]`, emits the `vector(N)` Liquibase
  column, builds an **HNSW** index, and wires up automatic embedding generation + an **AI search bar**
  on the list page. The embedding field name must follow `<sourceField>Embedding`.

```jdl
entity PsqlBlog {
  id UUID,
  @customAnnotation("DISPLAY_IN_GUI_RELATIONSHIP_LINK") @customAnnotation("-") name String required minlength(3)
  @customAnnotation("DISPLAY_IN_GUI_RELATIONSHIP_LINK") @customAnnotation("-") handle String required minlength(2)
}

// Single-value Primary Key with AI Semantic Search (Vector Embeddings):
entity PsqlTag {
  id UUID
  @customAnnotation("DISPLAY_IN_GUI_RELATIONSHIP_LINK") @customAnnotation("") name String maxlength(100) required
  description String maxlength(255)
  @customAnnotation("VECTOR") @customAnnotation("1536") nameEmbedding Blob
  @customAnnotation("VECTOR") @customAnnotation("1536") descriptionEmbedding Blob
}

relationship ManyToOne {
  PsqlBlog{tajUser(login)} to PsqlTajUser
  PsqlPost{blog} to PsqlBlog{post}
  PsqlReport{product} to PsqlProduct
}
relationship ManyToMany {
  PsqlPost{tag(name)} to PsqlTag{post}
}
```

### Cassandra entities (the `Cass*` half)

The Cassandra services carry the `generator-jhipster-cassandra` features. Composite primary keys are
declared field-by-field with `@customAnnotation`:

- `@customAnnotation("PrimaryKeyType.PARTITIONED")` — a partition-key column (one or more allowed).
- `@customAnnotation("PrimaryKeyType.CLUSTERED")` — a clustering column; greater-than / less-than /
  equal query methods are auto-generated for it.
- `@customAnnotation("CassandraType.Name.SET")` / `@customAnnotation("CassandraType.Name.MAP")` —
  Cassandra collection columns; the blueprint generates custom Angular editors for `MAP<TEXT,*>` over
  TEXT, DECIMAL, BOOLEAN, and BIGINT/`UTC_DATETIME` value types.
- `@customAnnotation("VECTOR")` — a Cassandra `VECTOR<FLOAT, N>` column with a **SAI** index and
  **ANN OF** similarity queries (the Cassandra equivalent of the pgvector AI search above).

Because a field can carry at most four annotations, unused slots are passed as a dummy
`@customAnnotation("")` so JHipster still parses the JDL and exposes the annotations to the blueprint.

```jdl
// Complex Composite Primary Key (multiple clustered keys, TimeUUID, UTC date/time):
entity CassPost {
  @Id @customAnnotation("PrimaryKeyType.PARTITIONED") @customAnnotation("CassandraType.Name.BIGINT") @customAnnotation("UTC_DATE") @customAnnotation("0") createdDate Long
  // Do not name composite primary key fields 'id' — it conflicts with JHipster's own 'id'.
  @customAnnotation("PrimaryKeyType.CLUSTERED") @customAnnotation("CassandraType.Name.BIGINT") @customAnnotation("UTC_DATETIME") @customAnnotation("1") addedDateTime Long
  @customAnnotation("PrimaryKeyType.CLUSTERED") @customAnnotation("CassandraType.Name.UUID") @customAnnotation("") @customAnnotation("2") postId UUID
  @customAnnotation("") @customAnnotation("CassandraType.Name.TEXT") @customAnnotation("") @customAnnotation("") title String required
  @customAnnotation("") @customAnnotation("CassandraType.Name.TEXT") @customAnnotation("") @customAnnotation("") content String required
}

// MAP value types (TEXT / DECIMAL / BOOLEAN / BIGINT-as-datetime):
entity CassAddOnsAvailableByOrganization {
  @Id @customAnnotation("PrimaryKeyType.PARTITIONED") @customAnnotation("CassandraType.Name.UUID") @customAnnotation("") organizationId UUID
  @customAnnotation("PrimaryKeyType.CLUSTERED") @customAnnotation("CassandraType.Name.UUID") @customAnnotation("") addOnId UUID
  @customAnnotation("CassandraType.Name.MAP") @customAnnotation("CassandraType.Name.TEXT") @customAnnotation("") addOnDetailsText String
  @customAnnotation("CassandraType.Name.MAP") @customAnnotation("CassandraType.Name.DECIMAL") @customAnnotation("") addOnDetailsDecimal BigDecimal
  @customAnnotation("CassandraType.Name.MAP") @customAnnotation("CassandraType.Name.BOOLEAN") @customAnnotation("") addOnDetailsBoolean Boolean
  @customAnnotation("CassandraType.Name.MAP") @customAnnotation("CassandraType.Name.BIGINT") @customAnnotation("UTC_DATETIME") addOnDetailsBigInt Long
}
```

The full set of Cassandra shapes (single-value keys, `Set` columns, multi-partition keys, `VECTOR`
AI search on `CassTag`) is in [`saathratri-apps-orchestrator-mf.jdl`](saathratri-apps-orchestrator-mf.jdl).
For deeper, dedicated walk-throughs of each feature — including the MAP-editor and AI-search
screenshots — see the two base examples:
[`jhipster-ai-postgresql-example`](https://github.com/amarpatel-xx/jhipster-ai-postgresql-example) and
[`jhipster-cassandra-example`](https://github.com/amarpatel-xx/jhipster-cassandra-example).

## What the orchestrator layer adds

These customizations exist only in the orchestrator blueprint — they are not produced by either base
blueprint and are re-applied on every regeneration:

- **DTO Maven module per service** (`…dto/`) — each service's entity DTOs are copied into a sibling
  module and published as a shared JAR, so services can exchange typed payloads.
- **Service-to-service OAuth2** — a Keycloak realm with a dedicated service-account client for
  machine-to-machine calls between microservices.
- **Federated micro frontend fix** — the orchestrator's `angular` post-writing step neutralizes
  JHipster core's `shareMappings` in `webpack.microfrontend.js`, so each of the four remotes bundles
  its **own** copy of the local `app/*` modules (npm `@angular/*` stays shared). Without this, cold
  in-app SPA navigation across four federated remotes throws `NG0919`. See
  [`TESTING.md` §7.1](TESTING.md).
- **pgvector Liquibase changelogs**, **dev CORS / file-logging** patches, and the **db-dispatch**
  wiring that lets one gateway front both PostgreSQL and Cassandra back ends.

## Prerequisites

- [Java](https://sdkman.io/) 21+
- [Node.js](https://nodejs.org/) 22+
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (running — integration tests start PostgreSQL/pgvector and Cassandra 5 Testcontainers)
- [JHipster](https://www.jhipster.tech/installation/) 8.10.0
- The `generator-jhipster-orchestrator` blueprint, linked globally (the regen scripts assemble it from the two base repos)

Behind a TLS-intercepting corporate proxy, point each toolchain at the OS trust store:

```bash
export NODE_OPTIONS=--use-system-ca                              # npm / ng / vitest / eslint
export MAVEN_OPTS="-Djavax.net.ssl.trustStoreType=Windows-ROOT"  # Windows; macOS: KeychainStore
```

### AI Semantic Search (Optional)

Two entities ship vector fields — `PsqlTag` (pgvector) and `CassTag` (Cassandra `VECTOR`). To enable
AI-powered semantic search, supply your OpenAI API key in any of these ways (checked in this order):

1. **`application-dev.yml`** in the service — note the property name differs by service type:

   ```yaml
   # SQL services (psqlblog):
   openai:
     api-key: sk-your-key-here

   # Cassandra services (cassandrablog):
   spring:
     ai:
       openai:
         api-key: sk-your-key-here
   ```

2. **Environment variable**, set before starting the services:

   ```console
   export OPENAI_API_KEY=sk-your-key-here
   ```

3. **A `.env` file** in the app root (e.g. `psqlblog/.env`, `cassandrablog/.env`). Each vector-enabled
   app has a checked-in `.env.example` — copy it and fill in the key:

   ```console
   cp psqlblog/.env.example psqlblog/.env    # then edit: OPENAI_API_KEY=sk-your-key-here
   ```

   `.env` is git-ignored, so the key can never be committed; only `.env.example` (with an empty value)
   is checked in.

Without the API key the applications run normally — embedding generation and AI search are simply
disabled, and the test suite still passes (the key is only used at runtime).

**Offline/e2e alternative — fake embedding model.** Set `OPENAI_EMBEDDING_FAKE=true` to replace the
OpenAI model with a deterministic offline one (same text → same vector), so exact-text semantic
search works with no key and no API cost. The Cypress suites include an embedding-lifecycle e2e that
requires this mode (skipped unless `CYPRESS_fakeEmbeddings=true`); `saathratri-run-all-tests.sh`
enables both automatically during its e2e phase.

## Regenerate the code

The generated services are **disposable** — regeneration overwrites them. Run the workspace script
(do **not** call raw `jhipster import-jdl`; it bypasses the prepare/assembly step):

```console
sh saathratri-generate-code-dev-orchestrator.sh
```

It runs, in order:

1. **`saathratri-cleanup-dev-main.sh`** — delete the previous generated output (the 5 apps, the 4 DTO
   modules, and the root build/config artifacts).
2. **`saathratri-generate-code-dev-orchestrator-mf.sh`** — assemble the blueprint
   (`saathratri-generator-code-prepare.sh` copies `sql-*` from the pgvector base repo and `cassandra-*`
   from the Cassandra base repo into the orchestrator generator, renames namespaces, and applies the
   DTO-copy needles) then runs
   `jhipster --blueprints orchestrator jdl saathratri-apps-orchestrator-mf.jdl --skip-jhipster-dependencies --skip-install --force`.
3. **`saathratri-copy-files.sh custom-files .`** — overlay the hand-maintained custom files onto the generated tree.

When complete you should see:

```console
Congratulations, JHipster execution is complete!
```

> Run from **Git Bash** or **WSL** on Windows — this workspace ships `.sh` scripts only (no `.bat`
> equivalents). The generated per-service Maven wrapper is invoked as `.\mvnw.cmd` from PowerShell.

## Build

DTO modules are shared JARs the services depend on, so build them first:

```console
# 1) install the four shared DTO JARs
cd psqlblogdto       && ./mvnw clean install && cd ..
cd psqlstoredto      && ./mvnw clean install && cd ..
cd cassandrablogdto  && ./mvnw clean install && cd ..
cd cassandrastoredto && ./mvnw clean install && cd ..

# 2) package each service (skip tests for a fast compile sanity check)
cd psqlblog && ./mvnw -ntp -DskipTests package && cd ..
# …repeat for orchestratorgateway, psqlstore, cassandrablog, cassandrastore
```

## Run the federated stack

1. Start **Keycloak** and the **JHipster Registry** (Eureka) from the gateway:

```console
cd orchestratorgateway
docker compose -f src/main/docker/keycloak.yml up -d
docker compose -f src/main/docker/jhipster-registry.yml up -d
```

> If the `jhipster-registry-1` container does not stay up, start it manually in Docker Desktop. The
> Registry does eager OIDC discovery at boot and exits if Keycloak's endpoint isn't ready yet — bring
> Keycloak up first.

2. Start each app's database (Docker), then the app (Maven), each in its own terminal:

```console
# Gateway (PostgreSQL)
cd orchestratorgateway && npm run docker:db:up && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# SQL / pgvector microservices
cd psqlblog  && npm run docker:db:up && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd psqlstore && npm run docker:db:up && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Cassandra microservices
cd cassandrablog  && npm run docker:db:up && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd cassandrastore && npm run docker:db:up && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Wait until all five apps appear in the registry at <http://localhost:8761> and the gateway UI loads at
<http://localhost:8080>. Login uses the bundled Keycloak realm (`admin`/`admin`).

## Testing the generated code

Every app generated into this workspace ships the full JHipster test suite — **backend** (JUnit +
Testcontainers), **frontend** (ESLint + Vitest), and **E2E** (Cypress). See **[`TESTING.md`](TESTING.md)**
for the full per-layer runbook; the short version follows.

> Integration tests use **Testcontainers**, so **Docker Desktop must be running**. The SQL services'
> `*IT` tests start a real **PostgreSQL (with pgvector)** container and the Cassandra services'
> `*ResourceIT` tests start a real **Cassandra 5** container — no manual database setup is required.
> The OpenAI API key is **not** needed to run the tests.

### One-shot — the full gamut across all 5 apps

`saathratri-run-all-tests.sh` runs every layer across all five apps in one reliable pass (the merged equivalent
of both base examples' harnesses, plus the orchestrator's DTO-module install). It is the fastest way
to confirm a regen is fully green:

```console
sh saathratri-run-all-tests.sh                 # full run, no regen (apps already generated)
sh saathratri-run-all-tests.sh --regen         # prepare + jhipster jdl --force, then the full run
sh saathratri-run-all-tests.sh --no-e2e        # backend + frontend only (no infra / Cypress)
sh saathratri-run-all-tests.sh --skip-backend --skip-frontend   # e2e only
```

It frees ports 8080–8084, installs the four DTO JARs, runs `npm install` + backend unit/IT + frontend
Vitest per app, builds each webapp, brings up Keycloak/Registry (gating OIDC before the Registry
boots), launches all five backends, waits for gateway→remote routes, then runs Cypress per app. A
final table reports unit / IT / frontend / e2e per app with `✅ ALL GREEN` or `❌ FAILURES` and log
pointers in `.test-logs/`.

### Backend tests (per service)

Build the sibling `…dto/` module first (services depend on the shared JAR), then from a service
directory (`orchestratorgateway`, `psqlblog`, `psqlstore`, `cassandrablog`, `cassandrastore`):

```console
./mvnw -ntp -DskipTests package    # compile + package only (no Docker)
./mvnw -ntp verify                 # unit + integration tests (Docker required)
```

`verify` runs the unit tests plus the entity REST CRUD integration tests: create / get-one / get-all /
update (PUT) / partial update (PATCH) / delete and their negative cases. The SQL services exercise the
human-readable FK display columns and pgvector embedding columns against live PostgreSQL/pgvector; the
Cassandra services exercise composite primary keys (including auto-generated `TIMEUUID` clustering
keys and `Set`/`Map` columns) against live Cassandra 5.

> **Grep, don't dump** the Maven logs — the database containers emit huge config blocks. Filter with
> `grep -iE "Tests run:.*Failures|<<< (FAILURE|ERROR)|expected:|but was:|BUILD (SUCCESS|FAILURE)"`.
> Run one IT while iterating:
> `./mvnw -ntp -Dsurefire.skip=true -Dit.test=FooResourceIT verify -DfailIfNoTests=false`.

(On Windows PowerShell, use `.\mvnw.cmd` and quote `-D` properties that contain a dot — e.g.
`.\mvnw.cmd -ntp "-Dskip.npm" verify` — otherwise PowerShell parses `-Dskip.npm` as `-Dskip` plus a
`.npm` member-access token.)

### Frontend tests (per app)

```console
NODE_OPTIONS=--use-system-ca npm install     # once (slow; needs the proxy CA)
NODE_OPTIONS=--use-system-ca npm test        # eslint pretest THEN ng test (Vitest), one-shot
npm run test:watch                           # keep Vitest in watch mode for iterative TDD
```

`npm test` runs **`eslint .` first** (the `pretest` hook) — a lint **error** (not a warning) blocks
Vitest — then the Angular unit tests on **Vitest** (`ng test --coverage --watch=false`), one-shot. The
`--watch=false` flag is added by the blueprints to work around an upstream JHipster bug where, after
the Karma→Vitest switch, plain `ng test --coverage` defaults to watch mode and never exits. To run
just one half: `npx eslint .` (lint only) or `npx ng test --watch=false` (Vitest only).

### End-to-End (E2E) tests with Cypress

Cypress drives a **running, fully-assembled** app in a real browser, so bring the full stack up first
(see [*Run the federated stack*](#run-the-federated-stack)). A local **Chrome** is required for the
headless run and **Docker Desktop** must be running. Then, from a generated app dir:

```console
NODE_OPTIONS=--use-system-ca npm install     # once (also fetches the Cypress binary)
npm run e2e:devserver                        # starts backend + frontend, waits, runs cypress
npm run e2e                                  # cypress run against an already-running app
npm run cypress                              # interactive Cypress runner (cypress open)
```

> **Micro frontend note:** every app (gateway and each remote) carries its **own** entity specs but
> points `baseUrl` at the **gateway** (port 8080), so the specs run against the assembled shell. The
> gateway **and** the service whose entities you are testing must both be running.

The SQL per-entity specs carry the blueprint's **human-readable foreign-key assertion** (the
relationship dropdown shows a label, not a raw UUID); the Cassandra specs carry the **composite-key
DELETE handling** (the cleanup URL uses every `compositeId` segment). Catalogues live in the base
repos' `TESTING.md` §5.2.

### Debugging test failures — fix templates, never the generated code

This example is **generated code** — a hand-edited `*.java`/`*.ts` fix evaporates on the next regen.
Fix the **source template** and regenerate. Where the template lives depends on which half produced
the code:

| Generated code | Fix in |
| --- | --- |
| SQL service (`orchestratorgateway`, `psqlblog`, `psqlstore`) | `generator-jhipster-ai-postgresql` (`sql-*` templates) |
| Cassandra service (`cassandrablog`, `cassandrastore`) | `generator-jhipster-cassandra` (`cassandra-*` templates) |
| Orchestrator infra (Heroku, `…dto/` module, dev CORS/logback, pgvector changelogs, Keycloak service-account client, db dispatch, the micro frontend `shareMappings` fix) | the matching `*-orchestrator` submodule of `generator-jhipster-orchestrator` |

⚠️ Do **not** edit the orchestrator's `sql-*` / `cassandra-*` directories — the prepare step
overwrites them from the base repos on every regen.

The deep backend / frontend / E2E bug catalogues live in the base repos'
[`generator-jhipster-cassandra/TESTING.md`](https://github.com/amarpatel-xx/generator-jhipster-cassandra/blob/main/TESTING.md)
and
[`generator-jhipster-ai-postgresql/TESTING.md`](https://github.com/amarpatel-xx/generator-jhipster-ai-postgresql/blob/main/TESTING.md).

## Scripts reference

| Script | Purpose |
| --- | --- |
| `saathratri-generate-code-dev-orchestrator.sh` | Full regen: cleanup → assemble + generate → sync custom files |
| `saathratri-generate-code-dev-orchestrator-mf.sh` | Assemble the blueprint and run the JDL generation |
| `saathratri-generator-code-prepare.sh` | Copy `sql-*` / `cassandra-*` from the base repos into the orchestrator and rename namespaces |
| `saathratri-cleanup-dev-main.sh` | Remove previous generated output (apps, DTO modules, root build artifacts) |
| `saathratri-copy-files.sh <src> <dst>` | Overlay `custom-files/` onto the generated tree |
| `saathratri-deploy.sh` | Deploy the full stack locally: Keycloak (gated) + Registry, per-app DBs, all 5 apps in terminal tabs |
| `saathratri-run-all-tests.sh` | One-shot full test gamut across all 5 apps (`--regen`, `--no-e2e`, `--skip-backend`, `--skip-frontend`) |

Every script has a Windows `.bat` companion with the same name (run e.g. `.\saathratri-deploy.bat`).
The `.sh` versions target macOS/Linux (`saathratri-deploy.sh` uses `ttab`); the `.bat` versions use
Windows Terminal tabs or run the `.sh` under Git Bash.

## Switch Identity Providers

JHipster ships with Keycloak when you choose OAuth 2.0 / OIDC as the authentication type.

If you'd like to use Okta for your identity provider, see [JHipster's documentation](https://www.jhipster.tech/security/#okta).

You can configure JHipster quickly with the [Okta CLI](https://cli.okta.com):

```console
okta apps create jhipster
```

## See the code in action

Open your favorite browser to [http://localhost:8080](http://localhost:8080) and log in with the
credentials displayed on the page (`admin`/`admin`).

### Create a SQL blog (human-readable foreign keys + AI search)

1. Navigate to the **psqlblog** menu.
2. Add a `PsqlTajUser` by giving it a login name.
3. Add a `PsqlBlog` by giving it a name, a handle, and selecting the user.
4. Add a `PsqlTag` by giving it a name and description. If the OpenAI API key is configured, embeddings
   are generated automatically when you save.
5. Add a `PsqlPost` by providing a title, content, selecting the blog and a tag.

Notice the blog column of the post shows `<name>-<handle>` and **not** the blog's UUID. That is
success! Then go to the `PsqlTag` list page and use the **AI Search** bar — type a natural-language
query (e.g. "animals" or "vehicles") and cosine similarity over pgvector returns the closest tags.

### Create a Cassandra blog (composite primary keys + MAP/SET widgets)

1. Navigate to the **cassandrablog** menu.
2. Create a `CassTajUser` (UUID id + login), a `CassBlog` (category + TimeUUID blogId composite key),
   and a `CassPost` (a complex composite key over created-date / added-date-time / postId).
3. Explore `CassAddOnsAvailableByOrganization` to see the four `MAP<TEXT, *>` editors (text, decimal,
   boolean toggle, and date-time picker) and `CassSetEntityByOrganization` for the `SET<TEXT>` widget.

Notice the Cassandra entities show their full composite primary key during create / update / delete.
That is success!

### Create a store

- **psqlstore** — add a `PsqlProduct` (title, price, image) and a `PsqlReport` (PDF AnyBlob upload).
- **cassandrastore** — add a `CassProduct` (UUID, title, price, image, added date) and a `CassReport`.

## Have Fun with Micro Frontends and JHipster!

I hope you enjoyed this demo, and that it helped you understand how to build better microservice
architectures that combine **human-readable foreign keys + AI semantic search (PostgreSQL/pgvector)**
and **composite primary keys + Cassandra collections** behind a single federated gateway.

☕️ The orchestrator blueprint that generates this example: https://github.com/amarpatel-xx/generator-jhipster-orchestrator

☕️ The two base blueprints it composes:
[`generator-jhipster-ai-postgresql`](https://github.com/amarpatel-xx/generator-jhipster-ai-postgresql) ·
[`generator-jhipster-cassandra`](https://github.com/amarpatel-xx/generator-jhipster-cassandra)

🤓 Read the blog post, by Matt Raible, that inspired this project:
[Micro Frontends for Java Microservices](https://auth0.com/blog/micro-frontends-for-java-microservices/)

## Acknowledgements

The `generator-jhipster-orchestrator` blueprint and this example are open-source software made with
love by `Amar Premsaran Patel`.

Thank you to [yelhouti](https://github.com/yelhouti), [Jeremy Artero](https://www.linkedin.com/in/jeremyartero/),
[Matt Raible](https://github.com/mraible), [Gaël Marziou](https://github.com/gmarziou),
[Cedrick Lunven](https://www.linkedin.com/in/clunven/),
[Christophe Bornet](https://www.linkedin.com/in/christophe-bornet-bab1193/),
[Disha Patel](https://www.linkedin.com/in/dishapatel860/), and
[Catherine Guevara](https://www.linkedin.com/in/catherine-guevara-1a5375b1/) for your invaluable
contributions to these examples and the underlying JHipster blueprints.
</content>
</invoke>
