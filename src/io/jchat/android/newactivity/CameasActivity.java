package io.jchat.android.newactivity;

import java.io.File;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class CameasActivity extends Activity {

	private WebView wb;
	private ProgressBar myProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cameas);
		wb = (WebView) findViewById(R.id.web);
		myProgressBar=(ProgressBar) findViewById(R.id.myProgressBar);
		
		PushAgent.getInstance(CameasActivity.this).onAppStart();
		setUI();
	}

	String URL = "http://192.168.1.241:8080/gh/static/upload.html";

	public void setUI() {
		WebSettings wSet = wb.getSettings();
		wSet.setJavaScriptEnabled(true);
		wSet.setSupportZoom(true);
		wSet.setAllowFileAccess(true); // 设置可以访问文件

		wb.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);// 禁止调用自带浏览器打开网页
				return true;
			}
		});
		// Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
		String urii = URL;
		wb.loadUrl(urii);

		wb.setWebChromeClient(new WebChromeClient() {

			// For Android 4.1
			 public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
				 mUploadFile=  uploadFile;
				 startActivityForResult(createDefaultOpenableIntent(), REQUEST_UPLOAD_FILE_CODE);
	            }
			 @Override
				public void onProgressChanged(WebView view, int newProgress) {
					myProgressBar.setMax(100);
					if (newProgress < 100) {
						if (myProgressBar.getVisibility() == View.INVISIBLE) {
							myProgressBar.setVisibility(View.VISIBLE);
						}
						myProgressBar.setProgress(newProgress);
					} else {
						myProgressBar.setProgress(100);
						myProgressBar.setVisibility(View.INVISIBLE);
					}
					super.onProgressChanged(view, newProgress);
				}
		});
	}
	
	private ValueCallback<Uri> mUploadFile;
	    private String mCameraFilePath="myfiles.png";
	    private static final int REQUEST_UPLOAD_FILE_CODE = 1003; // 选择文件请求码

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_UPLOAD_FILE_CODE) {
//            if (resultCode == RESULT_OK) {
                // 获取需上传文件成功
//                if (null == mUploadFile) {
//                	mUploadFile.onReceiveValue(null);
//                    return;
//                }
                Uri result = (null == data) ? null : data.getData();
                if (result == null) {
                    // 从相机获取
                    File cameraFile = new File(mCameraFilePath);
                    if (cameraFile.exists()) {
                        result = Uri.fromFile(cameraFile);
                        // 扫描相册
                        CameasActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                    }
                }
//                mUploadFile.onReceiveValue(null);
                try {
                    if (mUploadFile != null) {
                        mUploadFile.onReceiveValue(result);
                        mUploadFile = null;
                    }
                } catch (Exception e) {
//                    MeilaLog.e(TAG, e);
                }
            } else {
                // 获取需上传文件失败或者取消操作
                if (mUploadFile != null) {
                    mUploadFile.onReceiveValue(null);
                    mUploadFile = null;
                }
            }
        }
	
	
	private Intent createDefaultOpenableIntent() {
        // Create and return a chooser with the default OPENABLE
        // actions including the camera, camcorder and sound
        // recorder where available.
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");

        Intent camcorderIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Intent soundRecorderIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

        Intent chooser = createChooserIntent(createCameraIntent(), soundRecorderIntent);
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        chooser.putExtra(Intent.EXTRA_TITLE, "请选择要上传的文件");
        return chooser;
    }

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        
        return cameraIntent;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
//        chooser.putExtra("android.intent.action.GET_CONTENT", intents);
        return chooser;
    }
    		
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
			wb.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
