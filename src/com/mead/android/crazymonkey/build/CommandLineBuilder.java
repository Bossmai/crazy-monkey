package com.mead.android.crazymonkey.build;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import com.mead.android.crazymonkey.AndroidEmulator;
import com.mead.android.crazymonkey.AndroidEmulatorContext;
import com.mead.android.crazymonkey.CrazyMonkeyBuild;
import com.mead.android.crazymonkey.StreamTaskListener;
import com.mead.android.crazymonkey.process.ProcStarter;
import com.mead.android.crazymonkey.sdk.AndroidSdk;
import com.mead.android.crazymonkey.util.Utils;

public abstract class CommandLineBuilder extends Builder{
	
	private String script;
	
	public CommandLineBuilder(String script) {
		this.script = script;
	}
	
	public abstract String[] buildCommandLine(File script);
	
	public boolean perform(CrazyMonkeyBuild build, AndroidSdk androidSdk, AndroidEmulator emulator, AndroidEmulatorContext emuContext,
			StreamTaskListener taskListener) throws IOException, InterruptedException {

		final PrintStream logger = taskListener.getLogger();
		File file = new File(script);

		if (!file.exists()) {
			AndroidEmulator.log(logger, String.format("The script file '%s' is not existing.", script));
			return false;
		}
		int r = -1;
		try {
			Map<String, String> buildEnvironment = new TreeMap<String, String>();
			//buildEnvironment.put("ANDROID_ADB_SERVER_PORT", Integer.toString(adbServerPort));
			if (androidSdk.hasKnownHome()) {
				buildEnvironment.put("ANDROID_SDK_HOME", androidSdk.getSdkHome());
			}
			if (Utils.isUnix()) {
				buildEnvironment.put("LD_LIBRARY_PATH", String.format("%s/tools/lib", androidSdk.getSdkRoot()));
			}
			r = new ProcStarter().cmds(buildCommandLine(file)).stdout(taskListener).start().join();
		} catch (IOException e) {
			e.printStackTrace();
			AndroidEmulator.log(logger, String.format("Run the batch file '%s' failed.", script));
		}
		return r == 0;
	}
}
