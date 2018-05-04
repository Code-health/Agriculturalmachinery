package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import io.jchat.android.activity.LoginActivity;
import io.jchat.android.dialog.CustomDialog;
import io.jchat.android.entity.Updatefarmcoop;

@SuppressLint("NewApi")
public class MineSelfActivity extends Activity {

	private ImageView bank, change;
	private TextView text_phone, names, address, carid,goout,build,buildname;
	private RelativeLayout farmcarinfor, togethinfor, ceshi, notice;
	private Context context;
	private View view1;
	String cardid;
	String name;
	String coo;
	String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		cardid = getIntent().getStringExtra("carid");
		name = getIntent().getStringExtra("name");
		coo = getIntent().getStringExtra("coo");
		type=getIntent().getStringExtra("type");
		
		setContentView(R.layout.activity_mineself);
		build=(TextView) findViewById(R.id.build);
		buildname=(TextView) findViewById(R.id.buildname);
		bank = (ImageView) findViewById(R.id.bank);
		text_phone = (TextView) findViewById(R.id.text_2);
		goout = (TextView) findViewById(R.id.gouout);
		farmcarinfor = (RelativeLayout) findViewById(R.id.farmcarinfor);
		togethinfor = (RelativeLayout) findViewById(R.id.togethinfor);
		ceshi = (RelativeLayout) findViewById(R.id.xinxiceshi);
		change = (ImageView) findViewById(R.id.change);
		names = (TextView) findViewById(R.id.name);
		address = (TextView) findViewById(R.id.address);
		carid = (TextView) findViewById(R.id.carid);
		notice = (RelativeLayout) findViewById(R.id.not);
		view1=findViewById(R.id.view1);
		String carids=cardid+"1";
		carid.setText(carids.substring(0,cardid.length()));
		address.setText(coo);
		names.setText(name);
		if(type.equals("3")||type.equals("1")){
			notice.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
		}
		PushAgent.getInstance(context).onAppStart();
		initView();
	}

	//版本名
	public static String getVersionName(Context context) {
	    return getPackageInfo(context).versionName;
	}
	 
	//版本号
	public static int getVersionCode(Context context) {
	    return getPackageInfo(context).versionCode;
	}
	 
	private static PackageInfo getPackageInfo(Context context) {
	    PackageInfo pi = null;
	 
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(context.getPackageName(),
	                PackageManager.GET_CONFIGURATIONS);
	 
	        return pi;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	 
	    return pi;
	}

	
	public void initView() {
		build.setText("Build: "+getVersionCode(context));
		buildname.setText("版本:"+getVersionName(context));
		bank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		farmcarinfor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(NewMainActivity.getContext(), AddFarmMachineryActivity.class);
				startActivity(in);
			}
		});

		togethinfor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, AddCooperationActivity.class);
				startActivity(in);
			}
		});

		goout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				diaiogs();
			}
		});

		text_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(Intent.ACTION_CALL,
				 Uri.parse("tel:02483761061"));
				 startActivity(intent);
			}
		});
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent();
				in1.setClass(context, ChangePasswordActivity.class);
				startActivity(in1);
			}
		});
		ceshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent();
				in1.setClass(context, Shujiceshi.class);
				startActivity(in1);
			}
		});
		notice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent();
				in1.setClass(context, EorrNoticeActivity.class);
				startActivity(in1);
			}
		});
	}

	public void diaiogs() {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("是否退出登录？");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				final Intent intent = new Intent();
				SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.clear();
				editor.commit();
				intent.setClass(context, SignActivity.class);
				startActivity(intent);
				if(type.equals("1")){
					Updatefarmcoop.getNongactivity().finish();
				}else {
					Updatefarmcoop.getCoopactivity().finish();
				}
				
				MineSelfActivity.this.finish();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

}
