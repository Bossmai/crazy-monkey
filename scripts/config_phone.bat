set ADB_PATH=D:\tools\Android\android-sdk\platform-tools
set USER_DATA_PATH=D:\projects\private\crazy-monkey\userdata

%ADB_PATH%\adb -s %1 push %USER_DATA_PATH%\xposeDevice.txt /mnt/sdcard/.system
%ADB_PATH%\adb -s %1 shell am instrument -w com.crazymonkey.test009/android.test.InstrumentationTestRunner