package com.example.realdaydb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseConnector {
	private static final String DATABASE_NAME = "db";
	private SQLiteDatabase database;
	private DatabaseOpenHelper databaseOpenHelper;
	DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	DatabaseConnector(Context context) {
		databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME,
				null, 1);

	};

	public void open() throws SQLException {
		database = databaseOpenHelper.getWritableDatabase();
	}

	public void close() {
		if (database != null)
			database.close(); // close the database connection
	}

	public void insertItem(String name, String date, Date beginTime,
			Date endTime, String description, String type, String state) {
		open();
		Log.i("Database", "database opened");
		Cursor cursor = database.query("schedule", null, "username=?"
				+ "and date=?" + "and beginTime=?" + "and endTime=?"
				+ "and description=?" + "and type=?",
				new String[] { name, date, sdf1.format(beginTime),
						sdf1.format(endTime), description, type }, null, null,
				null);
		Log.i("Database", "finish query");
		if (cursor.getCount() == 0) {

			ContentValues newItem = new ContentValues();
			newItem.put("username", name);
			// newItem.put("date", date.toString());
			newItem.put("date", date);
			newItem.put("beginTime", sdf1.format(beginTime));
			newItem.put("endTime", sdf1.format(endTime));
			newItem.put("description", description);

			newItem.put("type", type);
			newItem.put("state", state);

			database.insert("schedule", null, newItem);
			Log.i("Database",
					"database inserted username " + name + " date "
							+ date + " description "
							+ description.toString());
			close();
		}

	}

	public Cursor getSchedule(String specialUsername, String date) {
		Log.i("Database", "database getSchedule for " + specialUsername + " "
				+ date);

		String selectQuery = "SELECT  sl._id,sl.beginTime,sl.endTime,sl.description,sl.type "
				+ "FROM schedule sl,contact con where sl.username=con.username and sl.username=?and date=?;";
		return database.rawQuery(selectQuery, new String[] { specialUsername,
				date });

		/*
		 * return database.query("schedule", new String[] { "_id", "beginTime",
		 * "endTime", "description", "type", "state" }, "username=?" +
		 * "and date=?", new String[] { specialUsername, date.toString() },
		 * null, null, null, null);
		 */

	}

	public Cursor getItem(int id) {
		open();
		return database.query("schedule", null, "_id=" + id, null, null, null,
				null);
	}

	public void insertContact(String name, String email, String username,
			String password) {
		open();

		Cursor cursor = database.query("contact", null, "name=?"
				+ "and email=?" + "and username=?" + "and password=?",
				new String[] { name, email, username, password }, null, null,
				null);

		if (cursor.getCount() == 0) {

			ContentValues newItem = new ContentValues();
			newItem.put("name", name);
			newItem.put("email", email);
			newItem.put("username", username);

			newItem.put("password", password);

			database.insert("contact", null, newItem);

			close();
		}// close the database

	} // end method insertContact

	public Cursor getContact(String specialUsername) {
		open();
		return database.query("contact", new String[] { "name", "email",
				"username", "password" }, "username=?",
				new String[] { specialUsername }, null, null, null, null);

	}

	public boolean authorizeUser(String username, String password) {
		open();
		Cursor cursor = database.query("contact", null, "username=?"
				+ "and password=?", new String[] { username, password }, null,
				null, null, null);
		if (cursor.getCount() == 0) {
			close();
			return false;

		} else {
			close();
			return true;
		}

	}

	public void insertInbox(String date, String description, String username) {
		open();

		Cursor cursor = database.query("inbox", null, "date=?"
				+ "and description=?" + "and username=?", new String[] { date,
				description, username }, null, null, null);

		if (cursor.getCount() == 0) {

			ContentValues newItem = new ContentValues();
			newItem.put("date", date);
			newItem.put("description", description);
			newItem.put("username", username);

			database.insert("inbox", null, newItem);

			close();
		}

	}

	public Cursor getInbox(String specialUsername) {

		String selectQuery = "SELECT * from inbox where username=?;";

		return database.rawQuery(selectQuery, new String[] { specialUsername });

	}

	/*
	 * public Cursor getItemState(int id) {
	 * 
	 * return database.query("schedule", new String[] { "state" }, "_id=" + id,
	 * null, null, null, null); // return
	 * database.rawQuery("select state from schedule where _id=?", // new
	 * String[]{"id"});
	 * 
	 * }
	 */

	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		public DatabaseOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			Log.i("Database", "DatabaseOpenHelper constructor is called");
		}

		// creates the contacts table when the database is created
		@Override
		public void onCreate(SQLiteDatabase db) {
			// query to create a new table named contacts
			Log.i("Database", "DatabaseOpenHelper onCreate is called");
			String createQuery = "CREATE TABLE schedule"
					+ "(_id integer primary key autoincrement,"
					+ "username TEXT,"
					+ "date Date, beginTime Date, endTime Date,"
					+ "description TEXT," + "type TEXT," + "state TEXT);";
			String createQuery1 = "create table contact"
					+ "(_id integer primary key autoincrement,"
					+ "name text, email text, username text,password text);";
			String createQuery2 = "create table inbox"
					+ "(_id integer primary key autoincrement,"

					+ "username text,date Date, description text);";

			db.execSQL(createQuery); // execute the query
			db.execSQL(createQuery1);
			db.execSQL(createQuery2);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		} // end method onCreate

	}

	public void deleteItem(int id) {
		open();
		database.delete("schedule", "_id=" + id, null);

		close();

	}

	public void updateItem(int id, String date, Date beginTime, Date endTime,
			String description, String type, String state) {

		ContentValues editItem = new ContentValues();
		editItem.put("date", date);
		editItem.put("beginTime", sdf1.format(beginTime));
		editItem.put("endTime", sdf1.format(endTime));
		editItem.put("description", description);
		editItem.put("type", type);
		editItem.put("state", state);

		open(); // open the database
		database.update("schedule", editItem, "_id=" + id, null);
		close();

	}
}
