package com.ex.activity;

import java.util.ArrayList;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ex.adapters.ListViewOrderAdapter;
import com.ex.api.OrderAPI;
import com.ex.api.UserAPI;
import com.ex.fascinator.R;
import com.ex.objects.Order;

public class CreateOrder extends AppCompatActivity {

	private EditText telephoneNumber;
	private EditText numberHours;
	private EditText nameClient;
	private EditText date;
	private EditText adress;
	private TextView sum;
	private Spinner spinner;
	private Button btnPlus;

	private ArrayList<String> data = new ArrayList<String>();

	private UserAPI userapi = new UserAPI();
	private OrderAPI o = new OrderAPI();

	private Handler h = new Handler();
	private static final int CONNECTION = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crate_order);

		telephoneNumber = (EditText) findViewById(R.id.editTelephonNumber);
		numberHours = (EditText) findViewById(R.id.editNumberOfHourseSeleb);
		nameClient = (EditText) findViewById(R.id.editClientName);
		date = (EditText) findViewById(R.id.editDate);
		adress = (EditText) findViewById(R.id.editAdress);
		btnPlus = (Button) findViewById(R.id.btnCreate);

		Log.d("UserAPI", "onCreate");
		Log.d("UserAPI", "flag = " + AdminFirstActivity.connect);
		setSpinner();
		userapi.readAnimators();

		if (AdminFirstActivity.connect) {
			getinformation();
			btnPlus.setVisibility(View.INVISIBLE);
		} else
			createorder();

	}

	private void getinformation() {
		Log.d("UserAPI", "getCustomerName = " + o.getCustomerName());
		o.readOrder();
		o.h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == o.CONNECTION) {
					telephoneNumber.setText(o.listInfOrder.get(0));
					adress.setText(o.listInfOrder.get(1));
					date.setText(o.listInfOrder.get(2));
					nameClient.setText(o.listInfOrder.get(3));
					numberHours.setText(o.listInfOrder.get(4));

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							CreateOrder.this,
							android.R.layout.simple_spinner_item, data);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					spinner = (Spinner) findViewById(R.id.spinPersons);
					spinner.setAdapter(adapter);
					spinner.setPrompt("Animator");

					Log.d("UserAPI", "animator = " + o.listInfOrder.get(5));

					for (int j = 0; j < data.size(); j++) {
						if (o.listInfOrder.get(5).equals(data.get(j))) {
							spinner.setSelection(j);
						}
					}

				} else
					Log.d("CreateOrder", "Error");
			}
		};
	}

	private void createorder() {

		h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CONNECTION) {

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							CreateOrder.this,
							android.R.layout.simple_spinner_item, data);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					spinner = (Spinner) findViewById(R.id.spinPersons);
					spinner.setAdapter(adapter);
					spinner.setPrompt("Animator");
					spinner.setSelection(0);
				} else {
					Log.d("CreateOrder", "error in handler");
				}

			}
		};

		btnPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (check()) {
					Order order = new Order();
					order.setCustomerName(nameClient.getText().toString());
					order.setAdress(adress.getText().toString());
					order.setDate(date.getText().toString());
					order.setPhoneNumber(telephoneNumber.getText().toString());
					order.setNumberofhours(numberHours.getText().toString());
					order.setAnimatorName(spinner.getSelectedItem().toString());
					OrderAPI ord = new OrderAPI();
					ord.create(order);
					Intent intent = new Intent(v.getContext(),
							AdminFirstActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}

			}
		});
	}

	private void setSpinner() {

		userapi.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == userapi.CONNECTION) {

					for (String str : userapi.listAnimators) {
						data.add(str);
					}
					h.sendEmptyMessage(CONNECTION);
				} else {
					Log.d("CreateOrder", "error in handler");
					h.sendEmptyMessage(0);
				}
			}
		};
	}

	private boolean check() {
		if (telephoneNumber.getText().toString() != ""
				&& numberHours.getText().toString() != ""
				&& nameClient.getText().toString() != ""
				&& date.getText().toString() != ""
				&& adress.getText().toString() != "") {
			return true;
		} else
			return false;
	}

}
