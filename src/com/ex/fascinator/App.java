package com.ex.fascinator;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// Parse.enableLocalDatastore(this);

		Parse.initialize(this, "DCKrV5w1jwDEF0QVZiyIfGDWSW2nMZcNn4l0svgw",
				"SDXKiLEpLrfTKqeBwYDetXy0J5D5uc7D0XkWnzE3");

		ParseInstallation.getCurrentInstallation().saveInBackground();

	}
}
