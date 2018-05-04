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
import io.jchat.android.adapter.NumoperFarmAdapter.Myhead;
import io.jchat.android.entity.Carinfohistortylist;

public class TownshipAdapter extends BaseAdapter{

	List<Carinfohistortylist> list = new ArrayList<Carinfohistortylist>();
	private Context context;
	private String type;
	
	public TownshipAdapter(Context context,List<Carinfohistortylist> list,String type) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
		this.type=type;
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
			head=new Myhead();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_numoperfarm, null);
			head.num=(TextView) convertView.findViewById(R.id.num);
			head.name=(TextView) convertView.findViewById(R.id.name);
			head.passate=(TextView) convertView.findViewById(R.id.passRate);
			head.area=(TextView) convertView.findViewById(R.id.area);
			convertView.setTag(head);
		}else {
			head=(Myhead) convertView.getTag();
		}
		int bb=0;
		String cc="";
		bb=list.get(position).getPassRate().indexOf(".");
		if(bb>0){
			cc=list.get(position).getPassRate().substring(0,bb);
		}else {
			cc=list.get(position).getPassRate();
		}
		head.passate.setText(cc+"%");
		head.num.setText(list.get(position).getCounty());
		bb=list.get(position).getArea().indexOf(".");
		if(bb>0){
			cc=list.get(position).getArea().substring(0,bb);
		}else {
			cc=list.get(position).getArea();
		}
		head.name.setText(cc+"亩");
		head.area.setText(list.get(position).getDepth()+"CM");
		if(type.equals("3")){
			bb=list.get(position).getDistance().indexOf(".");
			if(bb>0){
				cc=list.get(position).getDistance().substring(0,bb);
			}else {
				cc=list.get(position).getDistance();
			}
			head.area.setText(cc+"KM");
			bb=list.get(position).getOverlapRate().indexOf(".");
			if(bb>0){
				cc=list.get(position).getOverlapRate().substring(0,bb);
			}else {
				cc=list.get(position).getOverlapRate();
			}
			head.passate.setText(cc+"%");
		}
		
		return convertView;
	}
	
	
	private Myhead head;
	class Myhead{
		public TextView num;
		public TextView name;
		public TextView area;
		public TextView passate;
	}

}
