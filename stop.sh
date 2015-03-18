#!/bin/bash

# set the env
source ./setenv.sh

echo "----- Clean the enviroment -----"
# Kill the adb
echo "Kill the adb server..."
pgrep adb | xargs -rt kill -9

# Kill the emulator process
echo "Kill the emulator processes..."
pgrep emulator64-arm | xargs -rt kill -9

# Clean the lock file of avd
echo "Clean the locked avd files..."
cd $ANDROID_SDK_HOME/.android/avd && find $ANDROID_SDK_HOME/.android/avd -name *.lock | xargs -rt rm -rf

# Kill the java
echo "Kill the crazy monkey java process..."
ps aux | grep "bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar" | awk '{print $2}' | xargs -rt sudo kill -9

# Tar the log files
echo "Compress all the log files..."
cd $CRAZY_MONKEY_HOME/logs && tar zcvf ./log_`date '+%Y-%m-%dT%H:%M:%S'`.zip ./*.log

echo "Remove the log files..."
cd $CRAZY_MONKEY_HOME/logs && rm *.log 

echo "----- Done -----"