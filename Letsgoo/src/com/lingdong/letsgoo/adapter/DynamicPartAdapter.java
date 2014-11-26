package com.lingdong.letsgoo.adapter;

import java.util.List;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicPart;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DynamicPartAdapter extends BaseAdapter {
	private List<DynamicPart> data;
	private LayoutInflater layoutInflater;
	private Context context;
	
	private ImageView gender;
	private SmartImageView pictureurl;
	private TextView nickname;
	private TextView phone;
	private TextView school;
	private ImageView call;
	private Integer type;

	public DynamicPartAdapter(List<DynamicPart> data,
			LayoutInflater layoutInflater, Context context) {
		super();
		this.data = data;
		this.layoutInflater = layoutInflater;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addItem(DynamicPart item) {
		data.add(item);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = layoutInflater
				.inflate(R.layout.zanlist, null);
		gender = (ImageView) convertView
				.findViewById(R.id.zan_sex);
		pictureurl = (SmartImageView) convertView.findViewById(R.id.zan_image);
		nickname = (TextView) convertView
				.findViewById(R.id.zan_name);
		phone = (TextView) convertView.findViewById(R.id.zan_phonenumber);
		school = (TextView) convertView
				.findViewById(R.id.zan_school);
		call = (ImageView) convertView
				.findViewById(R.id.zan_phone);
		//œ‘ æ
		if(data.get(position).getGender()=="boy")
		{
			gender.setImageResource(R.drawable.zan_boy);
		}else{
			gender.setImageResource(R.drawable.zan_girl);
		}
		
		pictureurl.setImageUrl(data.get(position).getPictureurl());
		nickname.setText(data.get(position).getNickname());
		phone.setText((String) data.get(position).getPhone());
		phone.setVisibility(8);
		school.setText((String)data.get(position).getSchool());
		type=data.get(position).getType();
		if(type==0){
			call.setVisibility(8);
		}else{
			call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// ≤¶∫≈π¶ƒ‹
					Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+(String) data.get(position).getPhone())); 
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

}
