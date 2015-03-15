#!/bin/bash

# set the env
source ./setenv.sh

# Kill the adb
echo "Kill the adb server"
pgrep adb | xargs -rt kill -9

# Kill the emulator process
echo "Kill the emulator processes"
ps aux | grep emulator | awk '{print $2}' | xargs -rt kill -9 

# Clean the lock file of avd
echo "Cleaning the locked avd files"
cd $ANDROID_SDK_HOME/.android/avd
# ls -l /home/ericchen/crazy-monkey/android-sdk-linux/.android/avd | grep "\.avd" | awk '{print $9}' | xargs -rt rm -rf *.lock
ls -l $ANDROID_SDK_HOME/.android/avd | grep "\.avd" | awk '{print $9}' | xargs -rt rm -rf *.lock

# Kill the java
echo "Kill the crazy monkey java process"
ps aux | grep "bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar" | awk '{print $2}' | xargs -rt sudo kill -9