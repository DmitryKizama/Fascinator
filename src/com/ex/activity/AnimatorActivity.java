package com.ex.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.ex.adapters.ListViewOrderAdapter;
import com.ex.api.OrderAPI;
import com.ex.fascinator.R;
import com.parse.ParseUser;

public class AnimatorActivity extends AppCompatActivity {

	private ListView listView;

	private ListViewOrderAdapter listAdapter;
	private OrderAPI orderapi = new OrderAPI();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animator);

		listView = (ListView) findViewById(R.id.listViewAnimators);

		orderapi.readCustumersForAnimators(ParseUser.getCurrentUser()
				.getUsername());
		Log.d("AnimatorActivity", "getCurrentUser = "
				+ ParseUser.getCurrentUser().getUsername());

		orderapi.h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == orderapi.CONNECTION) {
					Log.d("AnimatorActivity", "get in handler");
					listAdapter = new ListViewOrderAdapter(
							AnimatorActivity.this, orderapi.list);
					listView.setAdapter(listAdapter);
				} else {
					Log.d("AnimatorActivity", "something wrong");
				}
			}
		};
	}
}
