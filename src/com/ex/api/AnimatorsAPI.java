package com.ex.api;

import com.ex.objects.Animators;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AnimatorsAPI {
	private static final String ANIMATOR = "Animator";
	private static final String NAME = "name";

	public void create(Animators anim) {
		ParseObject bool = new ParseObject(ANIMATOR);
		bool.put(NAME, anim.getAnimator());
		bool.saveInBackground();
	}

	public void read(String animName) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Point");

		query.getInBackground(animName, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				
			}
		});
	}
}
