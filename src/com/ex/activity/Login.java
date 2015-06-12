package com.ex.activity;

import android.annotation.SuppressLint;
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
import com.ex.fascinator.BaseActivity;
import com.ex.fascinator.R;

@SuppressLint("HandlerLeak") public class Login extends BaseActivity {
	private TextView entLogin;
	private TextView entPassword;
	private Button btnLogin;
	private Button btnReg;
	public static final int ISADMIN_TRUE = 1;
	public static final int ISADMIN_FALSE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.d("User", "start login activity");
		// comment this if u want to see login and registration pages!
		// if (ParseUser.getCurrentUser() != null) {
		// login();
		// }
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
		showProgress();
		userAPI.handler = new Handler() {
			public void handleMessage(Message msg) {
				hideProgress();
				switch (msg.what) {
				case ISADMIN_TRUE:
					loginAdmin();
					break;
				case ISADMIN_FALSE:
					loginAnimator();
					break;
				case 0:
					incorrect();
					break;

				default:
					break;
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
		Intent intent = new Intent(this, AdminFirstActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}
}
