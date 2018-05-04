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
import io.jchat.android.entity.AddressNameItme;

public class AddrssNameAdapter extends BaseAdapter{
	
	private List<AddressNameItme> list=new ArrayList<AddressNameItme>();
	private Context context;
	
	public AddrssNameAdapter(Context context,List<AddressNameItme> list) {
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
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(v==null){
			hendle=new Thendle();
			v=LayoutInflater.from(context).inflate(R.layout.adapter_addressname, null);
			hendle.tv=(TextView) v.findViewById(R.id.name);
			v.setTag(hendle);
		}else {
			hendle=(Thendle) v.getTag();
		}
		if(position==list.get(position).getColor()){
			hendle.tv.setBackgroundColor(0xffaaaaaa);
		}else {
			hendle.tv.setBackgroundColor(0x00aaaaaa);
		}
		hendle.tv.setText(list.get(position).getName());
		return v;
	}
	
	private Thendle hendle;
	class Thendle {
		private TextView tv;
	}

}
