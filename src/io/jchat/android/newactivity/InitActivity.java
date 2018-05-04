package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.crash.PgyCrashManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.jpush.android.api.JPushInterface;

public class InitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initactivity);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int a = 0;
		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		try {
			if (null != sp.getString("loginName", null)) {
				Intent intent = new Intent();
				intent.setClass(this, CooperativeActivity1.class);
				startActivity(intent);
				a = 1;
				this.finish();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (a == 0) {
			new MyTheadle().start();
		}
		
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.obj.equals("0")) {
				Intent in = new Intent();
				in.setClass(InitActivity.this, SignActivity.class);
				startActivity(in);
				InitActivity.this.finish();
			}
		}
	};

	class MyTheadle extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message mes = new Message();
			mes.obj = "0";
			handler.sendMessage(mes);
		}
	}
}
