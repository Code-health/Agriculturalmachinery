package io.jchat.android.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lnpdit.agriculturalmachinery.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.jchat.android.activity.CooperativesActivity;
import io.jchat.android.activity.LoginActivity;
import io.jchat.android.activity.MainActivity;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.chatting.utils.HandleResponseCode;
import io.jchat.android.chatting.utils.SharePreferenceManager;
import io.jchat.android.db.DBHelper;
import io.jchat.android.entity.UserInfo;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;
import io.jchat.android.view.LoginView;

public class LoginController implements LoginView.Listener, OnClickListener, CompoundButton.OnCheckedChangeListener {

	private LoginView mLoginView;
	private LoginActivity mContext;
	private DBHelper dbh;
	private int asd;

	/** soapService **/
	public ISoapService soapService = new SoapService();

	public LoginController(LoginView mLoginView, LoginActivity context) {
		this.mLoginView = mLoginView;
		this.mContext = context;
		asd = 0;
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			mContext.finish();
			break;
		case R.id.login_btn:
			dbh = new DBHelper(mContext);
			final String userId = mLoginView.getUserId();
			final String password = mLoginView.getPassword();
			ab = 0;
			// 内部登录
			if (asd == 0) {
				login_validate(userId, password);
			}
			// 极光登录//社交功能
			// 隐藏软键盘
			// InputMethodManager manager = ((InputMethodManager) mContext
			// .getSystemService(Activity.INPUT_METHOD_SERVICE));
			// if (mContext.getWindow().getAttributes().softInputMode
			// != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			// if (mContext.getCurrentFocus() != null) {
			// manager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(),
			// InputMethodManager.HIDE_NOT_ALWAYS);
			// }
			// }
			//
			// if (userId.equals("")) {
			// mLoginView.userNameError(mContext);
			// break;
			// } else if (password.equals("")) {
			// mLoginView.passwordError(mContext);
			// break;
			// }

			// 环信 社交 api登陆
			// final Dialog dialog = DialogCreator.createLoadingDialog(mContext,
			// mContext.getString(R.string.login_hint));
			// dialog.show();
			// JMessageClient.login(userId, password, new BasicCallback() {
			// @Override
			// public void gotResult(final int status, final String desc) {
			// dialog.dismiss();
			// if (status == 0) {
			// mContext.startMainActivity();
			// } else {
			// Log.i("LoginController", "status = " + status);
			// HandleResponseCode.onHandle(mContext, status, false);
			// }
			// }
			// });
			// mContext.startMainActivity();

			break;

		case R.id.register_btn:
			mContext.startRegisterActivity();
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 */
	private void login_validate(String username, String password) {
		if (username == null || username.equals("")) {
			Toast.makeText(mContext, "用户名为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || password.equals("")) {
			Toast.makeText(mContext, "密码为空", Toast.LENGTH_SHORT).show();
			return;
		}

		Object[] property_va = { username, password };
		soapService.login(property_va);

	}

	/**
	 * http回调SoapObject
	 * 
	 * @param obj
	 */

	int ab = 0;

	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		// mContext.onEvent(obj);
		// SoapRes res = (SoapRes) obj;
		if (obj.getCode().equals(SOAP_UTILS.METHOD.LOGIN)) {
			if (obj.getObj() != null) {
				UserInfo loginUser = (UserInfo) obj.getObj();
					if (loginUser.getId().equals("0")) {
						
						Toast.makeText(mContext, "您的账号与密码不符", Toast.LENGTH_SHORT).show();
						
					} else {
						SharedPreferences sps = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
						if (null != sps.getString("loginName", null)) {
							mContext.finish();
						} else {
							String userId = mLoginView.getUserId();

							Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();

							// dbh.clearUserInfoData();
							// dbh.insUserInfo(loginUser);
							// 登陆保存密码
							loginUser.setLoginPassWd(mLoginView.getPassword());
							SharedPreferences sp = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
							Editor editor = sp.edit();
							editor.putString("id", loginUser.getId());
							editor.putString("isNewRecord", loginUser.getIsNewRecord());
							editor.putString("loginName", loginUser.getLoginName());
							// editor.putString("remarks",
							// loginUser.getRemarks());
							// editor.putString("updateDate",
							// loginUser.getUpdateDate());
							// editor.putString("loginName",
							// loginUser.getLoginName());
							// editor.putString("no", loginUser.getNo());
							editor.putString("name", loginUser.getName());
							// editor.putString("email", loginUser.getEmail());
							// editor.putString("phone", loginUser.getPhone());
							// editor.putString("mobile",
							// loginUser.getMobile());
							// editor.putString("loginIp",
							// loginUser.getLoginIp());
							// editor.putString("loginDate",
							// loginUser.getLoginDate());
							editor.putString("loginFlag", loginUser.getLoginFlag());
							// editor.putString("oldLoginIp",
							// loginUser.getOldLoginIp());
							// editor.putString("oldLoginDate",
							// loginUser.getOldLoginDate());
							editor.putString("roleNames", loginUser.getRoleNames());
							editor.putString("admin", loginUser.getAdmin());
							editor.putString("cardid", loginUser.getCardid());
							// editor.putString("loginPassWd",
							// loginUser.getLoginPassWd());
							editor.commit();

							// Object[] property_va = { cooperativeId, 1 + "",
							// "30"};//登陆成功跳农机信息页
							// soapService.carInfoList(property_va);
							Intent intent = new Intent();
							intent.putExtra("cardid", loginUser.getCardid());
							intent.putExtra("name", loginUser.getName());
							intent.setClass(mContext, CooperativesActivity.class);
							mContext.startActivity(intent);
							// mContext.startCooperActivity(loginUser.getCardid());//
							// 添加用户权限判定分配不同界面

							mContext.finish();
							// Intent intent_login = new Intent();
							// intent_login.setClass(mContext,
							// MainActivity.class);
							// mContext.startActivity(intent_login);
							// mContext.finish();
							// }
						}
					}
			} else {
				if(obj.getDlpand().equals("0")){
					Toast.makeText(mContext, "您的账号与密码不符", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
				}
				
			}
		}
	}

	@Override
	public void onSoftKeyboardShown(int w, int h, int oldw, int oldh) {
		int softKeyboardHeight = oldh - h;
		if (softKeyboardHeight > 300) {
			mLoginView.setRegistBtnVisable(View.INVISIBLE);
			boolean writable = SharePreferenceManager.getCachedWritableFlag();
			if (writable) {
				Log.i("LoginController", "commit h: " + softKeyboardHeight);
				SharePreferenceManager.setCachedKeyboardHeight(softKeyboardHeight);
				SharePreferenceManager.setCachedWritableFlag(false);
			}
		} else {
			mLoginView.setRegistBtnVisable(View.VISIBLE);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d("sdfs", "onCheckedChanged !!!! isChecked = " + isChecked);
		if (isChecked) {
			swapEnvironment(true);
		} else {
			swapEnvironment(false);
		}
	}

	private void swapEnvironment(boolean isTest) {
		try {
			Method method = JMessageClient.class.getDeclaredMethod("swapEnvironment", Context.class, Boolean.class);
			method.invoke(null, mContext, isTest);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
