package io.jchat.android.newactivity;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.update.PgyUpdateManager;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.adapter.CoopFarmListAdapter;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.entity.Carinfohistory;
import io.jchat.android.entity.Coop;
import io.jchat.android.entity.CoopFarmList;
import io.jchat.android.entity.Cooperatives;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.HttpGetService;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;
import io.jchat.android.view.MyLinearLayout;

@SuppressLint("NewApi")
public class CooperativeActivity extends Activity implements OnClickListener {

	private LinearLayout lists, linwork;
	private RelativeLayout work1, work2, work3, work4,work5,work6,work7,work8,work9;
	private RelativeLayout[] work = new RelativeLayout[4];
	private RelativeLayout[] worktwo = new RelativeLayout[5];
	private View wv1, wv2, wv3, wv4;
	private View[] wv = new View[4];
	private ImageView menuItem, bank;
	private MyLinearLayout liner;
	private Button notice;
	private Context content;
	private TextView getinfor, coopname, allmianji, hegel, hegem, pjshen, usernames;
	private TextView shensun, njshu, types, zuoysh, time, hgl, title;
	private ListView farm_list;
	String cardid = "";
	String carId = "";
	String officeid = "";
	String officename = "";
	String username = "";
	String usertype = "";
	static String name = "";
	private CoopFarmListAdapter coopfladapter;
	private CoopFarmList coopfl;
	private List<CoopFarmList> list;
	private List<Carinfohistory> listhttp;
	public ISoapService soapService = new SoapService();
	private PushAgent mPushAgent;
	Dialog dialog;
	private HorizontalScrollView horsview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cooperative);
		content = this;

		try {
			cardid = getIntent().getStringExtra("cardid");
			usertype = getIntent().getStringExtra("userType");
			username = getIntent().getStringExtra("name");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (usertype.equals("3")) {
			SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
			officeid = sp.getString("officeid", "");
			officename = sp.getString("officename", "");
		} else if (usertype.equals("-1")) {
			officeid = cardid;
			officename = username;
		} else if (usertype.equals("4")) {
			SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
			officename = sp.getString("officename", "");
			officeid = cardid;
		}
		horsview=(HorizontalScrollView) findViewById(R.id.horsview);
		work[0] = (RelativeLayout) findViewById(R.id.work1);
		work[2] = (RelativeLayout) findViewById(R.id.work2);
		work[3] = (RelativeLayout) findViewById(R.id.work3);
		work[1] = (RelativeLayout) findViewById(R.id.work4);
		worktwo[0] = (RelativeLayout) findViewById(R.id.work5);
		worktwo[1] = (RelativeLayout) findViewById(R.id.work6);
		worktwo[2] = (RelativeLayout) findViewById(R.id.work7);
		worktwo[3] = (RelativeLayout) findViewById(R.id.work8);
		worktwo[4] = (RelativeLayout) findViewById(R.id.work9);
		wv[0] = findViewById(R.id.workv1);
		wv[2] = findViewById(R.id.workv2);
		wv[3] = findViewById(R.id.workv3);
		wv[1] = findViewById(R.id.workv4);
		bank = (ImageView) findViewById(R.id.imag_bank);
		zuoysh = (TextView) findViewById(R.id.zuoysh);
		usernames = (TextView) findViewById(R.id.username1);
		menuItem = (ImageView) findViewById(R.id.hzsh_lists);
		coopname = (TextView) findViewById(R.id.coopname);
		allmianji = (TextView) findViewById(R.id.allmianji);
		hegel = (TextView) findViewById(R.id.hegelu);
		hgl = (TextView) findViewById(R.id.hgl);
		njshu = (TextView) findViewById(R.id.njshu);
		// types = (TextView) findViewById(R.id.type);
		pjshen = (TextView) findViewById(R.id.pinjunsh);
		farm_list = (ListView) findViewById(R.id.list_farm);
		time = (TextView) findViewById(R.id.time);
		title = (TextView) findViewById(R.id.title);
		linwork = (LinearLayout) findViewById(R.id.linwork);

		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		coopname.setText(officename);
		usernames.setText(username);
		zuoysh.setText("作业里程");
		hgl.setText("昨日作业面积");
		bank.setVisibility(View.VISIBLE);
		if (usertype.equals("-1")) {// 合作社
			usernames.setText(officename);
			coopname.setVisibility(View.GONE);
			menuItem.setVisibility(View.GONE);
			bank.setVisibility(View.VISIBLE);
		}
		if (usertype.equals("3")) {
			usernames.setText(officename);
			coopname.setVisibility(View.GONE);
			menuItem.setVisibility(View.VISIBLE);
		}
		// PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
//		mPushAgent = PushAgent.getInstance(content);
//		mPushAgent.onAppStart();
//		addTag();// 添加tag
//		addAlias();
		// AddExclusiveAlias();//只有最后登录的手机能获得
		initView();
        
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
		WindowManager wm1 = this.getWindowManager();  //获取屏幕款高
		int width1 = wm1.getDefaultDisplay().getWidth();  
		int height1 = wm1.getDefaultDisplay().getHeight(); 
		android.view.ViewGroup.LayoutParams para;
		for(int i=0;i<5;i++){
			para = worktwo[i].getLayoutParams();//获取按钮的布局
			para.width=width1/4;//修改宽度
			para.height=40;//修改高度
			worktwo[i].setLayoutParams(para); //设置修改后的布局。
		}
	}

	int year = 2016;
	int typeintfice = 0;

	public void initView() {
		Updatefarmcoop.setCoopactivity(CooperativeActivity.this);
		dialog = new Dialog(content, R.style.dialogss);
		
		new MyTead().start();
		// }
		menuItem.setOnClickListener(this);
		work[0].setOnClickListener(this);
		work[1].setOnClickListener(this);
		work[2].setOnClickListener(this);
		work[3].setOnClickListener(this);
		bank.setOnClickListener(this);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = sDateFormat.format(new java.util.Date());
		time.setText(date);
	}

	String date = "";
	String[] workname = { "深松作业", "水田深翻", "秸秆还田", "免耕播种" };

	public static void setMenuEvent(LinearLayout layout) {
		if (layout.getVisibility() == android.view.View.GONE) {
			layout.setVisibility(android.view.View.VISIBLE);
			Log.d("menu", "!!!!!!I am here   Nothidden");
		} else {
			layout.setVisibility(android.view.View.GONE);
			Log.d("menu", "!!!!!!I am here   hidden");
		}

	}

	List<Carinfohistortylist> dd = new ArrayList<Carinfohistortylist>();
	String miji = "";// zuoyemianji
	static String coo = "";

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFOHISTORY) && typeintfice == 1) {
			typeintfice = 0;
			try {
				listhttp = (List<Carinfohistory>) obj.getObj();
				dd = listhttp.get(0).getHistoryList();
				name = dd.get(0).getOwner();

				if (dd.size() > 0) {
					coo = dd.get(0).getCooperativeName();
				} else {
					coo = "加载中";
				}
				if (coo.length() > 9) {
					coo = coo.substring(0, coo.length() - 9) + "\n" + coo.substring(coo.length() - 9);
				}
				coopname.setText(coo);
				usernames.setText(name);
				hegel.setText(listhttp.get(0).getAvgPassRate() + "%");
				String cc = "";
				if (listhttp.get(0).getAvgDepth().indexOf(".") >= 0) {
					cc = listhttp.get(0).getAvgDepth().substring(0, listhttp.get(0).getAvgDepth().indexOf("."));
				} else {
					cc = listhttp.get(0).getAvgDepth();
				}
				pjshen.setText(cc + " 厘米");
				// int bb = listhttp.get(0).getGoodArea().indexOf(".");
				// if (bb + 3 >= listhttp.get(0).getGoodArea().length()) {
				// miji = listhttp.get(0).getGoodArea();
				// } else {
				// miji = listhttp.get(0).getGoodArea().substring(0, bb + 3);
				// }
				// hegem.setText(miji + "亩\n" + "合格面积");
				njshu.setText("1" + "台");
				int aa = listhttp.get(0).getTotalArea().indexOf(".");
				if (aa + 3 >= listhttp.get(0).getTotalArea().length()) {
					miji = listhttp.get(0).getTotalArea();
				} else {
					miji = listhttp.get(0).getTotalArea().substring(0, aa + 3);
				}
				allmianji.setText(miji + "亩");

				list = new ArrayList<CoopFarmList>();
				for (int i = 0; i < 1; i++) {
					coopfl = new CoopFarmList();
					coopfl.setType(1);
					coopfl.setNames(name);
					// coopfl.setNames("深松");
					coopfl.setNums(cardid);
					coopfl.setMarks(miji + "亩");
					list.add(coopfl);
				}
				listViewFarm(list);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	int type = 1;
	int worktype = 0;
	String typ = "1";

	public void listViewFarm(final List<CoopFarmList> list) {

		coopfladapter = new CoopFarmListAdapter(content, list, typ);
		farm_list.setAdapter(coopfladapter);

		farm_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("position", position);
				in.putExtra("type", worktype + "");
				in.putExtra("area", list.get(position).getMarks());
				in.putExtra("date", date);
				// in.putParcelableArrayListExtra("list", (ArrayList<? extends
				// Parcelable>) listhttp);

				in.putExtra("carid", list.get(position).getNums());

				in.setClass(content, OperatorFarmActivity.class);
				startActivity(in);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imag_bank:
//			finish();
			dialog.show();
			new MyTead().start();
			break;
		case R.id.work1:
			wv[0].setBackgroundColor(0xffffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = 0;
//			new MyTead().start();
			onlyWork(worktype + "");
			break;
		case R.id.work2:
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0xffffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = 2;
			onlyWork(worktype + "");
			break;
		case R.id.work3:
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0xffffffff);
			wv[1].setBackgroundColor(0x00ffffff);
			worktype = 3;
			onlyWork(worktype + "");
			break;
		case R.id.work4:
			wv[0].setBackgroundColor(0x00ffffff);
			wv[2].setBackgroundColor(0x00ffffff);
			wv[3].setBackgroundColor(0x00ffffff);
			wv[1].setBackgroundColor(0xffffffff);
			worktype = 1;
			onlyWork(worktype + "");
			break;
		case R.id.hzsh_lists:
			Intent in = new Intent();
			in.putExtra("carid", cardid);
			if (usertype.equals("4")) {
				in.putExtra("name", username);
				in.putExtra("coo", officename);
				in.putExtra("type", "4");
			} else {
				in.putExtra("name", "合作社");
				in.putExtra("coo", officename);
				in.putExtra("type", "3");
			}
			in.setClass(content, MineSelfActivity.class);
			startActivity(in);
			break;
		default:
			break;
		}
		// notify();
	}

	class MyTead extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			// String url1 = officeid + "/" + worktype + "/" + usertype +
			// "/workInfoList";
			// if (usertype.equals("-1")) {
			// url1 = officeid + "/" + worktype + "/" + "3" + "/workInfoList";
			// }

			String url1 = officeid + "/" + usertype + "/workInfoList";
			if (usertype.equals("-1")) {
				url1 = officeid + "/" + "3" + "/workInfoList";
			}

			Message msg = new Message();
			msg.arg1 = 0;
			// msg.obj = responseFromServer;
			msg.obj = HttpGetService.data(url1, "");
			mThirdHandler.sendMessage(msg);
		}
	}

	Handler mThirdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == 0) {
				if (msg.obj != null) {
					try {
						jas = new JSONArray(msg.obj.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listitme();
				}else {
					dialog.dismiss();
					Toast.makeText(content, "网络请求失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	Map<String, String> map = new HashMap<String, String>();
	List<JSONObject> listwork = new ArrayList<JSONObject>();
	String type1 = "";
	int starts = 0;
	JSONArray jas = new JSONArray();

	public void listitme() {
		dialog.dismiss();
		try {
			for (int i = 0; i < jas.length(); i++) {
				JSONObject jb = (JSONObject) jas.get(i);

				if (jb.getString("result").equals("true")) {
					if (type1.equals("")) {
						type1 = jb.getString("type");
					}
					map.put(jb.getString("type"), 1 + "");
					listwork.add(jb);
				} else {
//					 if
//					 (jb.getString("type").equals("2"))
//					 {
//					 map.put(jb.getString("type"), 1 + "");
//					 } else {
					map.put(jb.getString("type"), 0 + "");
//					 }
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onlyWork(type1);
		if (starts == 0) {
			pageWorknum();
			starts = 1;
		}
	}

	public void onlyWork(String type1) {
		Cooperatives cp = new Cooperatives();
		try {
			for (int i = 0; i < listwork.size(); i++) {
				JSONObject jb = listwork.get(i);
				if (jb.getString("type").equals(type1)) {
					try {
						cp.setTotalArea(jb.getString("totalArea"));
						cp.setTotalDistance(jb.getString("totalDistance"));
						cp.setYesterdayArea(jb.getString("yesterdayArea"));
						cp.setCount(jb.getString("count"));
					} catch (Exception e) {
						// TODO: handle exception
					}

					List<Coop> list1 = new ArrayList<Coop>();
					JSONArray ja = jb.getJSONArray("detailsList");
					for (int j = 0; j < ja.length(); j++) {
						JSONObject jo = (JSONObject) ja.get(j);
						Coop coop = new Coop();
						coop.setCarid(jo.getString("carId"));
						coop.setCarname(jo.getString("owner"));
						// coop.setCount(jo.getString("count"));
						coop.setTotalArea(jo.getString("todayArea"));
						list1.add(coop);
					}
					cp.setListc(list1);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		events(cp);
	}

	public void events(Cooperatives cp) {
		try {
			// allmianji.setText(cp.getTotalArea() + "亩");
			// hegel.setText(cp.getYesterdayArea() + "亩");
			// njshu.setText(cp.getCount() + "台");
			// pjshen.setText(cp.getTotalDistance() + "公里");

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
				hegel.setText(0.00 + "亩");
			} else {
				cc = cp.getYesterdayArea();
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				hegel.setText(cc + "亩");
			}

			if (cp.getTotalDistance() == null) {
				pjshen.setText(0 + "公里");
			} else {
				cc = cp.getTotalDistance();
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				pjshen.setText(cc + "公里");
			}
			njshu.setText(cp.getCount());
			if (cp.getCount() == null) {
				njshu.setText("0");
			}
			String miji1 = "";
			list = new ArrayList<CoopFarmList>();
			try {
				for (int i = 0; i < cp.getListc().size(); i++) {
					coopfl = new CoopFarmList();
					coopfl.setType(1);
					coopfl.setNames(cp.getListc().get(i).getCarname());
					// coopfl.setNames("深松");
					coopfl.setNums(cp.getListc().get(i).getCarid());

					int aa = cp.getListc().get(i).getTotalArea().indexOf(".");
					if (aa + 3 >= cp.getListc().get(i).getTotalArea().length()) {
						miji1 = cp.getListc().get(i).getTotalArea();
					} else {
						miji1 = cp.getListc().get(i).getTotalArea().substring(0, aa + 3);
					}

					coopfl.setMarks(miji1 + " 亩");
					list.add(coopfl);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		listViewFarm(list);
	}

	public void pageWorknum() {
		int nums = 0;
		int num = 0;
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i + "").equals(1 + "")) {
				work[i].setVisibility(View.VISIBLE);
				if (num == 0) {
					// work[i].setBackgroundColor(0x22000000);
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

	public Handler handler = new Handler();

	private void addTag() {
		String tag = cardid;

		mPushAgent.getTagManager().add(new TagManager.TCallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						// edalias.setText("");
						if (isSuccess) {
							// Toast.makeText(context, result + "",
							// Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(content, "加入tag失败", Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		}, tag);
	}

	private void AddExclusiveAlias() {
		String exclusiveAlias = cardid;
		String aliasType = cardid;
		mPushAgent.addExclusiveAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(boolean isSuccess, String message) {
				// Log.i(TAG, "isSuccess:" + isSuccess + "," + message);
				if (Boolean.TRUE.equals(isSuccess)) {
					// Log.i(TAG, "exclusive alias was set successfully.");

					final boolean success = isSuccess;
					handler.post(new Runnable() {
						@Override
						public void run() {
							// edalias.setText("");
							// updatelog("Add Exclusive Alias:" + (success ?
							// "Success" : "Fail"));
						}
					});
				}
			}
		});
	}

	private void addAlias() {
		String alias = cardid;
		String aliasType = cardid;
		mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(boolean isSuccess, String message) {
				// Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				if (isSuccess) {
					// Toast.makeText(context, "alias was set successfully.",
					// Toast.LENGTH_LONG).show();
					final boolean success = isSuccess;
					handler.post(new Runnable() {
						@Override
						public void run() {
							// Toast.makeText(context, "Add Alias:" + (success ?
							// "Success" : "Fail"), Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		});
	}

}
