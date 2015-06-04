package com.ex.api;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.ex.interfaces.InterfaceAnimatorList;
import com.ex.objects.Animators;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AnimatorsAPI {
	private static final String ANIMATOR = "Animator";
	private static final String NAME = "name";
	public static final int CONNECTION = 1;
	public ArrayList<String> listAnimators = new ArrayList<String>();

	public Handler h = new Handler();
	private InterfaceAnimatorList infAnibList = new InterfaceAnimatorList() {

		@Override
		public void animList(List<String> listAnimators) {
			// TODO Auto-generated method stub

		}
	};

	public void create(Animators anim) {
		ParseObject bool = new ParseObject(ANIMATOR);
		bool.put(NAME, anim.getAnimator());
		bool.saveInBackground();
	}

	public void read() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ANIMATOR);
		query.equals(NAME);

		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				if (e == null) {

					List<String> list = new ArrayList<String>();
					for (ParseObject parseObject : scoreList) {
						list.add(parseObject.getString(NAME));
						listAnimators.add(parseObject.getString(NAME));
						Log.d("AnimatorsAPI",
								"animator = " + parseObject.getString(NAME));
					}
					Log.d("AnimatorsAPI", "list = " + list.size());
					infAnibList.animList(list);

					h.sendEmptyMessage(CONNECTION);
				} else {
					Log.d("AnimatorsAPI", "Error: " + e.getMessage());
				}
			}
		});
	}

}
