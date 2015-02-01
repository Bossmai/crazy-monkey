package com.mead.android.crazymonkey.build;

import java.io.File;

import com.mead.android.crazymonkey.AndroidEmulatorContext;

public class RunShellBuilder extends CommandLineBuilder {

	public RunShellBuilder(String script) {
		super(script);
	}

	private String cmd = "/bin/sh";

	public String[] buildCommandLine(File script, AndroidEmulatorContext emuContext) {
		return new String[] { cmd, "-xe", script.getAbsolutePath(), emuContext.getSerial() };
	}
}
