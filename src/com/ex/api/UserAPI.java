package com.ex.api;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.ex.objects.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

public class UserAPI {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String ISADMIN = "isAdmin";

	public static final int CONNECTION_OK = 1;

	public static final int ISADMIN_TRUE = 1;
	public static final int ISADMIN_FALSE = 2;

	public Handler handler = new Handler();

	public Handler handlerIsAdmin = new Handler();
	public ArrayList<String> listAnimators = new ArrayList<String>();

	public void create(User user) {
		Log.d("User", "start method create in UserCRUD");
		ParseUser usr = new ParseUser();
		usr.setUsername(user.getUsername());
		usr.setPassword(user.getPassword());
		usr.put(ISADMIN, user.getIsAdmin());
		Log.d("User", user.getUsername());
		Log.d("User", "is admin = " + user.getIsAdmin());
		Log.d("User", user.getPassword());

		usr.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("User", "h send empty message");
					handler.sendEmptyMessage(CONNECTION_OK);
					Log.d("User", "h = 1");
				} else {
					handler.sendEmptyMessage(0);
					Log.d("User", "exception" + e.toString());
				}
			}
		});
	}

	public void readAnimators() {
		Log.d("UserAPI", "enter in read Animators");
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(ISADMIN, false);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					for (ParseUser parseUser : objects) {
						listAnimators.add(parseUser.getString(USERNAME));
						handler.sendEmptyMessage(CONNECTION_OK);
					}
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	public void login(String name, String password) {
		ParseUser.logInInBackground(name, password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {

					Log.d("User", "is admin = " + ISADMIN);
					if (user.getBoolean(ISADMIN))
						handler.sendEmptyMessage(ISADMIN_TRUE);
					else
						handler.sendEmptyMessage(ISADMIN_FALSE);

				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	public void sentPassword(String email) {
		ParseUser.requestPasswordResetInBackground(email,
				new RequestPasswordResetCallback() {
					public void done(ParseException e) {
						if (e == null) {
							handler.sendEmptyMessage(CONNECTION_OK);
						} else {
							handler.sendEmptyMessage(0);
						}
					}
				});
	}

	public User getCurrentUser() {
		User user = new User();
		user.setObjectId(ParseUser.getCurrentUser().getObjectId());
		user.setUsername(ParseUser.getCurrentUser().getUsername());
		return user;
	}

	public void sync(User user) {
		Log.d("User", "start method sync");
		// if (user.getObjectId() == null) {
		// Log.d("User", "create user");
		create(user);
		// } else {
		// updateName(user.getUsername(), user.getObjectId());
		// }
	}

}
