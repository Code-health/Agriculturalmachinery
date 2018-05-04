package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
/**
 * 安装人员  添加新的合作社到后台，以方便对新农机的安装
 * @author Administrator
 *
 */
public class AddCooperationActivity extends Activity{
	
	private ImageView bank;
	private Spinner sps,spx;
	String[] spsitme={"沈阳","鞍山","朝阳"};
	String[] spxitme={"沈阳","鞍山","朝阳"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcooperation);
		
		sps=(Spinner) findViewById(R.id.spaddress);
		spx=(Spinner) findViewById(R.id.spxian);
		bank=(ImageView) findViewById(R.id.bank);
		PushAgent.getInstance(AddCooperationActivity.this).onAppStart();
		initView();
	}
	
	public void initView(){
		bank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spxitme);
		adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sps.setAdapter(adapters);
		sps.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ArrayAdapter<String> adapterx = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spxitme);
		adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spx.setAdapter(adapterx);
		spx.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
