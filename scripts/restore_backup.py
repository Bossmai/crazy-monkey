# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import sys
# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection(100, sys.argv[1])
device.wake()
MonkeyRunner.sleep(3)
device.touch(240, 450, 'DOWN_AND_UP')
MonkeyRunner.sleep(5)