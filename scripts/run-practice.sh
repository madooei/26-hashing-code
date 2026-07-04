#!/usr/bin/env bash
# Compile all source into out/ and run the practice demo.
# Bootstrap replaces practice.PracticeMain with practice.PracticeMain
# (or removes this script if the chapter has no practice demo).
set -e
cd "$(dirname "$0")/.."
javac -d out $(find src/main -name "*.java")
java -cp out practice.PracticeMain
