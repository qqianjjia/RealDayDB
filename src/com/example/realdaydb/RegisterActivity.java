package com.example.realdaydb;

import com.example.database.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class RegisterActivity extends Activity {
	private Button registerSave = null;
	private DatabaseConnector databaseConnector;
	private EditText name;
	private EditText email;
	private EditText username;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		name = (EditText) findViewById(R.id.inputName);
		email = (EditText) findViewById(R.id.inputEmail);
		username = (EditText) findViewById(R.id.inputUsername);
		password = (EditText) findViewById(R.id.inputPassword);

		registerSave = (Button) findViewById(R.id.registerSaver);
		registerSave.setOnClickListener(registerSaverListener);

	}

	public OnClickListener registerSaverListener = new OnClickListener() {
		public void onClick(View v) {
			databaseConnector = new DatabaseConnector(RegisterActivity.this);
			databaseConnector.insertContact(name.getText().toString(), email
					.getText().toString(), username.getText().toString(),
					password.getText().toString());
			Intent i = new Intent(RegisterActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
