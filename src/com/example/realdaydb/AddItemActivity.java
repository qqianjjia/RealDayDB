package com.example.realdaydb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.database.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
/**
 * AddItemActivity add new item into schedule or update item into schedule
 * @author qianjia
 *
 */
public class AddItemActivity extends Activity {
	String username;
	private EditText date;
	private EditText beginTime;
	private EditText endTime;
	private EditText description;
	private EditText type;
	private EditText state;
	private Button itemSaver;
	DatabaseConnector databaseConnector;
	DateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");
	DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
	DateFormat dbdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		date = (EditText) findViewById(R.id.date);
		beginTime = (EditText) findViewById(R.id.beginTime);
		endTime = (EditText) findViewById(R.id.endTime);
		description = (EditText) findViewById(R.id.description);
		type = (EditText) findViewById(R.id.type);
		state = (EditText) findViewById(R.id.state);
		itemSaver = (Button) findViewById(R.id.itemSaver);
		itemSaver.setOnClickListener(addItemListener);

		extras = getIntent().getExtras();
		if (extras.containsKey("itemId")) {
			date.setText(extras.getString("date"));
			beginTime.setText(extras.getString("beginTime"));
			endTime.setText(extras.getString("endTime"));
			description.setText(extras.getString("description"));
			type.setText(extras.getString("type"));
			state.setText(extras.getString("state"));
		}
	}

	public OnClickListener addItemListener = new OnClickListener() {

		public void onClick(View v) {
			saveItem();
			finish();
		}
/**
 * if itemId exists then update the item,else create a new item
 */
		private void saveItem() {
			if (!extras.containsKey("itemId")) {
				username = extras.getString("username");
				databaseConnector = new DatabaseConnector(AddItemActivity.this);
				try {
					Log.i("Database",
							"input data is "
									+ date.getText().toString()
									+ " after parse is "
									+ sdf2.format(sdf2.parse(date.getText()
											.toString())));
					databaseConnector.insertItem(username,
							date.getText().toString(), sdf1.parse(beginTime
							.getText().toString()), sdf1.parse(endTime
							.getText().toString()), description.getText()
							.toString(), type.getText().toString(), state
							.getText().toString());

				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (extras.containsKey("itemId")) {
				username = extras.getString("username");
				databaseConnector = new DatabaseConnector(AddItemActivity.this);

				try {
					databaseConnector.updateItem(Integer.parseInt(extras
							.getString("itemId")), date.getText()
							.toString(), sdf1.parse(beginTime.getText()
							.toString()), sdf1.parse(endTime.getText()
							.toString()), description.getText().toString(),
							type.getText().toString(), state.getText()
									.toString());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
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
