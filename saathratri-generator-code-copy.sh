#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -lt 2 ]; then
    echo "Usage: $0 source_parent_folder destination_parent_folder [prefix]"
    exit 1
fi

SOURCE_PARENT_FOLDER=$1
DESTINATION_PARENT_FOLDER=$2
PREFIX=$3

# Ensure the source parent folder exists
if [ ! -d "$SOURCE_PARENT_FOLDER" ]; then
    echo "Source parent folder does not exist"
    exit 1
fi

# Ensure the destination parent folder exists, if not create it
mkdir -p "$DESTINATION_PARENT_FOLDER"

# Build the list of subfolders. Quote the base path so Windows backslash paths from
# `npm root -g` (e.g. C:\Users\...\node_modules) don't break glob expansion — bash treats
# unquoted backslashes as escapes, so the old `for f in $PATTERN` matched nothing on Windows
# and silently copied zero generators. Quoting the base keeps the backslashes literal while the
# trailing * still globs (mirrors saathratri-generator-code-remove.sh, which already did this).
if [ -n "$PREFIX" ]; then
    SUBFOLDERS=("$SOURCE_PARENT_FOLDER"/"$PREFIX"-*)
else
    SUBFOLDERS=("$SOURCE_PARENT_FOLDER"/*)
fi

# Iterate over each subfolder in the source parent folder
for SUBFOLDER in "${SUBFOLDERS[@]}"; do
    if [ -d "$SUBFOLDER" ]; then
        # Get the base name of the subfolder
        SUBFOLDER_BASENAME=$(basename "$SUBFOLDER")

        # Create the destination folder with the prefix
        DESTINATION_FOLDER="${DESTINATION_PARENT_FOLDER}/${SUBFOLDER_BASENAME}"

        # Copy the subfolder to the destination folder recursively and force overwrite
        cp -rf "$SUBFOLDER" "$DESTINATION_FOLDER"

        echo "Copied $SUBFOLDER to $DESTINATION_FOLDER"
    fi
done

echo "Copy operation complete."
