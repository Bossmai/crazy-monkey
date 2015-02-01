package com.mead.android.crazymonkey.persistence;

import java.util.Date;
import java.util.List;

import com.mead.android.crazymonkey.model.Task;

public interface TaskDAO {

	public List<Task> getTasksListByDay(int times, String slaverMac, Date date);

	public List<Task> getTasks(int times, String slaverMac, Date date);

}
