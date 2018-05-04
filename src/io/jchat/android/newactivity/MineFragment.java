package io.jchat.android.newactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.dialog.CustomDialog3;
import io.jchat.android.entity.Cooperatives;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.HttpGetService;

public class MineFragment extends Fragment implements OnClickListener {

	private ImageView mune;
	Dialog dialog, dialogs;
	private Context content;
	private String officeid, usertype;
	private RelativeLayout[] work = new RelativeLayout[6];
	private RelativeLayout rlayout;
	private View[] wv = new View[6];
	private TextView[] wt = new TextView[6];
	JSONArray jas = new JSONArray();
	private TextView area, yesdayarea, worklin, num, tv_title, area_mu_z, sett;
	private TextView njnum, mubiao, mubiao1;
	private RelativeLayout re_weizhi, dw_re;
	private ImageView dinwei, iv_lic;
	private ProgressBar myProgressBar;
	private LinearLayout f_lin;
	private View view1;
	private View viewS;
	int worktype = -1;
	String carid = "";
	String date = "";
	int width1 = 0;
	int count = 0;
	Paint paint = new Paint();
	String name;
	View view;
	RadioGroup pgroup;
	HorizontalScrollView horsview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);

		mune = (ImageView) view.findViewById(R.id.mune);
		area = (TextView) view.findViewById(R.id.area);
		yesdayarea = (TextView) view.findViewById(R.id.yesdayarea);
		worklin = (TextView) view.findViewById(R.id.worklin);
		num = (TextView) view.findViewById(R.id.num);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		dinwei = (ImageView) view.findViewById(R.id.dinwei);
		area_mu_z = (TextView) view.findViewById(R.id.area_mu_z);
		sett = (TextView) view.findViewById(R.id.sett);
		viewS = view.findViewById(R.id.view);
		view1 = view.findViewById(R.id.view1);
		f_lin = (LinearLayout) view.findViewById(R.id.f_lin);
		re_weizhi = (RelativeLayout) view.findViewById(R.id.re_weizhi);
		mubiao = (TextView) view.findViewById(R.id.mubiao);
		mubiao1 = (TextView) view.findViewById(R.id.mubiao1);
		iv_lic = (ImageView) view.findViewById(R.id.iv_lic);
		dw_re = (RelativeLayout) view.findViewById(R.id.dw_re);

		sett.setOnClickListener(this);
		work[0] = (RelativeLayout) view.findViewById(R.id.work5);
		work[1] = (RelativeLayout) view.findViewById(R.id.work6);
		work[2] = (RelativeLayout) view.findViewById(R.id.work7);
		work[3] = (RelativeLayout) view.findViewById(R.id.work8);
		work[4] = (RelativeLayout) view.findViewById(R.id.work9);
		work[5] = (RelativeLayout) view.findViewById(R.id.worka);
		wv[0] = view.findViewById(R.id.workv5);
		wv[1] = view.findViewById(R.id.workv6);
		wv[2] = view.findViewById(R.id.workv7);
		wv[3] = view.findViewById(R.id.workv8);
		wv[4] = view.findViewById(R.id.workv9);
		wv[5] = view.findViewById(R.id.workva);
		wt[0] = (TextView) view.findViewById(R.id.work_tv5);
		wt[1] = (TextView) view.findViewById(R.id.work_tv6);
		wt[2] = (TextView) view.findViewById(R.id.work_tv7);
		wt[3] = (TextView) view.findViewById(R.id.work_tv8);
		wt[4] = (TextView) view.findViewById(R.id.work_tv9);
		wt[5] = (TextView) view.findViewById(R.id.work_tva);
		myProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		pgroup = (RadioGroup) view.findViewById(R.id.rgroup);
		horsview = (HorizontalScrollView) view.findViewById(R.id.horsview);

		tv_title.setOnClickListener(this);
		work[0].setOnClickListener(this);
		work[1].setOnClickListener(this);
		work[2].setOnClickListener(this);
		work[3].setOnClickListener(this);
		work[4].setOnClickListener(this);
		work[5].setOnClickListener(this);
		area.setOnClickListener(this);
		iv_lic.setOnClickListener(this);
		yesdayarea.setOnClickListener(this);
		worklin.setOnClickListener(this);
		num.setOnClickListener(this);
		mune.setOnClickListener(this);

		// pgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		initView();

		// new MyTheads().start();
		SharedPreferences spfs = content.getSharedPreferences("fristlogin", Context.MODE_PRIVATE);
		if (null == spfs.getString("login", null) || spfs.getString("login", null).equals("0"))

		{
			diaiogs();
			Editor editorf = spfs.edit();
			editorf.putString("login", "1");
			editorf.commit();
		}
		return view;
	}

	float strwidth = 0;
	int psdd1 = 0;
	int vieww1 = 0;
	int pss1 = 0;
	SharedPreferences sp;

	public void initView() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = sDateFormat.format(new java.util.Date());
		content = Updatefarmcoop.getContext();

		sp = content.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		usertype = sp.getString("userType", "");
		String loginName = sp.getString("loginName", "");
		// if (sp.getString("photo", "") == null || sp.getString("photo",
		// "").equals("")) {
		// mubiaoarea = 0;
		// } else {
		// mubiaoarea = Integer.parseInt(sp.getString("photo", ""));
		// }
		// mubiao.setText("" + mubiaoarea);
		// mubiao1.setText("" + mubiaoarea);
		name = sp.getString("name", "");
		if (usertype.equals("4")) {
			carid = sp.getString("cardid", "");
			officeid = carid;
		} else {
			officeid = sp.getString("officeid", "");
		}

		WindowManager wm1 = ((Activity) content).getWindowManager(); // 获取屏幕款高
		width1 = wm1.getDefaultDisplay().getWidth();
		int height1 = wm1.getDefaultDisplay().getHeight();
		android.view.ViewGroup.LayoutParams para;
		for (int i = 0; i < work.length; i++) {
			para = work[i].getLayoutParams();// 获取按钮的布局
			para.width = width1 / 4;// 修改宽度
			// para.height = 65;// 修改高度 注：此处单位不能按dp计算
			work[i].setLayoutParams(para); // 设置修改后的布局。
		}

		dialog = new Dialog(content, R.style.dialogss);
		dialog.show();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new MyTead().start();
		areaMeassage();

	}

	public void areaMeassage() {
		try {

			SharedPreferences spfs = content.getSharedPreferences("loginmessage", Context.MODE_PRIVATE);
			String photoArea = "0";
			if (worktype == 0) {
				photoArea = spfs.getString("photoone", "");
			} else if (worktype == 1) {
				photoArea = spfs.getString("phototwo", "");
			} else if (worktype == 2) {
				photoArea = spfs.getString("photothree", "");
			} else if (worktype == 3) {
				photoArea = spfs.getString("photofour", "");
			} else if (worktype == 4) {
				photoArea = spfs.getString("photofive", "");
			} else if (worktype == 5) {
				photoArea = spfs.getString("photosex", "");
			}
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i + "").equals(1 + "") && worktype == i) {
					if (photoArea.equals("0")) {
						area.setVisibility(View.GONE);
						dinwei.setVisibility(View.GONE);
						myProgressBar.setProgress(100);
						area_mu_z.setText("总作业面积");
						area_mu_z.setVisibility(View.VISIBLE);
						mubiao1.setText("" + shijiareas);
						mubiao.setVisibility(View.GONE);
						sett.setVisibility(View.VISIBLE);
					} else {
						area_mu_z.setVisibility(View.GONE);
						area.setVisibility(View.VISIBLE);
						dinwei.setVisibility(View.VISIBLE);
						area_mu_z.setText("目标作业面积");
						mubiao.setVisibility(View.VISIBLE);
						sett.setVisibility(View.GONE);
						if (photoArea.length() >= 8) {
							photoArea = photoArea.substring(0, 7);
						}
						mubiaoarea = Integer.parseInt(photoArea);
						new MyTheads().start();
						mubiao.setText("" + mubiaoarea + "亩");
						mubiao1.setText("" + mubiaoarea);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mune:
			CooperativeActivity1.openLeftLayout();
			break;
		case R.id.work5:
			worktype = 0;
			topTitle();
			break;
		case R.id.work6:
			worktype = 1;
			topTitle();
			break;
		case R.id.work7:
			worktype = 2;
			topTitle();
			break;
		case R.id.work8:
			worktype = 3;
			topTitle();
			break;
		case R.id.work9:
			worktype = 4;
			topTitle();
			break;
		case R.id.worka:
			worktype = 5;
			topTitle();
			break;
		case R.id.iv_lic:
		case R.id.area:
		case R.id.num:
			if (usertype.equals("4")) {//4是普通用户
				// if (count >= 2) {
				if(worktype == 5){
					Intent in = new Intent();
					in.putExtra("carid", carid);
					in.putExtra("type", worktype + "");
					in.putExtra("usertype", "4");
					in.putExtra("name", name);
					in.setClass(content, NumOperFarmAdditionalActivity.class);
					startActivity(in);
				}else{
					Intent in = new Intent();
					in.putExtra("carid", carid);
					in.putExtra("type", worktype + "");
					in.putExtra("usertype", "4");
					in.putExtra("name", name);
					in.setClass(content, NumOperFarmActivity.class);
					startActivity(in);
				}
				
			} else if (usertype.equals("3")) {
				if(worktype == 5){
					Intent in = new Intent();
					in.putExtra("carid", officeid);
					in.putExtra("type", worktype + "");
					in.putExtra("name", name);
					in.putExtra("usertype", "3");
					in.setClass(content, NumOperFarmAdditionalActivity.class);
					startActivity(in);
				}else{
					Intent in = new Intent();
					in.putExtra("carid", officeid);
					in.putExtra("type", worktype + "");
					in.putExtra("name", name);
					in.putExtra("usertype", "3");
					in.setClass(content, NumOperFarmActivity.class);
					startActivity(in);
				}
				
			} else if (usertype.equals("2")) {
				Intent in = new Intent();
				in.putExtra("officeid", officeid);
				in.putExtra("type", worktype + "");
				in.putExtra("name", name);
				in.setClass(content, TownshipActivity.class);
				startActivity(in);
			} else if (usertype.equals("1")) {
				Intent in = new Intent();
				in.putExtra("officeid", officeid);
				in.putExtra("type", worktype + "");
				in.putExtra("name", name);
				in.setClass(content, CountyActivity.class);
				startActivity(in);
			}
			break;
		case R.id.sett:
			Intent inte = new Intent();
			inte.putExtra("type", worktype + "");
			inte.setClass(content, SettingAreaActivity.class);
			startActivity(inte);
			break;
		default:
			break;
		}
	}

	public void topTitle() {
		for (int i = 0; i < work.length; i++) {
			if (i == worktype) {
				wt[i].setTextColor(0xff108bf9);
				wv[i].setBackgroundColor(0xff108bf9);
				rlayout = work[i];
			} else {
				wt[i].setTextColor(0xffaaafc3);
				wv[i].setBackgroundColor(0x00777777);
			}
		}
		// Display d = getActivity().getWindowManager().getDefaultDisplay();
		// DisplayMetrics dm = new DisplayMetrics();
		// d.getMetrics(dm);d.getWidth();
		final int screenHalf = width1 / 5;// 屏幕宽度的
		int scrollX = horsview.getScrollX();
		System.out.println("scrollX----->" + scrollX);
		// RadioButton rb = (RadioButton) findViewById(checkedId);
		int left = rlayout.getLeft();
		int leftScreen = left - scrollX;
		horsview.scrollTo((leftScreen - screenHalf), 0);

		onlyWork(worktype + "");
	}

	class MyTead extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			String url1 = officeid + "/" + usertype + "/v3/workInfoList";

			Message msg = new Message();
			msg.arg1 = 0;
			// msg.obj = responseFromServer;
			msg.obj = HttpGetService.data(url1, "");
			mThirdHandler.sendMessage(msg);
		}
	}

	class MyTheads extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			for (;;) {
				if (shijiarea > 0) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg = new Message();
					msg.arg1 = 1;
					mThirdHandler.sendMessage(msg);

					break;
				}
			}

		}
	}

	int sja = 0;
	int PSS = 0;
	float ONE1 = 0.00f;
	float TWO1 = 0.00f;
	float THREE1 = 0.00f;
	float FOUR1 = 0.00f;

	class MyTheadChange extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			float num = 1.00f;
			for (int i = 1; i < 11; i++) {
				Message msg = new Message();
				msg.arg1 = 2;
				mThirdHandler.sendMessage(msg);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler mThirdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == 0) {
				if (msg.obj != null) {
					try {
						jas = new JSONArray(msg.obj.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listitme();
				} else {
					dialog.dismiss();
					Toast.makeText(content, "网络请求失败", Toast.LENGTH_SHORT).show();
				}
			} else if (msg.arg1 == 1) {
				try {

					// android.view.ViewGroup.LayoutParams para;
					// android.view.ViewGroup.LayoutParams para1;
					// imageOriginalWidth = width1 - 130;
					// int views1=(int) (imageOriginalWidth -strwidth-10);
					// int vieww = imageOriginalWidth-10;
					int pss = 100;
					float one = 0.04f;
					float two = 0.96f;
					float three = 0.50f;
					float four = 0.50f;
					if (shijiarea < mubiaoarea) {
						if (shijiarea == 0) {
							pss = 1;
							one = 1.00f;
							two = 0.00f;
							three = 1.00f;
							four = 0.00f;
						} else {

							float por = (shijiarea * 100 / mubiaoarea);
							pss = (shijiarea * 100 / mubiaoarea) + 1;

							four = two = por / 100;
							if (por < 1) {
								four = 0.00f;
								three = 1.00f;
								two = 0.00f;
								one = 1.00f;
							} else {
								if (four > 0.80f) {
									three = 0.50f;
									four = 0.50f;
								} else if (four < 0.40f) {
									three = 1.00f;
									four = 0.00f;
								} else {
									three = 1 - four + 0.4f;
									four = four - 0.4f;
								}
								if (two > 0.96f) {
									one = 0.04f;
									two = 0.96f;
								} else if (two < 0.05f) {
									one = 1 - two - 0.01f;
									two = two + 0.01f;
								} else {
									pss = (int) (two * 100);
									one = 1 - two + 0.02f;
									two = two - 0.02f;
								}
							}
						}
					}
					// new MyTheadChange().start();
					// 图标view
					viewS.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, one));
					// 图标
					dw_re.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, two));
					// 面积view
					view1.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, three));
					// 面积
					area.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, four));
					// para = viewS.getLayoutParams();// 获取按钮的布局
					// para.width = vieww;// 修改宽度
					// para.height = 2;// 修改高度
					// viewS.setLayoutParams(para); // 设置修改后的布局。
					//// para1 = view1.getLayoutParams();// 获取按钮的布局
					//// para1.width = views1;// 修改宽度
					//// para1.height = 2;// 修改高度
					//// viewS.setLayoutParams(para1);
					area.setText("总作业面积：" + shijiarea + "亩");
					myProgressBar.setProgress(pss);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else if (msg.arg1 == 2) {
				try {

					// //图标view
					viewS.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, ONE1));
					// 图标
					dw_re.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, TWO1));
					// 面积view
					view1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT, THREE1));
					// 面积
					area.setLayoutParams(
							new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, FOUR1));
					myProgressBar.setProgress(PSS);
					area.setText("总作业面积：" + sja + "亩");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	};

	public void ppp() {
	}

	int mubiaoarea = 0;
	int shijiarea = 0;
	String shijiareas = "";
	int imageOriginalHeight = 0;
	int imageOriginalWidth = 0;

	Map<String, String> map = new HashMap<String, String>();
	List<JSONObject> listwork = new ArrayList<JSONObject>();
	String type1 = "";
	int starts = 0;

	public void listitme() {
		dialog.dismiss();
		try {
			for (int i = 0; i < jas.length(); i++) {
				JSONObject jb = (JSONObject) jas.get(i);

				if (jb.getString("result").equals("true")) {
					if (type1.equals("")) {
						type1 = jb.getString("type");
					}
					map.put(jb.getString("type"), 1 + "");
					listwork.add(jb);
				} else {
					// if
					// (jb.getString("type").equals("2")||jb.getString("type").equals("5")||jb.getString("type").equals("3")||jb.getString("type").equals("1")||jb.getString("type").equals("0"))
					// {
					// map.put(jb.getString("type"), 1 + "");
					// listwork.add(jb);
					// } else {
					map.put(jb.getString("type"), 0 + "");
					// }
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (worktype > -1) {
			type1 = worktype + "";
		}

		if (starts == 0) {
			workAllType = "";
			pageWorknum();
			starts = 1;
		}
		onlyWork(type1);
	}

	public void onlyWork(String type1) {
		Cooperatives cp = new Cooperatives();
		try {
			for (int i = 0; i < listwork.size(); i++) {
				JSONObject jb = listwork.get(i);
				if (jb.getString("type").equals(type1)) {
					try {
						cp.setTotalArea(jb.getString("totalArea"));
						cp.setTotalDistance(jb.getString("totalDistance"));
						cp.setYesterdayArea(jb.getString("yesterdayArea"));
						cp.setCount(jb.getString("count"));

					} catch (Exception e) {
						// TODO: handle exception
						cp.setTotalArea("");
						cp.setTotalDistance("");
						cp.setYesterdayArea("");
						cp.setCount("");
					}
					events(cp);
					break;
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void events(Cooperatives cp) {
		try {
			String cc = cp.getTotalArea(), cc1 = "";
			int bb;
			if (cp.getTotalArea() == null) {
				area.setText("总作业面积：" + 0.00 + "亩");
			} else {
				bb = cc.indexOf(".");
				if (bb > 0) {
					cc = cc.substring(0, bb);
					cc1 = cc;

				}
				shijiareas = cc1;
				area.setText("总作业面积：" + 0 + "亩");
			}
			// strwidth = paint.measureText(cc);

			shijiarea = Integer.parseInt(cc);
			// shijiarea = 2000;
			// new MyTheads().start();
			areaMeassage();

			if (cp.getYesterdayArea() == null) {
				yesdayarea.setText(0.00 + "亩");
			} else {
				cc = cp.getYesterdayArea();
				bb = cc.indexOf(".");
				if (bb > 0) {
					cc = cc.substring(0, bb);
				}
				yesdayarea.setText(cc + "亩");
			}

			if (cp.getTotalDistance() == null) {
				worklin.setText(0 + "公里");
			} else {
				cc = cp.getTotalDistance();
				bb = cc.indexOf(".");
				if (bb + 3 < cc.length()) {
					cc = cc.substring(0, bb + 3);
				}
				worklin.setText(cc + " 公里");
			}
			num.setText(cp.getCount() + " 台");
			if (cp.getCount() == null) {
				num.setText("0  台");
				count = 0;
			} else {
				count = Integer.parseInt(cp.getCount());
			}

			String miji1 = "";

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	static String workAllType = "";

	public void pageWorknum() {
		int nums = 0;
		int num = 0;
		for (int i = 0; i < map.size(); i++) {
			workAllType += map.get(i + "");
			if (map.get(i + "").equals(1 + "")) {

				if (num == 0) {
					if (worktype == -1) {
						worktype = i;
					}
					wt[i].setTextColor(0xff108bf9);
					wv[i].setBackgroundColor(0xff108bf9);
					num++;
				}
				nums = i;
			} else {
				work[i].setVisibility(View.GONE);
			}
		}
	}

	public void diaiogs() {
		CustomDialog3.Builder builder = new CustomDialog3.Builder(content);
		builder.setMessage("");
		builder.setTitle("");
		builder.setPositiveButton("", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

}
