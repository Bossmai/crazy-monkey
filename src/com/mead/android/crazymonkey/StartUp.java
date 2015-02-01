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
import com.mead.android.crazymonkey.persistence.TaskDAO;
import com.mead.android.crazymonkey.sdk.AndroidSdk;

public class StartUp {

	public static void main(String[] args) throws IOException {
		
		final CrazyMonkeyBuild build = new CrazyMonkeyBuild(null);
		final AndroidSdk sdk = new AndroidSdk(build.getAndroidSdkHome(), build.getAndroidRootHome());

		ExecutorService threadPool = Executors.newCachedThreadPool();
		CompletionService<Task> cs = new ExecutorCompletionService<Task>(threadPool);

		// TODO Get the real tasks list
		TaskDAO taskDAO = new DummyTask();
		List<Task> tasks = taskDAO.getTasksListByDay(build.getNumberOfEmulators(), "AC-DE-35-43-97-0E", new Date());

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

		BufferedWriter file = null;
		try {
			File f = new File(build.getLogPath() + "//build.log");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			file = new BufferedWriter(new FileWriter(f, true));

			for (int i = 0; i < tasks.size(); i++) {
				Task task = cs.take().get();
				String result = String.format("[" + new Date() + "] - The task '%s' has been completed with status %s. \r\n", task.getId(),
						task.getStatus());
				file.append(result);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				file.flush();
				file.close();
			}
			threadPool.shutdown();
		}
		System.out.println("DONE");
	}
}
