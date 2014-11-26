package com.lingdong.letsgoo.activity;

import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.fragment.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Register_thir extends Activity{
	private ImageView register_back;
	private RelativeLayout next;
	
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthd);
		
		register_back = (ImageView) this.findViewById(R.id.register_back3);
		next = (RelativeLayout)this.findViewById(R.id.register_next3);
		
		register_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Register_thir.this.finish();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Register_thir.this,
						MainFragment.class);
				Register_thir.this.startActivity(intent);
				Register_thir.this.finish();
			}
		});
	}
}
