#!/bin/bash

WORK_DIR=$(pwd)

function help() {
  echo "publish.sh help"
  echo ""
  echo "publish.sh <command> <option> <flag>"
  echo ""
  echo "            --host                        Builds and publishes gradle project for specified platform"
  echo ""
  echo "                       linux"
  echo "                       darwin"
  echo "                       mingw"
  echo ""
  echo "            --check-publication           Verifies MavenCentral publications are good"
  echo ""
  echo "            --publish-android             Publishes only the android source set"
  echo ""
  echo "            --publish-jvm                 Publishes only the jvm source set"
  echo ""
  echo "            --publish-multiplatform       Publishes the multiplatform sources"
  echo ""
  echo "            --publish-multiplatform-android-jvm-js"
  echo ""
  echo "            --publish-npm                 Publishes npm packages to specified registry"
  echo ""
  echo "                       verdaccio"
  echo "                       npmjs"
  echo ""
}

function checkGradlewExists() {
  if [ ! -f "$WORK_DIR/gradlew" ]; then
    echo ""
    echo "    SCRIPT: gradlew file not found"
    echo ""
    exit 1
  fi
}

function checkKotlinComponentsProject() {
  if [ ! -d "$WORK_DIR/kotlin-components" ]; then
    echo ""
    echo "    SCRIPT: Current working directory must be a subproject of kotlin-components"
    echo ""
    exit 1
  fi
}

function checkCheckPublicationProjectExists() {
  if [ ! -f "$WORK_DIR/tools/check-publication/build.gradle.kts" ]; then
    echo ""
    echo "    SCRIPT: Project :tools:check-publication does not exist"
    echo ""
    exit 1
  fi
}

function clean() {
  echo ""
  echo "    SCRIPT: ./gradlew clean -DKMP_TARGETS_ALL"
  echo ""

  if ! ./gradlew clean -DKMP_TARGETS_ALL; then
    echo ""
    echo "    SCRIPT: Clean failure. Exiting"
    echo ""
    exit 1
  fi
}

function sync() {
  # $1 = KMP_TARGETS

  echo ""
  echo "    SCRIPT: ./gradlew prepareKotlinBuildScriptModel $1"
  echo ""

  if ! ./gradlew prepareKotlinBuildScriptModel $1; then
    echo ""
    echo "    SCRIPT: Sync failure. Exiting"
    echo ""
    exit 1
  fi
}

function build() {
  # $1 = KMP_TARGETS

  echo ""
  echo "    SCRIPT: ./gradlew build --no-daemon --no-parallel $1"
  echo ""

  if ! ./gradlew build --no-daemon --no-parallel $1; then
    echo ""
    echo "    SCRIPT: Build failure. Exiting."
    echo ""
    exit 1
  fi
}

function getPublishTasks() {
  # $1 = KMP_TARGETS
  # $2 = grep filter

  ./gradlew tasks $1 |
  grep "ToMavenCentralRepository" |
  cut -d ' ' -f 1 |
  grep $2
}

function getNpmPublishTasks() {
  # $1 = Verdaccio or Npmjs

  ./gradlew tasks |
  grep "NpmPublicationTo$1" |
  cut -d ' ' -f 1
}

function publish() {
  # $1 = publish tasks
  # $2 = KMP_TARGETS

  echo ""
  echo "    SCRIPT: ./gradlew $1 --no-daemon --no-parallel $2"
  echo ""

  ./gradlew $1 --no-daemon --no-parallel $2
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
        TARGETS="-PKMP_TARGETS=JVM,JS,IOS_ARM32,IOS_ARM64,IOS_X64,MACOS_ARM64,MACOS_X64,TVOS_ARM64,TVOS_X64,WATCHOS_ARM32,WATCHOS_ARM64,WATCHOS_DEVICE_ARM64,WATCHOS_X64,WATCHOS_X86,IOS_SIMULATOR_ARM64,TVOS_SIMULATOR_ARM64,WATCHOS_SIMULATOR_ARM64"
        sync "$TARGETS"
        PUBLISH_TASKS=$(getPublishTasks "$TARGETS" '-e publishIos -e publishMacos -e publishTvos -e publishWatchos')

        if [ "$PUBLISH_TASKS" != "" ]; then
          build "$TARGETS"
          publish "$PUBLISH_TASKS" "$TARGETS"
        else
          echo ""
          echo "    SCRIPT: No darwin publication tasks available"
          echo ""
          exit 1
        fi
        ;;

      "mingw")
        TARGETS="-PKMP_TARGETS=MINGW_X64,MINGW_X86"
        sync "$TARGETS"
        PUBLISH_TASKS=$(getPublishTasks "$TARGETS" '-e Mingw')
        build "$TARGETS"

        echo ""
        echo "Windows hates bash..."
        echo "paste the following into powershell"
        echo ""
        echo "./gradlew $PUBLISH_TASKS --no-parallel --no-daemon $TARGETS"
        echo ""
        sleep 15
        ;;

      *)
        help
        ;;

    esac
    ;;

  # Requires that publications for all platforms be published first, otherwise will fail
  # Can be run from a single platform as KMP_TARGETS_ALL is enabled.
  "--check-publication")
    checkGradlewExists
    checkKotlinComponentsProject
    checkCheckPublicationProjectExists
    clean
    ./gradlew :tools:check-publication:build --refresh-dependencies -PCHECK_PUBLICATION -DKMP_TARGETS_ALL
    ;;

  "--publish-android")
    checkGradlewExists
    checkKotlinComponentsProject
    clean
    build "-PKMP_TARGETS=ANDROID,JVM"
    publish "publishAndroidReleasePublicationToMavenCentralRepository" "-PKMP_TARGETS=ANDROID,JVM"
    ;;

  "--publish-jvm")
    checkGradlewExists
    checkKotlinComponentsProject
    clean
    build "-PKMP_TARGETS=JVM"
    publish "publishJvmPublicationToMavenCentralRepository" "-PKMP_TARGETS=JVM"
    ;;

  "--publish-multiplatform")
    checkGradlewExists
    checkKotlinComponentsProject
    clean
    build "-DKMP_TARGETS_ALL"
    publish "publishKotlinMultiplatformPublicationToMavenCentralRepository" "-DKMP_TARGETS_ALL"
    ;;

  "--publish-multiplatform-android-jvm-js")
    checkGradlewExists
    checkKotlinComponentsProject
    clean
    build "-DKMP_TARGETS_ALL"
    publish "publishKotlinMultiplatformPublicationToMavenCentralRepository publishAndroidReleasePublicationToMavenCentralRepository publishJsPublicationToMavenCentralRepository publishJvmPublicationToMavenCentralRepository" "-DKMP_TARGETS_ALL"
    ;;

  "--publish-npm")
    checkGradlewExists
    checkKotlinComponentsProject
    clean
    sync "-DKMP_TARGETS_ALL"

    PUBLISH_TASKS=

    case $2 in
      "verdaccio")
        PUBLISH_TASKS=$(getNpmPublishTasks "Verdaccio")
        ;;

      "npmjs")
        PUBLISH_TASKS=$(getNpmPublishTasks "Npmjs")
        ;;

      *)
        help
        ;;
    esac

    if [ "$PUBLISH_TASKS" != "" ]; then
      publish "$PUBLISH_TASKS" ""
    fi
    ;;

  *)
    help
    ;;

esac

sleep 1
exit 0
