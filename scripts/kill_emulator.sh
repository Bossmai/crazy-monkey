#!/bin/bash
# $1 Android_Monkey_1
# $2 emulator_5555

echo "[Android Emulator] Kill the emulator64-arm by the name: $1"
pid=`ps aux | grep $1 | grep -v grep | awk '{print $2}'`
echo "[Android Emulator] The process id is $pid"

if [ -n "$pid" ]
then
	echo "[Android Emulator] Killing the pid: $pid"
	timeout 5 kill -9 $pid
fi

avd_file=$ANDROID_SDK_HOME/.android/avd/$1.avd
echo "[Android Emulator] Remove the locked files from avd directory: $avd_file"

if [ -f $avd_file ]
then
	echo "[Android Emulator] Removing the locked files: $avd_file"
	rm -f $avd_file/*.lock
fi

echo "[Android Emulator] Kill the adb process"
adb_pid=`ps aux | grep "adb -s $2" | grep -v grep | awk '{print $2}'`

if [ -n "$adb_pid" ]
then	
	echo "[Android Emulator] Killing the adb pid: $adb_pid"
	timeout 5 kill -9 $adb_pid
fi
