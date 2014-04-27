package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.database.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

/**
 * MainActivity show the login screen
 * 
 * @author qianjia
 * 
 */
public class MainActivity extends Activity {
	
	private EditText username = null;
	private EditText password = null;
	private Button loginButton = null;
	private Button registerButton = null;
	DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	DatabaseConnector databaseConnector;

	/**
	 * initialize the data
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.inputUsernameEditText);
		password = (EditText) findViewById(R.id.inputPasswordEditText);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.register);
		// initialize the database
		databaseConnector = new DatabaseConnector(MainActivity.this);

		databaseConnector.insertContact("rose", "user2@gmail.com", "user2",
				"123");
		databaseConnector.insertContact("mike", "user3@gmail.com", "user3",
				"123");
		Date today = new Date();
		String stoday = sdf2.format(today);
		Log.i("Database", "the string of today is: " + stoday);

		try {
			databaseConnector.insertItem("user2", stoday,
					sdf1.parse("8:00:00"), sdf1.parse("9:00:00"), "homework1",
					"task", "undone");

			databaseConnector.insertItem("user2", stoday,
					sdf1.parse("10:00:00"), sdf1.parse("11:00:00"),
					"chinese class", "event", "undone");

			databaseConnector.insertItem("user2", stoday,
					sdf1.parse("12:00:00"), sdf1.parse("13:00:00"),
					"homework2", "task", "done");

			databaseConnector.insertItem("user3", stoday,
					sdf1.parse("14:00:00"), sdf1.parse("15:00:00"),
					"homework3", "task", "done");

			databaseConnector.insertItem("user2", stoday,
					sdf1.parse("16:00:00"), sdf1.parse("17:00:00"), "quiz",
					"task", "undone");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat df = DateFormat.getDateInstance();
		String dateStr = df.format(new Date());
		databaseConnector.insertInbox(dateStr, "attend conference", "user2");
		databaseConnector.insertInbox(dateStr, "have meeting", "user2");
		databaseConnector.insertInbox(dateStr, "dance performance", "user3");

		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
	}

	public OnClickListener loginListener = new OnClickListener() {

		public void onClick(View v) {
			if (!username.getText().toString().equals("user1"))

			{
				HttpRealDayClient hclient = new HttpRealDayClient(
						MainActivity.this);
				if (hclient.authorizeUser(username.getText().toString(),
						password.getText().toString()))

				{
					Intent intent1 = new Intent(MainActivity.this,
							DisplayScheduleActivity.class);
					intent1.putExtra("username", username.getText().toString());
					startActivity(intent1);

					Log.i("QQIANJJIA", "startActivity(intent1)");
					startActivity(intent1);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("Login failed");
					builder.setMessage("Please retry");
					builder.setPositiveButton("ok", retryListener);
					AlertDialog alert = builder.create();
					alert.show();
				}
			} else {

				DummyRealDayClient drd = new DummyRealDayClient();
				if (drd.authorizeUser(username.getText().toString(), password
						.getText().toString())) {
					Intent intent1 = new Intent(MainActivity.this,
							DisplayScheduleActivity.class);
					intent1.putExtra("username", username.getText().toString());
					startActivity(intent1);

					Log.i("QQIANJJIA", "startActivity(intent1)");
					startActivity(intent1);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("Login failed");
					builder.setMessage("Please retry");
					builder.setPositiveButton("ok", retryListener);
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
		}
	};

	public OnClickListener registerListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent2 = new Intent(MainActivity.this,
					RegisterActivity.class);
			startActivity(intent2);

		}
	};
	public DialogInterface.OnClickListener retryListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();

		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
