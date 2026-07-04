#!/usr/bin/env bash
# Compile all source into out/ and run the chapter demo.
# Bootstrap replaces hashing.Main with the chapter's demo entry point
# (e.g., stack.Main).
set -e
cd "$(dirname "$0")/.."
javac -d out $(find src/main -name "*.java")
java -cp out hashing.Main
