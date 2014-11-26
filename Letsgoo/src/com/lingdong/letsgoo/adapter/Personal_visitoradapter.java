package com.lingdong.letsgoo.adapter;

import java.util.List;

import com.lingdong.letsgoo.R;
import com.lingdong.letsgoo.Bean.DynamicMessage;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Personal_visitoradapter extends BaseAdapter{
	private List<DynamicMessage> data;  
    private LayoutInflater layoutInflater;  
    private Context context;  
    public Personal_visitoradapter(Context context,List<DynamicMessage> data){  
        this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context);  
    }  

        public SmartImageView image;  
        public TextView name;  
        public TextView content;  
        public TextView time;  
   
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
            //获得组件，实例化组件  
            convertView=layoutInflater.inflate(R.layout.visitorlist, null);  
            image=(SmartImageView)convertView.findViewById(R.id.visitor_image);  
            name=(TextView)convertView.findViewById(R.id.visitor_name);  
            content=(TextView)convertView.findViewById(R.id.visitor_content);  
            time=(TextView)convertView.findViewById(R.id.visitor_time);  
       
        //绑定数据  
        image.setImageUrl(data.get(position).getPictureurl());  
        name.setText((String)data.get(position).getNickname());  
        content.setText("查看了活动“"+(String)data.get(position).getActheme()+"”");  
        //time.setText(Messageadapter.getLocalTimeFromUTC(data.get(position).getUpdated_at()));  
        return convertView;  
    }  
	

}
