package io.jchat.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import io.jchat.android.entity.Coop;

public class NongjisuoAdapter extends BaseAdapter{

	private Context context;
	private List<Coop> list = new ArrayList<Coop>();
	public NongjisuoAdapter(Context context,List<Coop> list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
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
		if(convertView==null){
			head=new MyHead();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_noj, null);
			head.coopname=(TextView) convertView.findViewById(R.id.marks);
			head.fjnum=(TextView) convertView.findViewById(R.id.names);
			head.TotalArea=(TextView) convertView.findViewById(R.id.nums);
			convertView.setTag(head);
		}else {
			head=(MyHead) convertView.getTag();
		}
		head.coopname.setText(list.get(position).getCooperativeName());
		head.fjnum.setText(list.get(position).getCount()+"台");
		int aa = list.get(position).getTotalArea().indexOf(".");
		String miji="";
		if (aa + 3 >= list.get(position).getTotalArea().length()) {
			miji = list.get(position).getTotalArea();
		} else {
			miji = list.get(position).getTotalArea().substring(0, aa + 3);
		}
		head.TotalArea.setText(miji+"亩");
		return convertView;
	}
	
	private MyHead head;
	class MyHead {
		private TextView coopname;
		private TextView fjnum;
		private TextView TotalArea;
	}

}
