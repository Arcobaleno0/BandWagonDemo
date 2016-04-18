package com.laomaizi.bandwagondemo.lightpainting;

import java.util.List;

import com.laomaizi.bandwagondemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FavItemAdapter extends ArrayAdapter<FavItem> {


	private int resourceId;
	public FavItemAdapter(Context context, int resource, List<FavItem> objects) {
		super(context, resource, objects);
		resourceId = resource;
		// TODO �Զ����ɵĹ��캯�����
	}
	
	public View getView(int position, View contentView, ViewGroup parent) {
		
		FavItem favItem = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView tvTextView = (TextView) view.findViewById(R.id.id);
		tvTextView.setText(favItem.getText());
		return view;
	}



}