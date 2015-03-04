#!/bin/bash

export JAVA_HOME=/home/ericchen/crazy-monkey/jdk1.7.0_75
export JRE_HOME=$JAVA_HOME/jre
export ANDROID_SDK_HOME=/home/ericchen/crazy-monkey/android-sdk-linux
export ANT_HOME=/home/ericchen/crazy-monkey/apache-ant-1.9.4
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
export CRAZY_MONKEY_HOME=/home/ericchen/crazy-monkey/crazy-monkey

export DISPLAY=:0.0
export LD_LIBRARY_PATH=$ANDROID_SDK_HOME/tools/lib

export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$ANDROID_SDK_HOME/tools:$ANDROID_SDK_HOME/platform-tools:$ANT_HOME/bin:$PATH

cd /home/ericchen/crazy-monkey/crazy-monkey
/home/ericchen/crazy-monkey/jdk1.7.0_75/bin/java -jar /home/ericchen/crazy-monkey/crazy-monkey/crazy-monkey-0.1.jar