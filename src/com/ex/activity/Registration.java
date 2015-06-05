package com.ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ex.api.UserAPI;
import com.ex.fascinator.R;
import com.ex.objects.Animators;
import com.ex.objects.User;

public class Registration extends AppCompatActivity {

	private TextView entLogin;
	private TextView entPassword;
	private TextView entRPassword;
	private RadioGroup rGroup;
	// private RadioButton rAdmin;
	// private RadioButton rUser;

	private Button btnCreate;

	protected int CONNECTION_OK = 1;
	private User user = new User();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Shit", "onCreate start");
		setContentView(R.layout.activity_registr);
		Log.d("User", "start onCreate create user activity ");
		rGroup = (RadioGroup) findViewById(R.id.radioGroup);
		user.setIsAdmin(true);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioAdmin) {
					Log.d("Shit", "chosen Admin");
					Toast.makeText(getApplicationContext(), "Are you sure?",
							Toast.LENGTH_SHORT).show();
					user.setIsAdmin(true);
				} else {
					Log.d("Shit", "is a User");
					user.setIsAdmin(false);
				}
			}
		});

		entLogin = (TextView) findViewById(R.id.etnNickName);
		entPassword = (TextView) findViewById(R.id.etPassword);
		entRPassword = (TextView) findViewById(R.id.etRepitPassword);
		btnCreate = (Button) findViewById(R.id.btnCreate);

		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Shit", "start onClick");
				if (!entLogin.getText().toString().equals("")
						&& !entPassword.getText().toString().equals("")
						&& !entRPassword.getText().toString().equals("")) {
					Log.d("Shit", "first if");
					if (entPassword.getText().toString()
							.equals(entRPassword.getText().toString())) {
						Log.d("Shit", "start create ");
						check();
						createUser();
					} else {
						Toast.makeText(getApplicationContext(),
								"Use different passwords", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Something isn't correct", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

	private void check() {

	}

	private void autorisation() {
		Toast.makeText(Registration.this, "Welcome", Toast.LENGTH_SHORT).show();
		if (user.getIsAdmin()) {
			Intent intent = new Intent(Registration.this,
					AdminFirstActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			Intent intent = new Intent(Registration.this,
					AnimatorActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}

	private void incorrect() {
		Toast.makeText(Registration.this, "Please check and try again",
				Toast.LENGTH_SHORT).show();

	}

	public void createUser() {
		Log.d("Shit", "create user");
		user.setUsername(entLogin.getText().toString());
		user.setPassword(entPassword.getText().toString());
		final UserAPI c = new UserAPI();
		c.sync(user);
		c.handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == c.CONNECTION_OK) {
					Log.d("Shit", "handler ok");
					autorisation();
				} else {
					incorrect();
				}
			};
		};
	}
}
