package com.lingdong.letsgoo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lingdong.letsgoo.R;

public class Setting_tag extends Activity{
	private ImageView fanhui;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_tag);
		
		fanhui=(ImageView)this.findViewById(R.id.fanhui_tag);
        fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Setting_tag.this.finish();
			}
		});
	}

}