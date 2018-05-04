package io.jchat.android.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import io.jchat.android.entity.Carinfohistortylist;

public class OperFarmAdapter extends BaseAdapter {
	List<Carinfohistortylist> list = new ArrayList<Carinfohistortylist>();
	Context context;
	private String type;

	public OperFarmAdapter(Context context, List<Carinfohistortylist> dd, String type) {
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
			thead = new MyThead();
			convertView = LayoutInflater.from(context).inflate(R.layout.operfarm_adapter, null);
			thead.time = (TextView) convertView.findViewById(R.id.time);
			// thead.timel=(TextView) convertView.findViewById(R.id.timel);
			thead.work = (TextView) convertView.findViewById(R.id.workm);
			// thead.workh = (TextView) convertView.findViewById(R.id.workh);
			thead.hrgl = (TextView) convertView.findViewById(R.id.hegl);
			thead.shenp = (TextView) convertView.findViewById(R.id.shen);
			convertView.setTag(thead);
		} else {
			thead = (MyThead) convertView.getTag();
		}
		// thead.workh.setText(list.get(position).getQualifiedLandarea() + "亩");
		 SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String date = sDateFormat.format(new java.util.Date());
		if (list.get(position).getWorkDate().substring(0, 10).equals(date)) {
			thead.time.setText("今日");
			String miji;
			int aa = list.get(position).getWorkLandarea().indexOf(".");
			if (aa + 3 >= list.get(position).getWorkLandarea().length()&&aa>-1) {
				miji = list.get(position).getWorkLandarea();
			} else {
				miji = list.get(position).getWorkLandarea().substring(0, aa + 3);
			}
			thead.work.setText(miji + "亩");
			thead.hrgl.setText("计算中");
		} else {

			String shiji = list.get(position).getWorkDate().substring(0, 10);
			String yueri = shiji.substring(5, 7);
			String ri = shiji.substring(8);
			thead.time.setText(shiji);
			String miji;
			int aa = list.get(position).getWorkLandarea().indexOf(".");
			if (aa + 3 >= list.get(position).getWorkLandarea().length()) {
				miji = list.get(position).getWorkLandarea();
			} else {
				miji = list.get(position).getWorkLandarea().substring(0, aa + 3);
			}
			thead.work.setText(miji + "亩");
			// thead.shenp.setVisibility(View.GONE);
			if (list.get(position).getPassRate().equals("计算中")) {
				thead.hrgl.setText(list.get(position).getPassRate());
				if (type.equals("3")) {
					thead.shenp.setVisibility(View.GONE);
				}
			} else {
				String cc = "";
				if (list.get(position).getDepth().indexOf(".") >= 0) {
					cc = list.get(position).getDepth().substring(0, list.get(position).getDepth().indexOf("."));
				} else {
					cc = list.get(position).getDepth();
				}
				thead.shenp.setText(cc + "CM");
				if (type.equals("3")) {
					int bb = list.get(position).getOverlapRate().indexOf(".");
					if (bb >= 0) {
						miji = list.get(position).getOverlapRate().substring(0, bb);
					} else {
						miji = list.get(position).getOverlapRate();
					}
					thead.hrgl.setText(miji + "%");
					thead.shenp.setText(list.get(position).getDistance() + "KM");
				} else {
					int bb = list.get(position).getPassRate().indexOf(".");
					if (bb >= 0) {
						miji = list.get(position).getPassRate().substring(0, bb);
					} else {
						miji = list.get(position).getPassRate();
					}
					thead.hrgl.setText(miji + "%");
				}
			}
			
		}
		return convertView;
	}

	private MyThead thead;

	class MyThead {
		private TextView time;
		private TextView timel;
		private TextView work;
		private TextView workh;
		private TextView shenp;
		private TextView hrgl;
	}

}
