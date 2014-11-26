package com.lingdong.letsgoo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.Header;

import cn.smssdk.SMSSDK;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.resource.CircleImageView;
import com.lingdong.letsgoo.resource.SelectPopupWindow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Personal_setting extends Activity{
	private ImageView fanhui;
	private TextView setting_name;
	private RelativeLayout setting_geren;
	private RelativeLayout setting_phone;
	private RelativeLayout setting_exit;
	private CircleImageView setting_image;

	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	private Bitmap bitmap;
	private static Boolean success = false;
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	
	// private RelativeLayout setting_tag;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_setting);

		fanhui = (ImageView) this.findViewById(R.id.fanhui_setting);
		setting_image = (CircleImageView) this.findViewById(R.id.setting_image);
		setting_name = (TextView) this.findViewById(R.id.setting_name);
		setting_geren = (RelativeLayout) this.findViewById(R.id.setting_geren);
		setting_phone = (RelativeLayout) this.findViewById(R.id.setting_phone);
		setting_exit = (RelativeLayout) this.findViewById(R.id.setting_exit);
		// setting_tag=(RelativeLayout)this.findViewById(R.id.setting_tag);

		setting_image.setImageUrl("");
		setting_name.setText("LMN");

		setting_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Personal_setting.this).setTitle("确认").setMessage("确定退出吗？")
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setNegativeButton("取消", null).show();
			}
		});
		
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Personal_setting.this.finish();
			}
		});
		setting_geren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Personal_setting.this,
						Setting_geren.class);
				Personal_setting.this.startActivity(intent);
			}
		});
		setting_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.initSDK(Personal_setting.this, "45c34afd98c6",
						"1b8e98a051b0c70b65da3721b26e3f36");
				Setting_phone phone=new Setting_phone();
				phone.show(Personal_setting.this);
			}
		});

		setting_image.setOnClickListener(new OnClickListener() {
			SelectPopupWindow menuWindow;
			// 为弹出窗口实现监听类
			private OnClickListener itemsOnClick = new OnClickListener() {

				public void onClick(View v) {
					menuWindow.dismiss();
					switch (v.getId()) {
					case R.id.take_photo:
						camera(v);
						break;
					case R.id.take_picture:
						gallery(v);
						break;
					default:
						break;
					}
				}

			};

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 实例化SelectPicPopupWindow
				menuWindow = new SelectPopupWindow(Personal_setting.this,
						itemsOnClick);
				// 显示窗口
				menuWindow.showAtLocation(
						Personal_setting.this.findViewById(R.id.setting_image),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				// 设置layout在PopupWindow中显示的位置
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

							Toast.makeText(Personal_setting.this, "头像上传成功!", 0)
									.show();
							success = true;
						} else {
							Toast.makeText(Personal_setting.this,
									"网络访问异常，错误码：" + statusCode, 0).show();

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@SuppressLint("ShowToast")
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Toast.makeText(Personal_setting.this,
							"网络访问异常，错误码  > " + statusCode, 0).show();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}

	/*
	 * 从相册获取
	 */
	public void gallery(View view) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
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

	@SuppressLint("ShowToast")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(Personal_setting.this, "未找到存储卡，无法存储照片！", 0)
						.show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				setting_image.setImageBitmap(bitmap);
				boolean delete = tempFile.delete();
				System.out.println("delete = " + delete);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 剪切图片
	 * 
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
