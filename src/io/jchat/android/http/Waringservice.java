package io.jchat.android.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.agriculturalmachinery.R;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;
import io.jchat.android.newactivity.NewMainActivity;

public class Waringservice extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    private String time = "2016-01-12 12:12:12";
    private int a = 0;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, NewMainActivity.class);

        pd = PendingIntent.getActivity(Waringservice.this, 0, intent, 0);
        MyTeale1 mt = new MyTeale1();
        mt.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        cardid=NewMainActivity.getCardid();
    }

    String cardid = "";
    int b = 0;
    
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        flags = START_STICKY;  //服务不被杀死
        return super.onStartCommand(intent, flags, startId);  
    } 

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 2) {
                JSONObject user_obj;
                try {
                	Toast.makeText(Waringservice.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    b=1;
                    user_obj = new JSONObject(msg.obj.toString());
                    if (!(user_obj.getString("time").trim()
                            .equals(null))) {
                        b=0;
                        System.out.println(
                                user_obj.getString("time").trim());
                        time = user_obj.getString("time").trim();
                        noticef();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    };

    class MyTeale1 extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (true) {
               
                if (b == 0) {
                    time = formatUrlParam(time);// 空格转义 若直接URL1 会出现将其他字符过度转义的现象
                }
                String url1 = "http://123.57.72.71/api/app/" + cardid + "/" + time
						+ "/mobileClient/list";
//                String url1 = "http://123.57.72.71:8080/fm/api/app/" + "GWA100171001" + "/" + time
//						+ "/mobileClient/list";
                System.out.println(time);
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url1);
                HttpResponse response = null;
                try {
                    response = httpClient.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String result = reader.readLine();
                    System.out.println(result + "MyTeale1");
                    Message msg = new Message();
                    msg.arg1 = 2;
                    msg.obj = result;
                    handler1.sendMessage(msg);
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

    public static String formatUrlParam(String param) { // 对URL非法字符转义
        param.replaceAll(" ", "%20");// 字符替换
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return param;
        }
    }

    // 判断 app、activity是否在运行
    public boolean isForeground(String PackageName) {
        // Get the Activity Manager
        ActivityManager manager = (ActivityManager) getSystemService(
                ACTIVITY_SERVICE);

        // Get a list of running tasks, we are only interested in the last one,
        // the top most so we give a 1 as parameter so we only get the topmost.
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);

        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).topActivity;

        // Check if it matches our package name.
        if (componentInfo.getPackageName().equals(PackageName)) {
            return true;
        }

        // If not then our app is not on the foreground.
        return false;
    }

    private Notification baseNF;
    private int Notification_ID_BASE = 110;
    // 通知管理器
    private NotificationManager nm;

    // 通知显示内容
    private PendingIntent pd;

    // 状态栏通知方法
    public void noticef() {
        if (a == 0) {
            // 新建状态栏通知
            baseNF = new Notification();
            // 设置通知在状态栏显示的图标
            baseNF.icon = R.drawable.njlogo;
            // 通知时在状态栏显示的内容
            baseNF.tickerText = "有新的设备报警!";
            // 通知的默认参数 DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
            // 如果要全部采用默认值, 用 DEFAULT_ALL.
            // 此处采用默认声音
            // baseNF.defaults |= Notification.DEFAULT_VIBRATE;
            // baseNF.defaults |= Notification.DEFAULT_LIGHTS;
            baseNF.defaults = Notification.DEFAULT_SOUND;
            // 让声音、振动无限循环，直到用户响应
            // baseNF.flags |= Notification.FLAG_INSISTENT;
            baseNF.flags |= Notification.DEFAULT_VIBRATE;

            // 通知被点击后，自动消失
            baseNF.flags |= Notification.FLAG_AUTO_CANCEL;
            // 第二个参数 ：下拉状态栏时显示的消息标题 expanded message title
            // 第三个参数：下拉状态栏时显示的消息内容 expanded message text
            // 第四个参数：点击该通知时执行页面跳转
            baseNF.setLatestEventInfo(Waringservice.this, "联科优农", "有新的设备报警!",pd);

            // 发出状态栏通知tification
            // and the second is the Notification object.
            // The first parameter is the unique ID for the No
            nm.notify(Notification_ID_BASE, baseNF);
            a = a + 1;
        } else {
            // 更新通知
            // 比如状态栏提示有一条新短信，还没来得及查看，又来一条新短信的提示。
            // 此时采用更新原来通知的方式比较。
            // (再重新发一个通知也可以，但是这样会造成通知的混乱，而且显示多个通知给用户，对用户也不友好)
            baseNF.setLatestEventInfo(Waringservice.this, "联科优农", "有新的设备报警!",pd);
            nm.notify(Notification_ID_BASE, baseNF);
        }
    }

}
