package com.ex.api;

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

	private static final String ADMIORUSER = "AdminorUser";
	private static final String ISADMINORUSER = "isAdmin";

	public int CONNECTION_OK = 1;

	public Handler handler = new Handler();

	public Handler handlerIsAdmin = new Handler();

	public void create(User user) {
		Log.d("User", "start method create in UserCRUD");
		ParseUser usr = new ParseUser();
		usr.put(USERNAME, user.getUsername());
		usr.put(PASSWORD, user.getPassword());
		Log.d("User", user.getUsername());
		ParseObject bool = new ParseObject(ADMIORUSER);
		bool.put(USERNAME, user.getUsername());
		bool.put(ISADMINORUSER, user.isAdmin());
		bool.saveInBackground();
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

	public void login(String name, String password) {
		ParseUser.logInInBackground(name, password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					handler.sendEmptyMessage(CONNECTION_OK);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	public void isAdmin() {
		Log.d("login", "entered in @is admin@");
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ADMIORUSER);
		query.whereEqualTo(USERNAME, ParseUser.getCurrentUser().getUsername());
		Log.d("login", "user = " + ParseUser.getCurrentUser().getUsername());
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject parseName, ParseException e) {
				if (e == null) {
					Log.d("login", "find this");
					if (parseName.getBoolean(ISADMINORUSER)) {
						Log.d("login", "true");
						handlerIsAdmin.sendEmptyMessage(CONNECTION_OK);
					} else {
						Log.d("login", "false");
						handlerIsAdmin.sendEmptyMessage(0);
					}

				} else {
					e.printStackTrace();
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

	public void updateName(final String name, final String id) {
		Log.d("UserAPI", "start updateName");
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(USERNAME, ParseUser.getCurrentUser().getUsername());
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					for (ParseUser parseUser : objects) {
						Log.d("UserAPI", "find object");
						if (parseUser.getObjectId().equals(id)) {
							Log.d("UserAPI", "found, rename");
							ParseUser.getCurrentUser().put(USERNAME, name);
						}
					}
				} else {
					Log.d("UserAPI", "problem in updateName");
					e.printStackTrace();
				}
			}
		});
	}

	public void updateEmail(final String email, final String id) {
		Log.d("UserAPI", "start updateEmail");
		ParseQuery<ParseUser> queryOne = ParseUser.getQuery();
		queryOne.whereEqualTo(EMAIL, email);
		queryOne.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					for (ParseUser parseUser : objects) {
						if (parseUser.getObjectId().equals(id)) {
							ParseUser.getCurrentUser().put(EMAIL, email);
						}
					}
				} else {
					Log.d("UserAPI", "problem in updateEmail");
					e.printStackTrace();
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
		if (user.getObjectId() == null) {
			Log.d("User", "create user");
			create(user);
		} else {
			updateName(user.getUsername(), user.getObjectId());
		}
	}

}
