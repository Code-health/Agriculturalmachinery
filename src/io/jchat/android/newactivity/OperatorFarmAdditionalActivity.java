package io.jchat.android.newactivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.adapter.OperFarmAdapter;
import io.jchat.android.dialog.CustomDialog;
import io.jchat.android.dialog.CustomDialog2;
import io.jchat.android.dialog.DateTimePickerDialog;
import io.jchat.android.dialog.DateTimePickerDialog.OnDateTimeSetListener;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.entity.Carinfohistory;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.http.AsyncTaskBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;
import io.jchat.android.view.PercentageRing;

public class OperatorFarmAdditionalActivity extends Activity {

	private RelativeLayout rel_yuan1;
	private ImageView time, iamg_namk, yearl, yearr;
	private PullToRefreshListView daywork;
	public ISoapService soapService = new SoapService();
	private Context content;
	private String cardid;
	private TextView names, address, mianj, hgl, hgmj, deth, years,dep,dep1;
	private String type = "0";
	private String area = "0";
	private String date = "0";
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		content = this;
		cardid = getIntent().getStringExtra("carid");
		type = getIntent().getStringExtra("type");
		area = getIntent().getStringExtra("area");
		date = getIntent().getStringExtra("date") + "0";
		name=getIntent().getStringExtra("name");

		setContentView(R.layout.activity_opertorfarm_additional);
		iamg_namk = (ImageView) findViewById(R.id.imag_bank);
		time = (ImageView) findViewById(R.id.mine);
		daywork = (PullToRefreshListView) findViewById(R.id.daywork);
		names = (TextView) findViewById(R.id.names);
		address = (TextView) findViewById(R.id.address);
		mianj = (TextView) findViewById(R.id.mianj);
		hgl = (TextView) findViewById(R.id.hgl);
		hgmj = (TextView) findViewById(R.id.hegmj);
		deth = (TextView) findViewById(R.id.deth);
		yearl = (ImageView) findViewById(R.id.yearleft);
		yearr = (ImageView) findViewById(R.id.yearreght);
		years = (TextView) findViewById(R.id.year);
		dep=(TextView) findViewById(R.id.dep);
		dep1=(TextView) findViewById(R.id.dep1);
		
//		if(type.equals("3")){
//			dep.setText("作业里程");
//			dep1.setText("覆盖率");
//		}
		names.setText(name);
//		PushAgent.getInstance(OperatorFarmActivity.this).onAppStart();

		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);

		initView();

	}

	int year = 2017;
	int aa = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private void initView() {
		dd.clear();
//		years.setText(year + "");
		String[] property_va = new String[] { cardid, (year) + "-01-01", (year) + "-12-31", type };
		soapService.carInfoHistory(property_va);

		daywork.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				abc = 0;
				dd.clear();
				String[] property_va = new String[] { cardid, (year) + "-01-01", (year) + "-12-31", type };
				soapService.carInfoHistory(property_va);
			}
		});
		iamg_namk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
			}
		});

		daywork.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				String carIds = dd.get(position - 1).getCarId();
				String date = dd.get(position - 1).getWorkDate().substring(0, 10);
				String area = dd.get(position - 1).getWorkLandarea();
				if (carIds != null||area.equals("0")) {
					Intent in = new Intent();
					in.putExtra("carid", carIds);
					in.putExtra("workdate", date);
					in.putExtra("type", type);
					 in.setClass(OperatorFarmAdditionalActivity.this,
					 DayWorkActivity.class);
//					in.setClass(OperatorFarmActivity.this, DayWorkMaps.class);
					startActivity(in);
				} else {
					Toast.makeText(content, "无数据", Toast.LENGTH_SHORT).show();
				}

			}
		});
		//
		// yearl.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (year > 2016) {
		// dd.clear();
		// todays = 0;
		// abc = 0;
		// year = year - 1;
		// years.setText(year + "");
		// String[] property_va = new String[] { cardid, (year) + "-01-01",
		// (year) + "-12-31", type };
		// soapService.carInfoHistory(property_va);
		// }
		// }
		// });

		// yearr.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (true) {
		// dd.clear();
		// todays = 0;
		// abc = 0;
		// year = year + 1;
		// years.setText(year + "");
		// String[] property_va = new String[] { cardid, (year) + "-01-01",
		// (year) + "-12-31", type };
		// soapService.carInfoHistory(property_va);
		// }
		// }
		// });
	}

	public void init() {

		PercentageRing mPercentageRing2 = (PercentageRing) findViewById(R.id.Circle1);

		mPercentageRing2.setMax(100);
		mPercentageRing2.setCricleProgressColor(Color.BLUE);// 弧线颜色
		mPercentageRing2.setmRate("" + a1);// 比例值
		mPercentageRing2.setmText1("完成面积");//
		mPercentageRing2.setmText2("");
		mPercentageRing2.setProgress(a1);// 弧线焦度计算值

		PercentageRing mPercentageRing1 = (PercentageRing) findViewById(R.id.Circle1);

		mPercentageRing1.setMax(100);
		mPercentageRing1.setCricleProgressColor(Color.RED);
		mPercentageRing1.setmRate("" + b1);
		mPercentageRing1.setmText1("");
		mPercentageRing1.setmText2("");
		mPercentageRing1.setProgress(b1);

		PercentageRing mPercentageRing3 = (PercentageRing) findViewById(R.id.Circle1);

		mPercentageRing3.setMax(100);
		mPercentageRing3.setCricleProgressColor(Color.YELLOW);
		mPercentageRing3.setmRate("" + c1);
		mPercentageRing3.setmText1("作业占比");
		mPercentageRing3.setmText2("");
		mPercentageRing3.setProgress(c1);
	}

	Float a = (float) 10, b = (float) 50, c = (float) 75;
	Float a1 = (float) 0, b1 = (float) 0, c1 = (float) 0;

	List<Carinfohistortylist> dd = new ArrayList<Carinfohistortylist>();
	private List<Carinfohistory> listhttp;
	String miji = "";// zuoyemianji
	String coo = "";

	@SuppressLint("NewApi")
	public void onEvent(SoapRes obj) {

		if (obj != null) {
			if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFOHISTORY)) {
				dd.clear();
				try {
					listhttp = (List<Carinfohistory>) obj.getObj();
					dd = listhttp.get(0).getHistoryList();
//					String name = dd.get(0).getOwner();

//					if (dd.size() > 0) {
//						coo = dd.get(0).getCooperativeName();
//					} else {
//						coo = "加载中";
//					}
//					names.setText(name);
//					address.setText(coo);

//					int aa = listhttp.get(0).getTotalArea().indexOf(".");
//					if (aa + 3 >= listhttp.get(0).getTotalArea().length()) {
//						miji = listhttp.get(0).getTotalArea();
//					} else {
//						miji = listhttp.get(0).getTotalArea().substring(0, aa + 3);
//					}
//					a = Float.valueOf(miji) / 400;
//					mianj.setText(miji + "亩");
//					hgl.setText(listhttp.get(0).getAvgPassRate() + "%");
//					String cc = "";
//					if (listhttp.get(0).getAvgDepth().indexOf(".") >= 0) {
//						cc = listhttp.get(0).getAvgDepth().substring(0, listhttp.get(0).getAvgDepth().indexOf("."));
//					} else {
//						cc = listhttp.get(0).getAvgDepth();
//					}
//					deth.setText(cc + "厘米");
//					int bb = listhttp.get(0).getGoodArea().indexOf(".");
//					if (bb + 3 >= listhttp.get(0).getGoodArea().length()) {
//						miji = listhttp.get(0).getGoodArea();
//					} else {
//						miji = listhttp.get(0).getGoodArea().substring(0, bb + 3);
//					}
//					hgmj.setText(miji + "亩");
				} catch (Exception e) {
					// TODO: handle exception
				}
				listViewFarm();

			}
		}
		daywork.postDelayed(new Runnable() {
			@Override
			public void run() {
				daywork.onRefreshComplete();
			}
		}, 1000);
	}

	private OperFarmAdapter operfarm;
	int abc = 0;
	int todays = 0;

	private void listViewFarm() {
		// if (dd.size() == 0) {
		// if(abc==0){
		// abc=1;
		//// Toast.makeText(content, "无作业数据", Toast.LENGTH_SHORT).show();
		// }
		// }else {
		if (date.substring(0, 4).equals(year + "")) {
			if (todays == 0) {
				todays = 1;
				if (!(area.equals("0.00 亩"))) {
					Carinfohistortylist car = new Carinfohistortylist();
					car.setWorkDate(date);
					car.setPassRate("计算中");
					car.setWorkLandarea(area);
					car.setDepth("计算中");
					car.setQualifiedLandarea(area);
					car.setCarId(cardid);
					dd.add(0, car);
				} else {

				}
			}
		}
		operfarm = new OperFarmAdapter(content, dd, type);
		daywork.setAdapter(operfarm);
		// }s

		// daywork.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// daywork.onRefreshComplete();
		// }
		// }, 1000);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// AsyncTaskBase.asDestroy();
		super.onDestroy();
		// Carinfohistortylist ca=new Carinfohistortylist();
		// ca.setBrand("1");
		dd.clear();
	}

	public void showDialog() {
		CustomDialog2.Builder builder = new CustomDialog2.Builder(OperatorFarmAdditionalActivity.this);
		builder.setMessage("选择年");
		builder.setTitle("");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				year=Updatefarmcoop.getmYear();
				 String[] property_va = new String[] { cardid, (year) + "-01-01",
				 (year) + "-12-31", type };
				 soapService.carInfoHistory(property_va);
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	// 计算控件宽高
	public static int measureHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int height = view.getMeasuredHeight();
		return height;
	}

	public static int measureWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int width = view.getMeasuredWidth();
		return width;
	}

}
