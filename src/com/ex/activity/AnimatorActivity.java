package com.ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ex.adapters.ListViewAdapter;
import com.ex.api.OrderAPI;
import com.ex.fascinator.BaseActivity;
import com.ex.fascinator.R;
import com.parse.ParseUser;

public class AnimatorActivity extends BaseActivity {

	private ListView listView;

	private ListViewAdapter listAdapter;
	private OrderAPI orderapi = new OrderAPI();

	private Button btnTake;

	public static boolean ANIMATOR_CONNECT = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animator);

		listView = (ListView) findViewById(R.id.listViewAnimators);
		btnTake = (Button) findViewById(R.id.btnTake);

		orderapi.checkForAnimator(ParseUser.getCurrentUser().getUsername());
		Log.d("animator", "current user= "
				+ ParseUser.getCurrentUser().getUsername());

		orderapi.handlerOrderForAnimator = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d("animator", "get in handler");
				if (msg.what == orderapi.CONNECTION_CHECK) {
					Log.d("animator", "get in handler connection");
					AdminFirstActivity.connect = true;
					Intent intent = new Intent(AnimatorActivity.this,
							InformationOfOrder.class);
					startActivity(intent);
					finish();
				}
			}

		};

		orderapi.readCustumersForAnimators(ParseUser.getCurrentUser()
				.getUsername());
		Log.d("AnimatorActivity", "getCurrentUser = "
				+ ParseUser.getCurrentUser().getUsername());

		showProgress();
		orderapi.handlerOrderAPI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				hideProgress();
				if (msg.what == orderapi.CONNECTION) {
					ANIMATOR_CONNECT = true;
					listAdapter = new ListViewAdapter(AnimatorActivity.this,
							orderapi.list);
					listView.setAdapter(listAdapter);
				} else {
					Log.d("AnimatorActivity", "something wrong");
				}
			}
		};

		btnTake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ListViewAdapter.mSelectedVariation != -1) {
					orderapi.takeOrder(orderapi.getCustomerName());
					Log.d("taked",
							"getCustomerName = " + orderapi.getCustomerName());
					showProgress();
					orderapi.handlerOrderAPI = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == orderapi.CONNECTION_TAKEORDER) {
								hideProgress();
								Intent intent = new Intent(
										AnimatorActivity.this,
										InformationOfOrder.class);
								startActivity(intent);

							} else
								Log.d("taked", "error in OrderAPI");
						}
					};
				} else
					error();

			}
		});

	}

	private void error() {
		Toast.makeText(AnimatorActivity.this, "Choose any order",
				Toast.LENGTH_SHORT).show();
	}
}
