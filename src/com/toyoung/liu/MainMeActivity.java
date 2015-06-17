package com.toyoung.liu;


import com.example.toyoung.R;
import com.toyuong.util.MyApplication;
import com.tuyoung.wu.AvatarView;
import com.tuyoung.wu.Message;
import com.tuyoung.wu.MyAnswer;
import com.tuyoung.wu.MyFavoriate;
import com.tuyoung.wu.MyFollowing;
import com.tuyoung.wu.MyQuestion;
import com.tuyoung.wu.Setting;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainMeActivity extends Fragment implements OnPageChangeListener,OnClickListener{
	private TextView mTvQuestion;
	private TextView mTvAnswer;	
	private TextView mTvFollowing;
	private TextView mTvFavorite;
	
	private TextView mTvUserName;
	private TextView mTvUserLocation;
	
	private LinearLayout mLlQuestion;
	private LinearLayout mLlAnswer;
	private LinearLayout mLlFollowing;
	private LinearLayout mLlFavorite;
	private ViewPager viewPager=null;
	private FragmentManager manager;
	
	private ImageView msetting;
	private ImageView mmessage;
	private NumChangeReceiver numChangeReceiver;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		  IntentFilter filter = new IntentFilter("myInfoNumChange");  
				numChangeReceiver = new NumChangeReceiver();
			    MyApplication.context().registerReceiver(numChangeReceiver, filter);  
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	

//		 MyApplication.context().unregisterReceiver(numChangeReceiver);  
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		manager=this.getChildFragmentManager();
		View root=inflater.inflate(R.layout.activity_main_me,null);
		viewPager=(ViewPager)root.findViewById(R.id.fragment_my_information);
		FrameLayout fl = (FrameLayout) root.findViewById(R.id.myinfo_head);
	    AvatarView mIvAvatar = (AvatarView) root.findViewById(R.id.iv_avatar);
	    mIvAvatar.setImageResource(R.drawable.userphoto1);
	    initView(fl);
		return root;
	}

	
	
	public void initView(View view) {
		 
		  
		mTvQuestion = (TextView) view.findViewById(R.id.tv_question);
		mTvAnswer = (TextView) view.findViewById(R.id.tv_answer);
		mTvFollowing =  (TextView) view.findViewById(R.id.tv_following);
		mTvFavorite =  (TextView) view.findViewById(R.id.tv_favorite);
		mTvQuestion.setText("0");
		mTvAnswer.setText("0");
		mTvFollowing.setText("0");
		mTvFavorite.setText("0");
		
		mLlQuestion = (LinearLayout) view.findViewById(R.id.ly_question);
		mLlAnswer = (LinearLayout) view.findViewById(R.id.ly_answer);
		mLlFollowing = (LinearLayout) view.findViewById(R.id.ly_following);
		mLlFavorite = (LinearLayout) view.findViewById(R.id.ly_favorite);
		
		msetting = (ImageView) view.findViewById(R.id.setting);
		msetting.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent_setting = new Intent(getActivity(),Setting.class);	        	
	        	getActivity().startActivity(intent_setting);
				
			}
		});
		mmessage = (ImageView) view.findViewById(R.id.message_notify);
		mmessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_message = new Intent(getActivity(),Message.class);	        	
	        	getActivity().startActivity(intent_message);
			}
		});
		
		mLlQuestion.setOnClickListener(this);
		mLlAnswer.setOnClickListener(this);
		mLlFollowing.setOnClickListener(this);
		mLlFavorite.setOnClickListener(this);
	
    	viewPager.setAdapter(new MyInfoPageAdapter(manager));
    	viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(1,true);
		
		
		 mTvUserName = (TextView) view.findViewById(R.id.tv_name);
		 mTvUserLocation = (TextView) view.findViewById(R.id.tv_location);
		 Drawable drawable1 = getResources().getDrawable(R.drawable.wd004);
		 Drawable drawable2 = getResources().getDrawable(R.drawable.wd006);
		 float scale = getResources().getDisplayMetrics().density;
		 Log.i("scale",String.valueOf(scale));
		 drawable1.setBounds(0, 0, (int) (20*scale), (int) (20*scale));
		 mTvUserName.setCompoundDrawables(null, null, drawable1,
                 null);
		 drawable2.setBounds(0, 0, (int) (15*scale), (int) (15*scale));
		 mTvUserLocation.setCompoundDrawables(drawable2, null, null,
                 null);
	}
	
	 @Override
	    public void onClick(View v) {
	       
	        final int id = v.getId();
	        switch (id) {
	        case R.id.ly_question:
	        	viewPager.setCurrentItem(0,true);
	        	 break;
	        case R.id.ly_answer:
	        	viewPager.setCurrentItem(1,true);
	        	 break;
	        case R.id.ly_following:	 
	        	viewPager.setCurrentItem(2,true);
	            break;	    
	        case R.id.ly_favorite:
	        	viewPager.setCurrentItem(3,true);
	        /*    UIHelper.showUserFavorite(getActivity(), AppContext.getInstance()
	                    .getLoginUid());*/
	            break;            
	        default:
	            break;
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
		mLlQuestion.setBackgroundColor(getResources().getColor(R.color.my_information_background));
		mLlAnswer.setBackgroundColor(getResources().getColor(R.color.my_information_background));
		mLlFollowing.setBackgroundColor(getResources().getColor(R.color.my_information_background));
		mLlFavorite.setBackgroundColor(getResources().getColor(R.color.my_information_background));
		viewPager.setCurrentItem(arg0);
		if(arg0==0){
			mLlQuestion.setBackgroundColor(getResources().getColor(R.color.my_information_option_pressed));
		}else if(arg0==1){
			mLlAnswer.setBackgroundColor(getResources().getColor(R.color.my_information_option_pressed));
		}else if(arg0==2){
			mLlFollowing.setBackgroundColor(getResources().getColor(R.color.my_information_option_pressed));
		}else{
			mLlFavorite.setBackgroundColor(getResources().getColor(R.color.my_information_option_pressed));
		}
		
	}

	class MyInfoPageAdapter extends FragmentPagerAdapter{

		public MyInfoPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			 switch(arg0) {  
	            case 0://
	                return new MyQuestion();
	            case 1://
	                return new MyAnswer();
	            case 2://
	                return new MyFollowing();
	            case 3://
	                return new MyFavoriate();
	             default:;
	            }  
	            return null;
		
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
		
	}
	
	public class NumChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String mAction = intent.getAction();
			if(mAction.equals("myInfoNumChange")){
				mTvQuestion.setText(MyApplication.context().quesnum+"");
				mTvAnswer.setText(MyApplication.context().ansnum+"");
				mTvFollowing.setText(MyApplication.context().follnum+"");
				mTvFavorite.setText(MyApplication.context().favonum+"");
			}
			
		}
		
	}

	

}
