#!/bin/bash
$ANDROID_SDK_HOME/platform-tools/adb -s $1 install $CRAZY_MONKEY_HOME/apk/kuwo_6.6.0_test.apk
$ANDROID_SDK_HOME/platform-tools/adb -s $1 shell am instrument -w com.crazyroba/android.test.InstrumentationTestRunner