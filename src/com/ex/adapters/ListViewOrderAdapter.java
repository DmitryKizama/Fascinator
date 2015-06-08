package com.ex.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ex.activity.AdminFirstActivity;
import com.ex.activity.InformationOfOrder;
import com.ex.api.OrderAPI;
import com.ex.fascinator.R;

public class ListViewOrderAdapter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater lInflater;
	private ArrayList<String> animators;

	public ListViewOrderAdapter(Context context, ArrayList<String> animatorsStr) {
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
			view = lInflater.inflate(R.layout.row_order_list_view, parent,
					false);
		}

		final String str = getItem(position).toString();

		((TextView) view.findViewById(R.id.rowTV)).setText(str);
		((TextView) view.findViewById(R.id.rowTV))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						OrderAPI.setCustomerName(str);
						Log.d("UserAPI", "setCustomerName = " + str);

						AdminFirstActivity.connect = true;
						Intent intent = new Intent(v.getContext(),
								InformationOfOrder.class);
						v.getContext().startActivity(intent);
					}
				});

		return view;
	}
}
