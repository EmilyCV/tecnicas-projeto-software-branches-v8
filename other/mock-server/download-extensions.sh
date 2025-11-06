#!/usr/bin/env bash
set -euo pipefail

echo "[wiremock-init] Creating /var/wiremock/extensions (if missing)"
mkdir -p /var/wiremock/extensions

# WireMock state extension coordinates (Maven Central)
JAR_URL="https://repo1.maven.org/maven2/org/wiremock/extensions/wiremock-state-extension-standalone/0.9.3/wiremock-state-extension-standalone-0.9.3.jar"
OUT_NAME="wiremock-state-extension-standalone"
OUT_PATH="/var/wiremock/extensions/${OUT_NAME}.jar"
TMP_PATH="${OUT_PATH}.tmp"

# If already present, skip download
if [ -f "$OUT_PATH" ]; then
  echo "[wiremock-init] $OUT_PATH already exists, skipping download"
else
  echo "[wiremock-init] Installing wget"
  apt-get update && apt-get install -y wget
  echo "[wiremock-init] Downloading $JAR_URL -> $OUT_PATH"
  wget -O "$TMP_PATH" "$JAR_URL"
  # Move into place atomically
  mv "$TMP_PATH" "$OUT_PATH"
  echo "[wiremock-init] Downloaded to $OUT_PATH"
fi

echo "[wiremock-init] Done"
