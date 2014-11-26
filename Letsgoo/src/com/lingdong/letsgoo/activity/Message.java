package com.lingdong.letsgoo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicMessage;
import com.lingdong.letsgoo.adapter.Messageadapter;
import com.lingdong.letsgoo.listview.CustomListView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Message extends Fragment{
	protected static final String TAG = null;
	private CustomListView messagelist;
	private Messageadapter messageadapter;
    private static final int LOAD_DATA_FINISH = 10;
    private static final int REFRESH_DATA_FINISH =11;
	private List<DynamicMessage> list = new ArrayList<DynamicMessage>();
//	DynamicMessage msg1=new DynamicMessage();
//	DynamicMessage msg2=new DynamicMessage();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAG", "---onCreateView");
		return inflater.inflate(R.layout.message, null);
	}
	
	public void onActivityCreated(Bundle savedInstanceState)  {
		super.onActivityCreated(savedInstanceState);
		messagelist = (CustomListView) this.getView().findViewById(R.id.messagelistview);
		//list=getData();
//		list.add(msg1);
//		list.add(msg2);
		messageadapter=new Messageadapter(getActivity(),list);
		messagelist.setAdapter(messageadapter);
		messagelist.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 涓嬫媺鍒锋柊
                Log.e(TAG, "onRefresh");
                loadData(0);
            }

        });
		messagelist.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO 鍔犺浇鏇村
                Log.e(TAG, "onLoad");
                loadData(1);
            }
        });
    }
	
    public void loadData(final int type){
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(type==0){
//                	list.clear();
//                	list.add(msg1);
//                    messageadapter=new Messageadapter(getActivity(), list);
//                	messagelist.setAdapter(messageadapter);
//                	
//                	myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
//                }else if(type==1){
//                	messageadapter.addItem(msg2);
//                	
//                    myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
//                }
//            }
//        }.start();
    	if(type==0){
    		myHandler.postDelayed(new Runnable() {
    			@Override
    			public void run() {
    				messagelist.onLoadMoreComplete();
    				messageadapter.notifyDataSetChanged();
    				list.clear();
    				//list.add(msg1);
    				messageadapter = new Messageadapter(getActivity(), list);
    				messagelist.setAdapter(messageadapter);
    				messagelist.onRefreshComplete();
    			}
    		}, 2000);

    	}
    	if(type==1){
    		myHandler.postDelayed(new Runnable() {
    			@Override
    			public void run() {
    				messagelist.onRefreshComplete();
    				messageadapter.notifyDataSetChanged();
    				//messageadapter.addItem(msg2);
    	    		messagelist.onLoadMoreComplete();
    			}
    		}, 2000);
    	}
    }
	
    private Handler myHandler = new Handler(){
    	@Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_DATA_FINISH:
                	if(new Messageadapter(getActivity(), null)!=null){
                    	new Messageadapter(getActivity(), null).notifyDataSetChanged();
                    }
                    messagelist.onRefreshComplete();
                    break;
                case LOAD_DATA_FINISH:
                	if(new Messageadapter(getActivity(), null)!=null){
                        new Messageadapter(getActivity(), null).notifyDataSetChanged();
                    }
                	messagelist.onLoadMoreComplete();
                    break;
                default:
                    break;
            }
        }

    };
    
    
	public List<DynamicMessage> getData(){
		String url = "http://192.168.1.17:3000/login.json";
		RequestQueue requestQueue = Volley.newRequestQueue(this.getView().getContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				//Toast.makeText(Message.this, response.toString(),1).show();
				list = JSON.parseArray(response.toString(), DynamicMessage.class);
				Messageadapter messageadapter = new Messageadapter(Message.this.getActivity(), list);
				messagelist.setAdapter(messageadapter);
			}
		}, new Response.ErrorListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(Message.this.getActivity(),"获取消息失败", 1).show();
			}
		});
		jsonArrayRequest.setTag("request");
		requestQueue.add(jsonArrayRequest);
		return list;
	}
}
