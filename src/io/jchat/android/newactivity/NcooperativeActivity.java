
package io.jchat.android.newactivity;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;
import io.jchat.android.view.MyLinearLayout;

@SuppressLint("NewApi")
public class NcooperativeActivity extends Activity implements OnClickListener {

	private LinearLayout lists;
	private RelativeLayout work1, work2, work3, work4;
	private View wv1, wv2, wv3, wv4;
	private ImageView menuItem, bank;
	private MyLinearLayout liner;
	private Button notice;
	private Context content;
	private TextView getinfor, coopname, allmianji, hegel, hegem, pjshen, usernames;
	private TextView shensun, njshu, types, zuoysh, time, hgl, title;
	private PullToRefreshListView farm_list;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ncooperative);
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
		work1 = (RelativeLayout) findViewById(R.id.work1);
		work2 = (RelativeLayout) findViewById(R.id.work2);
		work3 = (RelativeLayout) findViewById(R.id.work3);
		work4 = (RelativeLayout) findViewById(R.id.work4);
		wv1 = findViewById(R.id.workv1);
		wv2 = findViewById(R.id.workv2);
		wv3 = findViewById(R.id.workv3);
		wv4 = findViewById(R.id.workv4);
		title = (TextView) findViewById(R.id.title);
		title.setText(username);
		bank = (ImageView) findViewById(R.id.imag_bank);
		zuoysh = (TextView) findViewById(R.id.zuoysh);
		usernames = (TextView) findViewById(R.id.username1);
		menuItem = (ImageView) findViewById(R.id.hzsh_lists);
		menuItem.setVisibility(8);
		coopname = (TextView) findViewById(R.id.coopname);
		allmianji = (TextView) findViewById(R.id.allmianji);
		hegel = (TextView) findViewById(R.id.hegelu);
		hgl = (TextView) findViewById(R.id.hgl);
		njshu = (TextView) findViewById(R.id.njshu);
		// types = (TextView) findViewById(R.id.type);
		pjshen = (TextView) findViewById(R.id.pinjunsh);
		farm_list = (PullToRefreshListView) findViewById(R.id.list_farm);
		time = (TextView) findViewById(R.id.time);

		// PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
		mPushAgent = PushAgent.getInstance(content);
		mPushAgent.onAppStart();
		addTag();// 添加tag
		addAlias();
		// AddExclusiveAlias();//只有最后登录的手机能获得
		initView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
	}

	int year = 2016;
	int typeintfice = 0;

	public void initView() {
		new MyTead().start();
		// }
		farm_list.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
//				list.clear();
				new MyTead().start();
			}
		});
		menuItem.setOnClickListener(this);
		bank.setOnClickListener(this);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = sDateFormat.format(new java.util.Date());
	}

	String date = "";

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


	int type = 1;
	String typ="0";
	int worktype = 0;

	public void listViewFarm(final List<CoopFarmList> list) {

		coopfladapter = new CoopFarmListAdapter(content, list,typ);
		farm_list.setAdapter(coopfladapter);

		farm_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.putExtra("position", position-1);
				in.putExtra("type", worktype + "");
				in.putExtra("area", list.get(position-1).getTodayArea());
				in.putExtra("date", date);
				// in.putParcelableArrayListExtra("list", (ArrayList<? extends
				// Parcelable>) listhttp);

				in.putExtra("carid", list.get(position-1).getNums());

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
			finish();
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
			String url1 = officeid + "/" + worktype + "/detailsList";
			if (usertype.equals("-1")) {
				url1 = officeid + "/" + worktype + "/detailsList";
			}

			// String url1 = officeid + "/" + usertype + "/workInfoList";
			// if (usertype.equals("-1")) {
			// url1 = officeid + "/" + "3" + "/workInfoList";
			// }

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
				}

			}
		}
	};
	JSONArray jas = new JSONArray();

	public void listitme() {
		Cooperatives cp = new Cooperatives();
		try {
			List<Coop> list1 = new ArrayList<Coop>();
			for (int i = 0; i < jas.length(); i++) {

				JSONObject jo = (JSONObject) jas.get(i);
				Coop coop = new Coop();
				coop.setCarid(jo.getString("carId"));
				coop.setCarname(jo.getString("owner"));
				// coop.setCount(jo.getString("count"));
				coop.setTotalArea(jo.getString("workLandarea"));
				coop.setTadyArea(jo.getString("todayArea"));
				list1.add(coop);
				cp.setListc(list1);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		events(cp);
		farm_list.postDelayed(new Runnable() {
			@Override
			public void run() {
				farm_list.onRefreshComplete();
			}
		}, 1000);
	}

	public void events(Cooperatives cp) {
		try {
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
					miji1 = cp.getListc().get(i).getTadyArea();
					aa = cp.getListc().get(i).getTadyArea().indexOf(".");
					if (aa + 3 >= cp.getListc().get(i).getTadyArea().length()) {
						
					} else {
						miji1 = cp.getListc().get(i).getTadyArea().substring(0, aa + 3);
					}
					coopfl.setTodayArea(miji1+" 亩");
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
