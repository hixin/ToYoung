package com.tuyoung.wu;

import com.example.toyoung.R;
import com.tuyoung.wu.Message.MessageListAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Setting extends Activity{
	private ImageView back;
	private TextView titlebar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setCustomActionBar();
	}
	
	private void setCustomActionBar() {  
	    ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);  
	    View mActionBarView = LayoutInflater.from(this).inflate(R.layout.customtitle, null);
	    titlebar = (TextView) mActionBarView.findViewById(R.id.titlebar);
	    titlebar.setText("设 置");
	    ActionBar actionBar = getActionBar();  
	    actionBar.setCustomView(mActionBarView, lp);  
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
	    actionBar.setDisplayShowCustomEnabled(true);  
	    actionBar.setDisplayShowHomeEnabled(false);  
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    back = (ImageView) mActionBarView.findViewById(R.id.home);
	    back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
	    
	} 
}
