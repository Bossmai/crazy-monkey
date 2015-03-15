#!/bin/bash

# set the env
source ./setenv.sh

# Kill the adb
echo "Kill the adb server"
pgrep adb | xargs kill -9

# Kill the emulator process
echo "Kill the emulator processes"
ps aux | grep emulator | awk '{print $2}' | xargs kill -9 

# Clean the lock file of avd
echo "Cleaning the locked avd files"
cd $ANDROID_SDK_HOME/.android/avd
ls -l $ANDROID_SDK_HOME/.android/avd | grep "\.avd" | awk '{print $9}' | xargs rm -rf *.lock

# Kill the java
echo "Kill the crazy monkey java process"
ps aux | grep "bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar" | awk '{print $2}' | xargs sudo kill -9

# Kill the vpn-daemon
echo "Kill the VPN daemon"
pgrep vpn-daemon | xargs sudo kill -9 

# Kill the main
echo "Kill the main sh"
ps aux | grep main | awk '{print $2}' | xargs kill -9

# Kill the pptp
echo "Kill the pptp processes"
ps aux | grep pptp | awk '{print $2}' | xargs sudo kill -9 

# Restart the net interface
echo "Restart the net interface"
sudo ifconfig $NETWORK_INTERFACE down
sudo ifconfig $NETWORK_INTERFACE up