package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.activity.CooperativesActivity;
import io.jchat.android.activity.LoginActivity;
import io.jchat.android.dialog.CustomDialog;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.entity.UserInfo;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.utils.SOAP_UTILS;

public class SignActivity extends BaseActivity {

	private ImageView psee;
	private TextView sign;
	private EditText pwassword, username;
	Dialog dialog;
	/** soapService **/
	// public ISoapService soapService = new SoapService();
	private int a = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		sign = (TextView) findViewById(R.id.signbt);
		psee = (ImageView) findViewById(R.id.passwordsee);
		pwassword = (EditText) findViewById(R.id.pwassword);
		username = (EditText) findViewById(R.id.username);

		// PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
		// PgyCrashManager.unregister();//取消注册
		// PushAgent.getInstance(SignActivity.this).onAppStart();
		initView();
		dialog = new Dialog(SignActivity.this, R.style.dialogss);
	}

	public void initView() {

		SharedPreferences sp = SignActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		username.setText(sp.getString("loginName", ""));
		pwassword.setText(sp.getString("loginPassWd", ""));

		sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				login_validate(username.getText().toString().trim(), pwassword.getText().toString().trim());
			}
		});
		psee.setOnClickListener(new OnClickListener() {// 设置密码是否隐藏

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (a == 0) {
					pwassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					Editable etable = pwassword.getText();
					Selection.setSelection(etable, etable.length());
					a = 1;
				} else {
					pwassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					Editable etable1 = pwassword.getText();
					Selection.setSelection(etable1, etable1.length());
					a = 0;
				}
			}
		});
	}

	String passwords = "";

	private void login_validate(String username, String password) {
		// Intent intent = new Intent();
		// intent.putExtra("cardid", username);
		// intent.putExtra("name", password);
		// intent.setClass(this, NewMainActivity.class);
		// this.startActivity(intent);

		if (username == null || username.equals("")) {
			Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || password.equals("")) {
			Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
			return;
		}
		dialog.show();
		passwords = password;
		Object[] property_va = { username, password };
		soapService.login(property_va);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
		PgyFeedbackShakeManager.setShakingThreshold(700);

		// 以对话框的形式弹出
		PgyFeedbackShakeManager.register(SignActivity.this);

		// 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
		// 打开沉浸式,默认为false
		// FeedbackActivity.setBarImmersive(true);
		// PgyFeedbackShakeManager.register(LoginActivity.this, false);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// PgyFeedbackShakeManager.unregister();
	}

	@Override
	public void onEventMainThread(LoginStateChangeEvent event) {
		// TODO Auto-generated method stub
		super.onEventMainThread(event);
	}

	/**
	 * http回调SoapObject
	 * 
	 * @param obj
	 */

	int ab = 0;

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		// SharedPreferences sps = this.getSharedPreferences("userinfo",
		// Context.MODE_PRIVATE);
		// if (null != sps.getString("loginName", null)) {
		// this.finish();
		// } else {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.LOGIN)) {
			try {
				if (obj.getObj() != null) {
					UserInfo loginUser = (UserInfo) obj.getObj();
					dialog.dismiss();
					if (loginUser.getId().equals("0")) {

						Toast.makeText(this, "您的账号与密码不符", Toast.LENGTH_SHORT).show();

					} else {
						String usertt = loginUser.getuserType();
						if (loginUser.getuserType().endsWith("2") || loginUser.getuserType().endsWith("1")
								|| loginUser.getuserType().endsWith("4") || loginUser.getuserType().endsWith("3")) {
							SharedPreferences sps = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
							if (null != sps.getString("loginName", null)) {
								this.finish();
							} else {
								Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

								SharedPreferences sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
								Editor editor = sp.edit();
								editor.putString("id", loginUser.getId());
								editor.putString("isNewRecord", loginUser.getIsNewRecord());
								editor.putString("loginName", loginUser.getLoginName());
								editor.putString("officeid", loginUser.getOfficeid());
								editor.putString("officename", loginUser.getOfficename());
								editor.putString("name", loginUser.getName());
								editor.putString("loginFlag", loginUser.getLoginFlag());
								editor.putString("roleNames", loginUser.getRoleNames());
								editor.putString("admin", loginUser.getAdmin());
								editor.putString("cardid", loginUser.getCardid());
								editor.putString("loginPassWd", passwords);
								editor.putString("userType", loginUser.getuserType());
								editor.putString("photo", loginUser.getPhoto());
								editor.commit();

								SharedPreferences spt = this.getSharedPreferences("loginmessage", Context.MODE_PRIVATE);
								Editor editors = spt.edit();
								editors.putString("photoone", "0");
								editors.putString("phototwo", "0");
								editors.putString("photothree", "0");
								editors.putString("photofour", "0");
								editors.putString("photofive", "0");
								editors.putString("photosex", "0");
								editors.commit();

								SharedPreferences spfs = getSharedPreferences("fristlogin", Context.MODE_PRIVATE);
								if (null == spfs.getString("login", null)
										|| spfs.getString("login", null).equals("0")) {
									SharedPreferences spf = this.getSharedPreferences("fristlogin",
											Context.MODE_PRIVATE);
									Editor editorf = spf.edit();
									editorf.putString("login", "0");
									editorf.commit();
								}
								
								
								Intent intent = new Intent();
								intent.setClass(this, CooperativeActivity1.class);
								this.startActivity(intent);
								PgyCrashManager.unregister();// 取消注册
								this.finish();

							}
						} else {
							Toast.makeText(this, "用户权限不符", Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					if (obj.getDlpand().equals("0")) {
						dialog.dismiss();
						;
						// Toast.makeText(this, "您的账号与密码不符",
						// Toast.LENGTH_SHORT).show();
						diaiogs();
					} else {
						Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// }
	}

	public void diaiogs() {
		CustomDialog.Builder builder = new CustomDialog.Builder(SignActivity.this);
		builder.setMessage("忘记用户名密码请联系管理员");
		builder.setTitle("您的账号与密码不符");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.setNegativeButton(null, new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

}
