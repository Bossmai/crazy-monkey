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

		AppRunner appRunner = new AppRunner();
		appRunner.setAppId("ifeng.apk");
		appRunner.setAppName("凤凰新闻");
		appRunner.setAppType("ifengnew");
		appRunner.setPackageName("com.ifeng.new2");
		appRunner.setScriptName("123.bat");
		appRunner.setScriptType("New");

		task.setPhone(phone);
		task.setEmulator(emulator);
		task.setSlaver(slaver);
		task.setAppRunner(appRunner);

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

		AppRunner appRunner3 = new AppRunner();
		appRunner3.setAppId("ifeng.apk");
		appRunner3.setAppName("凤凰新闻");
		appRunner3.setAppType("ifengnew");
		appRunner3.setPackageName("com.ifeng.new2");
		appRunner3.setScriptName("123.bat");
		appRunner3.setScriptType("Alive");

		task2.setPhone(phone);
		task2.setEmulator(emulator2);
		task2.setSlaver(slaver);
		task2.setAppRunner(appRunner3);

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

}
