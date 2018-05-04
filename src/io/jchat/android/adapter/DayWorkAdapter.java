package io.jchat.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.newactivity.DayWorkActivity;
import io.jchat.android.newactivity.MessageFeedbackActivity;

public class DayWorkAdapter extends BaseAdapter {

	List<TractorDetailslist> list = new ArrayList<TractorDetailslist>();
	private Context context;
	String type;

	public DayWorkAdapter(Context context, List<TractorDetailslist> dd, String type) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = dd;
		this.type = type;
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
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_daywork_additional, null);
			vh.workm = (TextView) convertView.findViewById(R.id.workm);
			vh.workl = (TextView) convertView.findViewById(R.id.workl);
			vh.avedepth = (TextView) convertView.findViewById(R.id.she);
			vh.avedepth_tv1 = (TextView) convertView.findViewById(R.id.she1);
			vh.avedepth_tv2 = (TextView) convertView.findViewById(R.id.she2);
			vh.avedepth_tv3 = (TextView) convertView.findViewById(R.id.she3);
			vh.starttime_tv = (TextView) convertView.findViewById(R.id.starttime);
			vh.endtime_tv = (TextView) convertView.findViewById(R.id.endtime);
			vh.zuadd = (Button) convertView.findViewById(R.id.addwork);
			vh.fank = (Button) convertView.findViewById(R.id.feed);
			vh.endtime_tv = (TextView) convertView.findViewById(R.id.endtime);
			vh.fugl = (TextView) convertView.findViewById(R.id.fugl);
			vh.lin1 = (LinearLayout) convertView.findViewById(R.id.lin1);
			vh.lin2 = (LinearLayout) convertView.findViewById(R.id.lin2);
			vh.lin3 = (LinearLayout) convertView.findViewById(R.id.lin3);
			vh.dik = (TextView) convertView.findViewById(R.id.dik);
			vh.shuitianheg=(TextView) convertView.findViewById(R.id.shuitianheg);
			vh.landname=(LinearLayout) convertView.findViewById(R.id.landname);
			vh.landname_tv=(TextView) convertView.findViewById(R.id.landname_tv);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.dik.setText("# " + (position + 1) + " 地块");
		vh.landname_tv.setText("位置："+list.get(position).getLandname());
		vh.fugl.setVisibility(View.VISIBLE);
		vh.avedepth.setVisibility(View.VISIBLE);
		vh.workl.setVisibility(View.GONE);
		if (type.equals("0") || type.equals("1")) {
			// vh.lin1.setVisibility(View.VISIBLE);
			vh.lin2.setVisibility(View.VISIBLE);
			vh.workl.setVisibility(View.VISIBLE);
			vh.avedepth.setVisibility(View.VISIBLE);
			vh.fugl.setVisibility(View.GONE);
			vh.avedepth_tv3.setText("大于25CM:" + list.get(position).getPassRate() + "%");
		}
		String miji = "";
		int aa = list.get(position).getOverlapRate().indexOf(".");
		if (aa + 3 >= list.get(position).getOverlapRate().length()) {
			miji = list.get(position).getOverlapRate();
		} else {
			miji = list.get(position).getOverlapRate().substring(0, aa + 3);
		}
		vh.fugl.setText("覆盖率:" + miji + "%");
		vh.workl.setText("作业里程:" + list.get(position).getDistance() + "KM");
		vh.avedepth_tv1.setText("比例 (0~20CM):" + list.get(position).getPassRateB() + "%");

		vh.avedepth_tv2.setText("20到25CM:" + list.get(position).getPassRateA() + "%");
		if (type.equals("1")) {
			vh.lin2.setVisibility(View.GONE);
			vh.shuitianheg.setVisibility(View.GONE);
			vh.shuitianheg.setText("合格率:"+ list.get(position).getPassRate() + "%");
		}
		vh.workm.setText("作业面积:" + list.get(position).getWorkLandarea() + "亩");
		// viewHolder.percentage_tv.setText("合格率:
		// "+list.get(position).getPassRate()+"%");
		vh.starttime_tv.setText("开始时间:" + list.get(position).getBeginTime().substring(11, 16));
		vh.endtime_tv.setText("结束时间:" + list.get(position).getEndTime().substring(11, 16));
		String cc = "";
		try {
			if (list.get(position).getDepth().indexOf(".") >= 0) {
				cc = list.get(position).getDepth().substring(0, list.get(position).getDepth().indexOf("."));
			} else {
				cc = list.get(position).getDepth();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		positions = position;
		if (type.equals("0") || type.equals("1")) {
			vh.avedepth.setText("平均深度:" + cc + " CM");
		} else {
			vh.avedepth.setText("作业里程:" + list.get(position).getDistance() + "KM");
		}
		ls.add(list.get(position).getWorkDistanceArea());
		ls.add(list.get(position).getDistance());
		ls.add(list.get(position).getPassRateA());
		ls.add(list.get(position).getBeginTime().substring(11, 16));
		ls.add(list.get(position).getEndTime().substring(11, 16));
		vh.zuadd.setOnClickListener(new Maplistener(context, list, position, type));// 点击事件类
		vh.landname.setOnClickListener(new Maplistener(context, list, position, type));//地块名
		vh.fank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("type", "1");
				in.putStringArrayListExtra("list", (ArrayList<String>) ls);
				in.setClass(context, MessageFeedbackActivity.class);
				context.startActivity(in);
			}
		});
		return convertView;
	}

	// 以此 为 作业面积，里程，深度，开始时间，结束时间
	List<String> ls = new ArrayList<String>();
	int positions = 0;
	public ViewHolder vh;

	class ViewHolder {
		public TextView workm, dik;
		public LinearLayout landname;
		public TextView workl, landname_tv;
		public TextView starttime_tv;
		public TextView endtime_tv;
		public TextView avedepth;
		public TextView zuoyelicheng;
		public Button zuadd, fank;
		public TextView avedepth_tv1;
		public TextView avedepth_tv2;
		public TextView avedepth_tv3;
		public TextView fugl,shuitianheg;// 覆盖率
		public LinearLayout lin1;
		public LinearLayout lin2;
		public LinearLayout lin3;
	}

}
