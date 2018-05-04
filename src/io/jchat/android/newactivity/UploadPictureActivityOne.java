package io.jchat.android.newactivity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
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
import android.widget.Toast;
import io.jchat.android.entity.Updatefarmcoop;
import io.jchat.android.http.PhotoUpload;
import io.jchat.android.view.SelfcamereOrpicture;

public class UploadPictureActivityOne extends Activity {

	private WebView wb;
	private ImageView bank, camere, imag_biao;
	private Button last, next;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	Uri mImageCaptureUri;
	String miageuri = "";
	private Context context;
	private ProgressBar myProgressBar;
	private ValueCallback<Uri> mUploadFile;
	private String mCameraFilePath = "myfiles.png";
	private String mcamera;
	private static final int REQUEST_UPLOAD_FILE_CODE = 1003; // 选择文件请求码
	SelfcamereOrpicture selfcamere = new SelfcamereOrpicture();// 图片压缩类
	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_uppictureone);
		context = this;
		PushAgent.getInstance(context).onAppStart();
		bank = (ImageView) findViewById(R.id.bank);
		last = (Button) findViewById(R.id.last);
		camere = (ImageView) findViewById(R.id.cameres);
		next = (Button) findViewById(R.id.next);
		myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
		wb = (WebView) findViewById(R.id.web);
		imag_biao = (ImageView) findViewById(R.id.biaozh);
		mcamera = getIntent().getStringExtra("carid") + "0";
		initView();
		setUI();
	}

	public void initView() {
		Updatefarmcoop.setActivityOne(UploadPictureActivityOne.this);

		// Glide.with(this).load("http://imgsrc.baidu.com/forum/pic/item/a860b2a1cd11728bf7ad2fadc8fcc3cec2fd2c19.jpg")
		// .into(imag_biao);

		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		last.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		camere.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startCamearPicCut();
				// Intent in = new Intent();
				// in.setClass(UploadPictureActivityOne.this,
				// CameasActivity.class);
				// startActivity(in);
			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				miageuri = mImageCaptureUri + "";

				// photoup.uploadFile();
				// new MyTeard().start();

				Intent in = new Intent();
				in.putExtra("carid", getIntent().getStringExtra("carid"));
				in.setClass(UploadPictureActivityOne.this, UploadPictureActivitySensorOne.class);
				startActivity(in);
			}
		});
	}

	public void setUI() {
		WebSettings wSet = wb.getSettings();
		wSet.setJavaScriptEnabled(true);
		wSet.setSupportZoom(true);
		String URL = "http://123.57.72.71/static/mobileClient/upload.html";
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
			wb.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyTeard extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			PhotoUpload photoup = new PhotoUpload();

			photoup.uploadFile(tempFile, isBm);

			// final Map<String, String> params = new HashMap<String, String>();
			// final Map<String, File> files = new HashMap<String, File>();
			// files.put("uploadfile", tempFile);
			// try {
			// final String request = photoup.post( params, files);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}

	private void startCamearPicCut() {
		// TODO Auto-generated method stub
		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		intent.putExtra("camerasensortype", 1);// 调用前置摄像头
		intent.putExtra("autofocus", true);// 自动对焦
		intent.putExtra("fullScreen", false);// 全屏
		intent.putExtra("showActionIcons", false);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	public ByteArrayInputStream isBm;

	@SuppressWarnings("static-access")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			// setPicToView(data);
			// startPhotoZoom(Uri.fromFile(tempFile), 350);//对照片剪切

			mImageCaptureUri = Uri.fromFile(tempFile);

			Bitmap photoBmp = null;

			if (mImageCaptureUri != null) {
				try {
					photoBmp = selfcamere.getBitmapFormUri(UploadPictureActivityOne.this, mImageCaptureUri);
					isBm = selfcamere.isBm;
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
		case PHOTO_REQUEST_CUT:
			if (data != null) {
				// setPicToView(data);
			}
			break;
		case REQUEST_UPLOAD_FILE_CODE:
			Uri result = null;
			// 从相机获取
			File cameraFile = new File(mCameraFilePath);
			if (cameraFile.exists()) {
				Bitmap photoBmp1 = null;
				try {
					photoBmp1 = selfcamere.getBitmapFormUri(UploadPictureActivityOne.this, Uri.fromFile(cameraFile));// 图片压缩
					result = Uri
							.parse(MediaStore.Images.Media.insertImage(getContentResolver(), photoBmp1, mcamera, mcamera));
					setPicToView(photoBmp1);// 展示
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Toast.makeText(UploadPictureActivityOne.this, result + "", Toast.LENGTH_LONG).show();
				// .parse(MediaStore.Images.Media.insertImage(getContentResolver(),
				// photoBmp1, "", ""));
				// 扫描相册
				UploadPictureActivityOne.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));// 传输

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

		default:
			// 获取需上传文件失败或者取消操作
			if (mUploadFile != null) {
				mUploadFile.onReceiveValue(null);
				mUploadFile = null;
			}
			break;
		}

	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Bitmap picdata) {
		// Bundle bundle = picdata.getExtras();
		// if (bundle != null) {
		final Bitmap photo = picdata;
		// Bitmap bit = big(photo, 90, 80);
		camere.setImageBitmap(photo);

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
		mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator + mcamera+".jpg";
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));

		return cameraIntent;
	}

	private Intent createChooserIntent(Intent... intents) {
		Intent chooser = new Intent(Intent.ACTION_CHOOSER);
		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
		// chooser.putExtra("android.intent.action.GET_CONTENT", intents);
		return chooser;
	}

}
