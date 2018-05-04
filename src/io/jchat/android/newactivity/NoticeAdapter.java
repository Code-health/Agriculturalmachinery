package io.jchat.android.newactivity;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeAdapter extends BaseAdapter {

	private List<String> list = new ArrayList<String>();
	private Context context;

	public NoticeAdapter(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
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
			 heads=new MyThead();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_notice, null);
			heads.tv=(TextView) convertView.findViewById(R.id.not1);
			convertView.setTag(heads);
		} else {
            heads=(MyThead) convertView.getTag();
		}
		heads.tv.setText(list.get(position));
		return convertView;
	}
	
	private MyThead heads;
	
	class MyThead {
		private TextView tv;
	}

}
