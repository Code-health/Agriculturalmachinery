package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import io.jchat.android.entity.Updatefarmcoop;

public class SettingWorkAreaActivity extends Activity implements OnClickListener {

	private LinearLayout[] work = new LinearLayout[6];
	private Context context;
	private ImageView bank;
	private View[] v=new View[6];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swaa);
		context = this;
		work[0] = (LinearLayout) findViewById(R.id.work1);
		work[1] = (LinearLayout) findViewById(R.id.work4);
		work[2] = (LinearLayout) findViewById(R.id.work3);
		work[3] = (LinearLayout) findViewById(R.id.work2);
		work[4] = (LinearLayout) findViewById(R.id.work5);
		work[5] = (LinearLayout) findViewById(R.id.work6);
		v[0]=findViewById(R.id.v1);
		v[1]=findViewById(R.id.v4);
		v[2]=findViewById(R.id.v3);
		v[3]=findViewById(R.id.v2);
		v[4]=findViewById(R.id.v5);
		v[5]=findViewById(R.id.v6);
		bank = (ImageView) findViewById(R.id.bank);

		bank.setOnClickListener(this);
		work[0].setOnClickListener(this);
		work[1].setOnClickListener(this);
		work[2].setOnClickListener(this);
		work[3].setOnClickListener(this);
		work[4].setOnClickListener(this);
		work[5].setOnClickListener(this);
		
//		Updatefarmcoop.setSettworkactivity(SettingWorkAreaActivity.this);
		
		String workType=MineFragment.workAllType;
		String[] type=new String[workType.length()];
		for(int i=0;i<workType.length();i++){
			if(i+1<workType.length()){
			type[i]=workType.substring(i,i+1);
			}else {
				type[i]=workType.substring(i);
			}
		}
		for(int i=0;i<work.length;i++){
			if(type[i].equals("0")){
				work[i].setVisibility(View.GONE);
				v[i].setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent inte = new Intent();
		switch (v.getId()) {
		case R.id.work1:

			inte.putExtra("type", "0");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.work2:
			inte.putExtra("type", "3");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.work3:
			inte.putExtra("type", "2");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.work4:
			inte.putExtra("type", "1");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.work5:
			inte.putExtra("type", "4");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.work6:
			inte.putExtra("type", "5");
			inte.setClass(context, SettingAreaActivity.class);
			startActivity(inte);
			break;
		case R.id.bank:
			finish();
			break;
		default:
			break;
		}
		
	}
}
