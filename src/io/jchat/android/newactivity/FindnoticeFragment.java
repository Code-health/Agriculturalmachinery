package io.jchat.android.newactivity;

import java.util.ArrayList;
import java.util.List;

import com.lnpdit.agriculturalmachinery.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import io.jchat.android.adapter.FindNoticeAdapter;
import io.jchat.android.entity.FindnoticeEntity;

public class FindnoticeFragment extends Fragment{
	
	private ListView lv;
	List<FindnoticeEntity> list=new ArrayList<FindnoticeEntity>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_find, container,false);
		lv=(ListView) view.findViewById(R.id.listView);
		initView();
		
		return view;
	}
	
	public void initView(){
		for(int i=0;i<1;i++){
			FindnoticeEntity findnotice=new FindnoticeEntity();
			findnotice.setText("方式方法");
			findnotice.setTextbady("  SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			findnotice.setType(1);
			list.add(findnotice);
		}
		
		FindNoticeAdapter findnotice=new FindNoticeAdapter(NewMainActivity.getContext(),list);
		lv.setAdapter(findnotice);
		
	}
	
	private void setListViewPos(int pos) {
		lv.setSelection(pos); //自动到最下边

	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setListViewPos(list.size()-1);
	}

}
