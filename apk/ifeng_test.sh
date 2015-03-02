#!/bin/bash
$ANDROID_SDK_HOME/platform-tools/adb -s $1 install $CRAZY_MONKEY_HOME/apk/ifeng_test.apk
$ANDROID_SDK_HOME/platform-tools/adb -s $1 shell am instrument -w com.miles.ifeng.test/android.test.InstrumentationTestRunner