#!/usr/bin/env bash
# Запуск всего набора тестов. Отчёт Allure складывается в target/allure-results (см. pom.xml).
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
cd "${PROJECT_DIR}"

export PLATFORM="${PLATFORM:-android}"

mvn -Dtest=suites.TestSuite test
