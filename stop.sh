#!/bin/bash

export USER_HOME=/home/ericchen
export ANDROID_SDK_HOME=$USER_HOME/crazy-monkey/android-sdk-linux

# Kill the emulator process
ps aux | grep emulator | awk '{print $2}' | xargs kill -9
# Clean the lock file of avd
ls -l $ANDROID_SDK_HOME/.android/avd | grep "\.avd" | awk '{print $9}' | xargs rm -rf *.lock