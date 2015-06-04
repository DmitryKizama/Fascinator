package com.ex.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ex.adapters.ListViewAdapter;
import com.ex.api.AnimatorsAPI;
import com.ex.fascinator.R;

public class AdminActivity extends AppCompatActivity {

	private ListView listView;
	private Button btnCreateOrder;

	private ListViewAdapter listAdapter;
	private AnimatorsAPI an = new AnimatorsAPI();
	private List<String> lis = new ArrayList<String>();
	private Handler han = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);

		listView = (ListView) findViewById(R.id.list);

		an.read();
		Log.d("AnimatorsAPI", "finished read");
		an.h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == an.CONNECTION) {
					Log.d("AnimatorsAPI", "first handler");
					listAdapter = new ListViewAdapter(AdminActivity.this,
							an.listAnimators);
					listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					// listAdapter = new ArrayAdapter<String>(
					// getApplicationContext(),
					// android.R.layout.simple_list_item_single_choice,
					// an.listAnimators);
					listView.setAdapter(listAdapter);
				} else {
					Log.d("AnimatorsAPI", "something wrong");
				}
			}
		};

		btnCreateOrder = (Button) findViewById(R.id.btnCreateOrder);
		btnCreateOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (check()) {
					createNewOrder();
				} else {
					Toast.makeText(getApplicationContext(), "Choose animator",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void createNewOrder() {
		Toast.makeText(getApplicationContext(), "Logined", Toast.LENGTH_SHORT)
				.show();
		Intent intent = new Intent(AdminActivity.this, CreateOrder.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		// finish();
	}

	private boolean check() {
		boolean chek = false;

		return chek;
	}
}
