package com.example.realdaydb;

import java.util.Date;

public class TaskItem extends Item {

	public TaskItem(int id, Date date, Date beginTime, Date endTime,
			String description, String state, String type) {
		super(id, date, beginTime, endTime, description, state, type);
		// TODO Auto-generated constructor stub
	}

	public void setBeginTime(Date newTime) {
		super.beginTime = newTime;

	}

	public void setEndTime(Date newTime) {
		super.endTime = newTime;

	}

	public void setState(String state) {
		super.state = state;

	}

}
