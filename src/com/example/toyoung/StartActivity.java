package com.example.toyoung;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		gotoLoginView();
	}
    private void gotoLoginView(){
    	 new Handler().postDelayed(new Runnable() {
				public void run() {
					Intent intent=new Intent(StartActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}, 2000);
    }
	
}
