package com.ex.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ex.api.OrderAPI;
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
	private String[] data = { "one", "two", "three" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crate_order);
		telephoneNumber = (EditText) findViewById(R.id.editTelephonNumber);
		numberHours = (EditText) findViewById(R.id.editNumberOfHourseSeleb);
		nameClient = (EditText) findViewById(R.id.editClientName);
		date = (EditText) findViewById(R.id.editDate);
		adress = (EditText) findViewById(R.id.editAdress);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner = (Spinner) findViewById(R.id.spinPersons);
		spinner.setAdapter(adapter);
		spinner.setPrompt("Animator");
		spinner.setSelection(0);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		btnPlus = (Button) findViewById(R.id.btnCreate);
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
					OrderAPI ord = new OrderAPI();
					ord.create(order);
				}

			}
		});

	}

	private boolean check() {
		if (telephoneNumber.getText().toString() != ""
				&& numberHours.getText().toString() != ""
				&& nameClient.getText().toString() != ""
				&& date.getText().toString() != ""
				&& adress.getText().toString() != "") {
			return true;
		}
		return false;
	}

}
