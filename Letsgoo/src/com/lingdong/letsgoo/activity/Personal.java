package com.lingdong.letsgoo.activity;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.resource.CircleImageView;
import com.loopj.android.image.SmartImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Personal extends FragmentActivity {
	// private RelativeLayout my_caogao;
	// private RelativeLayout my_fangke;
	private RelativeLayout my_shoucang;
	private RelativeLayout my_canyu;
	private RelativeLayout my_fabu;
	private RelativeLayout my_shezhi;
	private CircleImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal);
		// 此时，欢迎界面以后不用显示
		// my_caogao = (RelativeLayout) this.findViewById(R.id.my_caogao);
		// my_fangke = (RelativeLayout) this.findViewById(R.id.my_fangke);
		my_shoucang = (RelativeLayout) this.findViewById(R.id.my_shoucang);
		my_canyu = (RelativeLayout) this.findViewById(R.id.my_canyu);
		my_fabu = (RelativeLayout) this.findViewById(R.id.my_fabu);
		my_shezhi = (RelativeLayout) this.findViewById(R.id.my_shezhi);
		image = (CircleImageView) this.findViewById(R.id.my_image);

		/*
		 * my_caogao.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(Personal.this,
		 * Personal_caogao.class); Personal.this.startActivity(intent); } });
		 * 
		 * my_fangke.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(Personal.this,
		 * Personal_visitor.class); Personal.this.startActivity(intent); } });
		 */

		my_shoucang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Personal.this,
						Personal_shoucang.class);
				Personal.this.startActivity(intent);
			}
		});

		my_canyu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Personal.this, Personal_canyu.class);
				Personal.this.startActivity(intent);
			}
		});

		my_fabu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Personal.this, Personal_fabu.class);
				Personal.this.startActivity(intent);
			}
		});

		my_shezhi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Personal.this,
						Personal_setting.class);
				Personal.this.startActivity(intent);
			}
		});

	}

}
