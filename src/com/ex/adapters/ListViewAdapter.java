package com.ex.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ex.fascinator.R;

public class ListViewAdapter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater lInflater;
	private ArrayList<String> animators;
	private int mSelectedVariation = -1;
	private RadioButton mSelectedRB;
	public boolean clicked = false;

	public ListViewAdapter(Context context, ArrayList<String> animatorsStr) {
		ctx = context;
		animators = animatorsStr;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return animators.size();
	}

	@Override
	public Object getItem(int position) {
		return animators.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.row_item_in_list_view, parent,
					false);
		}

		// Product p = getProduct(position);
		String str = getItem(position).toString();

		((TextView) view.findViewById(R.id.rowTV)).setText(str);

		final RadioButton radBtn = (RadioButton) view.findViewById(R.id.rdBtn);

		radBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clicked = true;
				if (position != mSelectedVariation && mSelectedRB != null) {
					mSelectedRB.setChecked(false);

				}
				mSelectedVariation = position;
				mSelectedRB = (RadioButton) v;
			}
		});

		if (mSelectedVariation != position) {
			radBtn.setChecked(false);
		} else {
			radBtn.setChecked(true);
			if (mSelectedRB != null && radBtn != mSelectedRB) {
				mSelectedRB = radBtn;
			}
		}

		return view;
	}
}
