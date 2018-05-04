package io.jchat.android.newactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import io.jchat.android.adapter.NumoperFarmAdapter;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.http.HttpGetService;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class NumOperFarmAdditionalActivity extends Activity {

	private ImageView imag_bank;
	private TextView names, ddp;
	String name;
	private String type = "";
	private io.jchat.android.pulltorefresh.library.PullToRefreshListView daywork;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_numoperfarm_additional);

		imag_bank = (ImageView) findViewById(R.id.imag_bank);
		names = (TextView) findViewById(R.id.names);
		ddp = (TextView) findViewById(R.id.ddp);
		daywork = (PullToRefreshListView) findViewById(R.id.daywork);

		// imag_bank.setOnClickListener(this);
		// names.setOnClickListener(this);

		// SharedPreferences sp = getSharedPreferences("userinfo",
		// Context.MODE_PRIVATE);
		carid = getIntent().getStringExtra("carid");
		worktype = getIntent().getStringExtra("type");
		type = getIntent().getStringExtra("usertype");
		// type=getIntent().getStringExtra("usertype");
		name = getIntent().getStringExtra("name");
		names.setText(name);
//		if (worktype.equals("3")) {
//			ddp.setText("覆盖率");
//		}
		dialog = new Dialog(NumOperFarmAdditionalActivity.this, R.style.dialogss);
		dialog.show();
		new MyTead().start();

		imag_bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		daywork.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new MyTead().start();
			}
		});

		daywork.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				String carIds = list.get(position - 1).getCarId();
				String name1 = list.get(position - 1).getOwner();
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = sDateFormat.format(new java.util.Date());
				if (carIds != null) {
					if(worktype.equals("5")){
						Intent in = new Intent();
						in.putExtra("carid", carIds);
						in.putExtra("type", worktype + "");
						in.putExtra("date", date);
						in.putExtra("area", "0.00 亩");
						in.putExtra("name", name1);
						// in.setClass(OperatorFarmActivity.this,
						// DayWorkActivity.class);
						in.setClass(NumOperFarmAdditionalActivity.this, OperatorFarmAdditionalActivity.class);
						startActivity(in);
					}else{
						Intent in = new Intent();
						in.putExtra("carid", carIds);
						in.putExtra("type", worktype + "");
						in.putExtra("date", date);
						in.putExtra("area", "0.00 亩");
						in.putExtra("name", name1);
						// in.setClass(OperatorFarmActivity.this,
						// DayWorkActivity.class);
						in.setClass(NumOperFarmAdditionalActivity.this, OperatorFarmActivity.class);
						startActivity(in);
					}
					
				} else {
					Toast.makeText(NumOperFarmAdditionalActivity.this, "无数据", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	String carid = "";
	String worktype = "";

	class MyTead extends Thread {
		@Override
		public void run() {

			// TODO Auto-generated method stub
			super.run();
			// String url1 = officeid + "/" + worktype + "/" + userType +
			// "/workInfoList";
			String url1 = carid + "/" + worktype + "/" + type + "/v2/detailsList";
			Message msg = new Message();
			msg.arg1 = 0;
			// msg.obj = responseFromServer;
			msg.obj = HttpGetService.data(url1, "");
			mThirdHandler.sendMessage(msg);
		}
	}

	List<Carinfohistortylist> list = new ArrayList<Carinfohistortylist>();

	Handler mThirdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == 0) {
				if (msg.obj != null && msg.obj != "") {
					list.clear();
					try {
						JSONArray jas = new JSONArray(msg.obj.toString());
						for (int i = 0; i < jas.length(); i++) {
							Carinfohistortylist ca = new Carinfohistortylist();
							try {
								JSONObject js = (JSONObject) jas.get(i);

								ca.setOwner(js.getString("owner"));
								ca.setArea(js.getString("workLandarea"));
								ca.setCarId(js.getString("carId"));//
								ca.setCounty(js.getString("flameNumber"));
								ca.setPassRate(js.getString("passRate"));
								ca.setOverlapRate(js.getString("overlapRate"));
								if (type.equals("4")) {
									names.setText(js.getString("owner"));
								} else {

								}
								list.add(ca);
							} catch (Exception e) {
								// TODO: handle exception
							}
							
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dialog.dismiss();
				NumoperFarmAdapter numadapter = new NumoperFarmAdapter(NumOperFarmAdditionalActivity.this, list, worktype);
				daywork.setAdapter(numadapter);
				daywork.postDelayed(new Runnable() {
					@Override
					public void run() {
						daywork.onRefreshComplete();
					}
				}, 1000);
			}
		}
	};
	
}
