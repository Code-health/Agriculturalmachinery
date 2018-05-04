package io.jchat.android.newactivity;

import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageFeedbackActivity extends Activity {

	private Button out, go;
	private ImageView bank;
	private LinearLayout canshu;
	private String type = "0";
	private TextView mian, lich, shen, st, et;
	private List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		bank = (ImageView) findViewById(R.id.bank);
		canshu = (LinearLayout) findViewById(R.id.canshu);
		out = (Button) findViewById(R.id.out);
		go = (Button) findViewById(R.id.go);
		et = (TextView) findViewById(R.id.etime);
		mian = (TextView) findViewById(R.id.mianji);
		lich = (TextView) findViewById(R.id.lichen);
		shen = (TextView) findViewById(R.id.shen);
		st = (TextView) findViewById(R.id.stime);
		
		PushAgent.getInstance(MessageFeedbackActivity.this).onAppStart();

		Intent in = getIntent();
		type = in.getStringExtra("type");
		if (type.equals("1")) {
			list = in.getStringArrayListExtra("list");
			mian.setText("作业面积："+list.get(0));
			lich.setText("作业里程："+list.get(1));
			shen.setText("深度比例(20-25cm)："+list.get(2));
			st.setText("开始时间："+list.get(3));
			et.setText("结束时间："+list.get(4));
		}
		
		canshu.setVisibility(1);
		if (type.equals("0")) {
			canshu.setVisibility(8);
		}
		initView();
	}

	public void initView() {

		bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
