package com.ex.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.ex.adapters.ListViewOrderAdapter;
import com.ex.api.OrderAPI;
import com.ex.fascinator.BaseActivity;
import com.ex.fascinator.R;
import com.parse.ParseUser;

@SuppressLint("HandlerLeak") public class AdminFirstActivity extends BaseActivity {

	private ListView listView;
	private Button btnCreateOrder;
	private Button btnCreateCostum;
	private Button btnLogout;

	private ListViewOrderAdapter listAdapter;
	private OrderAPI orderapi = new OrderAPI();

	public static boolean connect = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_first);
		listView = (ListView) findViewById(R.id.listView);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		btnCreateOrder = (Button) findViewById(R.id.btnCr);
		btnCreateCostum = (Button) findViewById(R.id.btncreateCostum);

		orderapi.readCustumers();

		showProgress();

		orderapi.handlerOrderAPI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				hideProgress();
				if (msg.what == OrderAPI.CONNECTION) {
					AnimatorActivity.ANIMATOR_CONNECT = false;
					listAdapter = new ListViewOrderAdapter(
							AdminFirstActivity.this, orderapi.list);
					listView.setAdapter(listAdapter);
				} else {
					// TODO: generate this
				}
			}
		};

		btnCreateOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				connect = false;
				AnimatorActivity.ANIMATOR_CONNECT = false;
				Intent intent = new Intent(v.getContext(),
						InformationOfOrder.class);
				startActivity(intent);
			}
		});

		btnCreateCostum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						CreateCostumActivity.class);
				startActivity(intent);
			}
		});

		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				Intent intent = new Intent(v.getContext(), Login.class);
				startActivity(intent);
				((Activity) v.getContext()).finish();
			}
		});

	}
}