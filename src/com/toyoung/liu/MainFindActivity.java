package com.toyoung.liu;

import com.example.toyoung.R;
import com.example.toyoung.R.layout;
import com.example.toyoung.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainFindActivity extends Fragment implements OnClickListener {

	
	//定义tag,从0到9，考试心得、学校申请、吃喝指南、购物秘籍、留学情感地、
	
    //              就业打工、交通住宿、寻找小伙伴、文化观察、其他问题
	
	private RelativeLayout rl_tag0,rl_tag1,rl_tag2,
	                       rl_tag3,rl_tag4,rl_tag5,
	                       rl_tag6,rl_tag7,rl_tag8,rl_tag9;
	private int tag=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View root=inflater.inflate(R.layout.activity_main_find,container, false);
		initView(root);
		
		return root;
	}

	private void initView(View view){
		rl_tag0=(RelativeLayout)view.findViewById(R.id.rl_tag_0);
		rl_tag0.setOnClickListener(this);
		rl_tag1=(RelativeLayout)view.findViewById(R.id.rl_tag_1);
		rl_tag1.setOnClickListener(this);
		rl_tag2=(RelativeLayout)view.findViewById(R.id.rl_tag_2);
		rl_tag2.setOnClickListener(this);
		rl_tag3=(RelativeLayout)view.findViewById(R.id.rl_tag_3);
		rl_tag3.setOnClickListener(this);
		rl_tag4=(RelativeLayout)view.findViewById(R.id.rl_tag_4);
		rl_tag4.setOnClickListener(this);
		rl_tag5=(RelativeLayout)view.findViewById(R.id.rl_tag_5);
		rl_tag5.setOnClickListener(this);
		rl_tag6=(RelativeLayout)view.findViewById(R.id.rl_tag_6);
		rl_tag6.setOnClickListener(this);
		rl_tag7=(RelativeLayout)view.findViewById(R.id.rl_tag_7);
		rl_tag7.setOnClickListener(this);
		rl_tag8=(RelativeLayout)view.findViewById(R.id.rl_tag_8);
		rl_tag8.setOnClickListener(this);
		rl_tag9=(RelativeLayout)view.findViewById(R.id.rl_tag_9);
		rl_tag9.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.rl_tag_0:
		{
			tag=0;
		}
		break;
		case R.id.rl_tag_1:
		{
			tag=1;
		}
		break;
		case R.id.rl_tag_2:
		{
			tag=2;
		}
		break;
		case R.id.rl_tag_3:
		{
			tag=3;
		}
		break;
		case R.id.rl_tag_4:
		{
			tag=4;
		}
		break;
		case R.id.rl_tag_5:
		{
			tag=5;
		}
		break;
		case R.id.rl_tag_6:
		{
			tag=6;
		}
		break;
		case R.id.rl_tag_7:
		{
			tag=7;
		}
		break;
		case R.id.rl_tag_8:
		{
			tag=8;
		}
		break;
		case R.id.rl_tag_9:
		{
			tag=9;
		}
		break;
		
			
		
		
		}
		Intent intent=new Intent(this.getActivity(),SubjectListActivity.class);
		intent.putExtra("subjectTag",tag);
		this.getActivity().startActivity(intent);
	}
}
