package io.jchat.android.newactivity;

import java.text.MessageFormat.Field;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.update.PgyUpdateManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import io.jchat.android.dialog.CustomDialog;
import io.jchat.android.entity.Updatefarmcoop;

public class CooperativeActivity1 extends FragmentActivity {

	private ActionBarDrawerToggle drawerbar;
	private static Context context;

	public static DrawerLayout drawerLayout;
	private LinearLayout lognout, chpwassword, servicephone, eorrh, settingarea;

	private MineFragment testFragment;
	private TextView text, username,versionName;
	private static RelativeLayout left_menu_layout, right_xiaoxi_layout;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_frame_activity);
		context = this;
		Updatefarmcoop.setContext(context);
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		initView();
		
		initEvent();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
	}
	
	public void initView() {

		testFragment = new MineFragment();

		FragmentManager fragmentManager = getSupportFragmentManager();

		FragmentTransaction f_transaction = fragmentManager.beginTransaction();

		f_transaction.replace(R.id.main_content_frame_parent, testFragment);

		f_transaction.commitAllowingStateLoss();

		initLeftLayout();

		initRightLayout();

	}

	public void initLeftLayout() {

		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

		// 设置透明

		drawerLayout.setScrimColor(0xbb000000);

		// 左边菜单

		left_menu_layout = (RelativeLayout) findViewById(R.id.main_left_drawer_layout);

		View view2 = getLayoutInflater().inflate(R.layout.menu_layout, null);

		versionName=(TextView) view2.findViewById(R.id.versionName);
		username = (TextView) view2.findViewById(R.id.username);
		lognout = (LinearLayout) view2.findViewById(R.id.lognout);
		chpwassword = (LinearLayout) view2.findViewById(R.id.chpwassword);
		servicephone = (LinearLayout) view2.findViewById(R.id.servicephone);
		eorrh = (LinearLayout) view2.findViewById(R.id.erroh);
		settingarea = (LinearLayout) view2.findViewById(R.id.settingarea);
		settingarea.setOnClickListener(liner);
		chpwassword.setOnClickListener(liner);
		lognout.setOnClickListener(liner);
		servicephone.setOnClickListener(liner);
		eorrh.setOnClickListener(liner);

		left_menu_layout.addView(view2);
		WindowManager wm1 = this.getWindowManager(); // 获取屏幕款高
		int width1 = wm1.getDefaultDisplay().getWidth();
		int height1 = wm1.getDefaultDisplay().getHeight();
		android.view.ViewGroup.LayoutParams para;
		para = left_menu_layout.getLayoutParams();// 获取按钮的布局
		para.width = width1 - 251;// 修改宽度
		para.height = height1;// 修改高度
		left_menu_layout.setLayoutParams(para); // 设置修改后的布局。

		username.setText(sp.getString("name", ""));
		versionName.setText("V"+getVersionName(context));
	}

	public void initRightLayout() {

		// 左边菜单
		//
		// right_xiaoxi_layout = (RelativeLayout)
		// findViewById(R.id.main_right_drawer_layout);
		//
		// View view = getLayoutInflater().inflate(R.layout.xiaoxi_layout,
		// null);
		//
		// text = (TextView) view.findViewById(R.id.text);
		//
		// text.setText("右边测试菜单");
		//
		// right_xiaoxi_layout.addView(view);

	}

	private void initEvent() {

		drawerbar = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher, R.string.open,
				R.string.close) {

			// 菜单打开

			@Override

			public void onDrawerOpened(View drawerView) {

				super.onDrawerOpened(drawerView);

			}

			// 菜单关闭

			@Override

			public void onDrawerClosed(View drawerView) {

				super.onDrawerClosed(drawerView);

			}

		};

		drawerLayout.setDrawerListener(drawerbar);

	}

	// 左边菜单开关事件

	public static void openLeftLayout() {

		if (drawerLayout.isDrawerOpen(left_menu_layout)) {

			drawerLayout.closeDrawer(left_menu_layout);
			setDrawerLeftEdgeSize( drawerLayout, 0.0f);

		} else {

			drawerLayout.openDrawer(left_menu_layout);
			setDrawerLeftEdgeSize( drawerLayout, 0.3f);
		}

	}

	// 右边菜单开关事件

	public void openRightLayout() {

		if (drawerLayout.isDrawerOpen(right_xiaoxi_layout)) {

			drawerLayout.closeDrawer(right_xiaoxi_layout);

		} else {

			drawerLayout.openDrawer(right_xiaoxi_layout);

		}

	}

	OnClickListener liner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.lognout:
				message = "是否退出登录？";
				diaiogtype = 1;
				diaiogs();
				break;
			case R.id.chpwassword:
				Intent in = new Intent();
				in.setClass(context, ChangePasswordActivity.class);
				startActivity(in);
				break;
			case R.id.erroh:
				Intent ine = new Intent();
				ine.setClass(context, EorrNoticeActivity.class);
				startActivity(ine);
				break;
			case R.id.settingarea:
				 Intent inte=new Intent();
				 inte.setClass(context, SettingWorkAreaActivity.class);
				 startActivity(inte);
				break;
			case R.id.servicephone:
				message = "024   83761061";
				diaiogtype = 2;
				diaiogs();
				break;
			default:
				break;
			}
		}
	};
	String message = "";
	int diaiogtype = 0;

	public void diaiogs() {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(message);
		builder.setTitle("");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				if (diaiogtype == 1) {
					final Intent intent = new Intent();
					SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.clear();
					editor.commit();
					intent.setClass(context, SignActivity.class);
					startActivity(intent);
					CooperativeActivity1.this.finish();
				} else {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02483761061"));
					startActivity(intent);
				}
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	public static void setDrawerLeftEdgeSize( DrawerLayout drawerLayout,
			float displayWidthPercentage) {
		if (context == null || drawerLayout == null)
			return;
		try {
			// find ViewDragHelper and set it accessible
			java.lang.reflect.Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
			leftDraggerField.setAccessible(true);
			ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
			// find edgesize and set is accessible
			java.lang.reflect.Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
			edgeSizeField.setAccessible(true);
			int edgeSize = edgeSizeField.getInt(leftDragger);
			// set new edgesize
			// Point displaySize = new Point();
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
			edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
		} catch (NoSuchFieldException e) {
			// ignore
		} catch (IllegalArgumentException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		}
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
}
