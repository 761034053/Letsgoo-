package com.lingdong.letsgoo.activity;

import com.lingdong.letsgoo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class Personal_caogao extends Activity{

	private ImageView fanhui;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_caogao);
		
		fanhui=(ImageView)this.findViewById(R.id.fanhui_caogao);
		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Personal_caogao.this.finish();
			}
		});
	}
	
	
}
