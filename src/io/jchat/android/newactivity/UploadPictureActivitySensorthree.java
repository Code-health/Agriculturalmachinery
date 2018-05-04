package io.jchat.android.newactivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.view.SelfcamereOrpicture;

public class UploadPictureActivitySensorthree extends Activity implements OnClickListener{

	
	private ImageView bank, camses;
	private Button last, next;
	private WebView wb;
	private ProgressBar myProgressBar;
	private ValueCallback<Uri> mUploadFile;
	private String mCameraFilePath = "myfiles.png";
	private static final int REQUEST_UPLOAD_FILE_CODE = 1003; // 选择文件请求码
	SelfcamereOrpicture selfcamere = new SelfcamereOrpicture();// 图片压缩类
//	Updatefarmcoop updatefarmcoop=new Updatefarmcoop();
	String card="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sensorthree);
		bank = (ImageView) findViewById(R.id.bank);
		camses = (ImageView) findViewById(R.id.cameres);
		last = (Button) findViewById(R.id.last);
		myProgressBar=(ProgressBar) findViewById(R.id.myProgressBar);
		wb = (WebView) findViewById(R.id.web);
		card=getIntent().getStringExtra("carid");
		mCameraFilePath=card+"3.jpg";
		next = (Button) findViewById(R.id.next);
		PushAgent.getInstance(UploadPictureActivitySensorthree.this).onAppStart();
		initView();
		setUI();
	}
	public void initView(){
		Updatefarmcoop.setActivitySensorthree(UploadPictureActivitySensorthree.this);
		bank.setOnClickListener(this);
		next.setOnClickListener(this);
		last.setOnClickListener(this);
		camses.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bank:
		case R.id.last:
			finish();
			break;
		case R.id.cameres:
//			selfcamere.startCamearPicCut(UploadPictureActivitySensorthree.this);
			break;
			case R.id.next:
				Intent in=new Intent();
				in.putExtra("carid", card);
				in.setClass(UploadPictureActivitySensorthree.this, UploadPictureActivitySensorfour.class);
				startActivity(in);
				break;
		default:
			break;
		}
	}
	
	public void setUI() {
		WebSettings wSet = wb.getSettings();
		wSet.setJavaScriptEnabled(true);
		wSet.setSupportZoom(true);
		String URL = "http://123.57.72.71/fm/static/mobileClient/upload.html";
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
				mUploadFile = uploadFile;
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
	
	/**
	 * H5调用系统相机相册等的方法
	 */
	public Intent createDefaultOpenableIntent() {
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
		File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator + "browser-photos");
		cameraDataDir.mkdirs();
		mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));

		return cameraIntent;
	}

	private Intent createChooserIntent(Intent... intents) {
		Intent chooser = new Intent(Intent.ACTION_CHOOSER);
		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
		// chooser.putExtra("android.intent.action.GET_CONTENT", intents);
		return chooser;
	}
	
	@SuppressWarnings("static-access")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			// setPicToView(data);
			// startPhotoZoom(Uri.fromFile(tempFile), 350);//对照片剪切

			Uri mImageCaptureUri = Uri.fromFile(selfcamere.tempFile);

			Bitmap photoBmp = null;

			if (mImageCaptureUri != null) {

				try {
					photoBmp = selfcamere.getBitmapFormUri(UploadPictureActivitySensorthree.this, mImageCaptureUri);
					setPicToView(photoBmp);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case REQUEST_UPLOAD_FILE_CODE:
Uri result = null;
			
			if (result == null) {
				// 从相机获取
				File cameraFile = new File(mCameraFilePath);
				if (cameraFile.exists()) {
					
					try {
						result = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
								selfcamere.getBitmapFormUri(UploadPictureActivitySensorthree.this, Uri.fromFile(cameraFile)), "", ""));
						Bitmap photoBmp1 = selfcamere.getBitmapFormUri(UploadPictureActivitySensorthree.this, Uri.fromFile(cameraFile));// 图片压缩
						setPicToView(photoBmp1);// 展示
						// photoBmp1=selfcamere.compressImage(photoBmp1);
//						Uri result1 = Uri
//								.parse(MediaStore.Images.Media.insertImage(getContentResolver(), photoBmp1, "", ""));
						// 扫描相册
						UploadPictureActivitySensorthree.this
								.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));// 传输
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				if (mUploadFile != null) {
					mUploadFile.onReceiveValue(result);
					mUploadFile = null;
				}
			} catch (Exception e) {
				// MeilaLog.e(TAG, e);
			}
			break;
		}
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Bitmap picdata) {
		// Bundle bundle = picdata.getExtras();
		// if (bundle != null) {
		final Bitmap photo = picdata;
		camses.setImageBitmap(photo);

	}
}
