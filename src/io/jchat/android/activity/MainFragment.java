package io.jchat.android.activity;

import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.adapter.MainList_Adapter;
import io.jchat.android.adapter.TractorList_Adapter;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.customview.LoadingPage;
import io.jchat.android.db.DBHelper;
import io.jchat.android.entity.Contacts;
import io.jchat.android.entity.Cooperatives;
import io.jchat.android.entity.TractorList;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapRes;
import io.jchat.android.http.SoapService;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import io.jchat.android.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import io.jchat.android.tools.NativeImageLoader;
import io.jchat.android.pulltorefresh.library.PullToRefreshListView;
import io.jchat.android.utils.EventCache;
import io.jchat.android.utils.SOAP_UTILS;

import com.lnpdit.agriculturalmachinery.R;

public class MainFragment extends BaseFragment implements OnChildClickListener {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private ExpandableListView listView;
	// 创建一级条目容器
	List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
	// 存放内容, 以便显示在列表中
	List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	private String[] group_title_array;

	Map<String, String> title_1 = new HashMap<String, String>();
	List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();

	// 子视图显示文字
	private static String[][] child_text1_array;
	private static String[][] child_text2_array;
	private static String[][] child_text3_array;
	private static String[][] child_text4_array;
	private String[][] child_cooperativeId_array;
	private String[][] child_carscount_array;
	private String[][] child_cooperativeName_array;
	private String[][] child_totalArea_array;
	private String[][] child_goodArea_array;
	private String[][] child_distanceGoodRate_array;
	private String[][] child_totalDistance_array;
	private String[][] child_depth_array;

	private List<Cooperatives> cooperativesList;
	private TextView totle_tvv;
	private TextView area_tv;
	private TextView percent_tv2;
	private TextView arvedepth_tv;
	private TextView yestodayarea_tv;
	private ImageView logout_tv;

	private Dialog mDialog;

	String totle_str = "";
	String area_str = "";
	String percent_str = "";
	String arvedepth_str = "";
	String yestodayarea_str = "";
	// String userId = "";

	private DBHelper dbh;

	SharedPreferences read;

	String areaName;

	String userId;
	String cooperativeId;
	String cooperativeName;
	String carscount;
	String totalArea;
	String goodArea;
	String distanceGoodRate;
	String totalDistance;
	String depth;
	String yesterdayArea;

	public MainFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		System.out.println("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("onCreateView");

		context = this.getActivity();
		read = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		userId = read.getString("id", "");

		view = inflater.inflate(R.layout.fragment_main, container, false);
		initView();
		initData();
		setListeners();
		return view;
	}

	private void initData() {
		dbh = new DBHelper(context);

		String[] property_va = new String[] { userId };
		soapService.workInfoList(property_va);
		//ClickableSp();//单个拖拉机信息
	}

	private void initView() {
		totle_tvv = (TextView) view.findViewById(R.id.totle_tvv);
		area_tv = (TextView) view.findViewById(R.id.area_tv);
		percent_tv2 = (TextView) view.findViewById(R.id.percent_tv2);
		arvedepth_tv = (TextView) view.findViewById(R.id.arvedepth_tv);
		yestodayarea_tv = (TextView) view.findViewById(R.id.yestodayarea_tv);
		logout_tv = (ImageView) view.findViewById(R.id.logout_tv);
		logout_tv.setClickable(true);
		logout_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						switch (view.getId()) {
						case R.id.jmui_cancel_btn:
							mDialog.cancel();
							break;
						case R.id.jmui_commit_btn:
							Logout();
							cancelNotification();
							NativeImageLoader.getInstance().releaseCache();
							getActivity().finish();
							mDialog.cancel();
							break;
						}
					}
				};

				mDialog = DialogCreator.createLogoutDialog(context, listener);
				mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
				mDialog.show();
			}
		});

		listView = (ExpandableListView) view.findViewById(android.R.id.list);
	}

	private void setListeners() {
		// TODO Auto-generated method stub

		listView.setOnChildClickListener(this);

	}

	public void ClickableSp() {
		cooperativeId = "2c0d3d4bfbbb4635ba37dbff71298cf5";
		a:for (int i = 0; i < childs.size(); i++) {
			for (int j = 0; j < sum_obj.length(); j++) {

				if (childs.get(i).get(j).get("cooperativeId").equals(cooperativeId)) {
					cooperativeName = childs.get(i).get(j).get("cooperativeName").toString();
					carscount = childs.get(i).get(j).get("carscount").toString();
					totalArea = childs.get(i).get(j).get("totalArea").toString();
					goodArea = childs.get(i).get(j).get("goodArea").toString();
					distanceGoodRate = childs.get(i).get(j).get("distanceGoodRate").toString();
					totalDistance = childs.get(i).get(j).get("totalDistance").toString();
					depth = childs.get(i).get(j).get("depth").toString();
					break a;
				}
			}
		}

		Intent intent = new Intent();
		intent.putExtra("cooperativeId", cooperativeId);
		intent.putExtra("carscount", carscount);
		intent.putExtra("cooperativeName", cooperativeName);
		intent.putExtra("totalArea", totalArea);
		intent.putExtra("goodArea", goodArea);
		intent.putExtra("distanceGoodRate", distanceGoodRate);
		intent.putExtra("totalDistance", totalDistance);
		intent.putExtra("depth", depth);
		intent.setClass(context, CooperativesActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	/**
	 * 列表内容按下
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		cooperativeId = childs.get(groupPosition).get(childPosition).get("cooperativeId").toString();
		cooperativeName = childs.get(groupPosition).get(childPosition).get("cooperativeName").toString();
		carscount = childs.get(groupPosition).get(childPosition).get("carscount").toString();
		totalArea = childs.get(groupPosition).get(childPosition).get("totalArea").toString();
		goodArea = childs.get(groupPosition).get(childPosition).get("goodArea").toString();
		distanceGoodRate = childs.get(groupPosition).get(childPosition).get("distanceGoodRate").toString();
		totalDistance = childs.get(groupPosition).get(childPosition).get("totalDistance").toString();
		depth = childs.get(groupPosition).get(childPosition).get("depth").toString();

		Intent intent = new Intent();
		intent.putExtra("cooperativeId", cooperativeId);
		intent.putExtra("carscount", carscount);
		intent.putExtra("cooperativeName", cooperativeName);
		intent.putExtra("totalArea", totalArea);
		intent.putExtra("goodArea", goodArea);
		intent.putExtra("distanceGoodRate", distanceGoodRate);
		intent.putExtra("totalDistance", totalDistance);
		intent.putExtra("depth", depth);
		intent.setClass(context, CooperativesActivity.class);
		startActivity(intent);

		return true;
	}

	private void setHeaderUI() {
		totle_tvv.setText(totle_str);
		area_tv.setText(area_str);
		percent_tv2.setText(percent_str);
		arvedepth_tv.setText(arvedepth_str);
		yestodayarea_tv.setText(yestodayarea_str);
	}

	JSONObject sum_obj ;
	
	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.WORKINFOLIST)) {

			try {

				JSONObject user_obj = new JSONObject(obj.getObj().toString());

				totle_str = user_obj.get("totalArea").toString();
				area_str = user_obj.get("goodArea").toString();
				percent_str = user_obj.get("avgPassRate").toString();
				arvedepth_str = user_obj.get("avgDeep").toString();
				yestodayarea_str = user_obj.get("yesterdayArea").toString();
				setHeaderUI();

				JSONArray area_array = user_obj.getJSONArray("areaInfoList");

				for (int i = 0; i < area_array.length(); i++) {
					JSONObject area_obj = (JSONObject) area_array.get(i);
					areaName = area_obj.get("areaName").toString();

					JSONArray sum_array = area_obj.getJSONArray("sumList");
					childs_1 = new ArrayList<Map<String, String>>();
					for (int j = 0; j < sum_array.length(); j++) {
						sum_obj = (JSONObject) sum_array.get(j);
						if (!(sum_obj.get("count").toString().equals("0"))) {
							cooperativeId = sum_obj.get("cooperativeId").toString();
							carscount = sum_obj.get("count").toString();
							cooperativeName = sum_obj.get("cooperativeName").toString();
							totalArea = sum_obj.get("totalArea").toString();
							goodArea = sum_obj.get("goodArea").toString();
							distanceGoodRate = sum_obj.get("distanceGoodRate").toString();
							totalDistance = sum_obj.get("totalDistance").toString();
							depth = sum_obj.get("depth").toString();
							yesterdayArea = sum_obj.get("yesterdayArea").toString();
						} else {
							cooperativeId = "";
							carscount = "";
							cooperativeName = "";
							totalArea = "";
							goodArea = "";
							distanceGoodRate = "";
							totalDistance = "";
							depth = "";
							yesterdayArea = "";
						}
						// 创建二级条目内容
						// 内容一
						Map<String, String> title_1_content_1 = new HashMap<String, String>();
						title_1_content_1.put("cooperativeId", cooperativeId);
						title_1_content_1.put("carscount", carscount);
						title_1_content_1.put("cooperativeName", cooperativeName);
						title_1_content_1.put("totalArea", totalArea);
						title_1_content_1.put("goodArea", goodArea);
						title_1_content_1.put("distanceGoodRate", distanceGoodRate);
						title_1_content_1.put("totalDistance", totalDistance);
						title_1_content_1.put("depth", depth);
						title_1_content_1.put("yesterdayArea", yesterdayArea);
						childs_1.add(title_1_content_1);
					}
					title_1 = new HashMap<String, String>();
					title_1.put("areaName", areaName);
					gruops.add(title_1);

					childs.add(childs_1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adapter = new ExpandableListAdapter(context, cooperativesList);
			listView.setAdapter(adapter);
			for (int k = 0; k < adapter.getGroupCount(); k++) {

				listView.expandGroup(k);

			}
		}
	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

		// 重写ExpandableListAdapter中的各个方法
		/**
		 * 获取一级标签总数
		 */
		@Override
		public int getGroupCount() {
			return gruops.size();
		}

		/**
		 * 获取一级标签内容
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return gruops.get(groupPosition);
		}

		/**
		 * 获取一级标签的ID
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * 获取一级标签下二级标签的总数
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			return childs.get(groupPosition).size();
		}

		/**
		 * 获取一级标签下二级标签的内容
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childs.get(groupPosition).get(childPosition);
		}

		/**
		 * 获取二级标签的ID
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * 指定位置相应的组视图
		 */
		@Override
		public boolean hasStableIds() {
			return true;
		}

		/**
		 * 对一级标签进行设置
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// 为视图对象指定布局
			convertView = (LinearLayout) LinearLayout.inflate(context, R.layout.activity_groups, null);
			/**
			 * 声明视图上要显示的控件
			 */
			// 新建一个TextView对象，用来显示一级标签上的标题信息
			TextView group_title = (TextView) convertView.findViewById(R.id.textgroup);
			// 设置标题上的文本信息
			String groupName = gruops.get(groupPosition).get("areaName").toString();
			if (groupName == null || groupName.equals("")) {
				group_title.setVisibility(TextView.GONE);
			} else {
				group_title.setText(groupName);
			}

			// 返回一个布局对象
			return convertView;
		}

		/**
		 * 对一级标签下的二级标签进行设置
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			// 为视图对象指定布局
			convertView = (RelativeLayout) RelativeLayout.inflate(context, R.layout.activity_childs, null);
			/**
			 * 声明视图上要显示的控件
			 */
			// 新建一个TextView对象，用来显示具体内容
			TextView child_text1 = (TextView) convertView.findViewById(R.id.textchild1);
			TextView child_text2 = (TextView) convertView.findViewById(R.id.textchild2);
			TextView child_text3 = (TextView) convertView.findViewById(R.id.textchild3);
			TextView child_text4 = (TextView) convertView.findViewById(R.id.textchild4);
			ImageView icon_img = (ImageView) convertView.findViewById(R.id.icon_img);
			ImageView next_img = (ImageView) convertView.findViewById(R.id.next_img);
			RelativeLayout childLayout = (RelativeLayout) convertView.findViewById(R.id.childLayout);
			/**
			 * 设置相应控件的内容
			 */
			// 设置要显示的文本信息
			String childName1 = childs.get(groupPosition).get(childPosition).get("cooperativeName").toString();
			String childName2 = childs.get(groupPosition).get(childPosition).get("totalArea").toString();
			String childName3 = childs.get(groupPosition).get(childPosition).get("carscount").toString();
			String childName4 = childs.get(groupPosition).get(childPosition).get("yesterdayArea").toString();

			if (childName1 == null || childName1.equals("")) {
				child_text1.setVisibility(8);
				child_text2.setVisibility(8);
				child_text3.setVisibility(8);
				child_text4.setVisibility(8);
				icon_img.setVisibility(8);
				next_img.setVisibility(8);
			} else {
				child_text1.setText(childName1);
				child_text2.setText("总作业面积:" + childName2 + "亩");
				child_text3.setText("农机数量：" + childName3 + "台");
				child_text4.setText("昨日作业面积:" + childName4 + "亩");
			}
			// 返回一个布局对象
			return convertView;
		}

		/**
		 * 当选择子节点的时候，调用该方法
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	};

}