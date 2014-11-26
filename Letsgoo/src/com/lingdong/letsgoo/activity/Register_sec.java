package com.lingdong.letsgoo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.Header;

import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.sax.EndElementListener;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class Register_sec extends Activity{
	private ImageView register_back;
	//private RelativeLayout next;
	private ImageView takephoto;
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private Bitmap bitmap;
	private static Boolean success = false;
	private File tempFile;
	
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registersec);
		
		register_back = (ImageView) this.findViewById(R.id.register_back2);
		//next = (RelativeLayout) this.findViewById(R.id.register_next2);
		takephoto=(ImageView)this.findViewById(R.id.register_takephoto);
		
		register_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Register_sec.this.finish();
			}
		});

//		next.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(Register_sec.this,
//						Register_thir.class);
//				Register_sec.this.startActivity(intent);
//			}
//		});
		
		takephoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				camera(v);
				if(upload(v))
				{
					Intent intent = new Intent(Register_sec.this,
							Register_thir.class);
					Register_sec.this.startActivity(intent);
				}
			}
		});
	}
	
	/*
	 * 上传图片
	 */
	@SuppressLint("ShowToast")
	public Boolean upload(View view) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			byte[] buffer = out.toByteArray();

			byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
			String photo = new String(encode);

			RequestParams params = new RequestParams();
			params.put("photo", photo);
			String url = "http://110.65.99.66:8080/jerry/UploadImgServlet";

			AsyncHttpClient client = new AsyncHttpClient();
			client.post(url, params, new AsyncHttpResponseHandler() {
				@SuppressLint("ShowToast")
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					try {
						if (statusCode == 200) {

							Toast.makeText(Register_sec.this, "照片上传成功!", 1)
									.show();
							success = true;
						} else {
							Toast.makeText(Register_sec.this,
									"网络访问异常，错误码：" + statusCode, 1).show();

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(Register_sec.this,
								"照片上传失败，请重试！" + statusCode, 1).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Toast.makeText(Register_sec.this,
							"网络访问异常，错误码  > " + statusCode, 1).show();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}
	
	
	/*
	 * 从相机获取
	 */
	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}
	
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressLint("ShowToast")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
			} else {
				Toast.makeText(Register_sec.this, "未找到存储卡，无法存储照片！", 1)
						.show();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
