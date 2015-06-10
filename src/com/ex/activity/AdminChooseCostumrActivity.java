package com.ex.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ex.adapters.ListViewAdapter;
import com.ex.fascinator.R;

public class AdminChooseCostumrActivity extends AppCompatActivity {
	private ListView listView;
	private Button btnCreateOrder;

	private ListViewAdapter listAdapter;
	private List<String> lis = new ArrayList<String>();
	private Handler han = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);

		listView = (ListView) findViewById(R.id.list);

		Log.d("AnimatorsAPI", "finished read");
		// an.h = new Handler() {
		// @Override
		// public void handleMessage(Message msg) {
		// if (msg.what == an.CONNECTION) {
		// Log.d("AnimatorsAPI", "first handler");
		// listAdapter = new ListViewAdapter(
		// AdminChooseAnimatorActivity.this, an.listAnimators);
		// listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// listView.setAdapter(listAdapter);
		// } else {
		// Log.d("AnimatorsAPI", "something wrong");
		// }
		// }
		// };

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
		Intent intent = new Intent(AdminChooseCostumrActivity.this,
				InformationOfOrder.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		// finish();
	}

	private boolean check() {
		if (listAdapter.clicked)
			return true;
		else
			return false;

	}
}
