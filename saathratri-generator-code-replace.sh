#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 parent_folder search_string replace_string"
    exit 1
fi

PARENT_FOLDER=$1
SEARCH_STRING=$2
REPLACE_STRING=$3

# Ensure the parent folder exists
if [ ! -d "$PARENT_FOLDER" ]; then
    echo "Parent folder does not exist"
    exit 1
fi

# Find and replace string in generator.js files
# Process both generator.js AND generator.spec.js: the copied sql-*/cassandra-* specs arrive from
# the base repos carrying their source BLUEPRINT_NAMESPACE (jhipster-cassandra: / jhipster-ai-postgresql:),
# so the namespace-rename pass must rewrite them to jhipster-orchestrator: too — otherwise the inherited
# snapshot specs can't resolve the generator and the orchestrator's Layer-1 suite goes red after a regen.
# (DTO-needle replaces target generator.js content only; spec files simply won't match and are skipped.)
find "$PARENT_FOLDER" -type f \( -name "generator.js" -o -name "generator.spec.js" \) | while read -r FILE; do
    if grep -q "$SEARCH_STRING" "$FILE"; then
        # Use sed to replace the string and write back to the file
        sed -i.bak "s|$SEARCH_STRING|$REPLACE_STRING|g" "$FILE"
        rm "${FILE}.bak"
        echo "Replaced '$SEARCH_STRING' with '$REPLACE_STRING' in $FILE"
    fi
done

echo "String replacement complete."
