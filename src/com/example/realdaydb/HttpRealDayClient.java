package com.example.realdaydb;

import java.text.ParseException;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
/**
 * implement the HttpRealDayClient
 * @author qianjia
 *
 */
public class HttpRealDayClient implements RealDayClient {
	private DatabaseConnector databaseConnector;

	// private Context context = null;
/**
 * HttpRealDayClient conscructor 
 * @param context
 */
	public HttpRealDayClient(Context context) {
		databaseConnector = new DatabaseConnector(context);
	}

	public User getUser(String specificUsername) {
		Cursor cursor = databaseConnector.getContact(specificUsername);
		String name = cursor.getString((cursor.getColumnIndex("name")));
		String email = cursor.getString((cursor.getColumnIndex("email")));
		String username = cursor.getString((cursor.getColumnIndex("username")));
		String password = cursor.getString((cursor.getColumnIndex("password")));
		return new User(name, email, username, password);

	};

	public Schedule getSchedule(User specificUser, Date specificDate)
			throws ParseException {
		return null;
	}

	public void resetPassword(User specificUser, String password) {
	}

	public void addInboxItem(InboxItem inboxItem) {
		databaseConnector.insertInbox(inboxItem.getDate(),
				inboxItem.getDescription(), inboxItem.getUsername());

	}

	public void markTaskDone(TaskItem taskItem) {
	}

	public Inbox getInbox(User specificUser) throws ParseException {
		return new Inbox();

	}

	public void markTaskUndone(TaskItem taskItem) {
	}

	public boolean authorizeUser(String username, String password) {

		return databaseConnector.authorizeUser(username, password);

	}

}
