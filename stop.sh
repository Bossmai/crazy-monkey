#!/bin/bash

# set the env
source ./setenv.sh

echo "-------------------- Clean the enviroment --------------------"
# Kill the adb
echo "[Android Emulator] Kill the adb server..."
pgrep adb | xargs -rt kill -9 | sed 's/^/[Android Emulator] &/g'

# Kill the emulator process
echo "[Android Emulator] Kill the emulator processes..."
pgrep emulator64-arm | xargs -rt kill -9 | sed 's/^/[Android Emulator] &/g'

# Clean the lock file of avd
echo "[Android Emulator] Clean the locked avd files..."
cd $ANDROID_SDK_HOME/.android/avd && find $ANDROID_SDK_HOME/.android/avd -name *.lock | xargs -rt rm -rf  | sed 's/^/[Android Emulator] &/g'
echo "[Android Emulator] Clean the modem files..."
cd $ANDROID_SDK_HOME/.android && rm modem-nv-ram-* | sed 's/^/[Android Emulator] &/g'

# Kill the java
echo "[Crazy Monkey] Kill the crazy monkey java process..."
ps aux | grep "bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar" | awk '{print $2}' | xargs -rt sudo kill -9 | sed 's/^/[Crazy Monkey] &/g'

# Tar the log files
echo "[Crazy Monkey] Compress all the log files..."
cd $CRAZY_MONKEY_HOME/logs && tar zcvf ./log_`date '+%Y-%m-%dT%H:%M:%S'`.zip ./*.log | sed 's/^/[Crazy Monkey] &/g'

echo "[Crazy Monkey] Remove the log files..."
cd $CRAZY_MONKEY_HOME/logs && rm *.log | sed 's/^/[Crazy Monkey] &/g'

echo "-------------------- Done --------------------"