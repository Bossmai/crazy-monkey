D:
cd D:\tools\Android\android-sdk\platform-tools
adb -s %1 shell mount -rw -o remount -t yaffs2 /dev/block/mtdblock0 /system
adb -s %1 shell chmod 755 /system
exit