package com.mead.android.crazymonkey.persistence;

import java.util.Date;
import java.util.List;

import com.mead.android.crazymonkey.model.Task;

public interface TaskDAO {

	public List<Task> getTasksListByDay(int times, String slaverMac, Date date);

	public List<Task> getTasks(int times, String slaverMac, Date date);

	public Task getTaskById(List<Task> tasks, String id);

	public boolean assignTask(List<Task> tasks, String id);

	public boolean startTask(List<Task> tasks, String id);

	public boolean compelteTask(List<Task> tasks, String id, Task.STATUS staus, String log);

}
