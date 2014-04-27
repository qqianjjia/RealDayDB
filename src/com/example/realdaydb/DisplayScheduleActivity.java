package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.example.database.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
/**
 * DislplayScheduleActivity display the schedule of today in screen
 * @author qianjia
 *
 */
public class DisplayScheduleActivity extends Activity {

	public class RealDayClientFactory {
		public RealDayClient produce(String type) {
			if ("dummy".equals(type)) {
				return new DummyRealDayClient();
			}
			if ("http".equals(type)) {
				return new HttpRealDayClient(DisplayScheduleActivity.this);
			}

			return null;

		}

	}

	DatabaseConnector databaseConnector = new DatabaseConnector(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_schedule);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		RealDayClientFactory factory = new RealDayClientFactory();
		if (!username.toString().equals("user1")) {
			client = factory.produce("http");
			// client = new HttpRealDayClient(DisplayScheduleActivity.this);
			// Intent intent = getIntent();

			try {
				Log.i("QQIANJJIA", "populateListView()");
				populateListView();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addInbox();
		} else {
			client = factory.produce("dummy");
			try {
				Log.i("QQIANJJIA", "populateListView()");
				dummyPopulateListView();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dummyAddInbox();
		}

	}
/**
 * add inboxItem into inbox
 */
	private void addInbox() {
		inboxEdit = (EditText) findViewById(R.id.inputInbox);
		inboxSave = (Button) findViewById(R.id.inboxSaver);
		inboxSave.setOnClickListener(saveListener);

	}

	OnClickListener saveListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			/*
			 * client.addInboxItem(client.getUser(username),new
			 * InboxItem(3,"user1",sdf2.parse("2014-03-11"),
			 * inboxEdit.getText().toString()) );
			 */
			DateFormat df = DateFormat.getDateInstance();
			Date date = new Date();
			String dateStr = df.format(date);

			client.addInboxItem(new InboxItem(0, username, dateStr, inboxEdit
					.getText().toString()));

			inboxEdit.setText("");
			Intent i = new Intent(DisplayScheduleActivity.this,
					DisplayInboxActivity.class);
			i.putExtra("username", username);
			startActivity(i);

		}

	};
/**
 *  add inboxItem into inbox
 */
	private void dummyAddInbox() {
		inboxEdit = (EditText) findViewById(R.id.inputInbox);
		inboxSave = (Button) findViewById(R.id.inboxSaver);
		inboxSave.setOnClickListener(dummySaveListener);

	}

	OnClickListener dummySaveListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// inboxEdit.setText("");
			Intent i = new Intent(DisplayScheduleActivity.this,
					DisplayInboxActivity.class);
			i.putExtra("username", username);
			i.putExtra("content", inboxEdit.getText().toString());
			startActivity(i);

		}

	};
/**
 * display the schedule in the listView
 * @throws ParseException
 */
	private void populateListView() throws ParseException {
		Date today = new Date();
		String stoday = sdf2.format(today);
		scheduleListView = (ListView) findViewById(R.id.listView1);
		scheduleListView.setOnItemClickListener(itemListener);
		scheduleListView.setOnHierarchyChangeListener(itemsAdded);

		String[] from = new String[] { "_id", "beginTime", "endTime",
				"description", "type" };
		int[] to = new int[] { R.id.id, R.id.beginTime, R.id.endTime,
				R.id.description, R.id.type };

		adapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to,
				0);
		scheduleListView.setAdapter(adapter);
		databaseConnector.open();
		try {
			result = databaseConnector.getSchedule(username, stoday);
			result.moveToFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("Database", "finish get schedule");
		// sdf2.parse("2014-03-11"));
		adapter.changeCursor(result);
		databaseConnector.close();

	}

	OnItemClickListener itemListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			Intent viewItem = new Intent(DisplayScheduleActivity.this,
					ViewItemActivity.class);

			TextView itemId = (TextView) arg1.findViewById(R.id.id);
			viewItem.putExtra("itemId", itemId.getText().toString());
			startActivity(viewItem);

		}
		/*
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * position, long id) { CheckBox ck = (CheckBox)
		 * arg1.findViewById(R.id.checkBox1); TextView itemId = (TextView)
		 * arg1.findViewById(R.id.id); if (ck.isShown()) { if (ck.isChecked()) {
		 * ck.setChecked(!ck.isChecked()); databaseConnector.updateItem(
		 * Integer.parseInt(itemId.getText().toString()), "undone");
		 * 
		 * } else if (!ck.isChecked()) { ck.setChecked(!ck.isChecked());
		 * databaseConnector.updateItem(
		 * Integer.parseInt(itemId.getText().toString()), "done");
		 * 
		 * } }
		 * 
		 * 
		 * 
		 * }
		 */
	};

	OnHierarchyChangeListener itemsAdded = new OnHierarchyChangeListener() {

		@Override
		public void onChildViewAdded(View parent, View child) {
			CheckBox ck = (CheckBox) child.findViewById(R.id.checkBox1);
			TextView type = (TextView) child.findViewById(R.id.type);
			TextView id = (TextView) child.findViewById(R.id.id);
			databaseConnector.open();
			Cursor cursor = databaseConnector.getItem(Integer.parseInt(id
					.getText().toString()));
			cursor.moveToFirst();

			if (cursor.getString(cursor.getColumnIndex("state")).equals("done"))
				ck.setChecked(true);
			else
				ck.setChecked(false);
			String stype = type.getText().toString();

			if (stype.equals("event")) {
				ck.setVisibility(View.INVISIBLE);
			}

			databaseConnector.close();
		}

		@Override
		public void onChildViewRemoved(View parent, View child) {
			// TODO Auto-generated method stub

		}

	};
/**
 * display the schedule in the listView
 * @throws ParseException
 */
	private void dummyPopulateListView() throws ParseException {

		schedule = client.getSchedule(client.getUser(username),
				sdf2.parse("2014-03-10"));
		schedule = client.getSchedule(client.getUser(username),
				sdf2.parse("2014-03-10"));

		Iterator it = schedule.iterator();

		System.out.println("populateListViews");
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		// for (int i = 0; i < schedule.getSize(); i++)
		while (it.hasNext()) {

			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("Id", String.valueOf(schedule.getItem(i).getId()));
			// map.put("BeginTime",sdf1.format(schedule.getItem(i).getBeginTime()));
			// map.put("EndTime",
			// sdf1.format(schedule.getItem(i).getEndTime()));
			// map.put("Description", schedule.getItem(i).getDescription());
			// map.put("Type", schedule.getItem(i).getType());
			Item item = (Item) it.next();
			map.put("Id", String.valueOf(item.getId()));
			map.put("BeginTime", sdf1.format(item.getBeginTime()));
			map.put("EndTime", sdf1.format(item.getEndTime()));
			map.put("Description", item.getDescription());
			map.put("Type", item.getType());

			data.add(map);
		}
		ListView listView = (ListView) findViewById(R.id.listView1);
		String[] from = new String[] { "Id", "BeginTime", "EndTime",
				"Description", "Type" };
		int[] to = new int[] { R.id.id, R.id.beginTime, R.id.endTime,
				R.id.description, R.id.type };

		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				from, to);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(dummyItemListener);
		listView.setOnHierarchyChangeListener(dummyItemsAdded);

	}

	OnItemClickListener dummyItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			CheckBox ck = (CheckBox) arg1.findViewById(R.id.checkBox1);
			//TextView itemId = (TextView) arg1.findViewById(R.id.id);
			
			if (ck.isShown()) {
				TaskItem taskItem = (TaskItem) schedule.getItem((int)id);
				if (ck.isChecked()) {
					
					ck.setChecked(!ck.isChecked());
					client.markTaskUndone(taskItem);

				} else if (!ck.isChecked()) {
					ck.setChecked(!ck.isChecked());
					client.markTaskDone(taskItem);

				}
			}

		}
	};

	OnHierarchyChangeListener dummyItemsAdded = new OnHierarchyChangeListener() {

		@Override
		public void onChildViewAdded(View parent, View child) {
			CheckBox ck = (CheckBox) child.findViewById(R.id.checkBox1);
			TextView type = (TextView) child.findViewById(R.id.type);
			TextView id = (TextView) child.findViewById(R.id.id);
			// String
			// state=schedule.getItem(Integer.parseInt(id.getText().toString())).getState();
			String state = ((Item) schedule.get(Integer.parseInt(id.getText()
					.toString()))).getState();
			if (state == "done")
				ck.setChecked(true);
			else
				ck.setChecked(false);
			String stype = type.getText().toString();

			if (stype == "event") {
				ck.setVisibility(View.INVISIBLE);
			}

		}

		@Override
		public void onChildViewRemoved(View parent, View child) {
			// TODO Auto-generated method stub

		}
	};

/**
 * create the menu
 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_schedule, menu);
		return true;
		// end method onCreateOptionsMenu
	}
/**
 * set the action of itemselected
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// create a new Intent to launch the AddEditContact Activity
		Intent addNewContact = new Intent(DisplayScheduleActivity.this,
				AddItemActivity.class);
		addNewContact.putExtra("username", username);
		startActivity(addNewContact); // start the AddEditContact Activity
		return super.onOptionsItemSelected(item); // call super's method
	} // end method onOptionsItemSelected

	Schedule schedule;
	DummyRealDayClient dummy;
	EditText inboxEdit;
	Button inboxSave;
	String username;
	RealDayClient client;
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
	private ListView scheduleListView;
	private CursorAdapter adapter;
	Cursor result;
}
