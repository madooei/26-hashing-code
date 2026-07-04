#!/usr/bin/env bash
# Compile all source and tests into out/, then run every JUnit test JUnit finds.
# These are the two commands the chapter describes; this script is the single
# place the classpath lives so you never have to type it by hand.
set -e
cd "$(dirname "$0")/.."
javac -d out -cp "lib/*" $(find src/main src/test -name "*.java")
java -jar lib/junit-platform-console-standalone-6.1.0.jar execute \
  -cp out --scan-classpath
