package io.jchat.android.newactivity;

import com.lnpdit.agriculturalmachinery.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import io.jchat.android.activity.NowtimeMapActivity;

public class Shujiceshi extends Activity{
	
	private ImageView bank;
	private WebView ntm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shujuceshi);
		
		bank=(ImageView) findViewById(R.id.bank);
		ntm=(WebView) findViewById(R.id.ntm);
		setUI();
		bank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void setUI() {
        WebSettings wSet = ntm.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setSupportZoom(true);
        String URL = "http://123.57.72.71:8080/fm/static/test/";
        ntm.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 禁止调用自带浏览器打开网页
                return true;
            }
        });

        String urii=URL;
        ntm.loadUrl(urii);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && ntm.canGoBack()) {
        	ntm.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}