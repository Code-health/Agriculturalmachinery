package io.jchat.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.jchat.android.entity.FindnoticeEntity;

public class FindNoticeAdapter extends BaseAdapter {

	List<FindnoticeEntity> list = new ArrayList<FindnoticeEntity>();
	private Context context;

	public FindNoticeAdapter(Context context, List<FindnoticeEntity> list) {
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
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;// 返回itme种数
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getType();// Itme的type值
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			thend = new MyThend();
			if (list.get(position).getType() == 1) {
				v = LayoutInflater.from(context).inflate(R.layout.adapter_findnotice, null);
				thend.tv = (TextView) v.findViewById(R.id.tv);
				thend.tvbady=(TextView) v.findViewById(R.id.tv_bady);
			}
			v.setTag(thend);
		} else {
			thend = (MyThend) v.getTag();
		}
		thend.tv.setText(list.get(position).getText());
		thend.tvbady.setText(list.get(position).getTextbady());
		return v;
	}

	private MyThend thend;

	class MyThend {
		private TextView tv;
		private TextView tvbady;
	}

}
