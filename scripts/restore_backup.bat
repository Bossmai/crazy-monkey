set ADB_PATH=D:\tools\Android\android-sdk\platform-tools
set MONKEY_RUNNER_PATH=D:\tools\Android\android-sdk\tools
%ADB_PATH%\adb -s %1 restore %2
%MONKEY_RUNNER_PATH%\monkeyrunner D:\projects\private\crazy-monkey\scripts\restore_backup.py %1