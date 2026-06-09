#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 folder prefix"
    exit 1
fi

FOLDER=$1
PREFIX=$2

# Ensure the specified folder exists
if [ ! -d "$FOLDER" ]; then
    echo "Folder does not exist"
    exit 1
fi

# Iterate over each subfolder in the specified directory
for SUBFOLDER in "$FOLDER"/"$PREFIX-"*; do
    if [ -d "$SUBFOLDER" ]; then
        rm -rf "$SUBFOLDER"
        echo "Deleted folder $SUBFOLDER"
    fi
done

echo "Deleted all folders in $FOLDER with prefix $PREFIX-"
