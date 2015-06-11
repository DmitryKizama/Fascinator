package com.ex.api;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CostumAPI {
	private static final String COSTUME = "Costume";
	private static final String NAMECOSTUME = "costume";
	private static final String TAKED = "taked";
	public static final int CONNECTION_COSTUM = 2;

	public Handler handlerReadCostum = new Handler();

	public ArrayList<String> listOfCostums = new ArrayList<String>();

	public void create(String name) {
		ParseObject parse = new ParseObject(COSTUME);
		parse.put(NAMECOSTUME, name);
		parse.put(TAKED, false);
		parse.saveInBackground();
	}

	public void readCostums() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(COSTUME);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e == null) {
					for (ParseObject parseObject : list) {
						listOfCostums.add(parseObject.getString(NAMECOSTUME));
						Log.d("Costum",
								"costum = "
										+ parseObject.getString(NAMECOSTUME));
					}
					handlerReadCostum.sendEmptyMessage(CONNECTION_COSTUM);
				} else
					e.printStackTrace();
			}
		});
	}
}
