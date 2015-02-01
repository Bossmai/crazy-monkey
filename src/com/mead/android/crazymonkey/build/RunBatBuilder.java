package com.mead.android.crazymonkey.build;

import java.io.File;

import com.mead.android.crazymonkey.AndroidEmulatorContext;

public class RunBatBuilder extends CommandLineBuilder {

	public RunBatBuilder(String script) {
		super(script);
	}

	public String[] buildCommandLine(File script, AndroidEmulatorContext emuContext) {
		return new String[] { "cmd", "/c", "call", script.getAbsolutePath(), emuContext.getSerial() };
	}
}
