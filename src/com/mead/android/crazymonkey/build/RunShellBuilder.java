package com.mead.android.crazymonkey.build;

import java.io.File;

public class RunShellBuilder extends CommandLineBuilder {

	public RunShellBuilder(String script) {
		super(script);
	}

	private String cmd = "/bin/sh";

	public String[] buildCommandLine(File script) {
		return new String[] { cmd, "-xe", script.getAbsolutePath() };
	}
}
