sh saathratri-generator-code-prepare.sh

echo "Invoking JHipster generator for Orchestrator development..."
jhipster --blueprints orchestrator jdl saathratri-apps-orchestrator-mf.jdl --skip-jhipster-dependencies --skip-install --force

