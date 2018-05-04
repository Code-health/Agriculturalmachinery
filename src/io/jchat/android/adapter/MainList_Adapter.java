package io.jchat.android.adapter;

import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
//import com.umeng.message.PushAgent;
//import com.umeng.message.tag.TagManager.Result;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.jchat.android.entity.Contacts;
import io.jchat.android.http.ISoapService;
import io.jchat.android.http.SoapService;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class MainList_Adapter extends BaseAdapter {
	private Context context;
	private List<Contacts> list;
	String LiveUserId = "";
	String UserId = "";
	String type = "";
	String Cclive = "";
	String Id = "";
	String UserPic = "";
//	private PushAgent mPushAgent;
	
	public ISoapService soapService = new SoapService();
	
	public MainList_Adapter(Context context, List<Contacts> list,String UserId) {
		this.context = context;
		this.list = list;
		this.UserId = UserId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//开启友盟推送服务
//		mPushAgent = PushAgent.getInstance(context);
//		mPushAgent.enable();
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_contacts, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.expert_name);
			viewHolder.attention_tv = (TextView) convertView
					.findViewById(R.id.attention_tv);
			viewHolder.zbj_text = (TextView) convertView
					.findViewById(R.id.zbj_text);
			viewHolder.intro = (TextView) convertView
					.findViewById(R.id.expert_content);
			viewHolder.head_img = (ImageView) convertView
					.findViewById(R.id.expert_headpic);
			viewHolder.livecount = (TextView) convertView
					.findViewById(R.id.expert_LiveCount);
			viewHolder.rewardmark = (TextView) convertView
					.findViewById(R.id.expert_rewardmark);
			viewHolder.watching_text = (TextView) convertView
					.findViewById(R.id.watching_text);
			viewHolder.living_tv = (TextView) convertView
					.findViewById(R.id.living_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

//		Instance.imageLoader.displayImage(
//				SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getUserHeadpic(),
//				viewHolder.head_img, Instance.user_s_options);
		viewHolder.name.setText(list.get(position).getLiveUserName());
         if(list.get(position).getAttention().equals("1")){
			
			viewHolder.attention_tv.setText("已关注");
			type = "0";
		}else{

			viewHolder.attention_tv.setText("关注ta");
			type = "1";
		}
         
         LiveUserId = list.get(position).getLiveUserId();
 		
 		viewHolder.attention_tv.setClickable(true);
 		viewHolder.attention_tv.setOnClickListener(new ButtonListener(position, LiveUserId,
 				UserId,type, viewHolder.attention_tv));
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.intro.setText(list.get(position).getUserResume());
		viewHolder.livecount.setText("解答" +list.get(position).getLiveCount() + "个网友提问");
		viewHolder.rewardmark.setText("赞：" +list.get(position).getLaud());

		Cclive = list.get(position).getCclive();
       if(Cclive.equals("1")){
			
			viewHolder.watching_text.setText("Cc直播");
//			viewHolder.watching_text.setClickable(true);
		}else{

			viewHolder.watching_text.setText("");
//			viewHolder.watching_text.setClickable(false);
		}
//       viewHolder.watching_text.setOnClickListener(new ButtonListener(position,  LiveUserId,
//				UserId,type, viewHolder.watching_text));		
		
		if(list.get(position).getLivings().equals("0")){
			viewHolder.living_tv.setVisibility(8);
		}else{
			viewHolder.living_tv.setVisibility(1);
		}
		return convertView;
	}

	class ButtonListener implements OnClickListener {
		private int position;
		private String LiveUserId;
		private String UserId ;
		private String type;
		private TextView tv;
		
		public ButtonListener(int pos,String _LiveUserId, String _UserId , String _type, TextView _tv) {
			// TODO Auto-generated constructor stub
			position = pos;
			LiveUserId = _LiveUserId;
			UserId  = _UserId ;
			type = _type;
			tv = _tv;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
//			switch (v.getId()) {
//			case R.id.watching_text:
//				
//				Id = list.get(position).getLiveUserId();
//				UserPic = list.get(position).getUserHeadpic();
//				 SharedPreferences sp = context.getSharedPreferences("live",Context.MODE_PRIVATE); // 私有数据 category是新建的表名
//				    Editor editor = sp.edit();// 获取编辑器
//				    editor.putString("Id", Id);
//				    editor.putString("UserPic", UserPic);
//				    editor.commit();
//				    
//				Intent intent = new Intent();
//				intent.setClass(context, CcliveActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//				context.startActivity(intent);
//				
//				break;
//	       case R.id.attention_tv:
				
			if(UserId.equals("")){
//				Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
//				Intent intent_login = new Intent();
//				intent_login.setClass(context, Login_Activity.class);
//				intent_login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
//				context.startActivity(intent_login);
			}else if(tv.getText().equals("关注ta")){
	
        	 tv.setText("已关注");
        	 try {
      			Thread thread = new Thread(new Runnable() {
      				
      				@Override
      				public void run() {
      					// TODO Auto-generated method stub					
      					try {
//      						Result result = mPushAgent.getTagManager().add("guzhang"+LiveUserId);
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", result.jsonString);
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", result.jsonString);
//      						Log.d("@@@@@@@@@@@@@@@@@@@@@", result.jsonString);
      					} catch (Exception e) {
      						// TODO Auto-generated catch block
      						e.printStackTrace();
      					}
      				}
      			});
      			thread.start();
//         		Welcome_Activity.mPushAgent.getTagManager().add("guzhang"+LiveUserId);
  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
        	 
            }else{

            	tv.setText("关注ta");
            	try {
          			Thread thread2 = new Thread(new Runnable() {
          				
          				@Override
          				public void run() {
          					// TODO Auto-generated method stub					
          					try {
//          						Result result = mPushAgent.getTagManager().delete("guzhang"+LiveUserId);
//          						Log.d("##########################", "##########################");
//          						Log.d("##########################", "##########################");
//          						Log.d("##########################", "##########################");
//          						Log.d("##########################", "##########################");
//          						Log.d("##########################", "##########################");
//          						Log.d("##########################", result.jsonString);
//          						Log.d("##########################", result.jsonString);
//          						Log.d("##########################", result.jsonString);
          					} catch (Exception e) {
          						// TODO Auto-generated catch block
          						e.printStackTrace();
          					}
          				}
          			});
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
//			String[] property_vas = new String[] {LiveUserId, UserId ,type };
//			soapService.getAttentionLiveUser(property_vas);	

		}
		
	}
	static class ViewHolder {
		public TextView name;
		public TextView attention_tv;
		public TextView zbj_text;
		public TextView intro;
		public ImageView head_img;
		public TextView livecount;
		public TextView rewardmark;
		public TextView watching_text;
		public TextView living_tv;
	}
}
