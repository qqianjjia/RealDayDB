package com.example.realdaydb;

import java.text.ParseException;
import java.util.Date;

public interface RealDayClient {
	public User getUser(String specificUsername);

	public Schedule getSchedule(User specificUser, Date specificDate)
			throws ParseException;

	public void resetPassword(User specificUser, String password);

	public void addInboxItem(InboxItem inboxItem);

	public void markTaskDone(TaskItem taskItem);

	public Inbox getInbox(User specificUser) throws ParseException;

	public void markTaskUndone(TaskItem taskItem);

	public boolean authorizeUser(String username, String password);

}
