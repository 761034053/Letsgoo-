package com.lingdong.letsgoo.fragment;


import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.activity.Message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainFragment extends FragmentActivity implements OnClickListener{
	long exitTime = 0;
	private RadioButton button1;
	private RadioButton button2;
	private RadioButton button3;
	private Fragment fragment1=null;
	private Fragment fragment2=null;
	private Fragment fragment3=null;
	
	private FragmentManager fragmentManager=getSupportFragmentManager();
	private FragmentTransaction ft;
	
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_bottom);
		
		initView();
		ft = fragmentManager.beginTransaction();
		homepage();
		ft.commit();
	}

	private void initView(){
		button1 = (RadioButton)findViewById(R.id.fragment_bottom_shouye);
		button2 = (RadioButton)findViewById(R.id.fragment_bottom_xiaoxi);
		button3 = (RadioButton)findViewById(R.id.fragment_bottom_wode);
		
		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button1.setSelected(true);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ft = fragmentManager.beginTransaction();
		//把显示的Fragment隐藏
		setSelected();
		switch (v.getId()) {
		case R.id.fragment_bottom_shouye:
			button1.setSelected(true);
			homepage();
			break;
		case R.id.fragment_bottom_xiaoxi:
			button2.setSelected(true);
			messagepage();
			
			break;
		case R.id.fragment_bottom_wode:
			button2.setSelected(true);
			personpage();
			break;
		}
		ft.commit();
	}
	
	private void setSelected(){
		button1.setSelected(false);
		button2.setSelected(false);
		button3.setSelected(false);
		if(fragment1 != null){
			//隐藏Fragment
			ft.hide(fragment1);
		}
		if(fragment2 != null){
			ft.hide(fragment2);
		}
		if(fragment3 != null){
			ft.hide(fragment3);
		}
	}
	
	private void homepage(){
		if(fragment1 == null){
			fragment1 = new Message();
			ft.add(R.id.fragment_container, fragment1);
		}else{
			//显示Fragment
			ft.show(fragment1);
		}
	}
	private void messagepage(){
		if(fragment2 == null){
			fragment2 = new MessagePage();
			ft.add(R.id.fragment_container, fragment2);
		}else{
			ft.show(fragment2);
		}
		
	}
	private void personpage(){
		if(fragment3 == null){
			fragment3 = new PersonPage();
			ft.add(R.id.fragment_container, fragment3);
		}else{
			ft.show(fragment3);
		}
		
	}
	
	@Override
	   public void onBackPressed() {
	       if ((System.currentTimeMillis() - exitTime) > 2000) {
	           // ToastUtil.makeToastInBottom("再按一次退出应用", MainMyselfActivity);
	           Toast.makeText(MainFragment.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
	           exitTime = System.currentTimeMillis();
	           return;
	       }
	      ExitApplication.getInstance().exit();
	      
	   }
}
