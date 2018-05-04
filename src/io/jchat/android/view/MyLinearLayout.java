package io.jchat.android.view;

import com.lnpdit.agriculturalmachinery.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
    private Context context;
    private Button add;
    private Button setting;
    private Button synchronize;
    private Button exit;
   
    public MyLinearLayout(Context context) {
            super(context);
            this.context = context;
            setView();
            // TODO Auto-generated constructor stub
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            setView();
            // TODO Auto-generated constructor stub
    }
   
    private void setView() {
            setTag(MyLinearLayout.class);
           
            LayoutInflater mInflate = LayoutInflater.from(context);
            final View menu = mInflate.inflate(R.layout.menu_cooper, null);
            menu.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,100));
            addView(menu);
    }

}
