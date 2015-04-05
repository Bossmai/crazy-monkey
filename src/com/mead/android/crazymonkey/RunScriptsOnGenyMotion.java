package com.mead.android.crazymonkey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mead.android.crazymonkey.build.Builder;
import com.mead.android.crazymonkey.model.Task;
import com.mead.android.crazymonkey.model.Task.STATUS;
import com.mead.android.crazymonkey.persistence.MongoTask;
import com.mead.android.crazymonkey.persistence.TaskDAO;
import com.mead.android.crazymonkey.sdk.AndroidSdk;
import com.mead.android.crazymonkey.sdk.SdkInstallationException;
import com.mead.android.crazymonkey.sdk.Tool;
import com.mead.android.crazymonkey.util.Utils;

public class RunScriptsOnGenyMotion implements java.util.concurrent.Callable<Task> {

	private static ObjectMapper objectMapper = new ObjectMapper();

	private CrazyMonkeyBuild build;

	private Task task;

	private AndroidSdk androidSdk;

	private StreamTaskListener taskListener;

	private PrintStream logger;

	public RunScriptsOnGenyMotion(CrazyMonkeyBuild build, Task task, AndroidSdk androidSdk, StreamTaskListener taskListener) {
		super();
		this.build = build;
		this.task = task;
		this.androidSdk = androidSdk;
		this.taskListener = taskListener;
	}

	@Override
	public Task call() throws Exception {

		boolean configPhoneSuccess = configPhoneInfo();
		try {
			if (configPhoneSuccess) {
				runScripts();
			}
		} catch (InterruptedException | IOException e) {
			task.setStatus(STATUS.FAILURE);
			String msg = String.format("The task '%d' is interrputed. ", task.getAppRunner().getAppId());
			Utils.log(logger, msg);
			System.out.println("[" + new Date() + "]" + msg);
		} finally {
			try {
				tearDown();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public void tearDown() throws IOException, InterruptedException {
		build.freeEmulator(this.task.getEmulator().getAvdName());
		
		TaskDAO taskDAO = new MongoTask(build);
		taskDAO.updateTask(task);
		
		long executeTime = (System.currentTimeMillis() - task.getAssignTime().getTime()) / 1000;
		String result = String.format("The monkey task '%s' has been completed %s in %d seconds. ",
				task.getId(), task.getStatus(), executeTime);
		
		AndroidEmulator.log(logger,result);
        logger.flush();
        logger.close();
        
        System.out.println("[" + new Date() + "] - " + result);
	}
	
	public boolean runScripts() throws IOException, InterruptedException {
		boolean result = false;
		task.startTask();
		String script = build.getApkFilePath() + "//" + task.getAppRunner().getScriptName() + "_gm";

		if (Utils.isUnix()) {
			script += ".sh";
		} else {
			script += ".bat";
		}

		List<String> args = new ArrayList<String>();
		args.add(CrazyMonkeyBuild.getGenyMotionEmulatorName(task.getEmulator().getAvdName()));

		Builder builder = Utils.getBuilder(script, args);
		int tryNum = 2;
		result = runTestCase(script, builder, tryNum);
		return result;
	}

	public boolean runTestCase(String script, Builder builder, int tryNum) throws IOException, InterruptedException {
		boolean result;
		result = builder.perform(build, androidSdk, task.getEmulator(), null, taskListener, "Monkey success.");

		if (!result) {
			Utils.log(logger, String.format("Run the script '%s' failed.", script));
			if ((System.currentTimeMillis() - task.getExecStartTime().getTime()) / 1000 >= build.getRunScriptTimeout()) {
				task.compelteTask(STATUS.NOT_COMPLETE);
			} else {
				if (tryNum >= 1) {
					result = runTestCase(script, builder, --tryNum);
				} else {
					task.compelteTask(STATUS.NOT_COMPLETE);
				}
			}
		} else {
			Utils.log(logger, String.format("Run the script '%s' scussfully.", script));
			task.compelteTask(STATUS.SUCCESS);
		}
		return result;
	}

	private void writeDeviceTxt() throws IOException, FileNotFoundException, JsonGenerationException, JsonMappingException {
		File f = new File(build.getUserDataPath() + "//xposeDevice.txt");
		f.getParentFile().mkdirs();
		f.createNewFile();
		FileOutputStream file = new FileOutputStream(f);
		objectMapper.writeValue(file, task.getPhone());
	}

	public synchronized boolean configPhoneInfo() throws IOException, InterruptedException {

		if (logger == null) {
			logger = taskListener.getLogger();
		}

		writeDeviceTxt();

		String script = build.getTestScriptPath() + "//config_phone.bat";
		if (Utils.isUnix()) {
			script = build.getTestScriptPath() + "//config_phone.sh";
		}

		List<String> args = new ArrayList<String>();
		args.add(CrazyMonkeyBuild.getGenyMotionEmulatorName(task.getEmulator().getAvdName()));

		String androidToolsDir = "";
		if (androidSdk.hasKnownRoot()) {
			try {
				androidToolsDir = androidSdk.getSdkRoot() + Tool.EMULATOR.findInSdk(androidSdk);
			} catch (SdkInstallationException e) {
				androidToolsDir = "";
			}
		} else {
			androidToolsDir = "";
		}
		args.add(androidToolsDir);

		Builder builder = Utils.getBuilder(script, args);

		boolean result = builder.perform(build, androidSdk, task.getEmulator(), null, taskListener, "OK (1 test)");

		if (!result) {
			Utils.log(logger, String.format("Config the phone information '%s' failed.", script));
			task.setStatus(STATUS.NOT_BUILT);
		} else {
			Utils.log(logger, String.format("Config the phone information '%s' scussfully.", script));
		}
		return result;
	}

}
