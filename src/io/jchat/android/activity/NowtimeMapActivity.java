package io.jchat.android.activity;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;
import io.jchat.android.newactivity.SignActivity;

public class NowtimeMapActivity extends Activity{

	private WebView wv;
	private ImageView nt_bank;
	String id="";
	String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nowtimemap);
		wv=(WebView) findViewById(R.id.ntm_wv);
		nt_bank=(ImageView) findViewById(R.id.nt_bank);
		id=getIntent().getStringExtra("workSerial");
		type=getIntent().getStringExtra("type");
		nt_bank.setOnClickListener(lin);
//		PushAgent.getInstance(NowtimeMapActivity.this).onAppStart();
		setUI();
	}
	
	OnClickListener lin=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.nt_bank:
				finish();
				break;

			default:
				break;
			}
		}
	};
	
	public void setUI() {
        WebSettings wSet = wv.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setSupportZoom(true);
        if(type.equals("3")){
        	String URL = "http://123.57.72.71/static/mobileClient/NoTillageSowingJS/mobile.html?workSerial=";
            wv.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);// 禁止调用自带浏览器打开网页
                    return true;
                }
            });
//            Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
            String urii=URL+id;
            wv.loadUrl(urii);
        }else if (type.equals("1")) {
        	String URL = "http://123.57.72.71/static/mobileClient/PaddyField/mobile.html?workSerial=";
            wv.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);// 禁止调用自带浏览器打开网页
                    return true;
                }
            });
//            Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
            String urii=URL+id;
            wv.loadUrl(urii);
		}else {
        	String URL = "http://123.57.72.71/static/mobileClient/mobile.html?workSerial=";
            wv.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);// 禁止调用自带浏览器打开网页
                    return true;
                }
            });
//            Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
            String urii=URL+id;
            wv.loadUrl(urii);
		}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
        	wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
