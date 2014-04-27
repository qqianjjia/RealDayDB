package com.example.realdaydb;

import java.util.Date;

public class Item {
	private String description;
	protected Date beginTime;
	protected Date endTime;
	protected Date date;
	protected String state;
	private int id;
	private String type;

	public Item(int id, Date date, Date beginTime, Date endTime,
			String description, String state, String type) {
		this.id = id;
		this.date = date;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.description = description;
		this.state = state;
		this.type = type;
	}

	public String getType() {
		return type;

	}

	public Date getBeginTime() {
		return beginTime;

	}

	public Date getEndTime() {
		return endTime;

	}

	public int getId() {
		return id;

	}

	public String getState() {
		return state;

	}

	public Date getDate() {
		return date;

	}

	public String getDescription() {
		return description;

	}

}
