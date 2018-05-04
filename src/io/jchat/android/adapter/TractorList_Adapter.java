package io.jchat.android.adapter;

import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
//import com.umeng.message.PushAgent;
//import com.umeng.message.tag.TagManager.Result;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import io.jchat.android.activity.TractorInfoActivity;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.entity.Carinfohistory;
import io.jchat.android.entity.TractorList;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapService;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class TractorList_Adapter extends BaseAdapter {
	private Context context;
	private List<Carinfohistortylist> list;
	String cooperativeId = "";
	String carId = "";
	
	public ISoapService soapService = new SoapService();
	
	public TractorList_Adapter(Context context, List<Carinfohistortylist> list,String cooperativeId) {
		this.context = context;
		this.list = list;
		this.cooperativeId = cooperativeId;
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
					R.layout.item_tractor, null);
			viewHolder = new ViewHolder();
			viewHolder.tractorid_tv = (TextView) convertView
					.findViewById(R.id.tractorid_tv);//riqi
			viewHolder.totle_tv = (TextView) convertView
					.findViewById(R.id.totle_tv);//zmiji
			viewHolder.icon_next = (ImageView) convertView
					.findViewById(R.id.icon_next);
			viewHolder.hegmiji=(TextView) convertView.findViewById(R.id.hegmiji);
			viewHolder.hegel=(TextView) convertView.findViewById(R.id.tx_hegel);
			viewHolder.shed=(TextView) convertView.findViewById(R.id.tx_pjsd);
			viewHolder.riqi=(TextView) convertView.findViewById(R.id.expert_headpic);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String shiji=list.get(position).getWorkDate().substring(0,10);
		String yueri=shiji.substring(5);
		viewHolder.riqi.setText(yueri);
		viewHolder.tractorid_tv.setText(shiji);
		String miji="";
		int aa=list.get(position).getWorkLandarea().indexOf(".");
		if(aa+3>=list.get(position).getWorkLandarea().length()){
			miji=list.get(position).getWorkLandarea();
		}else {
			miji=list.get(position).getWorkLandarea().substring(0,aa+3);
		}
		viewHolder.totle_tv.setText("作业面积:                        " + miji + "亩");
		
		int bb=list.get(position).getPassRate().indexOf(".");
		if(bb+3>=list.get(position).getPassRate().length()){
			miji=list.get(position).getPassRate();
		}else {
			miji=list.get(position).getPassRate().substring(0,aa+3);
		}
		viewHolder.hegmiji.setText( list.get(position).getQualifiedLandarea() + "亩");//合格面积改为作业时长
		viewHolder.hegel.setText(miji+"%");
		String cc="";
		if(list.get(position).getDepth().indexOf(".")>=0){
			cc=list.get(position).getDepth().substring(0,list.get(position).getDepth().indexOf("."));
		}else {
			cc=list.get(position).getDepth();
		}
		viewHolder.shed.setText(cc+ " cm");
		
		return convertView;
	}

	static class ViewHolder {
		public TextView tractorid_tv;
		public TextView totle_tv;
		public ImageView icon_next;
		public TextView hegmiji;
		public TextView hegel;
		public TextView shed;
		public TextView riqi;
	}
}
