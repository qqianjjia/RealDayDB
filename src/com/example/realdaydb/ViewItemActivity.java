package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.database.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
/**
 * ViewItemActivity show the information of the item
 * @author qianjia
 *
 */
public class ViewItemActivity extends Activity {
	String itemId;
	DatabaseConnector databaseConnector;
	Cursor cursor;
	private TextView date;
	private TextView beginTime;
	private TextView endTime;
	private TextView description;
	private TextView type;
	private TextView state;
	DateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
	DateFormat dbdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_item);
		Bundle extras = getIntent().getExtras();
		itemId = extras.getString("itemId");
		date = (TextView) findViewById(R.id.date1);
		beginTime = (TextView) findViewById(R.id.beginTime1);
		endTime = (TextView) findViewById(R.id.endTime1);
		description = (TextView) findViewById(R.id.description1);
		type = (TextView) findViewById(R.id.type1);
		state = (TextView) findViewById(R.id.state1);
		databaseConnector = new DatabaseConnector(ViewItemActivity.this);

		cursor = databaseConnector.getItem(Integer.parseInt(itemId));
		cursor.moveToFirst();

		String sdate = cursor.getString(cursor.getColumnIndex("date"));
		//String vdate = new String();
		//try {
		//	Date ddate = dbdf.parse(sdate);
		//	vdate = sdf2.format(ddate);
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		state.setText(cursor.getString(cursor.getColumnIndex("state")));
		type.setText(cursor.getString(cursor.getColumnIndex("type")));
		description.setText(cursor.getString(cursor
				.getColumnIndex("description")));
		endTime.setText(cursor.getString(cursor.getColumnIndex("endTime")));
		beginTime.setText(cursor.getString(cursor.getColumnIndex("beginTime")));
		date.setText(sdate);

		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
/**
 * create the menu
 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_item, menu);
		return true;
	}
	/**
	 * set the action the the itemselected,included edit and delete
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) // switch based on selected MenuItem's ID
		{
		case R.id.editItem:
			// create an Intent to launch the AddEditContact Activity
			Intent addEditItem = new Intent(this, AddItemActivity.class);
			addEditItem.putExtra("itemId", itemId);
			addEditItem.putExtra("username",
					cursor.getString(cursor.getColumnIndex("username")));
			addEditItem.putExtra("date", date.getText().toString());
			addEditItem.putExtra("beginTime", beginTime.getText().toString());
			addEditItem.putExtra("endTime", endTime.getText().toString());
			addEditItem.putExtra("description", description.getText()
					.toString());
			addEditItem.putExtra("state", state.getText().toString());
			addEditItem.putExtra("type", type.getText().toString());
			startActivity(addEditItem);
			return true;

		case R.id.deleteItem:
			deleteItem(); // delete the displayed contact
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
 /**
  * delete the item
  */
	private void deleteItem() {
		databaseConnector.deleteItem(Integer.parseInt(itemId));

	}

}
