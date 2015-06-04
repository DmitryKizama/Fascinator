package com.ex.objects;

import java.util.ArrayList;
import java.util.List;

public class Animators {
	private List<String> animators = new ArrayList<String>(	);
	private String animator;

	public String getAnimator() {
		return animator;
	}

	public void setAnimator(String animator) {
		this.animator = animator;
		animators.add(animator);
	}

	public List<String> getAnimators() {
		return animators;
	}

}
