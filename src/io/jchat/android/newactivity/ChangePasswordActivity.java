package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class ChangePasswordActivity extends BaseActivity implements OnClickListener {

	private ImageView bank;
	private EditText oldp, newp, newd;
	private TextView signbt, eo_opawss, eo_pawss;
	private View v1, v2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);
		bank = (ImageView) findViewById(R.id.bank);
		signbt = (TextView) findViewById(R.id.signbt);
		oldp = (EditText) findViewById(R.id.oldm);
		newp = (EditText) findViewById(R.id.newm);
		newd = (EditText) findViewById(R.id.newd);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		eo_opawss = (TextView) findViewById(R.id.eo_opawss);
		eo_pawss = (TextView) findViewById(R.id.eo_pawss);
		oldp.setOnClickListener(this);
		newp.setOnClickListener(this);
		newd.setOnClickListener(this);

		PushAgent.getInstance(ChangePasswordActivity.this).onAppStart();
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		initView();
	}

	SharedPreferences sp;

	public void initView() {
		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		signbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pawssword(oldp.getText().toString().trim(), newp.getText().toString().trim(),
						newd.getText().toString().trim());

			}
		});
	}

	public void pawssword(String oldpass, String newpass, String dnewpass) {
		if (oldpass == null || oldpass.equals("")) {
			// Toast.makeText(ChangePasswordActivity.this, "旧密码不能为空",
			// Toast.LENGTH_SHORT).show();
			eo_opawss.setVisibility(View.VISIBLE);
			v1.setBackgroundColor(0xffff2424);
			return;
		} else if (newpass == null || newpass.equals("")) {
			eo_pawss.setVisibility(View.VISIBLE);
			eo_pawss.setText("请输入新密码");
			v2.setBackgroundColor(0xffff2424);
		} else {

			if (newpass.equals(dnewpass)) {
				if (newpass.matches("^[0-9a-zA-Z_]+$")) {
					Object[] property_va = { sp.getString("loginName", ""), oldpass.trim(), newpass };
					soapService.modifyPwd(property_va);
				} else {
//					Toast.makeText(ChangePasswordActivity.this, "新密码不能有特殊字符", Toast.LENGTH_SHORT).show();
					eo_pawss.setVisibility(View.VISIBLE);
					eo_pawss.setText("新密码不能有特殊字符");
					v2.setBackgroundColor(0xffff2424);
				}
			} else {
				// Toast.makeText(ChangePasswordActivity.this, "请确认两次输入密码相同",
				// Toast.LENGTH_SHORT).show();
				eo_pawss.setVisibility(View.VISIBLE);
				eo_pawss.setText("两次密码输入不一致");
				v2.setBackgroundColor(0xffff2424);
			}

			// Toast.makeText(ChangePasswordActivity.this, "请输入新密码",
			// Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		if (obj != null) {
			if (obj.getCode().equals(SOAP_UTILS.METHOD.modifyPwd)) {
				String ss = obj.getObj().toString().substring(1, obj.getObj().toString().length() - 1);
				if (ss.equals("修改密码成功")) {
					Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.oldm:
			eo_opawss.setVisibility(View.GONE);
			v1.setBackgroundColor(0xffcccccc);
			break;
		case R.id.newm:
		case R.id.newd:
			eo_pawss.setVisibility(View.GONE);
			v2.setBackgroundColor(0xffcccccc);
			break;
		default:
			break;
		}
	}
}
