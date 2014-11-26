package com.lingdong.letsgoo.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicMessage;
import com.lingdong.letsgoo.resource.CircleImageView;
import com.loopj.android.image.SmartImageView;

public class Messageadapter extends BaseAdapter {
	private List<DynamicMessage> data;
	private LayoutInflater layoutInflater;
	private Context context;
	private int type;

	public Messageadapter(Context context, List<DynamicMessage> data) {
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public SmartImageView image;
	public TextView name;
	public TextView content;
	public TextView time;
	public ImageView huifu;

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

	public void addItem(DynamicMessage item) {
			data.add(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 获得组件，实例化组件
		convertView = layoutInflater
				.inflate(R.layout.message_pinglunlist, null);
		image = (SmartImageView) convertView
				.findViewById(R.id.message_pinglun_image);
		name = (TextView) convertView.findViewById(R.id.message_pinglun_name);
		content = (TextView) convertView
				.findViewById(R.id.message_pinglun_content);
		time = (TextView) convertView.findViewById(R.id.message_pinglun_time);
		huifu = (ImageView) convertView
				.findViewById(R.id.message_pinglun_huifu);

		// 绑定数据
		if(!data.isEmpty()){
		type = data.get(position).getOperate_type().intValue();
		System.out.println( data.get(position).toString()+"llllllllllllllllllllll"+data.size());
		switch (type) {
		case 1:
			huifu.setVisibility(8);
			image.setImageUrl(data.get(position).getPictureurl());
			name.setText(data.get(position).getNickname());
			content.setText((String) data.get(position).getActheme());
			time.setText(getLocalTimeFromUTC(data.get(position).getUpdated_at()));
			break;
		case 2:
			huifu.setVisibility(8);
			image.setImageUrl(data.get(position).getPictureurl());
			name.setText((String) data.get(position).getNickname());
			content.setText((String) data.get(position).getActheme());
			time.setText(getLocalTimeFromUTC(data.get(position).getUpdated_at()));
			break;
		case 3:
			image.setImageUrl(data.get(position).getPictureurl());
			name.setText((String) data.get(position).getNickname());
			content.setText((String) data.get(position).getActheme());
			time.setText(getLocalTimeFromUTC(data.get(position).getUpdated_at()));
			huifu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			break;
		case 4:
			image.setImageUrl(data.get(position).getPictureurl());
			name.setText((String) data.get(position).getNickname());
			content.setText((String) data.get(position).getActheme());
			time.setText(getLocalTimeFromUTC(data.get(position).getUpdated_at()));
			huifu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			break;

		}
		}
		return convertView;
	}

	@SuppressLint("SimpleDateFormat")
	public String getLocalTimeFromUTC(String UTCTime) {
		if(UTCTime==""){return "时间获取失败";}
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		UTCTime = UTCTime.replace("Z", " UTC");
		Date dt = null;
		try {
			dt = sdf.parse(UTCTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String utc = dt.toLocaleString();
		GregorianCalendar g = new GregorianCalendar();
		String current = g.getTime().toLocaleString();
		int time = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(current);
			Date d2 = df.parse(utc);
			time = (int) (d1.getTime() - d2.getTime()) / (1000 * 60);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (time <= 0)
			return "刚刚";
		if(time>0&&time<60)
			return time + "分钟前";
		int day = 0;
		if(time>60)
			day=(int)time/60;
			return day+"天前";
			
	}

}
