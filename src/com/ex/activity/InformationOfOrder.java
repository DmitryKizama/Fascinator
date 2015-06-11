package com.ex.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ex.adapters.ListViewOrderAdapter;
import com.ex.api.CostumAPI;
import com.ex.api.OrderAPI;
import com.ex.api.UserAPI;
import com.ex.fascinator.BaseActivity;
import com.ex.fascinator.R;
import com.ex.objects.Order;

public class InformationOfOrder extends BaseActivity {

	private EditText telephoneNumber;
	private EditText numberHours;
	private EditText nameClient;
	private EditText date;
	private EditText adress;
	private TextView sum;
	private Spinner spinner;
	private Spinner spinnerCostum;

	private Button btnPlus;
	private Button btnUpdate;

	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> listCostums = new ArrayList<String>();
	private UserAPI userapi = new UserAPI();
	private OrderAPI orderapi = new OrderAPI();
	private CostumAPI costumapi = new CostumAPI();

	private Handler handler = new Handler();
	private static final int CONNECTION = 1;
	private int checkonlyIfOnlyOneInHandler = 0;
	private int checkonlyIfOnlyOneInHandler2 = 0;

	private String changedText = "error";
	private String key;

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
		btnUpdate = (Button) findViewById(R.id.btnUpdate);

		setListAnimatorsInSpinner();
		setListCostumsInSpinner();

		userapi.readAnimators();

		if (AdminFirstActivity.connect) {
			getinformation();
			btnPlus.setVisibility(View.INVISIBLE);

			ifTextChanged();

		} else {
			createorder();
		}

		btnUpdate.setVisibility(View.INVISIBLE);

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				orderapi.update(orderapi.getCustomerName(), changedText, key);
				Intent intent = new Intent(v.getContext(),
						AdminFirstActivity.class);
				startActivity(intent);
			}
		});
	}

	private void setListCostumsInSpinner() {
		costumapi.readCostums();
		costumapi.handlerReadCostum = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d("Costum", "enter in handler");
				if (msg.what == costumapi.CONNECTION_COSTUM) {
					Log.d("Costum", "enter in IF handler");
					if (checkonlyIfOnlyOneInHandler2 == 0) {
						checkonlyIfOnlyOneInHandler2++;
						for (String str : costumapi.listOfCostums) {
							listCostums.add(str);
							Log.d("Costum", "str = " + str);
						}
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InformationOfOrder.this,
							android.R.layout.simple_spinner_item, listCostums);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					spinnerCostum = (Spinner) findViewById(R.id.spinnerCostum);
					spinnerCostum.setAdapter(adapter);
					// spinner.setPrompt("Costume");
					spinnerCostum.setSelection(0);
				} else {
					// something wrong
				}
			}
		};

		userapi.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == userapi.CONNECTION_OK) {
					if (checkonlyIfOnlyOneInHandler == 0) {
						checkonlyIfOnlyOneInHandler++;
						for (String str : userapi.listAnimators) {
							data.add(str);
						}
						handler.sendEmptyMessage(CONNECTION);
					}
				} else {
					Log.d("CreateOrder", "error in handler");
					handler.sendEmptyMessage(0);
				}
			}
		};
	}

	private void ifTextChanged() {

		TextWatcher textWatcherphoneNumber = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btnUpdate.setVisibility(View.VISIBLE);
				if (AnimatorActivity.ANIMATOR_CONNECT) {
					btnUpdate.setVisibility(View.INVISIBLE);
				}

				Log.d("Inf", "text = " + s.toString());
				changedText = s.toString();
				key = orderapi.PHONENUMBER;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};

		TextWatcher textWatcherHours = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btnUpdate.setVisibility(View.VISIBLE);
				Log.d("Inf", "text = " + s.toString());
				changedText = s.toString();
				key = orderapi.NUMBEROFHOURSE;
				if (AnimatorActivity.ANIMATOR_CONNECT) {
					btnUpdate.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};
		TextWatcher textWatchernameClient = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btnUpdate.setVisibility(View.VISIBLE);
				Log.d("Inf", "text = " + s.toString());
				changedText = s.toString();
				key = orderapi.CUSTOMERNAME;
				if (AnimatorActivity.ANIMATOR_CONNECT) {
					btnUpdate.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};
		TextWatcher textWatcherdate = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btnUpdate.setVisibility(View.VISIBLE);
				Log.d("Inf", "text = " + s.toString());
				changedText = s.toString();
				key = orderapi.DATE;
				if (AnimatorActivity.ANIMATOR_CONNECT) {
					btnUpdate.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};
		TextWatcher textWatcheradress = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				btnUpdate.setVisibility(View.VISIBLE);
				Log.d("Inf", "text = " + s.toString());
				changedText = s.toString();
				key = orderapi.ADRESS;
				if (AnimatorActivity.ANIMATOR_CONNECT) {
					btnUpdate.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		};

		telephoneNumber.addTextChangedListener(textWatcherphoneNumber);
		numberHours.addTextChangedListener(textWatcherHours);
		nameClient.addTextChangedListener(textWatchernameClient);
		date.addTextChangedListener(textWatcherdate);
		adress.addTextChangedListener(textWatcheradress);

	}

	private void getinformation() {
		Log.d("UserAPI", "getCustomerName = " + orderapi.getCustomerName());
		orderapi.readOrder();
		showProgress();
		orderapi.handlerOrderAPI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				hideProgress();
				if (msg.what == orderapi.CONNECTION) {
					telephoneNumber.setText(orderapi.listInfOrder.get(0));
					adress.setText(orderapi.listInfOrder.get(1));
					date.setText(orderapi.listInfOrder.get(2));
					nameClient.setText(orderapi.listInfOrder.get(3));
					numberHours.setText(orderapi.listInfOrder.get(4));

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InformationOfOrder.this,
							android.R.layout.simple_spinner_item, data);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					spinner = (Spinner) findViewById(R.id.spinPersons);
					spinner.setAdapter(adapter);
					spinner.setPrompt("Animator");
					if (AnimatorActivity.ANIMATOR_CONNECT) {
						spinner.setEnabled(false);
						spinner.setClickable(false);

						spinnerCostum.setEnabled(false);
						spinnerCostum.setClickable(false);
					}
					if (AnimatorActivity.ANIMATOR_CONNECT) {
						Log.d("G", "AnimatorActivity.ANIMATOR_CONNECT = "
								+ AnimatorActivity.ANIMATOR_CONNECT);
						telephoneNumber.setEnabled(false);
						numberHours.setEnabled(false);
						nameClient.setEnabled(false);
						date.setEnabled(false);
						adress.setEnabled(false);
						btnPlus.setEnabled(false);
					}

					Log.d("UserAPI",
							"animator = " + orderapi.listInfOrder.get(5));

					for (int j = 0; j < data.size(); j++) {
						if (orderapi.listInfOrder.get(5).equals(data.get(j))) {
							spinner.setSelection(j);
						}
					}

				} else
					Log.d("CreateOrder", "Error");
			}
		};
	}

	private void createorder() {
		showProgress();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				hideProgress();
				if (msg.what == CONNECTION) {
					Log.d("Inf", "entered in Handler h");
					checkonlyIfOnlyOneInHandler++;
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InformationOfOrder.this,
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
					order.setCostum(spinnerCostum.getSelectedItem().toString());
					OrderAPI ord = new OrderAPI();
					ord.create(order);
					Intent intent = new Intent(v.getContext(),
							AdminFirstActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				} else
					error();
			}
		});
	}

	private void error() {
		Toast.makeText(this, "Enter some information", Toast.LENGTH_SHORT)
				.show();
	}

	private void setListAnimatorsInSpinner() {

		userapi.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == userapi.CONNECTION_OK) {
					if (checkonlyIfOnlyOneInHandler == 0) {
						checkonlyIfOnlyOneInHandler++;
						for (String str : userapi.listAnimators) {
							data.add(str);
						}
						handler.sendEmptyMessage(CONNECTION);
					}
				} else {
					Log.d("CreateOrder", "error in handler");
					handler.sendEmptyMessage(0);
				}
			}
		};
	}

	private boolean check() {
		if (telephoneNumber.getText().toString().equals("")
				|| numberHours.getText().toString().equals("")
				|| nameClient.getText().toString().equals("")
				|| date.getText().toString().equals("")
				|| adress.getText().toString().equals("")) {
			return false;
		} else
			return true;
	}

}
