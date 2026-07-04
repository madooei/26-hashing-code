#!/usr/bin/env bash
# Compile all source into out/ and run the hash-function demo.
set -e
cd "$(dirname "$0")/.."
javac -d out $(find src/main -name "*.java")
java -cp out hashing.Main
