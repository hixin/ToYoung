package com.example.toyoung;

import com.toyoung.liu.AddQuestionActivity;
import com.toyoung.liu.MainHomePageActivity;
import com.toyoung.liu.MainMeActivity;
import com.toyuong.util.MyApplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle) ������Activity
	 * ---liuhaoxian--20150603
	 */
	// 150,149,148
	private ImageView im_me, im_homepage;
	private TextView tv_homepage, tv_me;
	private RelativeLayout rl_homepage, rl_add, rl_me;
	private ViewPager viewPager = null;
	private FragmentManager manager;
	private static LinearLayout ll_mainbottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {

		ll_mainbottom = (LinearLayout) findViewById(R.id.ll_mainbottom);
		manager = getSupportFragmentManager();

		im_homepage = (ImageView) findViewById(R.id.im_homepage);

		im_me = (ImageView) findViewById(R.id.im_me);

		tv_homepage = (TextView) findViewById(R.id.tv_homepage);
		tv_me = (TextView) findViewById(R.id.tv_me);

		rl_homepage = (RelativeLayout) findViewById(R.id.rl_homepage);
		rl_add = (RelativeLayout) findViewById(R.id.rl_add);
		rl_me = (RelativeLayout) findViewById(R.id.rl_me);

		rl_homepage.setOnClickListener(this);
		rl_add.setOnClickListener(this);
		rl_me.setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.vp_main_activity);
		viewPager.setAdapter(new MyViewPagerAdapter(manager));
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0, true);
		
		MyApplication app=(MyApplication)this.getApplicationContext();
		
	//	Log.i("liuhaoxian","用户信息---->"+app.getUserVo().toString());
	}

	public class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {

			switch (arg0) {
			case 0:// ��ҳ
				return new MainHomePageActivity();
			case 1:// �ҵ�
				return new MainMeActivity();
			default:
				break;
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
		viewPager.setCurrentItem(arg0, true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_homepage: {
			viewPager.setCurrentItem(0, true);
			im_homepage.setImageResource(R.drawable.homepage_icon_pressed);
			tv_homepage.setTextColor(getResources().getColor(
					R.color.homepage_icon_text_blue_color));
			im_me.setImageResource(R.drawable.me_icon_normal);
			tv_me.setTextColor(getResources().getColor(
					R.color.homepage_icon_text_gray_color));
		}
			break;
		case R.id.rl_add:// ��ת���м��+ҳ��
		{
			Intent intent = new Intent(this, AddQuestionActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.rl_me: {
			viewPager.setCurrentItem(1, true);
			im_homepage.setImageResource(R.drawable.homepage_icon_normal);
			tv_homepage.setTextColor(getResources().getColor(
					R.color.homepage_icon_text_gray_color));
			im_me.setImageResource(R.drawable.me_icon_pressed);
			tv_me.setTextColor(getResources().getColor(
					R.color.homepage_icon_text_blue_color));
		}
			break;
		default:
			;
		}
	}

	public static void setBottomVisiable(boolean show) {
		if (show) {
			ll_mainbottom.setVisibility(View.VISIBLE);
		} else {
			ll_mainbottom.setVisibility(View.GONE);
		}
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i("liuhaoxian","dispathKey");
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()!=KeyEvent.ACTION_UP)
		{
			exitDialog(MainActivity.this);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	/**
	 * 退出时的提示框
	 */
	private void exitDialog(Context context) {
		String smg="确定退出系统?";
		
		new AlertDialog.Builder(MainActivity.this).setIcon(null).setTitle("温馨提示").setMessage(smg)
				
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						
							
							// 先跳到LoginActivity再调用关闭
							new Thread() {
								public void run() {
									Intent intent = new Intent(MainActivity.this, LoginActivity.class);
									intent.putExtra("intent_exit_state", "system_exit");
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									MainActivity.this.finish();
								}
							}.start();

						}
					
				}).setNegativeButton("取消", null).create().show();
	}

}
