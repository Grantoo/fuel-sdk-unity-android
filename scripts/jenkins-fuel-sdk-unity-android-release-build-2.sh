#!/bin/bash

if ! [ -d .git ] && ! `git rev-parse --git-dir > /dev/null 2>&1`; then
    echo Script must be run within the scripts folder of the repository
    exit 1
fi

REPOSITORY_PATH=`git rev-parse --show-toplevel`

if [[ `pwd` != "$REPOSITORY_PATH/scripts" ]]; then 
    echo Script must be run within the scripts folder of the repository
    exit 1
fi

ROOT_PATH=`dirname $REPOSITORY_PATH`
FUEL_SDK_PATH="$ROOT_PATH/fuel-sdk"
OUTPUT_PATH="$ROOT_PATH/build"

# create output folder
rm -rf $OUTPUT_PATH
mkdir -p $OUTPUT_PATH

# ---------------------------------------------------------------------------------------------------- #
# NOTE:  Do not modify the script above unless you know what you are doing. It's a standard script for #
#        initializing and validating the build filesystem and environment variables common to all      #
#        client SDK related Jenkins jobs.                                                              #
# ---------------------------------------------------------------------------------------------------- #

PROJECT_PATH="$REPOSITORY_PATH/fuelsdkunity"
OUTPUT_FILENAME=fuelsdkunity.aar

# ensuring execute permission on gradle
cd $REPOSITORY_PATH
if ! chmod +x ./gradlew ; then
    exit 1
fi

# running gradle
cd $REPOSITORY_PATH
if ! ./gradlew clean assembleRelease ; then
    exit 1
fi

# copying generated artifacts to the output folder
cd $OUTPUT_PATH
if ! cp $PROJECT_PATH/build/outputs/aar/fuelsdkunity.aar $OUTPUT_FILENAME ; then
    exit 1
fi
