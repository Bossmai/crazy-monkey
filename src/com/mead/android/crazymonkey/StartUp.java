package com.mead.android.crazymonkey;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
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
			final CrazyMonkeyBuild build = new CrazyMonkeyBuild(null);
			final AndroidSdk sdk = new AndroidSdk(build.getAndroidSdkHome(), build.getAndroidRootHome());
			CompletionService<Task> cs = new ExecutorCompletionService<Task>(threadPool);
	
			// TODO Get the real tasks list
			List<Task> tasks = null;
			TaskDAO taskDAO = null;
			
			if (args != null && args.length > 0 &&  args[0].equals("-debug")) {
				taskDAO = new DummyTask();
			} else {
				taskDAO = new MongoTask(build);
			}
			
			tasks = taskDAO.getTasksListByDay(build.getNumberOfEmulators(), Utils.getMACAddr(), new Date());
	
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				task.assignTask();
				if (args != null && args.length > 0 &&  args[0].equals("-debug")) {
					cs.submit(new RunScripts(build, task, sdk, StreamTaskListener.fromStdout()));
				} else {
					File f = new File(build.getLogPath() + "//" + task.getId() + ".log");
					f.getParentFile().mkdirs();
					f.createNewFile();
					FileOutputStream file = new FileOutputStream(f);
					cs.submit(new RunScripts(build, task, sdk, new StreamTaskListener(new BufferedOutputStream(file))));
				}
			}
			
			File f = new File(build.getLogPath() + "//build.log");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			buildLogFile = new BufferedWriter(new FileWriter(f, true));

			for (int i = 0; i < tasks.size(); i++) {
				Task task = cs.take().get();
				String result = String.format("[" + new Date() + "] - The task '%s' has been completed with status %s in %d seconds. \r\n", task.getId(), task.getStatus(), (task.getExceEndTime().getTime() - task.getAssignTime().getTime() / 1000) );
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
		//TODO update the tasks to the data base
		System.out.println("DONE");
	}
}
