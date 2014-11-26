package com.lingdong.letsgoo.fragment;

import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;

import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.activity.Personal_canyu;
import com.lingdong.letsgoo.activity.Personal_fabu;
import com.lingdong.letsgoo.activity.Personal_setting;
import com.lingdong.letsgoo.activity.Personal_shoucang;
import com.lingdong.letsgoo.activity.Register_first;
import com.lingdong.letsgoo.resource.CircleImageView;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonPage extends Fragment {
	private FragmentActivity activity;
	private PopupWindow mpopupWindow;
	private SmartImageView bigimage;
	// private RelativeLayout my_caogao;
	// private RelativeLayout my_fangke;
	private RelativeLayout my_shoucang;
	private RelativeLayout my_canyu;
	private RelativeLayout my_fabu;
	private RelativeLayout my_shezhi;
	private CircleImageView image;
	private TextView myname;
	private ImageView my_renzheng;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAG", "---onCreateView");
		return inflater.inflate(R.layout.personal, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("TAG", "---onActivityCreated");
		// TODO Auto-generated method stub
		ExitApplication.getInstance().addActivity(getActivity());
		super.onActivityCreated(savedInstanceState);
		activity = this.getActivity();
		// my_caogao = (RelativeLayout) this.getView()
		// .findViewById(R.id.my_caogao);
		// my_fangke = (RelativeLayout) this.getView()
		// .findViewById(R.id.my_fangke);
		my_shoucang = (RelativeLayout) this.getView().findViewById(
				R.id.my_shoucang);
		my_canyu = (RelativeLayout) this.getView().findViewById(R.id.my_canyu);
		my_fabu = (RelativeLayout) this.getView().findViewById(R.id.my_fabu);
		my_shezhi = (RelativeLayout) this.getView()
				.findViewById(R.id.my_shezhi);
		image = (CircleImageView) this.getView().findViewById(R.id.my_image);
		myname=(TextView)this.getView().findViewById(R.id.my_name);
		my_renzheng=(ImageView)this.getView().findViewById(R.id.my_renzhengsure);
		
		image.setImageUrl("");
		myname.setText("");
		
		my_renzheng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.initSDK(getActivity(), "45c34afd98c6", "1b8e98a051b0c70b65da3721b26e3f36");
				Register_first register_first=new Register_first();
				register_first.show(getActivity());
			}
		});
		/*
		 * my_caogao.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(activity, Personal_caogao.class);
		 * activity.startActivity(intent); } });
		 * 
		 * my_fangke.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(activity, Personal_visitor.class);
		 * activity.startActivity(intent); } });
		 */

		my_shoucang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, Personal_shoucang.class);
				activity.startActivity(intent);
			}
		});

		my_canyu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, Personal_canyu.class);
				activity.startActivity(intent);
			}
		});

		my_fabu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, Personal_fabu.class);
				activity.startActivity(intent);
			}
		});

		my_shezhi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, Personal_setting.class);
				activity.startActivity(intent);
			}
		});

		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				RotateAnimation animation = new RotateAnimation(0, 360,
//						Animation.RELATIVE_TO_SELF, 0.5f,
//						Animation.RELATIVE_TO_SELF, 0.5f);
//				// 设置动画插值器 被用来修饰动画效果,定义动画的变化率
//				animation.setInterpolator(new DecelerateInterpolator());
//				// 设置动画执行时间
//				animation.setDuration(2000);
//				v.startAnimation(animation);
				
				View view = View.inflate(getActivity(), R.layout.bigimage, null);
				bigimage=(SmartImageView)view.findViewById(R.id.my_bigimage);
				bigimage.setImageUrl("");

				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						mpopupWindow.dismiss();
					}
				});
				view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
				
				if(mpopupWindow==null){
					mpopupWindow = new PopupWindow(getActivity());
					mpopupWindow.setWidth(LayoutParams.MATCH_PARENT);
					mpopupWindow.setHeight(LayoutParams.MATCH_PARENT);
					mpopupWindow.setBackgroundDrawable(new BitmapDrawable());

					mpopupWindow.setFocusable(true);
					mpopupWindow.setOutsideTouchable(true);
				}
				
				mpopupWindow.setContentView(view);
				mpopupWindow.showAtLocation(image, Gravity.CENTER, 0, 0);
				mpopupWindow.update();
			}
		});

	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("TAG", "---onAttach");
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("TAG", "---onCreate");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.i("TAG", "---onDestroy");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		Log.i("TAG", "---onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("TAG", "---onResume");
		// TODO Auto-generated method stub
		super.onResume();
	}
	

}
