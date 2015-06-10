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
	public static final String PHONENUMBER = "phonenumber";
	public static final String ADRESS = "adress";
	public static final String DATE = "date";
	public static final String CUSTOMERNAME = "customername";
	public static final String NUMBEROFHOURSE = "numberofhourse";
	public static final String ANIMATOR = "animator";
	public static final int CONNECTION = 1;

	public Handler h = new Handler();

	public ArrayList<String> list = new ArrayList<String>();

	public ArrayList<String> listInfOrder = new ArrayList<String>();

	public static String customername;

	public void create(Order order) {
		ParseObject parseOrder = new ParseObject(ORDER);
		parseOrder.put(PHONENUMBER, order.getPhoneNumber());
		parseOrder.put(ADRESS, order.getAdress());
		parseOrder.put(DATE, order.getDate());
		parseOrder.put(CUSTOMERNAME, order.getCustomerName());
		parseOrder.put(NUMBEROFHOURSE, order.getNumberofhourse());
		parseOrder.put(ANIMATOR, order.getAnimatorName());
		parseOrder.saveInBackground();
	}

	public void update(String name, final String text, final String key) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ORDER);
		query.whereEqualTo(CUSTOMERNAME, name);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parseList, ParseException e) {
				if (e == null) {
					Log.d("update", "get in update");
					for (ParseObject parseObject : parseList) {
						Log.d("update", "get list");
						parseObject.put(key, text);
						parseObject.saveInBackground();
						return;
					}
				} else
					Log.d("OrderAPI", "error = " + e);
			}
		});
	}

	public void readCustumers() {
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

	public void readCustumersForAnimators(String animator) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ORDER);
		query.whereEqualTo(ANIMATOR, animator);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parseList, ParseException e) {
				if (e == null) {
					for (ParseObject parseObject : parseList) {
						list.add(parseObject.getString(CUSTOMERNAME));
						Log.d("AnimatorActivity", "customer name = "
								+ parseObject.getString(CUSTOMERNAME));

					}
					h.sendEmptyMessage(CONNECTION);
				} else {
					Log.d(ORDER, "wrong: " + e.toString());
					h.sendEmptyMessage(0);
				}

			}
		});
	}

	public void readOrder() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ORDER);
		query.whereEqualTo(CUSTOMERNAME, getCustomerName());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parseList, ParseException e) {
				if (e == null) {
					for (ParseObject parseObject : parseList) {

						listInfOrder.add(parseObject.getString(PHONENUMBER));
						listInfOrder.add(parseObject.getString(ADRESS));
						listInfOrder.add(parseObject.getString(DATE));
						listInfOrder.add(parseObject.getString(CUSTOMERNAME));
						listInfOrder.add(parseObject.getString(NUMBEROFHOURSE));
						listInfOrder.add(parseObject.getString(ANIMATOR));
						h.sendEmptyMessage(CONNECTION);
						return;
					}
				} else {
					Log.d(ORDER, "wrong: " + e.toString());
					h.sendEmptyMessage(0);
				}

			}
		});
	}

	public void readOrderForAnimators(String animator) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ORDER);
		query.whereEqualTo(ANIMATOR, animator);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parseList, ParseException e) {
				if (e == null) {
					for (ParseObject parseObject : parseList) {
						listInfOrder.add(parseObject.getString(PHONENUMBER));
						listInfOrder.add(parseObject.getString(ADRESS));
						listInfOrder.add(parseObject.getString(DATE));
						listInfOrder.add(parseObject.getString(CUSTOMERNAME));
						listInfOrder.add(parseObject.getString(NUMBEROFHOURSE));
						listInfOrder.add(parseObject.getString(ANIMATOR));
					}
					h.sendEmptyMessage(CONNECTION);
				} else {
					Log.d(ORDER, "wrong: " + e.toString());
					h.sendEmptyMessage(0);
				}

			}
		});
	}

	public static String getCustomerName() {
		return customername;
	}

	public static void setCustomerName(String custname) {
		customername = custname;
	}
}
