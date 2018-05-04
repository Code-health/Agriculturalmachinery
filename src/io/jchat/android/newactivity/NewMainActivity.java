package io.jchat.android.newactivity;

import java.util.Stack;

import com.lnpdit.agriculturalmachinery.R;
import com.pgyersdk.update.PgyUpdateManager;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.base.BaseActivity;

public class NewMainActivity extends BaseActivity {
	private FragmentManager fm;
	private FragmentTransaction ft;
	private CooperativeActivity ftt;
	private EorrNoticeActivity ena;
	private MineSelfActivity msa;
	private FindnoticeFragment fnf;
	private LinearLayout tv1, tv2, tv3, tv4;
	private ImageView im1, im2, im3, im4;
	private TextView tex1, tex2, tex3, tex4;
	private static Context context;
	static String cardid = "";
	String carId = "";
	static String name = "";
	private PushAgent mPushAgent;

	public static String getCardid() {
		return cardid;
	}

	public static String getname() {
		return name;
	}

	public static Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newmain);
		context = this;

		tv1 = (LinearLayout) findViewById(R.id.one);
		tv2 = (LinearLayout) findViewById(R.id.two);
		tv3 = (LinearLayout) findViewById(R.id.three);
		tv4 = (LinearLayout) findViewById(R.id.four);
		im1 = (ImageView) findViewById(R.id.one1);
		tex1 = (TextView) findViewById(R.id.one2);
		im2 = (ImageView) findViewById(R.id.two1);
		tex2 = (TextView) findViewById(R.id.two2);
		im3 = (ImageView) findViewById(R.id.three1);
		tex3 = (TextView) findViewById(R.id.three2);
		im4 = (ImageView) findViewById(R.id.four1);
		tex4 = (TextView) findViewById(R.id.four2);

		tv1.setOnClickListener(liner);
		tv2.setOnClickListener(liner);
		tv3.setOnClickListener(liner);
		tv4.setOnClickListener(liner);

		PgyUpdateManager.register(this);// 注册蒲公英,已选择Application注册
		
//		Intent intent = getIntent();
//		cardid = intent.getStringExtra("cardid");
//		name = intent.getStringExtra("name");
//
//		mPushAgent = PushAgent.getInstance(context);
//		mPushAgent.onAppStart();
//		// PushAgent.setAlias("zhangsan@sina.com", ALIAS_TYPE.SINA_WEIBO);
//		addTag();//添加tag
//		addAlias();//所有登录账号的手机都能获得推送
////		AddExclusiveAlias();//只有最后登录的手机能获得
//		fm = getFragmentManager();
//		ft = fm.beginTransaction();
//		// 开始添加
//		ftt = new CooperativeActivity();
//		ft.add(R.id.frame, ftt);
//		// 添加fragment
//		ft.commit();

		Intent ine=new Intent("io.jchat.android.http.Waringservice");
		startService(ine);
	}

	public Handler handler = new Handler();

	private void addTag() {
		String tag = cardid;

		mPushAgent.getTagManager().add(new TagManager.TCallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						// edalias.setText("");
						if (isSuccess) {
//							Toast.makeText(context, result + "", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(context, "加入tag失败", Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		}, tag);
	}

	 private void AddExclusiveAlias() {
	        String exclusiveAlias = cardid;
	        String aliasType = cardid;
	        mPushAgent.addExclusiveAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
	            @Override
	            public void onMessage(boolean isSuccess, String message) {
//	                Log.i(TAG, "isSuccess:" + isSuccess + "," + message);
	                if (Boolean.TRUE.equals(isSuccess)){
//	                    Log.i(TAG, "exclusive alias was set successfully.");

	                final boolean success = isSuccess;
	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {
//	                        edalias.setText("");
//	                        updatelog("Add Exclusive Alias:" + (success ? "Success" : "Fail"));
	                    }
	                });
	                }
	            }
	        });
	    }

	private void addAlias() {
		String alias = cardid;
		String aliasType = cardid;
		mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
			@Override
			public void onMessage(boolean isSuccess, String message) {
				// Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				if (isSuccess) {
					// Toast.makeText(context, "alias was set successfully.",
					// Toast.LENGTH_LONG).show();
					final boolean success = isSuccess;
					handler.post(new Runnable() {
						@Override
						public void run() {
							// Toast.makeText(context, "Add Alias:" + (success ?
							// "Success" : "Fail"), Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		});
	}

	View.OnClickListener liner = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.id.one:
//				tubiao(1);
//				ft = fm.beginTransaction();
//				ftt = new CooperativeActivity();
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);// 动画效果
//				ft.replace(R.id.frame, ftt);
//				// 添加fragment
//				ft.commit();
//				break;
//			case R.id.two:
//				tubiao(2);
//				ft = fm.beginTransaction();
//				ena = new EorrNoticeActivity();
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);// 动画效果
//				ft.replace(R.id.frame, ena);
//				// 添加fragment
//				ft.commit();
//				break;
//			case R.id.three:
//				tubiao(3);
//				ft = fm.beginTransaction();
//				fnf = new FindnoticeFragment();
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);// 动画效果
//				ft.replace(R.id.frame, fnf);
//				// 添加fragment
//				ft.commit();
//				break;
//			case R.id.four:
//				tubiao(4);
//				ft = fm.beginTransaction();
//				msa = new MineSelfActivity();
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);// 动画效果
//				ft.replace(R.id.frame, msa);
//				// 添加fragment
//				ft.commit();
//				break;
//			default:
//				break;
//			}
		}
	};

	public void tubiao(int i) {
		if (i == 1) {
			im1.setImageResource(R.drawable.tab_icon_1_n);
			im2.setImageResource(R.drawable.tab_icon_4_u);
			im3.setImageResource(R.drawable.tab_icon_3_u);
			im4.setImageResource(R.drawable.settph);
			tex1.setTextColor(0xffd84528);
			tex2.setTextColor(0xffffffff);
			tex3.setTextColor(0xffffffff);
			tex4.setTextColor(0xffffffff);
		} else if (i == 2) {
			im1.setImageResource(R.drawable.tab_icon_1_u);
			im2.setImageResource(R.drawable.tab_icon_4_n);
			im3.setImageResource(R.drawable.tab_icon_3_u);
			im4.setImageResource(R.drawable.settph);
			tex1.setTextColor(0xffffffff);
			tex2.setTextColor(0xffd84528);
			tex3.setTextColor(0xffffffff);
			tex4.setTextColor(0xffffffff);
		} else if (i == 3) {
			im1.setImageResource(R.drawable.tab_icon_1_u);
			im2.setImageResource(R.drawable.tab_icon_4_u);
			im3.setImageResource(R.drawable.tab_icon_3_n);
			im4.setImageResource(R.drawable.settph);
			tex1.setTextColor(0xffffffff);
			tex2.setTextColor(0xffffffff);
			tex3.setTextColor(0xffd84528);
			tex4.setTextColor(0xffffffff);
		} else if (i == 4) {
			im1.setImageResource(R.drawable.tab_icon_1_u);
			im2.setImageResource(R.drawable.tab_icon_4_u);
			im3.setImageResource(R.drawable.tab_icon_3_u);
			im4.setImageResource(R.drawable.settb);
			tex1.setTextColor(0xffffffff);
			tex2.setTextColor(0xffffffff);
			tex3.setTextColor(0xffffffff);
			tex4.setTextColor(0xffd84528);
		}
	}

}
