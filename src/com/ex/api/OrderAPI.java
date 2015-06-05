package com.ex.api;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.ex.objects.Order;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class OrderAPI {

	private static final String ORDER = "Order";
	private static final String PHONENUMBER = "phonenumber";
	private static final String ADRESS = "adress";
	private static final String DATE = "date";
	private static final String CUSTOMERNAME = "customername";
	private static final String NUMBEROFHOURSE = "numberofhourse";
	public static final int CONNECTION = 1;

	public Handler h = new Handler();

	public ArrayList<String> list = new ArrayList<String>();

	public void create(Order order) {
		ParseObject parseOrder = new ParseObject(ORDER);
		parseOrder.put(PHONENUMBER, order.getPhoneNumber());
		parseOrder.put(ADRESS, order.getAdress());
		parseOrder.put(DATE, order.getDate());
		parseOrder.put(CUSTOMERNAME, order.getCustomerName());
		parseOrder.put(NUMBEROFHOURSE, order.getNumberofhourse());
		parseOrder.saveInBackground();
	}

	public void read() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ORDER);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parseList, ParseException e) {
				if (e == null) {
					for (ParseObject parseObject : parseList) {
						list.add(parseObject.getString(CUSTOMERNAME));
					}
					h.sendEmptyMessage(CONNECTION);
				} else {
					Log.d(ORDER, "wrong: " + e.toString());
					h.sendEmptyMessage(0);
				}

			}
		});
	}

}
