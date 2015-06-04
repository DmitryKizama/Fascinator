package com.ex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ex.api.UserAPI;
import com.ex.fascinator.R;
import com.parse.ParseUser;

public class Login extends Activity {
	protected static final int CONNECTION_OK = 1;
	private TextView entLogin;
	private TextView entPassword;
	private Button btnLogin;
	private Button btnReg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.d("User", "start login activity");
		// comment this if u want to see login and registration pages!
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			Log.d("User", "user = " + currentUser.getUsername());
			loginAdmin();
		}
		entLogin = (TextView) findViewById(R.id.etnNickName);
		entPassword = (TextView) findViewById(R.id.etPassword);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();

			}
		});

		btnReg = (Button) findViewById(R.id.btnCreate);
		btnReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, Registration.class);
				startActivity(intent);

			}
		});
	}

	public void login() {
		final UserAPI userAPI = new UserAPI();
		userAPI.login(entLogin.getText().toString(), entPassword.getText()
				.toString());
		Log.d("login", "entered in method");
		userAPI.handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == CONNECTION_OK) {
					Log.d("login", "entered in first handler");
					userAPI.isAdmin();
					userAPI.handlerIsAdmin = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Log.d("login", "entered in second handler");
							if (msg.what == userAPI.CONNECTION_OK) {
								loginAdmin();
							} else {
								loginAnimator();
							}
						}
					};

				} else {
					incorrect();
				}
			};
		};
	}

	private void incorrect() {
		Toast.makeText(getApplicationContext(), "Incorrect login or password",
				Toast.LENGTH_SHORT).show();
	}

	public void loginAnimator() {
		Toast.makeText(getApplicationContext(), "Logined", Toast.LENGTH_SHORT)
				.show();
		Intent intent = new Intent(this, AnimatorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	public void loginAdmin() {
		Toast.makeText(getApplicationContext(), "Logined", Toast.LENGTH_SHORT)
				.show();
		Intent intent = new Intent(this, AdminActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}
}
