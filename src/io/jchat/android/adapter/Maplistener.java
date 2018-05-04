package io.jchat.android.adapter;

import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import io.jchat.android.activity.NowtimeMapActivity;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.newactivity.AddressNameActivity;

/**
 * 点击事件类--以避免页面未刷新时由于POS没有变 化而导致的不同作业地图坐标相同的问题
 * 
 * @author Administrator
 *
 */
public class Maplistener implements OnClickListener {

	private Context context;
	private List<TractorDetailslist> list;
	private int pos;
	private String type;

	public Maplistener(Context context, List<TractorDetailslist> list, int pos, String type) {
		this.context = context;
		this.list = list;
		this.pos = pos;
		this.type = type;
	}

	public Maplistener() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.landname:
			Intent in1 = new Intent();
			in1.setClass(context, AddressNameActivity.class);
			in1.putExtra("workSerial", list.get(pos).getWorkSerial());
			context.startActivity(in1);
			break;
		case R.id.addwork:
			Intent in = new Intent();
			in.setClass(context, NowtimeMapActivity.class);
			in.putExtra("workSerial", list.get(pos).getWorkSerial());
			in.putExtra("type", type);
			context.startActivity(in);
			break;
		default:
			break;
		}
		
	}

}
