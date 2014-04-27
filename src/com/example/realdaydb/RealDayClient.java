package com.example.realdaydb;

import java.text.ParseException;
import java.util.Date;
/**
 * create the interface of realdayclient
 * @author qianjia
 *
 */
public interface RealDayClient {
	/**
	 * get the user information
	 * @param specificUsername the reference of username
	 * @return object user
	 */
	public User getUser(String specificUsername);
	/**
	 * get the information of the schedule
	 * @param specificUser  the reference of username
	 * @param specificDate the reference of date
	 * @return object schedule
	 * @throws ParseException
	 */

	public Schedule getSchedule(User specificUser, Date specificDate)
			throws ParseException;
	/**
	 * reset the password of the user
	 * @param specificUser the reference of user
	 * @param password the new password
	 */
	

	public void resetPassword(User specificUser, String password);
	/**
	 * add inboxItem into inbox
	 * @param inboxItem the reference of inboxItem
	 */

	public void addInboxItem(InboxItem inboxItem);
	/**
	 * make the state of task to be done
	 * @param taskItem the reference of taskItem
	 */

	public void markTaskDone(TaskItem taskItem);
	/**
	 * get the information of inbox
	 * @param specificUser the reference of user
	 * @return the object of inbox
	 * @throws ParseException
	 */

	public Inbox getInbox(User specificUser) throws ParseException;
	/**
	 * make the state of task to be undone
	 * @param taskItem the reference of taskItem
	 */

	public void markTaskUndone(TaskItem taskItem);
	/**
	 * check if the username and password are correct
	 * @param username the reference of username
	 * @param password the reference of password
	 * @return true if authorization succeeds
	 */

	public boolean authorizeUser(String username, String password);

}
