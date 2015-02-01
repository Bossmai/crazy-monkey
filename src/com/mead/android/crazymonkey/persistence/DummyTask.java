package com.mead.android.crazymonkey.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mead.android.crazymonkey.AndroidEmulator;
import com.mead.android.crazymonkey.model.AppRunner;
import com.mead.android.crazymonkey.model.Phone;
import com.mead.android.crazymonkey.model.Slaver;
import com.mead.android.crazymonkey.model.Task;

public class DummyTask implements TaskDAO {

	@Override
	public List<Task> getTasksListByDay(int times, String slaverMac, Date date) {

		List<Task> tasks = new ArrayList<Task>();

		Task task = new Task();

		task.setId("ifengnew_xxxxxxxxx1");
		task.setJobId("1");

		Phone phone = new Phone();
		phone.setIMEI("869585015357569");
		phone.setIMSI("460030912121001");
		phone.setManufacturer("中兴");
		phone.setModelId("ZTEU817");
		phone.setModelName("ZTEU817");
		phone.setWifiMAC("09-98-AC-A9-65-C3");

		Slaver slaver = new Slaver();
		slaver.setSlaverIP("192.168.3.105");
		slaver.setSlaverMAC("8C-70-5A-9C-71-C4");
		slaver.setVpnIP("192.168.56.1");
		slaver.setVpnMAC("08-00-27-00-A0-17");

		
		AndroidEmulator emulator = new AndroidEmulator(
			"Android_monkey_1",
			"android-17",
			"160",
			"HVGA",
			"en_US",
			"100M",
			false,
			true,
			false,
			"-partition-size 300",
			"armeabi-v7a",
			"D://tools//Android//android-sdk",
			"",
			""
		);
		
		

		List<AppRunner> appRunners = new ArrayList<AppRunner>();
		AppRunner appRunner = new AppRunner();
		appRunner.setAppId("ifengnew1");
		appRunner.setAppName("凤凰新闻");
		appRunner.setAppType("ifengnew");
		appRunner.setPackageName("com.ifeng.new2");
		appRunner.setScriptName("ifengnew1_new.zip");
		appRunner.setScriptType("New");

		AppRunner appRunner2 = new AppRunner();
		appRunner2.setAppId("ifengnew1");
		appRunner2.setAppName("凤凰新闻");
		appRunner2.setAppType("ifengnew");
		appRunner2.setPackageName("com.ifeng.new2");
		appRunner2.setScriptName("ifengnew_alive.zip");
		appRunner2.setScriptType("Alive");

		appRunners.add(appRunner);
		appRunners.add(appRunner2);

		task.setPhone(phone);
		task.setEmulator(emulator);
		task.setSlaver(slaver);
		task.setAppRunners(appRunners);

		task.setPlanExecDate(new Date());
		task.setPlanExecPeriod("7-22");
		task.setStatus(Task.STATUS.NONE);

		Task task2 = new Task();

		task2.setId("ifengnew_xxxxxxxxx2");
		task2.setJobId("1");
		
		AndroidEmulator emulator2 = new AndroidEmulator(
			"Android_monkey_2",
			"android-17",
			"160",
			"HVGA",
			"en_US",
			"100M",
			false,
			true,
			false,
			"-partition-size 300",
			"armeabi-v7a",
			"D://tools//Android//android-sdk",
			"",
			""
		); 

		List<AppRunner> appRunners2 = new ArrayList<AppRunner>();
		AppRunner appRunner3 = new AppRunner();
		appRunner3.setAppId("ifengnew1");
		appRunner3.setAppName("凤凰新闻");
		appRunner3.setAppType("ifengnew");
		appRunner3.setPackageName("com.ifeng.new2");
		appRunner3.setScriptName("ifengnew_alive.zip");
		appRunner3.setScriptType("Alive");

		appRunners2.add(appRunner3);

		task2.setPhone(phone);
		task2.setEmulator(emulator2);
		task2.setSlaver(slaver);
		task2.setAppRunners(appRunners2);

		task2.setPlanExecDate(new Date());
		task2.setPlanExecPeriod("7-22");
		task2.setStatus(Task.STATUS.NONE);

		tasks.add(task);
		tasks.add(task2);

		return tasks;
	}

	@Override
	public List<Task> getTasks(int times, String slaverMac, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean assignTask(List<Task> tasks, String id) {
		Task task = this.getTaskById(tasks, id);
		if (task != null) {
			task.setAssignTime(new Date());
			if (task.getStatus() == Task.STATUS.NONE) {
				task.setStatus(Task.STATUS.READY);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean startTask(List<Task> tasks, String id) {
		Task task = this.getTaskById(tasks, id);
		if (task != null) {
			task.setExecStartTime(new Date());
			if (task.getStatus() == Task.STATUS.READY) {
				task.setStatus(Task.STATUS.PROCESSING);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean compelteTask(List<Task> tasks, String id, Task.STATUS result, String log) {
		Task task = this.getTaskById(tasks, id);
		if (task != null) {
			task.setExceEndTime(new Date());
			if (task.getStatus() == Task.STATUS.PROCESSING) {
				task.setStatus(result);
				task.setLog(log);
				return true;
			}
		}
		return false;
	}

	@Override
	public Task getTaskById(List<Task> tasks, String id) {
		Task task = null;
		if (tasks != null && !tasks.isEmpty()) {
			for (Task t : tasks) {
				if (id != null && id.equals(t.getId())) {
					return t;
				}
			}
		}
		return task;
	}

}
