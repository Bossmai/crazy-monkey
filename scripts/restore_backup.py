# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection(3)

device.wake()

device.touch(240, 450, 'DOWN_AND_UP')

MonkeyRunner.sleep(3)