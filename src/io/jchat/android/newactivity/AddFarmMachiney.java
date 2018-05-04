package io.jchat.android.newactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.entity.Updatecoop;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.entity.UserInfo;
import io.jchat.android.http.NewHttpClient;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class AddFarmMachiney extends BaseActivity {

	private ImageView bank;
	private Spinner SipAddress, spxian, spcoop, sp1, sp2, sp_sensor1_direction, sp_sensor4_direction, sp_work_type;
	private Spinner spinner;
	private TextView Host_ID, anti_dismantle, Software_Version, Modifier, Sm_card, sensor1, sensor2, sensor3, sensor4,
			sensor5, sensor6, sensor7, sensor8;
	private EditText Agricultural, phone, Jijuhao, car_number, link_length2, Machine_spacing, VIN, Lead_qinghao, brand,
			model, radius, authorize, link_length1, upDepth, downDepth, lowBarPloughHeight, uploadType, uploadStart,
			uploadEnd, plough_ground_height;
	private Button queren;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_addframinfor);
		SipAddress = (Spinner) findViewById(R.id.spaddress);
		spxian = (Spinner) findViewById(R.id.spxian);
		spcoop = (Spinner) findViewById(R.id.spcoop);
		spinner = (Spinner) findViewById(R.id.sp1);
		sp2 = (Spinner) findViewById(R.id.sp2);
		sp_sensor1_direction = (Spinner) findViewById(R.id.sensor1_direction);
		sp_sensor4_direction = (Spinner) findViewById(R.id.sensor4_direction);
		sp_work_type = (Spinner) findViewById(R.id.work_type);
		bank = (ImageView) findViewById(R.id.bank);

		plough_ground_height = (EditText) findViewById(R.id.plough_ground_height);
		Host_ID = (TextView) findViewById(R.id.Host_ID);// 主机号
		Agricultural = (EditText) findViewById(R.id.Agricultural);// 农机手
		phone = (EditText) findViewById(R.id.phone);// 电话
		Jijuhao = (EditText) findViewById(R.id.Jijuhao);// 机具号
		car_number = (EditText) findViewById(R.id.car_number);// 车牌号
		VIN = (EditText) findViewById(R.id.VIN);// 车架号
		Lead_qinghao = (EditText) findViewById(R.id.Lead_qinghao);// 引擎号
		brand = (EditText) findViewById(R.id.brand);// 品牌
		model = (EditText) findViewById(R.id.model);// 型号
		radius = (EditText) findViewById(R.id.radius);// 后轮半径
		// authorize = (EditText) findViewById(R.id.authorize);// 授权信息
		// anti_dismantle = (TextView) findViewById(R.id.anti_dismantle);// 防拆标识
		// Software_Version = (TextView) findViewById(R.id.Software_Version);//
		// 软件版本
		// Sm_card = (TextView) findViewById(R.id.Sm_card);// SM卡号
		sensor1 = (TextView) findViewById(R.id.sensor1);// 传感器1
		sensor2 = (TextView) findViewById(R.id.sensor2);
		sensor3 = (TextView) findViewById(R.id.sensor3);
		sensor4 = (TextView) findViewById(R.id.sensor4);
		// sensor5 = (TextView) findViewById(R.id.sensor5);
		// sensor6 = (TextView) findViewById(R.id.sensor6);
		// sensor7 = (TextView) findViewById(R.id.sensor7);
		// sensor8 = (TextView) findViewById(R.id.sensor8);
		link_length1 = (EditText) findViewById(R.id.link_length1);// 下拉杆长度
		link_length2 = (EditText) findViewById(R.id.link_length2);// 下拉杆机具连接点到犁尖长度
		Machine_spacing = (EditText) findViewById(R.id.Machine_spacing);// 机具两侧犁尖间距离
		Modifier = (TextView) findViewById(R.id.Modifier);// 修改人//合作社ID
		queren = (Button) findViewById(R.id.bt_queren);// 确认修改
		// upDepth = (EditText) findViewById(R.id.upDepth);
		// downDepth = (EditText) findViewById(R.id.downDepth);
		lowBarPloughHeight = (EditText) findViewById(R.id.lowBarPloughHeight);
		// uploadType = (EditText) findViewById(R.id.uploadType);
		// uploadStart = (EditText) findViewById(R.id.uploadStart);
		// uploadEnd = (EditText) findViewById(R.id.uploadEnd);
		PushAgent.getInstance(AddFarmMachiney.this).onAppStart();
		
		Intent in = getIntent();
		Host_ID.setText(in.getStringExtra("id"));
		id = in.getStringExtra("id");
		init();
		if (id == null || id.equals("")) {
			Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
			finish();
		}
		Modifier.setText(CooperativeActivity.name);

		initView();

	}

	String id = "";

	public void init() {
		Updatefarmcoop.setAddFarmMachiney(AddFarmMachiney.this);
		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		String uaserid = sp.getString("id", "");

		Object[] property_va = { id, "1" };
		soapService.getCar(property_va);
	}

	String[] mItems = { "沈阳", "林源", "苏家屯" };
	String[] mItemx = { "朝阳县", "鞍山县" };

	String[] mItem1 = { "0后方", "1前方" };
	String[] mItem2 = { "0左侧", "1右侧" };
	String[] mItem3 = { "0前后", "1左右" };
	String[] mItem4 = { "0前后", "1左右" };
	String[] mItem5 = { "0深松", "1水田深翻", "2秸秆还田","3免耕播种"  };

	String spshi="0", spshu1="0", spshu2="0", spshu3="0", spshu4="0", spshu5="0";
	String coopid = "";
	String sphez = "";

	public void initView() {

		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 绑定 Adapter到控件
		SipAddress.setAdapter(adapter);
		SipAddress.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshi = mItems[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		ArrayAdapter<String> adapterx = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItemx);
		adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spxian.setAdapter(adapterx);
		spxian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// soapService.updateCar(property_va);

				new MyThead().start();
			}
		});
	}

	// String[] mItemc = { "0" };
	String zhuid = "";
	String ploughPosition = "", lowBarSensorPosition = "";
	String spworktype = "", spsen1 = "", spsen4 = "";
	List<Updatecoop> list = new ArrayList<Updatecoop>();
	List<String> listcoopid = new ArrayList<String>();
	List<String> listcoopiname = new ArrayList<String>();

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		listcoopid.clear();
		if (obj.getCode() == SOAP_UTILS.METHOD.GETCAR) {
			if (obj.getObj() != null) {
				Updatefarmcoop udf = (Updatefarmcoop) obj.getObj();
				try {
					Host_ID.setText(udf.getHost_ID());
					Agricultural.setText(udf.getAgricultural());
					phone.setText(udf.getPhone());
					Jijuhao.setText(udf.getJijuhao());
					car_number.setText(udf.getCar_number());
					Lead_qinghao.setText(udf.getLead_qinghao());
					brand.setText(udf.getBrand());
					radius.setText(udf.getRadius());
					model.setText(udf.getModel());
					VIN.setText(udf.getVIN());
					link_length1.setText(udf.getLowBarLength());
					link_length2.setText(udf.getLowBarPloughLength());
					lowBarPloughHeight.setText(udf.getLowBarPloughHeight());
					Machine_spacing.setText(udf.getPloughLength());
					plough_ground_height.setText(udf.getPloughgroundheight());
					ploughPosition = udf.getPloughPosition();
					lowBarSensorPosition = udf.getLowBarSensorPosition();
					zhuid = udf.getId();
					list = udf.getList();
					sphez = udf.getCoopid();
					spsen1 = udf.getSensor1direction();
					spsen4 = udf.getSensor4direction();
					spworktype = udf.getWorktype();
				} catch (Exception e) {
					// TODO: handle exception
				}
				String[] mItemc = new String[list.size()];
				if (list.size() == 0) {
					mItemc[0] = "空";
				} else {
					for (int i = 0; i < list.size(); i++) {
						listcoopid.add(list.get(i).getCoopid());
						mItemc[i] = list.get(i).getCoop();
					}
				}

				hezuoshe(mItemc);

			} else {
				Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
//				 finish();
				// mItemc[0] = "空";
				// hezuoshe();
			}
		} else if (obj.getCode() == SOAP_UTILS.METHOD.updatecar) {
			if (obj.getObj() != null) {
				Toast.makeText(this, obj.getObj().toString(), Toast.LENGTH_LONG).show();
			}
		}

	}

	private void hezuoshe(String[] mItemc) {
		ArrayAdapter<String> adapterc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItemc);
		adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spcoop.setAdapter(adapterc);
		spcoop.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				sphez = listcoopid.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		try {
			if (ploughPosition.equals("1")) {
				mItem1[0] = "1前方";
				mItem1[1] = "0后方";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		spshu1=mItem1[0];
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItem1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshu1 = mItem1[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
try {
	if (lowBarSensorPosition.equals("1")) {
		mItem2[0] = "1右侧";
		mItem2[1] = "0左侧";
	}
} catch (Exception e) {
	// TODO: handle exception
}
		
		spshu2=mItem2[0];
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItem2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter2);
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshu2 = mItem2[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		try {
			if (spsen1.equals("1")) {
				mItem3[0] = "1左右";
				mItem3[1] = "0前后";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		spshu3=mItem3[0];
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItem3);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_sensor1_direction.setAdapter(adapter3);
		sp_sensor1_direction.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshu3 = mItem3[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		try {
			if (spsen4.equals("1")) {
				mItem4[0] = "1左右";
				mItem4[1] = "0前后";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		spshu4=mItem4[0];
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItem4);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_sensor4_direction.setAdapter(adapter4);
		sp_sensor4_direction.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshu4 = mItem4[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		try {
			if (spworktype != null) {
				String ss = mItem5[0];
				mItem5[0] = mItem5[Integer.parseInt(spworktype)];
				mItem5[Integer.parseInt(spworktype)] = ss;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		spshu5=mItem5[0];
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItem5);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_work_type.setAdapter(adapter5);
		sp_work_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				spshu5 = mItem5[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	};

	class MyThead extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			String[] property_va = {Host_ID.getText().toString(), Host_ID.getText().toString(), Jijuhao.getText().toString(),
					car_number.getText().toString(), VIN.getText().toString(), Lead_qinghao.getText().toString(),
					brand.getText().toString(), model.getText().toString(), radius.getText().toString(),
					Agricultural.getText().toString(), phone.getText().toString(), link_length1.getText().toString(),
					link_length2.getText().toString(), 
					lowBarPloughHeight.getText().toString(), 
					spshu1.substring(0, 1),
					spshu2.substring(0, 1), Machine_spacing.getText().toString(), sphez, spshu5.substring(0, 1),
					spshu4.substring(0, 1), spshu3.substring(0, 1), plough_ground_height.getText().toString() };
			String[] property_nm = { "id","carId", "toolId", "plateNumber", "flameNumber", "engineNumber", "brand", "model",
					"wheelRadius", "owner", "ownerPhone", "lowBarLength", "lowBarPloughLength", "lowBarPloughHeight",
					"ploughPosition", "lowBarSensorPosition", "ploughLength", "officeId", "workType",
					"sensor4Direction", "sensor1Direction", "ploughGroundHeight" };

			String url = "http://123.57.72.71/api/app/updateCar";
			NewHttpClient httpClient = new NewHttpClient();

			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);

			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("User-Agent", "imgfornote");

			Map<String, String> params = new HashMap<String, String>();
			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i < property_va.length; i++) {
				try {
					jsonObject.put(property_nm[i], property_va[i]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params.put(property_nm[i], property_va[i].toString());
			}
			try {
				httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String response = httpClient.post(url, params);

			String responseFromServer = response.toString();
			Message msg = new Message();

			msg.obj = responseFromServer;
			mThirdHandler.sendMessage(msg);
			// Toast.makeText(AddFarmMachiney.this, responseFromServer,
			// Toast.LENGTH_LONG).show();

		}
	}

	Handler mThirdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj.toString().substring(1, 3).equals("ok")) {
				Toast.makeText(AddFarmMachiney.this, " 提交成功", Toast.LENGTH_LONG).show();
				Intent in = new Intent();
				in.putExtra("carid", Host_ID.getText().toString());
				in.setClass(AddFarmMachiney.this, UploadPictureActivityOne.class);
				startActivity(in);
			}else {
				Toast.makeText(AddFarmMachiney.this, " 提交失败", Toast.LENGTH_LONG).show();
			}
		};
	};
}
