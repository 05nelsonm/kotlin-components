#!/bin/bash

WORK_DIR=$(pwd)

function help() {
  echo "publish.sh help"
  echo ""
  echo "publish.sh <command> <option> <flag>"
  echo ""
  echo "            --host                        Builds and publishes gradle project for specified platform"
  echo ""
  echo "                       linux  --non-jvm   If --non-jvm flag present, will build and publish only the"
  echo "                                          Js, Linux, and Mingw targets. If no flag present, will"
  echo "                                          publish everything (ie. publishAllPublicationsToMavenCentralRepository)"
  echo ""
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
  ./gradlew clean -DKMP_TARGETS_ALL
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
        case $3 in
            "--non-jvm")
              # JVM is needed here so JS build won't throw compilation errors
              TARGETS="-PKMP_TARGETS=JVM,JS,LINUX_ARM32HFP,LINUX_MIPS32,LINUX_MIPSEL32,LINUX_X64,MINGW_X64,MINGW_X86"
              sync "$TARGETS"
              PUBLISH_TASKS=$(getPublishTasks '-e publishJs -e publishLinux -e publishMingw')

              if [ "$PUBLISH_TASKS" != "" ]; then
                build "$TARGETS"
                publish "$PUBLISH_TASKS"
              else
                echo "No non-jvm publication tasks available"
                exit 1
              fi
              ;;

            *)
              build "-DKMP_TARGETS_ALL"
              publish "publishAllPublicationsToMavenCentralRepository" "-DKMP_TARGETS_ALL"
              ;;
        esac
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
        sleep 15
        ;;

      *)
        help
        ;;

    esac
    ;;

  # Running on each platform for the given version requires that
  # publications on all platforms be published first.
  "--check-publication")
    checkGradlewExists
    checkKotlinComponentsProject
    checkCheckPublicationProjectExists
    clean
    ./gradlew :tools:check-publication:build --refresh-dependencies -PCHECK_PUBLICATION
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

  *)
    help
    ;;

esac

sleep 1
exit 0
