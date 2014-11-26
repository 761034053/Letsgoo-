package com.lingdong.letsgoo.fragment;

import com.lingdong.letsgoo.ExitApplication;
import com.lingdong.letsgoo.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomePage extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        Log.i("TAG", "---onCreateView");  
        return inflater.inflate(R.layout.message, null);       
    }  
  
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        Log.i("TAG", "---onActivityCreated");  
        // TODO Auto-generated method stub  
        ExitApplication.getInstance().addActivity(getActivity());
        super.onActivityCreated(savedInstanceState);  
    }  
  
    @Override  
    public void onAttach(Activity activity) {  
        Log.i("TAG", "---onAttach");  
        // TODO Auto-generated method stub  
        super.onAttach(activity);  
    }  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        Log.i("TAG", "---onCreate");  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
    }  
  
    @Override  
    public void onDestroy() {  
        Log.i("TAG", "---onDestroy");  
        // TODO Auto-generated method stub  
        super.onDestroy();  
    }  
  
    @Override  
    public void onPause() {  
        Log.i("TAG", "---onPause");  
        // TODO Auto-generated method stub  
        super.onPause();  
    }  
  
    @Override  
    public void onResume() {  
        Log.i("TAG", "---onResume");  
        // TODO Auto-generated method stub  
        super.onResume();  
    }  
}
