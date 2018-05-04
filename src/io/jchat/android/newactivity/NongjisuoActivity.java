package io.jchat.android.newactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.update.PgyUpdateManager;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.adapter.NongjisuoAdapter;
import io.jchat.android.entity.Coop;
import io.jchat.android.entity.Cooperatives;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.HttpGetService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class NongjisuoActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout work1, work2, work3, work4;
	private RelativeLayout[] work = new RelativeLayout[4];
	private View wv1, wv2, wv3, wv4;
	private View[] wv = new View[4];
	private LinearLayout linwork;
	private Context content;
	private ListView farm_list;
	private TextView time, username1, allmianji, hegelu, pinjunsh, njshu, title;
	private ImageView hzsh_lists,shuax;
	String carid = "";
	String userid = "";
	String officeid = "";
	String officename = "";
	String name = "";
	String userType = "";
	String worktype = "0";
	int starts = 0;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_nongjiju);
		content = this;
		shuax=(ImageView) findViewById(R.id.shuax);
		work[0] = (RelativeLayout) findViewById(R.id.work1);
		work[2] = (RelativeLayout) findViewById(R.id.work2);
		work[3] = (RelativeLayout) findViewById(R.id.work3);
		work[1] = (RelativeLayout) findViewById(R.id.work4);
		wv[0] = findViewById(R.id.workv1);
		wv[2] = findViewById(R.id.workv2);
		wv[3] = findViewById(R.id.workv3);
		wv[1] = findViewById(R.id.workv4);
		title = (TextView) findViewById(R.id.title);
		linwork = (LinearLayout) findViewById(R.id.work);
		hzsh_lists = (ImageView) findViewById(R.id.hzsh_lists);
		farm_list = (ListView) findViewById(R.id.list_farm);
		time = (TextView) findViewById(R.id.time);
		username1 = (TextView) findViewById(R.id.username1);
		allmianji = (TextView) findViewById(R.id.allmianji);// mianji
		hegelu = (TextView) findViewById(R.id.hegelu);
		pinjunsh = (TextView) findViewById(R.id.pinjunsh);// zongyelicheng
		njshu = (TextView) findViewById(R.id.njshu);// hezuosheshu

		try {
			SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
			userid = sp.getString("id", null);
			name = sp.getString("name", null);
			officeid = sp.getString("officeid", null);
			officename = sp.getString("officename", null);
			userType = getIntent().getStringExtra("userType");
		} catch (Exception e) {
			// TODO: handle exception
		}
		username1.setText(officename);
		// PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
		PushAgent.getInstance(content).onAppStart();
		initView();
		dialog = new Dialog(content, R.style.dialogss);
		dialog.show();
		new MyTead().start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
	}

	class MyTead extends Thread {
		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			super.run();
			// String url1 = officeid + "/" + worktype + "/" + userType +
			// "/workInfoList";
			String url1 = officeid + "/" + userType + "/workInfoList";
			Message msg = new Message();
			msg.arg1 = 0;
			// msg.obj = responseFromServer;
			msg.obj = HttpGetService.data(url1, "");
			mThirdHandler.sendMessage(msg);
		}
	}

	private JSONArray jas = new JSONArray();

	Handler mThirdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			map.clear();
			listwork.clear();
			if (msg.arg1 == 0) {
				if (msg.obj != null && msg.obj != "") {
					try {
						jas = new JSONArray(msg.obj.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listtype();
				}

			}
		}
	};

	// private int
	Map<String, String> map = new HashMap<String, String>();
	List<JSONObject> listwork = new ArrayList<JSONObject>();
	String types = "";
	public void listtype() {
		
		try {
			for (int i = 0; i < jas.length(); i++) {
				JSONObject jb = (JSONObject) jas.get(i);

				if (jb.getString("result").equals("true")) {
					if (types.equals("")) {
						types = jb.getString("type");
					}
					map.put(jb.getString("type"), 1 + "");
					listwork.add(jb);
				} else {
//					if (jb.getString("type").equals("2")||jb.getString("type").equals("1")) {
//						map.put(jb.getString("type"), 1 + "");
//					} else {
						map.put(jb.getString("type"), 0 + "");
//					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onlyWork(types);
		if (starts == 0) {
			pageWorknum();
			starts = 1;
		}
	}

	public void onlyWork(String type) {
		Cooperatives cp = new Cooperatives();
		for (int i = 0; i < listwork.size(); i++) {
			JSONObject jb = listwork.get(i);
			try {
				if (jb.getString("type").equals(type)) {
					cp.setWorkType(jb.getString("type"));
					cp.setTotalArea(jb.getString("totalArea"));
					cp.setTotalDistance(jb.getString("totalDistance"));
					cp.setYesterdayArea(jb.getString("yesterdayArea"));
					cp.setCount(jb.getString("count"));
					List<Coop> list1 = new ArrayList<Coop>();
					JSONArray ja = jb.getJSONArray("workHistoryList");
					for (int j = 0; j < ja.length(); j++) {/////////////////////////// 标记接口第一项为空
						JSONObject jo = (JSONObject) ja.get(j);
						Coop coop = new Coop();
						try {
							coop.setCooperativeId(jo.getString("cooperativeId"));
							coop.setCooperativeName(jo.getString("cooperativeName"));
							coop.setCount(jo.getString("count"));
							coop.setTotalArea(jo.getString("workLandarea"));
							list1.add(coop);
						} catch (Exception e) {
							// TODO: handle exception
						}

					}
					cp.setListc(list1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		events(cp);
	}

	String[] workname = { "深松作业", "水田深翻", "秸秆还田", "免耕播种" };

	public void pageWorknum() {
		int nums = 0;
		int num = 0;
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i + "").equals(1 + "")) {
				work[i].setVisibility(View.VISIBLE);
				if(num==0){
//				work[i].setBackgroundColor(0x22000000);
					wv[i].setBackgroundColor(0xffffffff);
				}
//				
				num++;
				nums = i;
				// listtype(worktype);
			}
		}
		if (num <= 1) {
			linwork.setVisibility(8);
			title.setText(workname[nums] + "监测");
		}
	}

	public void initView() {
		work[0].setOnClickListener(this);
		work[1].setOnClickListener(this);
		work[2].setOnClickListener(this);
		work[3].setOnClickListener(this);
		hzsh_lists.setOnClickListener(this);
		shuax.setOnClickListener(this);

		Updatefarmcoop.setNongactivity(NongjisuoActivity.this);

		// Object[] property_va = { userid };
		// soapService.workInfoList(property_va);
		try {
			farm_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.putExtra("cardid", list.get(position).getCooperativeId());
					in.putExtra("userType", "-1");
					in.putExtra("name", list.get(position).getCooperativeName());
					in.putExtra("worktype", worktype);
					in.setClass(content, NcooperativeActivity.class);
					// in.setClass(content, CooperativeActivity.class);
					startActivity(in);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = sDateFormat.format(new java.util.Date());
		time.setText(date);
	}

	String date = "";

	List<Coop> list = new ArrayList<Coop>();
	NongjisuoAdapter noadapter ;
	public void listViewadapter() {
		noadapter = new NongjisuoAdapter(content, list);
		farm_list.setAdapter(noadapter);
	}

	public void events(Cooperatives cp) {
		try {
			list.clear();

			String cc = cp.getTotalArea();
			int bb;
			if (cp.getTotalArea() == null) {
				allmianji.setText(0.00 + "亩");
			} else {
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				allmianji.setText(cc + "亩");
			}

			if (cp.getYesterdayArea() == null) {
				hegelu.setText(0.00 + "亩");
			} else {
				cc = cp.getYesterdayArea();
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				hegelu.setText(cc + "亩");
			}

			if (cp.getTotalDistance() == null) {
				pinjunsh.setText(0 + "公里");
			} else {
				cc = cp.getTotalDistance();
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				pinjunsh.setText(cc + "公里");
			}
			njshu.setText(cp.getCount());
			if (cp.getCount() == null) {
				njshu.setText("0");
			}
			try {
				for (int i = 0; i < cp.getListc().size(); i++) {
					Coop coop = new Coop();
					coop.setCount(cp.getListc().get(i).getCount());
					coop.setCooperativeName(cp.getListc().get(i).getCooperativeName());
					coop.setCooperativeId(cp.getListc().get(i).getCooperativeId());
					coop.setTotalArea(cp.getListc().get(i).getTotalArea());
					list.add(coop);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		dialog.dismiss();
		listViewadapter();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.work1:
//			work[0].setBackgroundColor(0x22000000);
//			work[1].setBackgroundColor(0x00000000);
//			work[2].setBackgroundColor(0x00000000);
//			work[3].setBackgroundColor(0x00000000);
			wv[0].setBackgroundColor(0xffffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = "0";
//			dialog.show();
			onlyWork(worktype);
//			new MyTead().start();
			break;
		case R.id.work2:
//			work[0].setBackgroundColor(0x00000000);
//			work[1].setBackgroundColor(0x22000000);
//			work[2].setBackgroundColor(0x00000000);
//			work[3].setBackgroundColor(0x00000000);
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0xffffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = "2";
			onlyWork(worktype);
			break;
		case R.id.work3:
//			work[0].setBackgroundColor(0x00000000);
//			work[1].setBackgroundColor(0x00000000);
//			work[2].setBackgroundColor(0x22000000);
//			work[3].setBackgroundColor(0x00000000);
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0xffffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = "3";
			onlyWork(worktype);
			break;
		case R.id.work4:
//			work[0].setBackgroundColor(0x00000000);
//			work[1].setBackgroundColor(0x00000000);
//			work[2].setBackgroundColor(0x00000000);
//			work[3].setBackgroundColor(0x22000000);
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0xffffffff);
			worktype = "1";
			onlyWork(worktype);
			break;
		case R.id.hzsh_lists:
			Intent in = new Intent();
			in.putExtra("carid", name);
			in.putExtra("name", name);
			in.putExtra("coo", name);
			in.putExtra("type", "1");
			in.setClass(content, MineSelfActivity.class);
			startActivity(in);
			break;
		case R.id.shuax:
			dialog.show();
			new MyTead().start();
			break;
		default:
			break;
		}
		// notify();
	}

}
