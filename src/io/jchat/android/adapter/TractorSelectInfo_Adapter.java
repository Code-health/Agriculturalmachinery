package io.jchat.android.adapter;

import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import io.jchat.android.activity.NowtimeMapActivity;
import io.jchat.android.activity.TractorInfoActivity;
import io.jchat.android.adapter.TractorTodayInfo_Adapter.ViewHolder;
import io.jchat.android.entity.Contacts;
import io.jchat.android.entity.TractorDetails;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapService;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class TractorSelectInfo_Adapter extends BaseAdapter {
	private Context context;
	private List<TractorDetailslist> list;
	String carId = "";
	String workDate  = "";
	private String type="0";
	private TractorInfoActivity aactivity;
	
	public ISoapService soapService = new SoapService();
	
	public TractorSelectInfo_Adapter(TractorInfoActivity aactivity,Context context, List<TractorDetailslist> list,String carId,String workDate) {
		this.context = context;
		this.list = list;
		this.carId = carId;
		this.workDate = workDate;
		this.aactivity=aactivity;
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
			viewHolder.butwz=(Button) convertView.findViewById(R.id.butwz);
			viewHolder.zuoyelicheng=(TextView) convertView.findViewById(R.id.zuoyelicheng);
			viewHolder.avedepth_tv1=(TextView) convertView.findViewById(R.id.avedepth_tv1);
			viewHolder.avedepth_tv2=(TextView) convertView.findViewById(R.id.avedepth_tv2);
			viewHolder.avedepth_tv3=(TextView) convertView.findViewById(R.id.avedepth_tv3);
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

		viewHolder.zuoyelicheng.setText(list.get(position).getDistance()+"公里");
		viewHolder.avedepth_tv1.setText("比例 (0~20CM):"+list.get(position).getPassRateB()+"%");
		viewHolder.avedepth_tv2.setText("比例 (20~25CM):"+list.get(position).getPassRateA()+"%");
		viewHolder.avedepth_tv3.setText("比例 (大于25CM):"+list.get(position).getPassRate()+"%");
		viewHolder.totle_tv.setText("作业面积:" + list.get(position).getWorkDistanceArea() + "亩");
//		viewHolder.percentage_tv.setText("合格率:      "+list.get(position).getPassRate()+"%");
		viewHolder.starttime_tv.setText(list.get(position).getBeginTime().substring(11,16));
		viewHolder.endtime_tv.setText(list.get(position).getEndTime().substring(11,16));
		String cc="";
		if(list.get(position).getDepth().indexOf(".")>=0){
			cc=list.get(position).getDepth().substring(0,list.get(position).getDepth().indexOf("."));
		}else {
			cc=list.get(position).getDepth();
		}
		positions=position;
		viewHolder.avedepth_tv.setText("平均深度:"+cc+ " cm");
		viewHolder.butwz.setOnClickListener(new Maplistener(context,list,position,type));//点击事件类
		return convertView;
	}
	
	int positions=0;

	static class ViewHolder {
		public TextView totle_tv;
		public TextView percentage_tv;
		public TextView starttime_tv;
		public TextView endtime_tv;
		public TextView avedepth_tv;
		public TextView zuoyelicheng;
		public Button butwz;
		public TextView avedepth_tv1;
		public TextView avedepth_tv2;
		public TextView avedepth_tv3;
	}
}
