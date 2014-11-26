package com.lingdong.letsgoo.activity;

import static cn.smssdk.framework.utils.R.getBitmapRes;
import static cn.smssdk.framework.utils.R.getIdRes;
import static cn.smssdk.framework.utils.R.getLayoutRes;
import static cn.smssdk.framework.utils.R.getStringRes;
import static cn.smssdk.framework.utils.R.getStyleRes;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.analysis.MobclickAgent;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.framework.FakeActivity;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.SMSReceiver;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.lingdong.letsgoo.R;

@SuppressLint("ShowToast")
public class Setting_phone extends FakeActivity implements TextWatcher {
	private ImageView fanhui;
	private TextView phone_post;
	private EditText phone;
	private EditText yanzheng;
	private FrameLayout setting_phonepost;

	private Dialog pd;
	private EventHandler handler;
	private BroadcastReceiver smsReceiver;
	private static final int RETRY_INTERVAL = 40;
	private int time = RETRY_INTERVAL;
	private static String phonenumber;

	private String successresponse = null;

	public void show(Context context) {
		super.show(context, null);
	}

	public void onCreate() {
		activity.setContentView(R.layout.setting_phone);

		fanhui = (ImageView) this.findViewById(R.id.fanhui_phone);
		phone_post = (TextView) this.findViewById(R.id.phone_post);
		phone = (EditText) this.findViewById(R.id.phone_number);
		yanzheng = (EditText) this.findViewById(R.id.phone_yanzheng);
		setting_phonepost = (FrameLayout) this
				.findViewById(R.id.setting_phonepost);

		handler = new EventHandler() {
			public void afterEvent(final int event, final int result,
					final Object data) {
				runOnUIThread(new Runnable() {
					public void run() {
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}
						if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
							// �ύ��֤��
							afterSubmit(result, data);
						}
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
								// ������֤�����ת����֤����дҳ��
								// afterVerificationCodeRequested();
								yanzheng.requestFocus();
								smsReceiver = new SMSReceiver(
										new SMSSDK.VerifyCodeReadListener() {
											@Override
											public void onReadVerifyCode(
													final String verifyCode) {
												runOnUIThread(new Runnable() {
													@Override
													public void run() {
														yanzheng.setText(verifyCode);
														yanzheng.setSelection(verifyCode
																.length());
													}
												});
											}
										});
								activity.registerReceiver(
										smsReceiver,
										new IntentFilter(
												"android.provider.Telephony.SMS_RECEIVED"));
							}
						} else {
							// ���ݷ��������ص�������󣬸�toast��ʾ
							try {
								((Throwable) data).printStackTrace();
								Throwable throwable = (Throwable) data;

								JSONObject object = new JSONObject(
										throwable.getMessage());
								String des = object.optString("detail");
								if (!TextUtils.isEmpty(des)) {
									// Toast.makeText(activity, des,
									// Toast.LENGTH_SHORT).show();
									return;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// ���ľ���ҵ���Դ��Ĭ����ʾ
							int resId = getStringRes(activity,
									"smssdk_network_error");
							if (resId > 0) {
								Toast.makeText(activity, resId,
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(handler);

		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		phone_post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SMSSDK.initSDK(activity, "45c34afd98c6",
						"1b8e98a051b0c70b65da3721b26e3f36");
				String phonenum = phone.getText().toString().trim()
						.replaceAll("\\s*", "");
				String countrycode = "+86";
				checkPhoneNum(phonenum, countrycode);
			}
		});

		// �ύ��֤��
		setting_phonepost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SMSSDK.initSDK(activity, "45c34afd98c6",
						"1b8e98a051b0c70b65da3721b26e3f36");
				String verificationCode = yanzheng.getText().toString().trim();
				String phonenum = phone.getText().toString().trim()
						.replaceAll("\\s*", "");
				String countrycode = "+86";
				if (countrycode.startsWith("+")) {
					countrycode = countrycode.substring(1);
				}
				if (!TextUtils.isEmpty(verificationCode)) {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					pd = CommonDialog.ProgressDialog(activity);
					if (pd != null) {
						pd.show();
					}
					SMSSDK.submitVerificationCode(countrycode, phonenum,
							verificationCode);
				} else {
					int resId = getStringRes(activity,
							"smssdk_write_identify_code");
					if (resId > 0) {
						Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT)
								.show();
					}
				}

			}
		});
	}

	/**
	 * �ύ��֤��ɹ����ִ���¼�
	 * 
	 * @param result
	 * @param data
	 */
	private void afterSubmit(final int result, final Object data) {
		runOnUIThread(new Runnable() {
			public void run() {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}

				if (result == SMSSDK.RESULT_COMPLETE) {
					//��֤�ɹ�
					post_phonenumber(phonenumber);
				} else {
					((Throwable) data).printStackTrace();
					// ��֤�벻��ȷ
					int resId = getStringRes(activity,
							"smssdk_virificaition_code_wrong");
					if (resId > 0) {
						Toast.makeText(activity, resId, Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		});
	}

	// ���绰����
	private void checkPhoneNum(String phone, String code) {
		if (code.startsWith("+")) {
			code = code.substring(1);
		}

		if (TextUtils.isEmpty(phone)) {
			int resId = getStringRes(activity, "smssdk_write_mobile_phone");
			if (resId > 0) {
				Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
			}
			return;
		}
		int resId = 0;
		if (phone.length() != 11) {
			resId = getStringRes(activity, "smssdk_write_right_mobile_phone");
			if (resId > 0) {
				Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
			}
			return;
		}
		// �����Ի��򣬷�����֤��
		phonenumber = phone;
		showDialog(phone, code);
	}

	// �Ƿ���������֤�룬�Ի���
	public void showDialog(final String phone, final String code) {
		int resId = getStyleRes(activity, "CommonDialog");
		if (resId > 0) {
			final String phoneNum = "+" + code + " " + splitPhoneNum(phone);
			final Dialog dialog = new Dialog(getContext(), resId);
			resId = getLayoutRes(activity, "smssdk_send_msg_dialog");
			if (resId > 0) {
				dialog.setContentView(resId);
				resId = getIdRes(activity, "tv_phone");
				((TextView) dialog.findViewById(resId)).setText(phoneNum);
				resId = getIdRes(activity, "tv_dialog_hint");
				TextView tv = (TextView) dialog.findViewById(resId);
				resId = getStringRes(activity, "smssdk_make_sure_mobile_detail");
				if (resId > 0) {
					String text = getContext().getString(resId);
					tv.setText(Html.fromHtml(text));
				}
				resId = getIdRes(activity, "btn_dialog_ok");
				if (resId > 0) {
					((Button) dialog.findViewById(resId))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									// ��ת����֤��ҳ��
									dialog.dismiss();

									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									pd = CommonDialog.ProgressDialog(activity);
									if (pd != null) {
										pd.show();
									}
									Log.e("verification phone ==>>", phone);
									SMSSDK.getVerificationCode(code,
											phone.trim());
									countDown();
								}
							});
				}
				resId = getIdRes(activity, "btn_dialog_cancel");
				if (resId > 0) {
					((Button) dialog.findViewById(resId))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
				}
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();

			}
		}
	}

	// ������ʱ
	@SuppressLint("ResourceAsColor")
	private void countDown() {
		runOnUIThread(new Runnable() {
			public void run() {
				time--;
				if (time == 0) {
					phone_post.setBackgroundResource(R.drawable.phonepost_before);
					phone_post.setText("����");
					phone_post.setEnabled(true);
					time = RETRY_INTERVAL;
				} else {
					int resId = getStringRes(activity, "receive_msg");
					if (resId > 0) {
						phone_post
								.setBackgroundResource(R.drawable.phonepost_after);
						String unReceive = getContext().getString(resId, time);
						phone_post.setText(Html.fromHtml(unReceive));
					}
					phone_post.setEnabled(false);
					runOnUIThread(this, 1000);
				}
			}
		}, 1000);
	}

	// �ָ�绰����
	private String splitPhoneNum(String phone) {
		StringBuilder builder = new StringBuilder(phone);
		builder.reverse();
		for (int i = 4, len = builder.length(); i < len; i += 5) {
			builder.insert(i, ' ');
		}
		builder.reverse();
		return builder.toString();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (s.length() > 0) {
			phone_post.setEnabled(true);
			int resId = getBitmapRes(activity, "smssdk_btn_enable");
			if (resId > 0) {
				phone_post.setBackgroundResource(resId);
			}
		} else {
			phone_post.setEnabled(false);
			int resId = getBitmapRes(activity, "smssdk_btn_disenable");
			if (resId > 0) {
				phone_post.setBackgroundResource(resId);
			}
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	public void onResume() {
		SMSSDK.registerEventHandler(handler);
		MobclickAgent.onPageStart("Setting_phone");
	}

	public void onPause() {
		SMSSDK.unregisterEventHandler(handler);
		MobclickAgent.onPageEnd("Setting_phone");
	}

	public boolean onFinish() {
		SMSSDK.unregisterEventHandler(handler);
		// activity.unregisterReceiver(smsReceiver);
		return super.onFinish();
	}

	public void post_phonenumber(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", "28");
		map.put("phonenumber", phone);
		String url = "http://192.168.1.7:3000/update_user";
		RequestQueue requestQueue = Volley.newRequestQueue(getContext());
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@SuppressLint("ShowToast")
					@Override
					public void onResponse(JSONObject response) {
						try {
							successresponse = response.get("success")
									.toString();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (successresponse.equals("1")) {
							Toast.makeText(activity, "�޸ĳɹ�", 1).show();
							finish();

						} else {
							Toast.makeText(activity, "���������ԣ�", 1).show();
						}
					}
				}, new Response.ErrorListener() {
					@SuppressLint("ShowToast")
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(activity, "���������ԣ�", 1).show();
					}
				});
		requestQueue.add(jsonRequest);
	}

}
