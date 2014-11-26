package com.lingdong.letsgoo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicMessage;
import com.lingdong.letsgoo.adapter.Messageadapter;
import com.lingdong.letsgoo.listview.CustomListView;

@SuppressLint("HandlerLeak")
public class MessagePage extends Fragment {

	protected static final String TAG = null;
	private static CustomListView messagelist;
	private static int num = 0;
	private static Messageadapter messageadapter;
	private List<DynamicMessage> list = new ArrayList<DynamicMessage>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAG", "---onCreateView");
		return inflater.inflate(R.layout.message, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("TAG", "---onActivityCreated");
		// TODO Auto-generated method stub
		ExitApplication.getInstance().addActivity(getActivity());
		super.onActivityCreated(savedInstanceState);
		messagelist = (CustomListView) this.getView().findViewById(
				R.id.messagelistview);

		messageadapter = new Messageadapter(this.getActivity()
				.getApplicationContext(), list);
		messagelist.setAdapter(messageadapter);
		loadData(0);

		messagelist
				.setOnRefreshListener(new CustomListView.OnRefreshListener() {
					@Override
					public void onRefresh() {

						Log.e(TAG, "onRefresh");
						loadData(0);
					}

				});
		messagelist.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				Log.e(TAG, "onLoad");
				loadData(1);
			}
		});
	}

	public void loadData(final int type) {
		Handler myHandler = new Handler();
		if (type == 0) {
			myHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					messagelist.onLoadMoreComplete();
					messageadapter.notifyDataSetChanged();
					num = 0;
					list.clear();
					messageadapter = new Messageadapter(getActivity()
							.getApplicationContext(), list);
					messagelist.setAdapter(messageadapter);
					getData(num);
					messagelist.onRefreshComplete();
				}
			}, 2000);

		}
		if (type == 1) {
			myHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					messagelist.onRefreshComplete();
					messageadapter.notifyDataSetChanged();
					if (list.isEmpty()) {
						num = 0;
					} else {
						num = list.get(list.size() - 1).getDynamic_id();
					}
					list.clear();
					getData(num);
					messagelist.onLoadMoreComplete();
				}
			}, 2000);
		}

	}

	public void getData(int acnumber) {
		// 参数添加
		Integer user_id = 28;
		String url = "http://192.168.1.7:3000/get_personaldynamics.json"
				+ "?user_id=" + user_id + "&dynamic_id=" + acnumber;
		RequestQueue requestQueue = Volley.newRequestQueue(this.getView()
				.getContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
				new Listener<JSONArray>() {
					@SuppressLint("ShowToast")
					@Override
					public void onResponse(JSONArray response) {
						// Toast.makeText(activity, response.toString(),
						// 1).show();
						list.clear();
						list = JSON.parseArray(response.toString(),
								DynamicMessage.class);
						for (int i = 0; i < list.size(); i++) {
							messageadapter.addItem(list.get(i));
						}
					}
				}, new Response.ErrorListener() {

					@SuppressLint("ShowToast")
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "获取消息失败", 1).show();
					}
				});
		jsonArrayRequest.setTag("request");
		requestQueue.add(jsonArrayRequest);
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
