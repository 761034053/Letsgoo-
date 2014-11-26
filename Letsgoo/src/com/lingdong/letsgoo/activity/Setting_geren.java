package com.lingdong.letsgoo.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.lingdong.letsgoo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class Setting_geren extends Activity {
	private ImageView fanhui;
	//private CircleImageView image;
	private EditText name;
	private EditText school;
	private EditText major;
	private ImageView boy;
	private ImageView girl;
	private FrameLayout settingpost;
	private static String sex =null;
	private String successresponse=null;
	//private static Boolean imageCheck = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_geren);

		fanhui = (ImageView) this.findViewById(R.id.fanhui_geren);

		//image = (CircleImageView) this.findViewById(R.id.setting_geren_image);
		name = (EditText) this.findViewById(R.id.setting_geren_name);
		school = (EditText) this.findViewById(R.id.setting_school);
		major = (EditText) this.findViewById(R.id.setting_major);

		boy = (ImageView) this.findViewById(R.id.setting_boy);
		girl = (ImageView) this.findViewById(R.id.setting_girl);
		settingpost = (FrameLayout) this.findViewById(R.id.setting_sure);

		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Setting_geren.this.finish();
			}
		});

		boy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boy.setImageResource(R.drawable.end_boy);
				girl.setImageResource(R.drawable.first_girl);
				sex = "boy";
			}
		});

		girl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boy.setImageResource(R.drawable.first_boy);
				girl.setImageResource(R.drawable.end_girl);
				sex = "girl";
			}
		});

//		image.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				/* 开启Pictures画面Type设定为image */
//				intent.setType("image/*");
//				/* 使用Intent.ACTION_GET_CONTENT这个Action */
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				/* 取得相片后返回本画面 */
//				startActivityForResult(intent, 1);
//			}
//		});

		settingpost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (DataCheck()) {
					new AlertDialog.Builder(Setting_geren.this).setTitle("确认").setMessage("确定修改吗？")
					.setPositiveButton("确定",new DialogInterface.OnClickListener() {
						public void onClick(
							   DialogInterface dialog,int which) {
							   // TODO Auto-generated method stub
							   Map<String, String> map = new HashMap<String, String>();
							   map.put("user_id", "28");
							   map.put("nickname", name.getText().toString().trim());
							   map.put("gender", sex);
							   map.put("school", school.getText().toString().trim());
							   map.put("major", major.getText().toString().trim());
							   
							   String url = "http://192.168.1.7:3000/update_user";
							   RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
							   JSONObject jsonObject = new JSONObject(map);
							   JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.POST,url,jsonObject,
										  new Response.Listener<JSONObject>() {
										  @SuppressLint("ShowToast")
										@Override
										  public void onResponse(JSONObject response) 
										  {
											  try {
												  successresponse=response.get("success").toString();
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											if(successresponse.equals("1"))
											{ 
											Toast.makeText(Setting_geren.this,"修改成功",1).show();
											Intent intent = new Intent(Setting_geren.this, Personal_setting.class);
											Setting_geren.this.startActivity(intent);
											Setting_geren.this.finish();
											}
											else
											{
										    Toast.makeText(Setting_geren.this,"修改失败",1).show();
											}
										  }
							   },new Response.ErrorListener() {
										  @SuppressLint("ShowToast")
										  @Override
										  public void onErrorResponse(VolleyError error) 
										  {
											  Toast.makeText(Setting_geren.this,"修改失败", 1).show();
										  }
							  });
							              requestQueue.add(jsonRequest);
						   //
						}
				}).setNegativeButton("取消", null).show();
					
				}
			}
		});
	}

	@SuppressLint({ "ShowToast", "ResourceAsColor" })
	public Boolean DataCheck() {
		Boolean resoult = true;
		if (TextUtils.isEmpty(name.getText().toString())) {
			Toast.makeText(Setting_geren.this, "请填写昵称", Toast.LENGTH_SHORT).show();
			resoult = false;
		}
		if (sex ==null) {
            Toast.makeText(Setting_geren.this, "请选择性别", Toast.LENGTH_SHORT).show();
			resoult = false;
		}
		return resoult;
	}

//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			Uri uri = data.getData();
//			Log.e("uri", uri.toString());
//			ContentResolver cr = this.getContentResolver();
//			try {
//				Bitmap bitmap = BitmapFactory.decodeStream(cr
//						.openInputStream(uri));
//				/* 将Bitmap设定到ImageView */
//				image.setImageBitmap(bitmap);
//			} catch (FileNotFoundException e) {
//				Log.e("Exception", e.getMessage(), e);
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//		imageCheck = true;
//	}
}
