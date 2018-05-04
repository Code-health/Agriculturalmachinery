package io.jchat.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.adapter.TractorList_Adapter;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.customview.LoadingPage;
import io.jchat.android.customview.LoadingPage.ILoadingDo;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.entity.Carinfohistory;
import io.jchat.android.entity.TractorList;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.tools.NativeImageLoader;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;

/*
 * 合作社界面
 */
public class CooperativesActivity extends BaseActivity {
	/** Called when the activity is first created. */
	/** soapService **/
	public ISoapService soapService = new SoapService();
	Context context;
	/** loading **/
	private LoadingPage loading;

	private PullToRefreshListView listView_tractorlist;
	private ListView listView;
	private int pageIndex = 1;

	private TractorList_Adapter adapter;
	private List<Carinfohistory> list;
	private long exitTime;
	private static final String TAG = "SU-JPush";
	private Button back_btn;
	private TextView name_tv;
	private TextView totle_tv;
	private TextView percentage_tv;
	private TextView count_tv;
	private TextView qualified_tv;
	private TextView avedepth_tv;
	private TextView zhuxiao;

	String userId = "";

	String cooperativeName = "";
	String carscount = "";
	String totalArea = "";
	String percentage = "";
	String count = "";
	String qualified = "";
	String avedepth = "";

	String cardid = "";
	String carId = "";
	String name = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_cooperatives);
		//
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);

		Intent intent = getIntent();
		cardid = intent.getStringExtra("cardid");
		name=intent.getStringExtra("name");
		//cardid="辽01h1623";
		// carscount = intent.getStringExtra("carscount");
		// cooperativeName = intent.getStringExtra("cooperativeName");
		// totalArea = intent.getStringExtra("totalArea");
		// percentage = intent.getStringExtra("distanceGoodRate");
		// qualified = intent.getStringExtra("goodArea");
		// avedepth = intent.getStringExtra("depth");
		
		initView();
		initData();
		setListeners();
	}

	private void initData() {
		list = new ArrayList<Carinfohistory>();
		// adapter = new TractorList_Adapter(context, list, cardid);
		// listView.setAdapter(adapter);

		String[] property_va = new String[] { cardid, "2016-01-01", "2016-12-31" };
		soapService.carInfoHistory(property_va);

	}

	private TextView cid,ueasname;
	
	private void initView() {
		zhuxiao = (TextView) findViewById(R.id.zhuxiao);
		name_tv = (TextView) findViewById(R.id.name_tv);
		totle_tv = (TextView) findViewById(R.id.totle_tv);
		percentage_tv = (TextView) findViewById(R.id.percentage_tv);
		qualified_tv = (TextView) findViewById(R.id.qualified_tv);
		avedepth_tv = (TextView) findViewById(R.id.avedepth_tv0);
		cid=(TextView) findViewById(R.id.carID);
		ueasname=(TextView) findViewById(R.id.usr_name);
		name_tv.setText("作业统计");
		cid.setText("车牌号:"+cardid);
		ueasname.setText("车主姓名:"+name+"  ");
		
		// totle_tv.setText("总作业面积:" + totalArea + "亩");
		// percentage_tv.setText(percentage + "%");
		// count_tv.setText("农机数量:" + carscount + "台");
		// qualified_tv.setText("合格面积:" + qualified + "亩");
		// avedepth_tv.setText("平均深度:" + avedepth + "cm");

		back_btn = (Button) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView_tractorlist = (PullToRefreshListView) findViewById(R.id.listView_tractorlist);
		listView = listView_tractorlist.getRefreshableView();

		zhuxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						switch (view.getId()) {
						case R.id.jmui_cancel_btn:
							mDialog.cancel();
							break;
						case R.id.jmui_commit_btn:
							mDialog.cancel();
							Logout();
//							cancelNotification();
//							NativeImageLoader.getInstance().releaseCache();
//							finish();
							break;
						}
					}
				};

				mDialog = DialogCreator.createLogoutDialog(context, listener);
				mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
				mDialog.show();
			}
		});
	}

	private Dialog mDialog;

	public void cancelNotification() {
		NotificationManager manager = (NotificationManager) this.getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancelAll();
	}

	public void Logout() {// 退出登录
		// TODO Auto-generated method stub
		
		final Intent intent = new Intent();
		SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		intent.setClass(context, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void setListeners() {
		listView_tractorlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				String carIds = dd.get(position-1).getCarId();
				String date=dd.get(position-1).getWorkDate().substring(0,10);
				
				if (carId != null) {
					Intent intent = new Intent();
					intent.putExtra("carId", carIds);
					intent.putExtra("date", date);
					intent.setClass(context, TractorInfoActivity.class);
					context.startActivity(intent);
				} else {
					Toast.makeText(CooperativesActivity.this, "无数据", Toast.LENGTH_SHORT).show();
				}
			}
		});
		listView_tractorlist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				String[] property_va = new String[] { cardid, "2016-01-01", "2016-12-31" };
				soapService.carInfoHistory(property_va);

			}
		});

		// end of list
//		listView_tractorlist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//
//				String[] property_va = new String[] { cardid, "2016-01-01", "2016-12-31" };
//				soapService.carInfoHistory(property_va);
//
//			}
//		});
	}

	List<Carinfohistortylist> dd = new ArrayList<Carinfohistortylist>();

	public void onEvent(SoapRes obj) {

		if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFOHISTORY)) {
			listView_tractorlist.onRefreshComplete();
			list = (List<Carinfohistory>) obj.getObj();
			dd = list.get(0).getHistoryList();
			name=dd.get(0).getOwner();
			ueasname.setText("车主姓名:"+name+"  ");
			String miji="";
			int aa=list.get(0).getTotalArea().indexOf(".");
			if(aa+3>=list.get(0).getTotalArea().length()){
				miji=list.get(0).getTotalArea();
			}else {
				miji=list.get(0).getTotalArea().substring(0,aa+3);
			}
			totle_tv.setText("总作业面积:" + miji+ "亩");
			percentage_tv.setText("合格率:"+list.get(0).getAvgPassRate() + "%");
			
//			int bb=list.get(0).getGoodArea().indexOf(".");
			if(dd.size()>0){
				miji=dd.get(0).getCooperativeName();
			}else {
				miji="加载中";
			}
			avedepth_tv.setText("所属合作社: " + miji );//合格面积换成所属合作社
			String ceshi="你好啊";
			String cc="";
//			cc=ceshi.substring(0,ceshi.indexOf("."));
			if(list.get(0).getAvgDepth().indexOf(".")>=0){
			 cc=list.get(0).getAvgDepth().substring(0,list.get(0).getAvgDepth().indexOf("."));
			}else {
				cc=list.get(0).getAvgDepth();
			}
			qualified_tv.setText("总平均深度:" + cc + " cm");

			adapter = new TractorList_Adapter(context, dd, cardid);
			listView.setAdapter(adapter);
		}
	}

	/**
	 * 添加loading
	 */
	public void addLoading() {
		loading = new LoadingPage(this, new ILoadingDo() {

			@Override
			public void soapFail(String methodName) {
				EventCache.errorHttp.post(methodName);
			}
		});

		addContentView(loading, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 移除 loading
	 */
	public void removeLoading() {
		if (loading != null) {
			ViewGroup parent = (ViewGroup) loading.ll_bg.getParent();
			parent.removeView(loading.ll_bg);
			loading = null;
		}
	}
	
	 @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	        PgyUpdateManager.register(this);//更新
	        // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
	        PgyFeedbackShakeManager.setShakingThreshold(700);

	        // 以对话框的形式弹出
	        PgyFeedbackShakeManager.register(CooperativesActivity.this);

	        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
	        // 打开沉浸式,默认为false
	        // FeedbackActivity.setBarImmersive(true);
//	        PgyFeedbackShakeManager.register(CooperativesActivity.this, false);

	    }

	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	        PgyFeedbackShakeManager.unregister();
	    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

}
