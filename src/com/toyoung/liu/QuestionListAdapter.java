package com.toyoung.liu;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.toyoung.R;
import com.tuyuong.ge.hotInfors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuestionListAdapter extends BaseAdapter{

	private ArrayList datas;
	private Context context;
	public  QuestionListAdapter(Context context,ArrayList datas){
		this.datas=datas;
		this.context=context;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder=null;
		
		if(arg1==null){
			arg1=LayoutInflater.from(context).inflate(R.layout.activity_question_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tv_title=(TextView)arg1.findViewById(R.id.tv_title);
			arg1.setTag(viewHolder);
		}
		else
			viewHolder=(ViewHolder)arg1.getTag();
		
		hotInfors infor=(hotInfors)datas.get(arg0);
		viewHolder.tv_title.setText(infor.getTitle());
		return arg1;
	}
    public class ViewHolder{
    	public TextView tv_title;
    }
}
