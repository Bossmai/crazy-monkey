#!/bin/bash

# setup the git

# set the env
export USER_HOME=/home/ericchen
export JAVA_HOME=$USER_HOME/crazy-monkey/jdk1.7.0_75
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
export ANT_HOME=$USER_HOME/crazy-monkey/apache-ant-1.9.4

export VPN_CLINET_HOME=$USER_HOME/crazy-monkey/vpn-client
export ANDROID_SDK_HOME=$USER_HOME/crazy-monkey/android-sdk-linux
export CRAZY_MONKEY_HOME=$USER_HOME/crazy-monkey/crazy-monkey

export DISPLAY=:0.0
export LD_LIBRARY_PATH=$ANDROID_SDK_HOME/tools/lib

export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$ANDROID_SDK_HOME/tools:$ANDROID_SDK_HOME/platform-tools:$ANT_HOME/bin:$PATH

# Kill the emulator process
ps aux | grep emulator | awk '{print $2}' | xargs kill -9
# Clean the lock file of avd
ls -l $ANDROID_SDK_HOME/.android/avd | grep "\.avd" | awk '{print $9}' | xargs rm -rf *.lock

# Git update and build
cd $CRAZY_MONKEY_HOME
git pull
$ANT_HOME/bin/ant

# Run the testing
$JAVA_HOME/bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar

