package io.jchat.android.newactivity;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.adapter.DayWorkAdapter;
import io.jchat.android.adapter.DayWorkAdditionalAdapter;
import io.jchat.android.entity.TractorDetails;
import io.jchat.android.entity.TractorDetailslist;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class DayWorkActivity extends BaseActivity {
	private ImageView bank, map;
	private Button feed;
	private TextView time;
	private ListView lv;
	String carId;
	String workDate;
	String type;
	int one = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_daywork);
		bank = (ImageView) findViewById(R.id.bank);
		time = (TextView) findViewById(R.id.toptime);
		lv = (ListView) findViewById(R.id.list);
		map = (ImageView) findViewById(R.id.map);
		// PushAgent.getInstance(DayWorkActivity.this).onAppStart();
		initView();

	}

	public void initView() {
		Intent intent = getIntent();
		carId = intent.getStringExtra("carid");
		workDate = intent.getStringExtra("workdate");
		type = intent.getStringExtra("type");
		String ri = "";
		// if (0 == Integer.parseInt(workDate.substring(8, 9))) {
		// ri = workDate.substring(9, 10);
		// } else {
		// ri = workDate.substring(8, 10);
		// }
		time.setText(workDate);
		
		if(type.equals("5")){
			
			daywork_additional = new DayWorkAdditionalAdapter(DayWorkActivity.this, dd, type);
			lv.setAdapter(daywork_additional);
		}else{
			daywork = new DayWorkAdapter(DayWorkActivity.this, dd, type);
			lv.setAdapter(daywork);
		}
		
		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(DayWorkActivity.this, DayWorkMaps.class);
				in.putExtra("carid", carId);
				in.putExtra("workdate", workDate);
				in.putExtra("type", type);
				startActivity(in);
			}
		});

	}

	List<TractorDetailslist> dd = new ArrayList<TractorDetailslist>();
	private List<TractorDetails> selectlist;
	DayWorkAdapter daywork;
	DayWorkAdditionalAdapter daywork_additional;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			String[] property_va = new String[] { workDate, carId };
			soapService.carInfoDetails(property_va);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.CARINFODETAILS)) {
			selectlist = (List<TractorDetails>) obj.getObj();
			List<TractorDetailslist> dds = new ArrayList<TractorDetailslist>();
			dds=selectlist.get(0).getDetailsList();
			dd.clear();
			dd.addAll(dds);

			if(type.equals("5")){

				daywork_additional.notifyDataSetChanged();
			}else{

				daywork.notifyDataSetChanged();
			}
//			}

		} else if (obj.getCode().equals("DateChange")) {

			String[] property_va = new String[] { workDate, carId };
			soapService.carInfoDetails(property_va);

		}
	}

}
