package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;
/**
 * implement the DummyRealDayClient
 * @author qianjia
 *
 */
public class DummyRealDayClient implements RealDayClient {
	Inbox inbox = new Inbox();

	public User getUser(String specificUsername) {
		String username = "user1";// specificUsername;
		String password = "123";
		String email = "123@gmail.com";
		String name = "John";
		return new User(name, email, username, password);
	}

	public Schedule getSchedule(User specificUser, Date specificDate)
			throws ParseException {
		Schedule schedule = new Schedule(specificDate);
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Item item1 = new EventItem(0, specificDate, sdf.parse("12:00:00"),
				sdf.parse("14:40:00"), "aa", "undone", "event");
		Item item2 = new TaskItem(1, specificDate, sdf.parse("16:00:00"),
				sdf.parse("16:30:00"), "bb", "undone", "task");
		Item item3 = new TaskItem(2, specificDate, sdf.parse("17:00:00"),
				sdf.parse("17:30:00"), "cc", "done", "task");
		Item item4 = new EventItem(3, specificDate, sdf.parse("20:00:00"),
				sdf.parse("21:00:00"), "dd", "undone", "event");

		schedule.addItem(item1);
		schedule.addItem(item2);
		schedule.addItem(item3);
		schedule.addItem(item4);
		return schedule;

	}

	public void resetPassword(User specificUser, String password) {
		// set the new password to the database
		specificUser.setPassword(password);

	}

	public/* Inbox */void addInboxItem(InboxItem specificInboxItem) {

		/*
		 * DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss"); DateFormat sdf2 =
		 * new SimpleDateFormat("yyyy-mm-dd"); InboxItem inboxItem1 = new
		 * InboxItem(1, sdf2.parse("2014-03-11"), sdf1.parse("12:00:00"),
		 * sdf1.parse("14:40:00"), "art class", "undone", "event"); InboxItem
		 * inboxItem2 = new InboxItem(2, sdf2.parse("2014-03-11"),
		 * sdf1.parse("14:00:00"), sdf1.parse("14:40:00"), "quiz", "undone",
		 * "task"); InboxItem inboxItem3 = specificInboxItem;
		 * inbox.addItem(inboxItem1); inbox.addItem(inboxItem2);
		 */
		inbox.addItem(specificInboxItem);
		// return inbox;

	}

	public void markTaskDone(TaskItem taskItem) {
		// set the done state to the specific task
		taskItem.setState("done");

	}

	public void markTaskUndone(TaskItem taskItem) {
		taskItem.setState("undone");
	}

	public boolean authorizeUser(String username, String password) {
		if (username.compareTo("user1") == 0 && password.compareTo("123") == 0)
			return true;
		else
			return false;

	}

	@Override
	public Inbox getInbox(User specificUser) throws ParseException {

		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		DateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
		/*
		 * Item item1 = new EventItem(0, sdf2.parse("2014-03-10"),
		 * sdf.parse("12:00:00"), sdf.parse("14:40:00"), "french class",
		 * "undone", "event"); Item item2 = new TaskItem(1,
		 * sdf2.parse("2014-03-10"), sdf.parse("16:00:00"),
		 * sdf.parse("16:30:00"), "chinese class", "undone", "task");
		 * inbox.addItem(item1); inbox.addItem(item2);
		 */
		DateFormat df = DateFormat.getDateInstance();
		String dateStr = df.format(new Date());
		InboxItem item1 = new InboxItem(0, "user1", dateStr, "aaaaa");
		InboxItem item2 = new InboxItem(1, "user1", dateStr, "bbbbb");
		inbox.addItem(item1);
		inbox.addItem(item2);

		return inbox;
	}

}
