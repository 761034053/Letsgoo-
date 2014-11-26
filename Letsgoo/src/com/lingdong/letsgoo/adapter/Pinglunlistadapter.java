package com.lingdong.letsgoo.adapter;

import java.util.List;
import java.util.Map;

import com.lingdong.letsgoo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Pinglunlistadapter extends BaseAdapter{
	
	private List<Map<String, Object>> data;  
    private LayoutInflater layoutInflater;  
    private Context context;
    
	public Pinglunlistadapter(Context context,List<Map<String, Object>> data)
	{
		this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context);  
	}
	
	public final class Zujian{  
        public ImageView image1;  
        public ImageView image2;  
        public TextView name1;  
        public TextView name2;
        public TextView time;
        public TextView content;
        public ImageView huifu;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Zujian zujian=null;  
        if(convertView==null){  
            zujian=new Zujian();  
            //获得组件，实例化组件  
            convertView=layoutInflater.inflate(R.layout.pinglunlist, null);  
            zujian.image1=(ImageView)convertView.findViewById(R.id.pinglun_image);  
            zujian.image2=(ImageView)convertView.findViewById(R.id.pinglun_image1);  
            zujian.name1=(TextView)convertView.findViewById(R.id.pinglun_name);  
            zujian.name2=(TextView)convertView.findViewById(R.id.pinglun_content1); 
            zujian.content=(TextView)convertView.findViewById(R.id.pinglun_content);
            zujian.time=(TextView)convertView.findViewById(R.id.pinglun_time);
            zujian.huifu=(ImageView)convertView.findViewById(R.id.pinglun_huifu); 
            convertView.setTag(zujian);  
        }else{  
            zujian=(Zujian)convertView.getTag();  
        }  
        //绑定数据  
        zujian.image1.setBackgroundResource((Integer)data.get(position).get("image1"));  
        zujian.image2.setBackgroundResource((Integer)data.get(position).get("image2"));  
        zujian.name1.setText((String)data.get(position).get("name1"));
        zujian.name2.setText((String)data.get(position).get("name2")); 
        zujian.content.setText((String)data.get(position).get("content"));  
        zujian.time.setText((String)data.get(position).get("time"));
        return convertView;
	}

}
