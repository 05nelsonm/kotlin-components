#!/bin/bash

WORK_DIR=$(pwd)

function help() {
  echo "publish.sh help"
  echo ""
  echo "publish.sh <command> <option>"
  echo ""
  echo "            --host                 builds and publishes gradle project for specified platform"
  echo "                       linux"
  echo "                       darwin"
  echo "                       mingw"
  echo ""
  echo "            --check-publication    verifys MavenCentral publications are good"
  echo ""
}

function checkGradlewExists() {
  if [ ! -f "$WORK_DIR/gradlew" ]; then
    echo ""
    echo "gradlew file not found"
    echo ""
    exit 1
  fi
}

function checkKotlinComponentsProject() {
  if [ ! -d "$WORK_DIR/kotlin-components" ]; then
    echo ""
    echo "current working directory must be a subproject of kotlin-components"
    echo ""
    exit 1
  fi
}

function checkCheckPublicationProjectExists() {
  if [ ! -f "$WORK_DIR/tools/check-publication/build.gradle.kts" ]; then
    echo ""
    echo "project :tools:check-publication does not exist"
    echo ""
    exit 1
  fi
}

function clean() {
  ./gradlew clean
}

function sync() {
  ./gradlew prepareKotlinBuildScriptModel $1
}

function build() {
  ./gradlew build --no-daemon --no-parallel $1
}

function getPublishTasks() {
  ./gradlew tasks |
  grep "ToMavenCentralRepository" |
  cut -d ' ' -f 1 |
  grep $1
}

function publish() {
  ./gradlew $1 --no-parallel --no-daemon $2
}

case $1 in

  "--host")
    checkGradlewExists
    checkKotlinComponentsProject
    clean

    case $2 in

      "linux")
        build "-DKMP_TARGETS_ALL"
        publish "publishAllPublicationsToMavenCentralRepository" "-DKMP_TARGETS_ALL"
        ;;

      "darwin")
        sync
        PUBLISH_TASKS=$(getPublishTasks '-e Ios -e Macos -e Tvos -e Watchos')
        build
        publish "$PUBLISH_TASKS"
        ;;

      "mingw")
        sync
        PUBLISH_TASKS=$(getPublishTasks '-e Mingw')
        build

        echo ""
        echo "Windows hates bash..."
        echo "paste the following into powershell"
        echo ""
        echo "./gradlew $PUBLISH_TASKS --no-parallel --no-daemon"
        echo ""
        sleep 10
        ;;

      *)
        help
        ;;

    esac
    ;;

  "--check-publication")
    checkGradlewExists
    checkKotlinComponentsProject
    checkCheckPublicationProjectExists
    clean
    ./gradlew :tools:check-publication:build --refresh-dependencies -PCHECK_PUBLICATION -DKMP_TARGETS_ALL
    ;;

  *)
    help
    ;;

esac

exit 0
