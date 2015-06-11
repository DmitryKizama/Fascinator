package com.ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ex.api.CostumAPI;
import com.ex.fascinator.BaseActivity;
import com.ex.fascinator.R;

public class CreateCostumActivity extends BaseActivity {

	private EditText etCostume;
	private Button btnCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_costume);

		etCostume = (EditText) findViewById(R.id.editCostume);
		btnCreate = (Button) findViewById(R.id.btnCreate);

		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CostumAPI costumapi = new CostumAPI();
				if (etCostume.getText().toString().equals("")) {
					error();
				} else {
					costumapi.create(etCostume.getText().toString());
					Intent intent = new Intent(v.getContext(),
							AdminFirstActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});

	}

	private void error() {
		Toast.makeText(CreateCostumActivity.this, "Enter name of costume",
				Toast.LENGTH_SHORT).show();
	}
}
