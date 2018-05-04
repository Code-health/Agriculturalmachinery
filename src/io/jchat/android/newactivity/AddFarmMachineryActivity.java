package io.jchat.android.newactivity;

import java.lang.annotation.Annotation;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;
import com.ztiany2011.simplezxing.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Cap;
import android.media.CameraProfile;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.SipAddress;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.http.SoapRes;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 安装人员 添加农机及修改信息
 * 
 * @author Administrator
 *
 */
public class AddFarmMachineryActivity extends BaseActivity {

	
	private ImageView bank,sao;
	private Button next;
	private EditText et;
	private TextView zhuji;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfarmmachinery);

		bank = (ImageView) findViewById(R.id.bank);
		next = (Button) findViewById(R.id.bt_next);
		sao=(ImageView) findViewById(R.id.saosao);
		et=(EditText) findViewById(R.id.id);
		zhuji=(TextView) findViewById(R.id.zhijih);
		PushAgent.getInstance(AddFarmMachineryActivity.this).onAppStart();
		initView();
	}

	public void initView() {
//		// 建立数据源

		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		sao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(AddFarmMachineryActivity.this, CaptureActivity.class), 1);
				
			}
		});
		
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				String ss=et.getText().toString();
				in.putExtra("id", zhuji.getText().toString()+et.getText().toString().trim());
//				in.putExtra("id", et.getText().toString().trim());
				in.setClass(AddFarmMachineryActivity.this, AddFarmMachiney.class);
				startActivity(in);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			String result=data.getStringExtra("Strings");
			et.setText(result);
		}
	}
	
	int a=0;
	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		
		
	}
}
