package com.mead.android.crazymonkey.model;

import java.util.Date;
import java.util.List;

import com.mead.android.crazymonkey.AndroidEmulator;

public class Task {

	public enum STATUS {
		NONE, READY, PROCESSING, SUCCESS, FAILURE, INCOMPLETE, NOT_BUILT
	}

	private String id;

	private String jobId;

	private Date planExecDate;

	private String planExecPeriod;

	private STATUS status;

	private Phone phone;

	private AndroidEmulator emulator;

	private Slaver slaver;

	private List<AppRunner> appRunners;
	
	private Date createTime;

	private Date assignTime;

	private Date execStartTime;

	private Date exceEndTime;

	private String log;

	private int[] ports;

	public Task() {
		super();
	}

	public List<AppRunner> getAppRunners() {
		return appRunners;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public AndroidEmulator getEmulator() {
		return emulator;
	}

	public Date getExceEndTime() {
		return exceEndTime;
	}

	public Date getExecStartTime() {
		return execStartTime;
	}

	public String getId() {
		return id;
	}

	public String getJobId() {
		return jobId;
	}

	public String getLog() {
		return log;
	}

	public Phone getPhone() {
		return phone;
	}

	public Date getPlanExecDate() {
		return planExecDate;
	}

	public String getPlanExecPeriod() {
		return planExecPeriod;
	}

	public int[] getPorts() {
		return ports;
	}

	public Slaver getSlaver() {
		return slaver;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setAppRunners(List<AppRunner> appRunners) {
		this.appRunners = appRunners;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setEmulator(AndroidEmulator emulator) {
		this.emulator = emulator;
	}

	public void setExceEndTime(Date exceEndTime) {
		this.exceEndTime = exceEndTime;
	}

	public void setExecStartTime(Date execStartTime) {
		this.execStartTime = execStartTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public void setPlanExecDate(Date planExecDate) {
		this.planExecDate = planExecDate;
	}

	public void setPlanExecPeriod(String planExecPeriod) {
		this.planExecPeriod = planExecPeriod;
	}

	public void setPorts(int[] ports) {
		this.ports = ports;
	}

	public void setSlaver(Slaver slaver) {
		this.slaver = slaver;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

}
