echo "Removing existing SQL and Cassandra generators..."
sh saathratri-generator-code-remove.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" sql
sh saathratri-generator-code-remove.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" cassandra

echo "Copy SQL generator code..."
sh saathratri-generator-code-copy.sh "$(npm root -g)/generator-jhipster-ai-postgresql/generators" "$(npm root -g)/generator-jhipster-orchestrator/generators" sql
echo "Copy Cassandra generator code..."
sh saathratri-generator-code-copy.sh "$(npm root -g)/generator-jhipster-cassandra/generators" "$(npm root -g)/generator-jhipster-orchestrator/generators" cassandra

# Copy the Cassandra cypress generator as the orchestrator's cypress override. Its POST_WRITING
# navbar/entityItemSelector fix is DB-agnostic (keyed on baseName) so it fixes the microfrontend
# entity-menu selector for ALL services (SQL + Cassandra). It imports ../cassandra-spring-boot/*
# which is already copied above, so the import resolves.
echo "Copy Cypress generator (Cassandra; shared navbar fix)..."
rm -rf "$(npm root -g)/generator-jhipster-orchestrator/generators/cypress"
cp -rf "$(npm root -g)/generator-jhipster-cassandra/generators/cypress" "$(npm root -g)/generator-jhipster-orchestrator/generators/cypress"

# Guard the Cassandra cypress POST_WRITING_ENTITIES patches (composite-key URLs, MAP/SET/date-time
# widgets, search form — they read Cassandra-only entity.primaryKeySaathratri metadata) so they run
# only for Cassandra services. The orchestrator is multi-database; without this, those patches would
# mis-touch the SQL services' entity specs. Done here (post-copy) so the base cassandra repo stays
# byte-for-byte as upstream tested it. The DB-agnostic navbar fix in POST_WRITING is untouched.
echo "Guard Cypress entity patches to Cassandra services only (orchestrator is multi-database)..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators/cypress" "async postWritingEntitiesTemplateTask({ application, entities }) {" "async postWritingEntitiesTemplateTask({ application, entities }) {\n        if (!application.databaseTypeCassandra) return;"

echo "Replace Cassandra generator references with Orchestrator generator references..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" "jhipster-cassandra" "jhipster-orchestrator"
echo "Replace Sql generator references with Orchestrator generator references..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" "jhipster-ai-postgresql" "jhipster-orchestrator"

echo "Replace Cassandra DTOId needle with code that created DTO JAR with DTOId in it for modularization..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" '\/\* saathratri-needle-cassandra-copy-dto-id-class \*\/' '\
                    {\
                        file: "service/dto/_dtoClass_Id.java",\
                        renameTo: `../../../../../../${this.appname}dto/src/main/java/${this.jhipsterConfig.packageFolder}/service/dto/${entity.dtoClass}Id.java`,\
                    }\
'

echo "Replace Cassandra DTO needle with code that created DTO JAR with DTO in it for modularization..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" '\/\* saathratri-needle-cassandra-copy-dto-class \*\/' '\
                    {\
                        file: "service/dto/_dtoClass_.java",\
                        renameTo: `../../../../../../${this.appname}dto/src/main/java/${this.jhipsterConfig.packageFolder}/service/dto/${entity.dtoClass}.java`,\
                    },\
'

echo "Replace Cassandra DTO needle with code that created DTO JAR with DTO in it for modularization..."
sh saathratri-generator-code-replace.sh "$(npm root -g)/generator-jhipster-orchestrator/generators" '\/\* saathratri-needle-sql-copy-dto-class \*\/' '\
                    {\
                        file: "service/dto/_dtoClass_.java",\
                        renameTo: `../../../../../../${this.appname}dto/src/main/java/${this.jhipsterConfig.packageFolder}/service/dto/${entity.dtoClass}.java`,\
                    },\
'