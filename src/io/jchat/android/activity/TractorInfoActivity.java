package io.jchat.android.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.adapter.TractorSelectInfo_Adapter;
import io.jchat.android.adapter.TractorTodayInfo_Adapter;
import io.jchat.android.customview.LoadingPage;
import io.jchat.android.customview.LoadingPage.ILoadingDo;
import io.jchat.android.entity.TractorDetails;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.entity.TractorTodayDetail;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;
import io.jchat.android.utils.datetimepicker.DateTimePickDialogUtil;

/*
 * 合作社界面
 */
public class TractorInfoActivity extends BaseActivity {
	/** Called when the activity is first created. */
	/** soapService **/
	public ISoapService soapService = new SoapService();
	Context context;
	/** loading **/
	private LoadingPage loading;

	private PullToRefreshListView listView_tractorinfolist;
	private ListView listView;

	private TractorTodayInfo_Adapter adapter;
	private TractorSelectInfo_Adapter selectadapter;
	private List<TractorTodayDetail> list;
	private List<TractorDetails> selectlist;
	private Button back_btn, pageup, pagedown;
	private EditText inputDate_et;
	private TextView date_tv;
	private TextView name_tv, yema;

	String carId = "";
	String workDate = "";

	private EditText startDateTime;
	private String initStartDateTime = "2016年8月22日"; // 初始化开始时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_tractordetails);
		//
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);

		
		
		initView();
		initData();
		setListeners();
	}
	
	 @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();

	        // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
	        PgyFeedbackShakeManager.setShakingThreshold(700);

	        // 以对话框的形式弹出
	        PgyFeedbackShakeManager.register(TractorInfoActivity.this);

	        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
	        // 打开沉浸式,默认为false
	        // FeedbackActivity.setBarImmersive(true);
//	        PgyFeedbackShakeManager.register(TractorInfoActivity.this, false);

	    }

	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	        PgyFeedbackShakeManager.unregister();
	    }

	private void initData() {
//
//		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
//		workDate = sdfs.format(new java.util.Date());
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//		initStartDateTime = sdf.format(new java.util.Date());
//		startDateTime.setText(initStartDateTime);
		Intent intent = getIntent();
		carId = intent.getStringExtra("carId");
		workDate = intent.getStringExtra("date");
		String ri="";
		if(0==Integer.parseInt(workDate.substring(8,9))){
			ri=workDate.substring(9,10);
		}else {
			ri=workDate.substring(8,10);
		}
		name_tv.setText("作业时间："+workDate.substring(5,7)+"月"+ri+"号");
		String[] property_va = new String[] { workDate, carId };
		soapService.carInfoDetails(property_va);

		list = new ArrayList<TractorTodayDetail>();
		adapter = new TractorTodayInfo_Adapter(context, list, carId);
		listView.setAdapter(adapter);
	}

	private void initView() {
		yema = (TextView) findViewById(R.id.txt_ye);
		pageup = (Button) findViewById(R.id.date_tv2);
		pagedown = (Button) findViewById(R.id.date_tv1);

		pageup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (page - 1 < 0) {
					Toast.makeText(context, "已到顶叶", Toast.LENGTH_LONG).show();
				} else {
					page = page - 10;
					chooseadapter();
				}

			}
		});

		pagedown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (intface.equals(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS)) {
					if (page + 10 > list.size()) {
						Toast.makeText(context, "已到末页", Toast.LENGTH_LONG).show();
					} else {
						page = page + 10;
						chooseadapter();
					}
				} else if (intface.equals(SOAP_UTILS.METHOD.CARINFODETAILS)) {
					if (dd.size() != 0) {
						if (page + 10 > dd.size()) {
							Toast.makeText(context, "已到末页", Toast.LENGTH_LONG).show();
						} else {
							page = page + 10;
							chooseadapter();
						}
					} else {
						Toast.makeText(context, "已到末页", Toast.LENGTH_LONG).show();
					}
				}

			}
		});

		date_tv = (TextView) findViewById(R.id.date_tv);
		date_tv.setClickable(true);
		date_tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(TractorInfoActivity.this,
						initStartDateTime);
				dateTimePicKDialog.dateTimePicKDialog(startDateTime);

			}
		});
		startDateTime = (EditText) findViewById(R.id.inputDate_et);

		startDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(TractorInfoActivity.this,
						initStartDateTime);
				dateTimePicKDialog.dateTimePicKDialog(startDateTime);

			}
		});

		name_tv = (TextView) findViewById(R.id.name_tv);
		back_btn = (Button) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		listView_tractorinfolist = (PullToRefreshListView) findViewById(R.id.listView_tractorinfolist);
		listView = listView_tractorinfolist.getRefreshableView();

	}

	private void setListeners() {

		listView_tractorinfolist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

//				workDate = startDateTime.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
//
//				String datestr = initStartDateTime.replace("年", "-").replace("月", "-").replace("日", "");
//				if (workDate.equals("") || workDate == null || workDate.equals(datestr)) {
//
//					// String[] property_va = new String[] { carId };
//					// soapService.carInfoTodayDetails(property_va);
//				} else {

					String[] property_va = new String[] { workDate, carId };
					soapService.carInfoDetails(property_va);
//				}

			}
		});

	}

	List<TractorDetailslist> dd = new ArrayList<TractorDetailslist>();

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS)) {
			listView_tractorinfolist.onRefreshComplete();
			intface = SOAP_UTILS.METHOD.CARINFOTODAYDETAILS;
			list = (List<TractorTodayDetail>) obj.getObj();
			// String bb="";
			// if (list==null) {
			// bb="qqqqqqqqq";
			// }else {
			// bb="wwwwwww";
			// }
			chooseadapter();
			// adapter = new TractorTodayInfo_Adapter(context, list, carId);
			// listView.setAdapter(adapter);

		} else if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFODETAILS)) {
			listView_tractorinfolist.onRefreshComplete();
			intface = SOAP_UTILS.METHOD.CARINFODETAILS;
			selectlist = (List<TractorDetails>) obj.getObj();
			dd = selectlist.get(0).getDetailsList();
			selectadapter = new TractorSelectInfo_Adapter(this,context, dd, carId, workDate);
			listView.setAdapter(selectadapter);
			
			//chooseadapter();//分页方法
			
			// selectadapter = new TractorSelectInfo_Adapter(context,
			// selectlist, carId, workDate);
			// listView.setAdapter(selectadapter);

		} else if (obj.getCode().equals("DateChange")) {
//			String newDate = obj.getObj().toString();
//			workDate = newDate.replace("年", "-").replace("月", "-").replace("日", "");
//			String tempstr = initStartDateTime.replace("年", "-").replace("月", "-").replace("日", "");
//			if (workDate.equals("") || workDate == null || workDate.equals(tempstr)) {
//
//				// String[] property_va = new String[] { carId };
//				// soapService.carInfoTodayDetails(property_va);
//			} else {

				String[] property_va = new String[] { workDate, carId };
				soapService.carInfoDetails(property_va);
//			}
		}

	}

	/**
	 * adapterfamgfa
	 */
	private int page = 0;
	private String intface;
	int zpage;

	public void chooseadapter() {
		if (intface.equals(SOAP_UTILS.METHOD.CARINFOTODAYDETAILS)) {
			List<TractorTodayDetail> list1 = new ArrayList<TractorTodayDetail>();
			for (int i = page; i < page + 10; i++) {
				if (i < list.size()) {
					list1.add(list.get(i));
				}
			}
			zpage = list.size() / 10;
			if (list.size() % 10 != 0) {
				zpage += 1;
			}
			yema.setText(page / 10 + 1 + "/" + zpage);
			adapter = new TractorTodayInfo_Adapter(context, list1, carId);
			listView.setAdapter(adapter);
		} else if (intface.equals(SOAP_UTILS.METHOD.CARINFODETAILS)) {
			List<TractorDetailslist> list1 = new ArrayList<TractorDetailslist>();
			for (int i = page; i < page + 10; i++) {
				if (i < dd.size()) {
					list1.add(dd.get(i));
				}
			}
			zpage = dd.size() / 10;
			if (dd.size() % 10 != 0) {
				zpage += 1;
			}
			yema.setText(page / 10 + 1 + "/" + zpage);
			selectadapter = new TractorSelectInfo_Adapter(this,context, list1, carId, workDate);
			listView.setAdapter(selectadapter);
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

}
