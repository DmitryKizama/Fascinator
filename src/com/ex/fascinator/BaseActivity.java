package com.ex.fascinator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;

public class BaseActivity extends AppCompatActivity {

	private Dialog progressDialog;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress);

		progressBar = (ProgressBar) findViewById(R.id.progress);
	}

	public void showProgress() {
		// hideKeyBoard();

		progressDialog = new Dialog(this);
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setContentView(R.layout.dialog_progress);
		progressDialog.setCancelable(false);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		
		Animation anim = new RotateAnimation(0.0f, 360.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(1000);
		progressBar.startAnimation(anim);

		progressDialog.show();
	}

	@SuppressLint("NewApi")
	public void hideProgress() {
		// we have to check isDestroyed(), @see
		// http://stackoverflow.com/questions/22924825/view-not-attached-to-window-manager-crash
		if (progressDialog != null && progressDialog.isShowing()) {
			if (android.os.Build.VERSION.SDK_INT >= 17) {
				if (!isDestroyed()) {
					progressDialog.dismiss();
					progressBar.clearAnimation();
				}
			} else if (!isFinishing()) {
				progressDialog.dismiss();
				progressBar.clearAnimation();
			}
		}
	}

	boolean getProgressVisible() {
		return progressDialog != null && progressDialog.isShowing();
	}

}
