package com.ex.fascinator;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.ex.api.AnimatorsAPI;
import com.ex.interfaces.InterfaceAnimatorList;

public class Fascinator extends AppCompatActivity {

	private AnimatorsAPI an = new AnimatorsAPI();
	private List<String> lis = new ArrayList<String>();
	private Handler han = new Handler();
	private InterfaceAnimatorList nf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fascinator);
		an.read();
		an.h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == an.CONNECTION) {
					nf = new InterfaceAnimatorList() {

						@Override
						public void animList(List<String> listAnimators) {
							for (String string : listAnimators) {
								lis.add(string);
								han.sendEmptyMessage(an.CONNECTION);
							}
						}
					};
				}
			}
		};

	}

}
