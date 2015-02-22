package com.mead.android.crazymonkey;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mead.android.crazymonkey.model.Task;
import com.mead.android.crazymonkey.persistence.DummyTask;
import com.mead.android.crazymonkey.persistence.MongoTask;
import com.mead.android.crazymonkey.persistence.TaskDAO;
import com.mead.android.crazymonkey.sdk.AndroidSdk;
import com.mead.android.crazymonkey.util.Utils;

public class StartUp {

	public static void main(String[] args) {
		BufferedWriter buildLogFile = null;
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		try {
			final CrazyMonkeyBuild build = new CrazyMonkeyBuild();
			final AndroidSdk sdk = new AndroidSdk(build.getAndroidSdkHome(), build.getAndroidRootHome());
			CompletionService<Task> cs = new ExecutorCompletionService<Task>(threadPool);
	
			List<Task> tasks = null;
			TaskDAO taskDAO = null;
			
			if (args != null && args.length > 0 &&  args[0].equals("-debug")) {
				taskDAO = new DummyTask();
			} else {
				taskDAO = new MongoTask(build);
			}
			
			tasks = taskDAO.getTasks(build.getNumberOfEmulators(), Utils.getMACAddr(), new Date());
	
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				task.setAssignTime(new Date());
				if (args != null && args.length > 0 &&  args[0].equals("-debug")) {
					cs.submit(new RunScripts(build, task, sdk, StreamTaskListener.fromStdout()));
				} else {
					FileOutputStream file = getLoggerForTask(build, task);
					cs.submit(new RunScripts(build, task, sdk, new StreamTaskListener(new BufferedOutputStream(file))));
				}
			}
			buildLogFile = getLoggerForBuild(build);
			for (int i = 0; i < tasks.size(); i++) {
				Task task = cs.take().get();
				String result = String.format("[" + new Date() + "] - The task '%s' has been completed with status %s in %d seconds. \r\n",
						task.getId(), task.getStatus(), (task.getExecEndTime().getTime() - task.getAssignTime().getTime()) / 1000);
				taskDAO.updateTask(task);
				buildLogFile.append(result);
			}
			
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (ExecutionException ee) {
			ee.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buildLogFile != null) {
				try {
					buildLogFile.flush();
					buildLogFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			threadPool.shutdown();
		}
		
		
		System.out.println("DONE");
		System.exit(0);
	}

	public static BufferedWriter getLoggerForBuild(final CrazyMonkeyBuild build) throws IOException {
		BufferedWriter buildLogFile;
		File f = new File(build.getLogPath() + "//build.log");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		buildLogFile = new BufferedWriter(new FileWriter(f, true));
		return buildLogFile;
	}

	public static FileOutputStream getLoggerForTask(final CrazyMonkeyBuild build, Task task) throws IOException, FileNotFoundException {
		File f = new File(build.getLogPath() + "//" + task.getId() + ".log");
		f.getParentFile().mkdirs();
		f.createNewFile();
		FileOutputStream file = new FileOutputStream(f);
		return file;
	}
}
