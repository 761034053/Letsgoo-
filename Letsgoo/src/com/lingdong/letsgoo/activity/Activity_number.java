package com.lingdong.letsgoo.activity;

import java.util.ArrayList;
import java.util.List;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicPart;
import com.lingdong.letsgoo.adapter.DynamicPartAdapter;
import com.lingdong.letsgoo.listview.CustomListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_number extends Activity {
	protected static final String TAG = null;
	private ImageView fanhui;
	private TextView number;
	private CustomListView numberlist;
	private DynamicPartAdapter numberadapter;
	private List<DynamicPart> list = new ArrayList<DynamicPart>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.canyu_activity);

		fanhui = (ImageView) this.findViewById(R.id.fanhui_canyuactivity);
		number = (TextView) this.findViewById(R.id.canyu_activitynum);
		numberlist = (CustomListView) this
				.findViewById(R.id.canyu_numberlistview);

		list.add(new DynamicPart(0, "name1", "", "school1", "boy", "123456"));
		list.add(new DynamicPart(0, "name2", "", "school2", "girl",
				"1234567"));
		list.add(new DynamicPart(1, "name3", "", "school3", "boy", "12345678"));
		list.add(new DynamicPart(1, "name4", "", "school4", "girl",
				"123456789"));
		numberadapter = new DynamicPartAdapter(list, getLayoutInflater(),
				Activity_number.this);
		numberlist.setAdapter(numberadapter);
		number.setText(list.size() + "人参与了活动");

		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Activity_number.this.finish();
			}
		});

		numberlist
				.setOnRefreshListener(new CustomListView.OnRefreshListener() {
					@Override
					public void onRefresh() {

						Log.e(TAG, "onRefresh");
						//loadData(0);
					}

				});
		numberlist.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				Log.e(TAG, "onLoad");
				//loadData(1);
			}
		});
	}

}
