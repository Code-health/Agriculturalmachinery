package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class SettingAreaActivity extends BaseActivity implements OnClickListener {

	private EditText marea;
	private TextView trues, ti, title, mess;
	private ImageView bank;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settarea);
		marea = (EditText) findViewById(R.id.marea);
		trues = (TextView) findViewById(R.id.trues);
		bank = (ImageView) findViewById(R.id.bank);
		ti = (TextView) findViewById(R.id.ti);
		title = (TextView) findViewById(R.id.title);
		mess = (TextView) findViewById(R.id.meass);
		marea.setOnClickListener(this);
		bank.setOnClickListener(this);
		trues.setOnClickListener(this);

		type = getIntent().getStringExtra("type");
		if (type.equals("1")) {
			title.setText("水田深翻目标作业面积");
			mess.setText("水田深翻目标作业面积");
		} else if (type.equals("2")) {
			title.setText("秸秆还田目标作业面积");
			mess.setText("秸秆还田目标作业面积");
		} else if (type.equals("3")) {
			title.setText("免耕播种目标作业面积");
			mess.setText("免耕播种目标作业面积");
		} else if (type.equals("4")) {
			title.setText("植保作业目标作业面积");
			mess.setText("植保作业目标作业面积");
		} else if (type.equals("5")) {
			title.setText("收获作业目标作业面积");
			mess.setText("收获作业目标作业面积");
		}
		
		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);

		id = sp.getString("id", "");
	}

	String id = "";
	Dialog dialog;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.marea:
			ti.setVisibility(View.GONE);
			break;
		case R.id.bank:
			finish();
			break;
		case R.id.trues:
			if (marea.getText().toString().trim() == null || marea.getText().toString().trim().equals("")) {
				ti.setVisibility(View.VISIBLE);
				ti.setText("目标面积不能为空");
				// Toast.makeText(SettingAreaActivity.this, "面积不能为空",
				// Toast.LENGTH_SHORT).show();
			} else {
				if (marea.getText().toString().trim().matches("^[0-9]+$")) {
					if (marea.getText().toString().trim().length() < 8) {
						if (0 != Integer.parseInt(marea.getText().toString().trim())) {
							dialog = new Dialog(SettingAreaActivity.this, R.style.dialogss);
							dialog.show();
							// Object[] property_va = { id,
							// marea.getText().toString().trim() };
							// soapService.updatePhoto(property_va);

							SharedPreferences sps = this.getSharedPreferences("loginmessage", Context.MODE_PRIVATE);
							Editor editor = sps.edit();
							if (type.equals("0")) {
								editor.putString("photoone", marea.getText().toString().trim());
							} else if (type.equals("1")) {
								editor.putString("phototwo", marea.getText().toString().trim());
							} else if (type.equals("2")) {
								editor.putString("photothree", marea.getText().toString().trim());
							} else if (type.equals("3")) {
								editor.putString("photofour", marea.getText().toString().trim());
							}else if (type.equals("4")) {
								editor.putString("photofive", marea.getText().toString().trim());
							}else if (type.equals("5")) {
								editor.putString("photosex", marea.getText().toString().trim());
							}
							editor.commit();
							Toast.makeText(SettingAreaActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
//							Updatefarmcoop.getSettworkactivity().finish();
							finish();
						}else {
							ti.setVisibility(View.VISIBLE);
							ti.setText("输入目标面积必须大于0");
						}
					} else {
						ti.setVisibility(View.VISIBLE);
						ti.setText("输入超出条件范围");
						// Toast.makeText(SettingAreaActivity.this, "目标面积过大",
						// Toast.LENGTH_SHORT).show();
					}
				} else {
					ti.setVisibility(View.VISIBLE);
					ti.setText("目标面积只能输入整数");
					// Toast.makeText(SettingAreaActivity.this, "面积不能有特殊字符",
					// Toast.LENGTH_SHORT).show();
				}
			}

			break;
		default:
			break;
		}
	}

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.updatePhoto)) {
			if (obj.getObj().toString().substring(1, 5).equals("true")) {
				SharedPreferences sps = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor editor = sps.edit();
				editor.putString("photo", marea.getText().toString().trim());
				editor.commit();
				Toast.makeText(SettingAreaActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(SettingAreaActivity.this, obj.getObj().toString(), Toast.LENGTH_SHORT).show();
			}

			dialog.dismiss();
		}
	}
}
