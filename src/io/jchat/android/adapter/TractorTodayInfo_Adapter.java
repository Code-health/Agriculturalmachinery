package io.jchat.android.adapter;

import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
//import com.umeng.message.PushAgent;
//import com.umeng.message.tag.TagManager.Result;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import io.jchat.android.entity.Contacts;
import io.jchat.android.entity.TractorDetails;
import io.jchat.android.entity.TractorTodayDetail;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapService;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class TractorTodayInfo_Adapter extends BaseAdapter {
	private Context context;
	private List<TractorTodayDetail> list;
	String carId = "";
	
	public ISoapService soapService = new SoapService();
	
	public TractorTodayInfo_Adapter(Context context, List<TractorTodayDetail> list,String carId) {
		this.context = context;
		this.list = list;
		this.carId = carId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_tractorinfo, null);
			viewHolder = new ViewHolder();
			viewHolder.totle_tv = (TextView) convertView
					.findViewById(R.id.totle_tv);
			viewHolder.percentage_tv = (TextView) convertView
					.findViewById(R.id.percentage_tv);
			viewHolder.starttime_tv = (TextView) convertView
					.findViewById(R.id.starttime_tv);
			viewHolder.endtime_tv = (TextView) convertView
					.findViewById(R.id.endtime_tv);
			viewHolder.avedepth_tv = (TextView) convertView
					.findViewById(R.id.avedepth_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		carId  = list.get(position).getCarId();

		viewHolder.totle_tv.setText("作业面积：    " + list.get(position).getTotalLandarea() + "亩");
		viewHolder.percentage_tv.setText("合格率：    "+list.get(position).getPassRate()+"%");
		viewHolder.starttime_tv.setText(list.get(position).getBeginTime().substring(11,16));
		viewHolder.endtime_tv.setText(list.get(position).getEndTime().substring(11,16));
		String cc="";
		if(list.get(position).getDepth().indexOf(".")>=0){
			cc=list.get(position).getDepth().substring(0,list.get(position).getDepth().indexOf("."));
		}else {
			cc=list.get(position).getDepth();
		}
		viewHolder.avedepth_tv.setText(cc+ "cm");
		return convertView;
	}

	static class ViewHolder {
		public TextView totle_tv;
		public TextView percentage_tv;
		public TextView starttime_tv;
		public TextView endtime_tv;
		public TextView avedepth_tv;
	}
}
