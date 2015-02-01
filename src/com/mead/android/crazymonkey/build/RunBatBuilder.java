package com.mead.android.crazymonkey.build;

import java.io.File;

public class RunBatBuilder extends CommandLineBuilder {

	public RunBatBuilder(String script) {
		super(script);
	}

	public String[] buildCommandLine(File script) {
		return new String[] { "cmd", "/c", "call", script.getAbsolutePath() };
	}
}
