package com.mead.android.crazymonkey.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.mead.android.crazymonkey.AndroidEmulator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	public enum STATUS {
		NONE, INPROGRESS, SUCCESS, FAILURE, NOT_BUILT
	}

	private String id;

	private String jobId;

	private String planExecDate;

	private String planExecPeriod;

	private STATUS status;

	private Phone phone;

	private AndroidEmulator emulator;

	private Slaver slaver;

	private AppRunner appRunner;
	
	private Date createTime;

	private Date assignTime;

	private Date execStartTime;

	private Date exceEndTime;

	private String log;


	public Task() {
		super();
	}

	public AppRunner getAppRunner() {
		return appRunner;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	@JsonIgnore
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

	public String getPlanExecDate() {
		return planExecDate;
	}

	public String getPlanExecPeriod() {
		return planExecPeriod;
	}


	public Slaver getSlaver() {
		return slaver;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setAppRunner(AppRunner appRunner) {
		this.appRunner = appRunner;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
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

	public void setPlanExecDate(String planExecDate) {
		this.planExecDate = planExecDate;
	}

	public void setPlanExecPeriod(String planExecPeriod) {
		this.planExecPeriod = planExecPeriod;
	}


	public void setSlaver(Slaver slaver) {
		this.slaver = slaver;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	

	@JsonIgnore
	public boolean startTask() {
		this.setExecStartTime(new Date());
		return false;
	}

	@JsonIgnore
	public boolean compelteTask(Task.STATUS result) {
		this.setExceEndTime(new Date());
		if (this.getStatus() == Task.STATUS.INPROGRESS) {
			this.setStatus(result);
			this.setLog(log);
			return true;
		}
		return false;
	}

}
