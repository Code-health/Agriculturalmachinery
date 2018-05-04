package io.jchat.android.newactivity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import io.jchat.android.adapter.TownshipAdapter;
import io.jchat.android.entity.Carinfohistortylist;
import io.jchat.android.http.HttpGetService;
import io.jchat.android.newactivity.NumOperFarmActivity.MyTead;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class TownshipActivity extends Activity implements OnClickListener{


	private ImageView imag_bank;
	private TextView names,ddp,workl;
	private String name;
	Dialog dialog;
	private io.jchat.android.pulltorefresh.library.PullToRefreshListView daywork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.adapter_township);
		
		imag_bank=(ImageView) findViewById(R.id.imag_bank);
		names=(TextView) findViewById(R.id.names);
		ddp = (TextView) findViewById(R.id.ddp);
		workl=(TextView) findViewById(R.id.workl);
		daywork=(PullToRefreshListView) findViewById(R.id.daywork);
		imag_bank.setOnClickListener(this);
//		names.setOnClickListener(this);
		
//		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		carid=getIntent().getStringExtra("officeid");
		worktype=getIntent().getStringExtra("type");
		name=getIntent().getStringExtra("name");
		names.setText(name);
		if (worktype.equals("3")) {
			ddp.setText("覆盖率");
			workl.setText("作业里程");
		}
		dialog = new Dialog(TownshipActivity.this, R.style.dialogss);
		dialog.show();
		new MyTead().start();
		
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
//				String date = list.get(position - 1).getWorkDate().substring(0, 10);
				String names = list.get(position - 1).getCounty();
				if (carIds != null) {
					
					if(worktype.equals("5")){
						Intent in = new Intent();
						in.putExtra("carid", carIds);
						in.putExtra("name", names);
						in.putExtra("type", worktype);
						in.putExtra("usertype", "3");
						// in.setClass(OperatorFarmActivity.this,
						// DayWorkActivity.class);
						in.setClass(TownshipActivity.this, NumOperFarmAdditionalActivity.class);
						startActivity(in);
					}else{
						Intent in = new Intent();
						in.putExtra("carid", carIds);
						in.putExtra("name", names);
						in.putExtra("type", worktype);
						in.putExtra("usertype", "3");
						// in.setClass(OperatorFarmActivity.this,
						// DayWorkActivity.class);
						in.setClass(TownshipActivity.this, NumOperFarmActivity.class);
						startActivity(in);
					}
					
				} else {
					Toast.makeText(TownshipActivity.this, "无数据", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
	
	String carid="";
	String worktype="";
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imag_bank:
			finish();
			break;

		default:
			break;
		}
	}
	
	class MyTead extends Thread {
		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			super.run();
			// String url1 = officeid + "/" + worktype + "/" + userType +
			// "/workInfoList";
			String url1 = carid + "/" + worktype + "/2/v2/detailsList";
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
					try {
						list.clear();
						JSONArray jas = new JSONArray(msg.obj.toString());
						for (int i = 0; i < jas.length(); i++) {
							JSONObject js=(JSONObject) jas.get(i);
							Carinfohistortylist ca = new Carinfohistortylist();
							ca.setCounty(js.getString("name"));
							ca.setDepth(js.getString("avgDepth"));
							ca.setArea(js.getString("totalArea"));
							ca.setCarId(js.getString("mechanismId"));//
							ca.setPassRate(js.getString("avgPassRate"));
							ca.setOverlapRate(js.getString("avgOverlapRate"));
							ca.setDistance(js.getString("totalDistance"));
							list.add(ca);
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dialog.dismiss();
				TownshipAdapter numadapter=new TownshipAdapter(TownshipActivity.this,list,worktype);
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
