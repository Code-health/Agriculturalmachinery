package io.jchat.android.newactivity;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.activity.BaseActivity;
import io.jchat.android.adapter.AddrssNameAdapter;
import io.jchat.android.entity.AddressNameItme;
import io.jchat.android.http.SoapRes;
import io.jchat.android.utils.SOAP_UTILS;

public class AddressNameActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private ListView lv_shi, lv_xiang, lv_cun;
	private ImageView bank;
	private TextView go, submit, re_election;
	private TextView tv_one, tv_two, tv_three;
	private EditText di_name;
	private LinearLayout liner_re, lin_lists;
	AddrssNameAdapter addressname, addressname1, addressname2;
	AddressNameItme addressnameitme;
	List<AddressNameItme> listshiname = new ArrayList<AddressNameItme>();
	List<AddressNameItme> listxname = new ArrayList<AddressNameItme>();
	List<AddressNameItme> listcunname = new ArrayList<AddressNameItme>();

	String workSerial = "";
	int pos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addressname);

		workSerial = getIntent().getStringExtra("workSerial");

		lv_shi = (ListView) findViewById(R.id.lv_shi);
		lv_xiang = (ListView) findViewById(R.id.lv_xiang);
		lv_cun = (ListView) findViewById(R.id.lv_cun);
		bank = (ImageView) findViewById(R.id.bank);
		go = (TextView) findViewById(R.id.go);
		di_name = (EditText) findViewById(R.id.di_name);
		liner_re = (LinearLayout) findViewById(R.id.liner_re);
		lin_lists = (LinearLayout) findViewById(R.id.lin_lists);
		submit = (TextView) findViewById(R.id.submit);
		re_election = (TextView) findViewById(R.id.re_election);
		tv_three = (TextView) findViewById(R.id.tv_three);
		tv_one = (TextView) findViewById(R.id.tv_one);
		tv_two = (TextView) findViewById(R.id.tv_two);

		bank.setOnClickListener(this);
		go.setOnClickListener(this);
		re_election.setOnClickListener(this);
		submit.setOnClickListener(this);
		lv_cun.setOnItemClickListener(this);
		lv_shi.setOnItemClickListener(this);
		lv_xiang.setOnItemClickListener(this);

		Object[] property_va = { "1", "" };
		soapService.regionList(property_va);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bank:
			finish();
			break;
		case R.id.go:
			lin_lists.setVisibility(View.GONE);
			liner_re.setVisibility(View.VISIBLE);
			di_name.setVisibility(View.VISIBLE);
			go.setVisibility(View.GONE);
			break;
		case R.id.submit:
			if (tv_one.getText().toString().trim().equals("") || tv_two.getText().toString().trim().equals("")
					|| tv_three.getText().toString().trim().equals("")
					|| di_name.getText().toString().trim().equals("")) {
				Toast.makeText(AddressNameActivity.this, "不能为空地名", Toast.LENGTH_SHORT).show();

			} else {
				Object[] property_va = { workSerial,
						tv_one.getText().toString().trim() + tv_two.getText().toString().trim()
								+ tv_three.getText().toString().trim() + di_name.getText().toString().trim() };
				soapService.update(property_va);
			}
			break;
		case R.id.re_election:
			lin_lists.setVisibility(View.VISIBLE);
			liner_re.setVisibility(View.GONE);
			di_name.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.lv_shi:
			tv_one.setText(listshiname.get(position).getName());
			Object[] property_va = { "2", listshiname.get(position).getId() };
			soapService.regionList(property_va);
			for(int i=0;i<listshiname.size();i++) {
				if(position==i){
					listshiname.get(i).setColor(position);
				}else {
					listshiname.get(i).setColor(-1);
				}
			}
			addressname.notifyDataSetChanged();
			tv_three.setText("");
			tv_two.setText("");
			go.setVisibility(View.GONE);
			break;
		case R.id.lv_xiang:
			tv_two.setText(listxname.get(position).getName());
			Object[] property_va1 = { "3", listxname.get(position).getId() };
			soapService.regionList(property_va1);
			for(int i=0;i<listxname.size();i++) {
				if(position==i){
					listxname.get(i).setColor(position);
				}else {
					listxname.get(i).setColor(-1);
				}
			}
			addressname1.notifyDataSetChanged();
			go.setVisibility(View.GONE);
			tv_three.setText("");
			break;
		case R.id.lv_cun:
			for(int i=0;i<listcunname.size();i++) {
				if(position==i){
					listcunname.get(i).setColor(position);
				}else {
					listcunname.get(i).setColor(-1);
				}
			}
			addressname2.notifyDataSetChanged();
			tv_three.setText(listcunname.get(position).getName());
			go.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onEvent(SoapRes obj) {
		// TODO Auto-generated method stub
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.regionList)) {
			if (obj.getObj() != null) {
				List<AddressNameItme> list = (List<AddressNameItme>) obj.getObj();
				if (list.size() > 0) {
					String type = list.get(0).getType();
					if (type.equals("1")) {
						listshiname.clear();
						listshiname = list;
						addressname = new AddrssNameAdapter(AddressNameActivity.this, listshiname);
						lv_shi.setAdapter(addressname);
						listxname.clear();
						addressname1 = new AddrssNameAdapter(AddressNameActivity.this, listxname);
						lv_xiang.setAdapter(addressname1);
						listcunname.clear();
						addressname2 = new AddrssNameAdapter(AddressNameActivity.this, listcunname);
						lv_cun.setAdapter(addressname2);
					} else if (type.equals("2")) {
						listxname.clear();
						listxname = list;
						addressname1 = new AddrssNameAdapter(AddressNameActivity.this, listxname);
						lv_xiang.setAdapter(addressname1);
						listcunname.clear();
						addressname2 = new AddrssNameAdapter(AddressNameActivity.this, listcunname);
						lv_cun.setAdapter(addressname2);
					} else if (type.equals("3")) {
						listcunname.clear();
						listcunname = list;
						addressname2 = new AddrssNameAdapter(AddressNameActivity.this, listcunname);
						lv_cun.setAdapter(addressname2);
					}
				}
			} else {
				Toast.makeText(AddressNameActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
		} else if (obj.getCode().equals(SOAP_UTILS.METHOD.update)) {
			if (obj.getObj() != null) {
				String oobj = obj.getObj().toString().substring(1, obj.getObj().toString().length() - 1);
				if (oobj.equals("ok")) {
					Toast.makeText(AddressNameActivity.this, "提交完成", Toast.LENGTH_SHORT).show();
//					try {
//						Thread.sleep(200);
						finish();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				} else {
					Toast.makeText(AddressNameActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(AddressNameActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void listViewChange(List<AddressNameItme> list) {

	}
}
