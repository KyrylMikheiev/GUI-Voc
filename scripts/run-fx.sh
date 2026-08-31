#!/bin/bash
# Runs the JavaFX version of the app.
#
# Unlike run.sh, this needs no local JavaFX install: the Maven wrapper downloads
# Maven itself, and Maven downloads the JavaFX SDK for the current platform.
#
#   ./scripts/run-fx.sh              # light theme
#   ./scripts/run-fx.sh dark         # dark theme
set -euo pipefail

cd "$(dirname "$0")/.."

echo "Loading submodules..."
git submodule update --init --recursive

THEME="${1:-light}"

echo "Building and starting (theme: $THEME)..."
./mvnw -B javafx:run -Dvt.theme="$THEME"
