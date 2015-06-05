package com.ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.ex.adapters.ListViewOrderAdapter;
import com.ex.api.OrderAPI;
import com.ex.fascinator.R;

public class AdminFirstActivity extends AppCompatActivity {

	private ListView listView;
	private Button btnCreateOrder;

	private ListViewOrderAdapter listAdapter;
	private OrderAPI orderapi = new OrderAPI();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_first);

		listView = (ListView) findViewById(R.id.listView);
		orderapi.read();
		orderapi.h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == orderapi.CONNECTION) {
					listAdapter = new ListViewOrderAdapter(
							AdminFirstActivity.this, orderapi.list);
					listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					listView.setAdapter(listAdapter);
				} else {
					// TODO: generate this
				}
			}
		};

		btnCreateOrder = (Button) findViewById(R.id.btnCr);
		btnCreateOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CreateOrder.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});

	}
}