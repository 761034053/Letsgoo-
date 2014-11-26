package com.lingdong.letsgoo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicMessage;
import com.lingdong.letsgoo.adapter.Personal_visitoradapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Personal_visitor extends Activity {
	private ImageView fanhui;
	private TextView number1;
	private TextView number2;
	private ListView visitorlist;
	private int screen_width;
	private Personal_visitoradapter visitoradapter;
	private List<DynamicMessage> list = new ArrayList<DynamicMessage>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_visitor);
		
		number1=(TextView)this.findViewById(R.id.visitornumber1);
		number2=(TextView)this.findViewById(R.id.visitornumber2);
		screen_width = getWindowManager().getDefaultDisplay().getWidth();
		LayoutParams lp1 = number1.getLayoutParams();
		lp1.width = (int) (screen_width/(3.4));
		
		fanhui=(ImageView)this.findViewById(R.id.fanhui_visitor);
		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Personal_visitor.this.finish();
			}
		});
		
		visitorlist=(ListView)this.findViewById(R.id.visitor_listview);
		list=getData();
		number1.setText(""+list.size());
		number2.setText("今日访客："+list.size());
	}
	
	public List<DynamicMessage> getData(){  
		String url = "http://192.168.1.17:3000/login.json";
		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				//Toast.makeText(Message.this, response.toString(),1).show();
				list = JSON.parseArray(response.toString(), DynamicMessage.class);
				visitoradapter=new Personal_visitoradapter(Personal_visitor.this,list);
			    visitorlist.setAdapter(visitoradapter);
			}
		}, new Response.ErrorListener() { 

			@SuppressLint("ShowToast")
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(Personal_visitor.this, "获取访客失败", 1).show();
			}
		});
		jsonArrayRequest.setTag("request");
		requestQueue.add(jsonArrayRequest);
        return list;  
    }  

}
