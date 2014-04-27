package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.database.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class DisplayInboxActivity extends Activity {
	Inbox inbox;
	// DummyRealDayClient dummy;
	DatabaseConnector databaseConnector = new DatabaseConnector(this);
	Cursor result;
	RealDayClient client;
	String username;
	String content;
	DateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");

	DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_inbox);
		Bundle extras = getIntent().getExtras();

		username = extras.getString("username");
		content = extras.getString("content");
		if (!username.toString().equals("user1")) {
			try {
				populateInbox();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else
			dummyPopulateInbox();
		setupActionBar();
	}

	private void dummyPopulateInbox() {
		DummyRealDayClient dummy = new DummyRealDayClient();
		try {
			DateFormat df = DateFormat.getDateInstance();
			dummy.addInboxItem(new InboxItem(3, username,
					df.format(new Date()), content));
			inbox = dummy.getInbox(dummy.getUser(username));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("populateInbox");
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < inbox.getSize(); i++) {

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Id", String.valueOf(inbox.getItem(i).getId()));
			map.put("Date", inbox.getItem(i).getDate());

			map.put("Description", inbox.getItem(i).getDescription());

			data.add(map);
		}
		ListView listView = (ListView) findViewById(R.id.inboxListView1);
		String[] from = new String[] { "Id", "Date", "Description" };
		int[] to = new int[] { R.id.inboxItemId, R.id.inboxItemDate,
				R.id.inboxItemDescription };
		// Log.i("QQIANJJIA", String.format("the length of data %d",
		// data.size()));

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.inbox_item, from, to);
		listView.setAdapter(adapter);

	}

	private void populateInbox() throws ParseException {
		ListView listView = (ListView) findViewById(R.id.inboxListView1);
		String[] from = new String[] { "_id", "date", "description" };

		int[] to = new int[] { R.id.inboxItemId, R.id.inboxItemDate,
				R.id.inboxItemDescription };
		CursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.inbox_item, null, from, to, 0);
		listView.setAdapter(adapter);
		databaseConnector.open();
		result = databaseConnector.getInbox(username);
		result.moveToFirst();

		adapter.changeCursor(result);
		databaseConnector.close();

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_inbox, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
