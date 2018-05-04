package io.jchat.android.application;

import com.pgyersdk.crash.PgyCrashManager;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import android.app.Application;
import android.util.Log;
import cn.jpush.im.android.api.JMessageClient;
import io.jchat.android.chatting.utils.SharePreferenceManager;
import io.jchat.android.receiver.NotificationClickEventReceiver;

public class JChatDemoApplication extends Application {

	public static final int REQUEST_CODE_TAKE_PHOTO = 4;
	public static final int REQUEST_CODE_SELECT_PICTURE = 6;
	public static final int RESULT_CODE_SELECT_PICTURE = 8;
	public static final int REQUEST_CODE_SELECT_ALBUM = 10;
	public static final int RESULT_CODE_SELECT_ALBUM = 11;
	public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
	public static final int RESULT_CODE_BROWSER_PICTURE = 13;
	public static final int RESULT_CODE_CHAT_DETAIL = 15;
	public static final int REQUEST_CODE_FRIEND_INFO = 16;
	public static final int RESULT_CODE_FRIEND_INFO = 17;
	public static final int REQUEST_CODE_CROP_PICTURE = 18;
	public static final int REQUEST_CODE_ME_INFO = 19;
	public static final int RESULT_CODE_ME_INFO = 20;
	public static final int REQUEST_CODE_ALL_MEMBER = 21;
	public static final int RESULT_CODE_ALL_MEMBER = 22;
	public static final int ON_GROUP_EVENT = 3004;

	private static final String JCHAT_CONFIGS = "JChat_configs";
	public static final String TARGET_APP_KEY = "targetAppKey";
	public static final String TARGET_ID = "targetId";
	public static final String NAME = "name";
	public static final String NICKNAME = "nickname";
	public static final String GROUP_ID = "groupId";
	public static final String GROUP_NAME = "groupName";
	public static final String STATUS = "status";
	public static final String POSITION = "position";
	public static final String MsgIDs = "msgIDs";
	public static final String DRAFT = "draft";
	public static final String DELETE_MODE = "deleteMode";
	public static final String MEMBERS_COUNT = "membersCount";
	public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("JpushDemoApplication", "init");
		// 注册蒲公英
		PgyCrashManager.register(this);
		// 初始化JMessage-sdk
		JMessageClient.init(getApplicationContext());
		SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
		// 设置Notification的模式
		JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
		// 注册Notification点击的接收器

//		mPushAgent = PushAgent.getInstance(this);
//		// 注册推送服务，每次调用register方法都会回调该接口
////		new MyThead().start();
//		
//		mPushAgent.register(new IUmengRegisterCallback() {
//
//			@Override
//			public void onSuccess(String deviceToken) {
//				// 注册成功会返回device token
//				deto=deviceToken;
//				System.out.println(deviceToken+"    vvvvvvvvvvvvvvvvvvvvv");
//			}
//
//			@Override
//			public void onFailure(String s, String s1) {
//
//			}
//		});

		new NotificationClickEventReceiver(getApplicationContext());
	}

	PushAgent mPushAgent;
    String deto="";
	class MyThead extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
		}
	}

	public static void setPicturePath(String appKey) {
		if (!SharePreferenceManager.getCachedAppKey().equals(appKey)) {
			SharePreferenceManager.setCachedAppKey(appKey);
			PICTURE_DIR = "sdcard/JChatDemo/pictures/" + appKey + "/";
		}
	}

}
