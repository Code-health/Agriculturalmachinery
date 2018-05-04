package io.jchat.android.newactivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EorrNoticeActivity extends Activity {

	private TextView not, not1, noy2, not3;
	private PullToRefreshListView lv;
	private List<String> list = new ArrayList<String>();
	private ListView listView;
	private ImageView bank;
	String userid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eorrnotice);
		lv = (PullToRefreshListView) findViewById(R.id.notice_list);
		not = (TextView) findViewById(R.id.not_zhu);
		bank = (ImageView) findViewById(R.id.bank);
		initView();
		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		userid = sp.getString("id", null);
		new MyTheale1().start();
		// new MyTheale().start();
		PushAgent.getInstance(EorrNoticeActivity.this).onAppStart();
	}

	NoticeAdapter noticeAdapter;

	public void initView() {
		noticeAdapter = new NoticeAdapter(EorrNoticeActivity.this, list);
		lv.setAdapter(noticeAdapter);
		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				not.setText(list.get(position - 1));
			}
		});

		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {//下拉刷新

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				eorrpage = 0;
				new MyTheale1().start();
				// lv.getRefreshableView();
			}
		});

		lv.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {//上啦刷新
			//
			@Override
			public void onLastItemVisible() {

				eorrpage += 1;
				new MyTheale1().start();

			}
		});

		// lv.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// // TODO Auto-generated method stub
		//// lv.onScrollStateChanged(view, scrollState);
		// boolean scrollEnd = false;
		// if (view == null) {
		// return;
		// }
		//
		// try {
		// // 滑动到底部
		// if (view.getPositionForView(lv) == view.getLastVisiblePosition()
		// && scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		// scrollEnd = true;
		//
		// } catch (Exception e) {
		// scrollEnd = false;
		// }
		// if (scrollEnd == true) {
		//
		// eorrpage += 1;
		// new MyTheale1().start();
		//
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem, int
		// visibleItemCount, int totalItemCount) {
		// // TODO Auto-generated method stub
		//// lv.onScroll(view, firstVisibleItem, visibleItemCount,
		// totalItemCount);
		// }
		// });

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.obj != null) {
				if (msg.arg1 == 1) {
					try {
						// Toast.makeText(NewMainActivity.getContext(),
						// msg.obj.toString(), Toast.LENGTH_SHORT).show();
						JSONObject user = new JSONObject(msg.obj.toString());
						time1 = user.getString("time");
						list.add(0, user.getString("time").substring(5, 16) + ":  " + user.getString("alarmContent")
								+ "请尽快检查设备，以免影响作业数据上传,若无法解决请尽快联系安装维护人员。     报警主机：" + user.getString("equipId"));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (msg.arg1 == 2) {
					try {
						if (eorrpage == 0) {
							list.clear();
						}

						// Toast.makeText(NewMainActivity.getContext(),
						// msg.obj.toString(), Toast.LENGTH_SHORT).show();
						// JSONObject user1 = new
						// JSONObject(msg.obj.toString());
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject user = ja.getJSONObject(i);
							if (i == 0) {
								time1 = user.getString("time");
							}
							String ss = user.getString("time").substring(5, 16) + ":  " + user.getString("alarmContent")
									+ "请尽快检查设备，以免影响作业数据上传,若无法解决请尽快联系安装维护人员。     报警主机：" + user.getString("carId");
							list.add(ss);

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				noticeAdapter.notifyDataSetChanged();
				lv.postDelayed(new Runnable() {
					@Override
					public void run() {
						lv.onRefreshComplete();
					}
				}, 1000);
			}
		}
	};

	String time1 = "2016-0-00 15:14:41";

	class MyTheale extends Thread {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (true) {

				String time = formatUrlParam(time1);// 空格转义 若直接URL1

				String url1 = "http://123.57.72.71/api/app/" + NewMainActivity.cardid + "/" + time + "/" + userid
						+ "/mobileClient/list";
				// String url1 = "http://123.57.72.71:8080/fm/api/app/" +
				// "GWA100171001" + "/" + time
				// + "/mobileClient/list";
				String url = url1;
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet1 = new HttpGet(url);
				HttpResponse response = null;
				try {
					response = httpClient.execute(httpGet1);
					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String result = reader.readLine();

					Message msg = new Message();
					msg.arg1 = 1;
					msg.obj = result;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					sleep(300000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	int eorrpage = 0;

	class MyTheale1 extends Thread {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			String time = formatUrlParam(time1);// 空格转义 若直接URL1
			// String url1 = "http://123.57.72.71:8080/fm/api/app/" +
			// NewMainActivity.cardid + "/" + time
			// + "/mobileClient/list";
			// String url1 = "http://123.57.72.71:8080/fm/api/app/" +
			// NewMainActivity.cardid + "/" + 0 + "/" + 15
			// + "/mobileClient/pageList";
			String url1 = "http://123.57.72.71/api/app/" + eorrpage + "/" + 15 + "/" + userid
					+ "/mobileClient/pageList";
			String url = url1;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet1 = new HttpGet(url);
			HttpResponse response = null;
			try {
				response = httpClient.execute(httpGet1);
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String result = reader.readLine();
				// lv.getRefreshableView();
				Message msg = new Message();
				msg.arg1 = 2;
				msg.obj = result;
				handler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 当对整个URL进行转义是会出现对://等字符的转义是的http不能正常访问， 当手动对“ ”转义%是http会直接传过去也不能正常访问
	 */
	public static String formatUrlParam(String param) { // 对URL非法字符转义
		param.replaceAll(" ", "%20");
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return param;
		}
	}

}
