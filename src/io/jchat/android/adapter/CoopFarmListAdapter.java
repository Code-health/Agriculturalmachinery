package io.jchat.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import io.jchat.android.entity.CoopFarmList;
import io.jchat.android.newactivity.CooperativeActivity;

public class CoopFarmListAdapter extends BaseAdapter {

	private List<CoopFarmList> list;
	private Context context;private String typ="";
	

	public CoopFarmListAdapter(Context context, List<CoopFarmList> list,String typ) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.typ=typ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			thead = new MyThead();
			convertView = LayoutInflater.from(context).inflate(R.layout.coop_adapter, null);
			thead.names=(TextView) convertView.findViewById(R.id.names);
			thead.nums=(TextView) convertView.findViewById(R.id.nums);
			thead.maeks=(TextView) convertView.findViewById(R.id.marks);
			thead.today=(TextView) convertView.findViewById(R.id.today);
			convertView.setTag(thead);
		}else{
			thead=(MyThead) convertView.getTag();
		}
		if(typ.equals("0")){
			thead.today.setText("作业面积");
		}
		thead.names.setText(list.get(position).getNames());
		thead.nums.setText(list.get(position).getNums().substring(6));
		thead.maeks.setText(list.get(position).getMarks());
		
		return convertView;
	}

	private MyThead thead;

	class MyThead {
		private TextView names;
		private TextView nums;
		private TextView maeks;
		private TextView today;
	}
}
