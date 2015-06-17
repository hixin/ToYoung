package com.toyoung.liu;

import com.example.toyoung.R;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainHomePageActivity extends Fragment implements OnClickListener, OnPageChangeListener {

	private TextView tv_hot,tv_find;
	private Button btn_search;
	private ViewPager viewPager=null;
	private FragmentManager manager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		manager=this.getChildFragmentManager();
		
		View root=inflater.inflate(R.layout.activity_main_home_page,container, false);
		initView(root);
		
		return root;
	}

	private void initView(View root){
		tv_hot=(TextView)root.findViewById(R.id.tv_hot);
		tv_find=(TextView)root.findViewById(R.id.tv_find);
		btn_search=(Button)root.findViewById(R.id.btn_search);
		tv_hot.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		
		viewPager=(ViewPager)root.findViewById(R.id.vp_main_activity);
    	viewPager.setAdapter(new HomePageViewPagerAdapter(manager));
    	viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0,true);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("liuhaoxian","click button"+v.getId());
		switch(v.getId()){
		case R.id.tv_hot:
		{
			viewPager.setCurrentItem(0,true);
			tv_hot.setTextColor(getResources().getColor(R.color.homepage_icon_text_blue_color));
			tv_find.setTextColor(getResources().getColor(R.color.homepage_icon_text_gray_color));
		}
			break;
		case R.id.tv_find:
		{
			viewPager.setCurrentItem(1,true);
			tv_hot.setTextColor(getResources().getColor(R.color.homepage_icon_text_gray_color));
			tv_find.setTextColor(getResources().getColor(R.color.homepage_icon_text_blue_color));
		}
			break;
		
		case R.id.btn_search:
		{
			Intent intent=new Intent(this.getActivity(),SearchActivity.class);
			startActivity(intent);
		}
			break;
		}
	}
	public class HomePageViewPagerAdapter extends FragmentPagerAdapter {  
		public HomePageViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override  
        public Fragment getItem(int arg0) {  
			
            switch(arg0) {  
            case 0://����
                return new MainHotActivity();
            case 1://����   
            	 return new MainFindActivity();
             default:;
            }  
            return null;
        }  
  
        @Override  
        public int getCount() {  
            return 2;  
        }  
          
          
          
    }
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(arg0);
		if(arg0==0)
		{
			tv_hot.setTextColor(getResources().getColor(R.color.homepage_icon_text_blue_color));
			tv_find.setTextColor(getResources().getColor(R.color.homepage_icon_text_gray_color));
		}
		else
		{
			tv_hot.setTextColor(getResources().getColor(R.color.homepage_icon_text_gray_color));
			tv_find.setTextColor(getResources().getColor(R.color.homepage_icon_text_blue_color));
		}
	}
}
