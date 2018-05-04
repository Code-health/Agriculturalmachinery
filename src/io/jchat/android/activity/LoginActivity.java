package io.jchat.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;

import io.jchat.android.controller.LoginController;
import io.jchat.android.view.LoginView;

public class LoginActivity extends BaseActivity {

	private LoginView mLoginView = null;
	private LoginController mLoginController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mLoginView = (LoginView) findViewById(R.id.login_view);
		mLoginView.initModule();
		mLoginController = new LoginController(mLoginView, this);
		mLoginView.setListener(mLoginController);
		mLoginView.setListeners(mLoginController);
		mLoginView.setOnCheckedChangeListener(mLoginController);
		Intent intent = this.getIntent();
		mLoginView.isShowReturnBtn(intent.getBooleanExtra("fromSwitch", false));
		
		PgyUpdateManager.register(this);//注册蒲公英,已选择Application注册
		// PgyCrashManager.unregister();//取消注册
	}


	@Override
	protected void onResume() {
		super.onResume();
		
		// 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
        PgyFeedbackShakeManager.setShakingThreshold(700);

        // 以对话框的形式弹出
        PgyFeedbackShakeManager.register(LoginActivity.this);

        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
        // 打开沉浸式,默认为false
        // FeedbackActivity.setBarImmersive(true);
//        PgyFeedbackShakeManager.register(LoginActivity.this, false);
		
		SharedPreferences sp =getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		
		if (null != sp.getString("loginName", null)) {
			Intent intent = new Intent();
			intent.putExtra("cardid", sp.getString("cardid", null));
			intent.setClass(this, CooperativesActivity.class);
			startActivity(intent);
			finish();
		}
	}
	

	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	        PgyFeedbackShakeManager.unregister();
	    }

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	public Context getContext() {
		return this;
	}

//	public void startMainActivity() {
//		Intent intent = new Intent();
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//		intent.setClass(getContext(), MainActivity.class);
//		startActivity(intent);
//	}

	public void startRegisterActivity() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	public void startCooperActivity(String cardid){
		
		Intent intent = new Intent();
		intent.putExtra("cardid", cardid);
		intent.setClass(this, CooperativesActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void startTractorActivity(){
		Intent intent = new Intent();
		intent.putExtra("carId", "LHBL124");
		intent.setClass(this, TractorInfoActivity.class);
		startActivity(intent);
	}

}
