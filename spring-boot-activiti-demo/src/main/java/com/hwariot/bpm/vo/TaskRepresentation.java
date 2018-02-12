package com.hwariot.bpm.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskRepresentation {

	private String id;
	private String name;
	private String assignee;
	private String desc;
	private Date createTime;

	public TaskRepresentation(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public TaskRepresentation(String id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	public TaskRepresentation(String id, String name, String assignee, String desc) {
		this.id = id;
		this.name = name;
		this.assignee = assignee;
		this.desc = desc;
	}

	public TaskRepresentation(String id, String name, String assignee, String desc, Date createTime) {
		this.id = id;
		this.name = name;
		this.assignee = assignee;
		this.desc = desc;
		this.createTime = createTime;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreateTime() {
		String date = null;
		if(createTime != null) {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(createTime);
		}
		return date;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}