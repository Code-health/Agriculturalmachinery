package io.jchat.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import io.jchat.android.adapter.ContactsList_Adapter;
import io.jchat.android.customview.LoadingPage;
import io.jchat.android.customview.LoadingPage.ILoadingDo;
import io.jchat.android.entity.Contacts;
import io.jchat.android.http.HttpService;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;

/*
 * 会话列表界面
 */
public class ContactsActivity extends BaseActivity{
    /** Called when the activity is first created. */
    /** soapService **/
    public ISoapService soapService = new SoapService();
    Context context;
    /** loading **/
    private LoadingPage loading;
    
	private PullToRefreshListView listView_contactslist;
	private ListView listView;
	private int pageIndex = 1;

	private ContactsList_Adapter adapter;
	private List<Contacts> list;
	private long exitTime;
	private static final String TAG = "SU-JPush";
	private Button back_btn;

	String Id = "";
	String UserPic = "";
	String UserId = "";
	String Cclive = "";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_contacts);

        EventCache.commandActivity.unregister(this);
        EventCache.commandActivity.register(this);
        EventCache.errorHttp.unregister(this);
        EventCache.errorHttp.register(this);
        
        initView();
		initData();
		setListeners();
    }

	// private void initDB() {
	// dbh = new DBHelper(context);
	// }

	private void initData() {

		String[] property_nm = {"pagesize", "pageindex", "UserId"};
        Object[] property_va = {"10", pageIndex + "", "196"};
        new getVideoListTask().execute(property_nm, property_va);
//			String[] property_va = new String[] { "10", pageIndex + "", "196" };
//			soapService.getLivingALl(property_va, false);

	}

	private void initView() {
		back_btn = (Button) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView_contactslist = (PullToRefreshListView) findViewById(R.id.listView_contactslist);
		listView = listView_contactslist.getRefreshableView();
	}

	private void setListeners() {
		listView_contactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Id = list.get(position - 1).getLiveUserId();
				UserPic = list.get(position - 1).getUserHeadpic();
				Cclive = list.get(position - 1).getCclive();
				SharedPreferences sp = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																											// category是新建的表名
				Editor editor = sp.edit();// 获取编辑器
				editor.putString("Id", Id);
				editor.putString("UserPic", UserPic);
				editor.putString("Cclive", Cclive);
				editor.commit();

			}
		});
		listView_contactslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				pageIndex = 1;

				String[] property_nm = {"pagesize", "pageindex", "UserId"};
		        Object[] property_va = {"10", pageIndex + "", "196"};
		        new getVideoListTask().execute(property_nm, property_va);
//					String[] property_va = new String[] { "10", pageIndex + "", "196" };
//					soapService.getLivingALl(property_va, false);

			}
		});

		// end of list
		listView_contactslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_nm = {"pagesize", "pageindex", "UserId"};
		        Object[] property_va = {"10", ++pageIndex + "", "196"};
		        new getVideoListTask().execute(property_nm, property_va);
		        
//					String[] property_va = new String[] { "10", ++pageIndex + "", "196" };
//					soapService.getLivingALl(property_va, true);

			}
		});
	}

//	private void getDBData() {
//		// list = dbh.queryAllLiving();
//
//		adapter = new ContactsList_Adapter(context, list, "196");
//		listView.setAdapter(adapter);
//
//	}

	  class getVideoListTask extends AsyncTask<Object, Object, Object> {
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }

	        @Override
	        protected Object doInBackground(Object... params) {
	            System.out.println(">>>>>");
	            Object res_obj = (Object) HttpService.data("http://123.56.88.189:8027/guzhang/GetLivingALl",SOAP_UTILS.METHOD.GETLIVINGALL, (String[]) params[0],
	                    (Object[]) params[1]);
	            return res_obj;
	        }

	        @Override
	        protected void onPostExecute(Object result) {
	            super.onPostExecute(result);
	            try {
	            	listView_contactslist.onRefreshComplete();
	                list = new ArrayList<Contacts>();
	            	JSONArray nowliving_array = new JSONArray(result.toString());
					List<Contacts> nowliving_list = new ArrayList<Contacts>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						Contacts nowliving = new Contacts();
						nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						nowliving.setAttention(json_nowliving.get("Attention").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						// nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						// nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						nowliving.setCclive(json_nowliving.get("Cclive").toString());
						nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
						nowliving.setCourseType(json_nowliving.get("CourseType").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
						nowliving.setDealControl(json_nowliving.get("DealControl").toString());
						nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
						nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setLaud(json_nowliving.get("Laud").toString());
						nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
						nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
						nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						nowliving.setLivings(json_nowliving.get("Livings").toString());
						nowliving.setNameCc(json_nowliving.get("NameCc").toString());
						nowliving.setToplive(json_nowliving.get("Toplive").toString());
						nowliving.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						nowliving.setUserResume(json_nowliving.get("UserResume").toString());
						nowliving_list.add(nowliving);
					}
					adapter = new ContactsList_Adapter(context,nowliving_list,"196");
					listView.setAdapter(adapter);
	            } catch (JSONException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	    }
	
	public void onEvent(SoapRes obj) {
//		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIVINGALL)) {
//			listView_contactslist.onRefreshComplete();
//			if (obj.isPage()) {
//				for (Contacts bean : (List<Contacts>) obj.getObj()) {
//					list.add(bean);
//				}
//				adapter.notifyDataSetChanged();
//			} else {
//				list = (List<Contacts>) obj.getObj();
//				// if (list.size() != 0) {
//				//
//				// dbh.clearAllLiving();
//				// dbh.insAllLivingList(list);
//				// pageIndex = 1;
//				// }
//				getDBData();
//			}
//		} 
	}
    /**
     * 添加loading
     */
    public void addLoading() {
        loading = new LoadingPage(this, new ILoadingDo() {
            
            @Override
            public void soapFail(String methodName) {
                EventCache.errorHttp.post(methodName);
            }
        });
        
        addContentView(loading, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 移除 loading
     */
    public void removeLoading() {
        if (loading != null) {
            ViewGroup parent = (ViewGroup) loading.ll_bg.getParent();
            parent.removeView(loading.ll_bg);
            loading = null;
        }
    }

}
