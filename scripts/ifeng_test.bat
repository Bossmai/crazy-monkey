D:
CD D:\projects\private\crazy-monkey\scripts
D:\tools\Android\android-sdk\platform-tools\adb -s %1 install ifengnewsTest.apk
D:\tools\Android\android-sdk\platform-tools\adb -s %1 shell am instrument -w com.miles.ifeng.test/android.test.InstrumentationTestRunner