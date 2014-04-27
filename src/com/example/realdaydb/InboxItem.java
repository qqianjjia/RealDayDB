package com.example.realdaydb;

import java.util.Date;

public class InboxItem {

	/*
	 * public InboxItem(int id, Date date, Date beginTime, Date endTime, String
	 * description, String state, String type) { super(id, date, beginTime,
	 * endTime, description, state, type); // TODO Auto-generated constructor
	 * stub }
	 */
	int id;
	String date;
	String description;
	String username;

	public InboxItem(int id, String username, String date, String description) {
		this.id = id;
		this.username = username;
		this.date = date;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getUsername() {
		return username;
	}

}
