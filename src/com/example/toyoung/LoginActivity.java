package com.example.toyoung;

import org.json.JSONException;
import org.json.JSONObject;

import com.toyuong.util.ConnectUtil;
import com.toyuong.util.DialogFactory;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
import com.toyuong.util.SharePreferenceUtil;
import com.tuyoung.vo.UserVo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private LinearLayout ll_message;
	private Button btn_register,btn_login;
	private TextView et_account,et_password;
	private ImageView im_message;
	private TextView  tv_message;
	private Dialog mDialog;
    private Handler myHandler=new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			dismissRequestDialog();
			ll_message.setVisibility(View.VISIBLE);
			String reCode=(String)msg.obj;
			dismissRequestDialog();
			Log.i("liuhaoxian", "reCode="+reCode);
			if(reCode!=null&&reCode.equals("success"))			
			{
				im_message.setImageResource(R.drawable.login_checked_success);
				tv_message.setText("登录成功");
				SharePreferenceUtil util=new SharePreferenceUtil(LoginActivity.this);
				util.setUserName(et_account.getText().toString());
				//跳转到主界面
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			}
			else
			{
				im_message.setImageResource(R.drawable.login_checked_fail);
				tv_message.setText("用户名或密码错误");
				//DialogFactory.ToastDialog(LoginActivity.this,"ToYoung","注册失败");
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		String logout=getIntent().getStringExtra("intent_exit_state");
		if(logout!=null&&logout.equals("system_exit")){
			System.exit(0);
		}
		initView();
	}
	@Override
	protected void onResume() {
		 super.onResume();
		// TODO Auto-generated method stub
	    if(!ConnectUtil.isNetworkAvailable(this)){
	    	Toast.makeText(this, "网络未开启", Toast.LENGTH_LONG).show();
	    }
	   
	}
	private void initView(){
		ll_message=(LinearLayout)findViewById(R.id.ll_message);
		ll_message.setVisibility(View.GONE);
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_login=(Button)findViewById(R.id.btn_login);
		
		et_account=(EditText)findViewById(R.id.tv_account);
		et_password=(EditText)findViewById(R.id.tv_password);
		
		im_message=(ImageView)findViewById(R.id.im_message);
		tv_message=(TextView)findViewById(R.id.tv_message);
		ll_message.setVisibility(View.GONE);
		
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		SharePreferenceUtil util=new SharePreferenceUtil(this);
		et_account.setText(util.getUserName());
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_login){
			if(et_account.getText().toString().equals(""))
			{
				DialogFactory.ToastDialog(this, "ToYoung","用户名不能为空");
				return;
			}
			if(et_password.getText().toString().equals(""))
			{
				DialogFactory.ToastDialog(this, "ToYoung","密码不能为空");
			}
			showRequestDialog();
			//进行登录验证
			new Thread(){
				public void run() {
					final PostParameter[] params=new PostParameter[2];
					params[0]=new PostParameter("userName",et_account.getText().toString());
					params[1]=new PostParameter("password",ConnectUtil.getEncode("MD5", et_password.getText().toString()));
					String jsonString=ConnectUtil.httpRequest(ConnectUtil.loginUrl, params, ConnectUtil.POST);
					if(jsonString==null){
						Message msg=new Message();
						msg.obj="fail";
						myHandler.sendMessage(msg);
						return ;
					}
					
					try {
						JSONObject result = new JSONObject(jsonString);
						String status=result.getString("Status");
						if(status.equalsIgnoreCase("success")){
							JSONObject userVoJson=result.getJSONObject("UserVo");
							UserVo userVo=new UserVo();
							userVo.setUserID(userVoJson.getString("UserID"));
							userVo.setAddress(userVoJson.getString("UserAddress"));
							userVo.setNickName(userVoJson.getString("NickName"));
							userVo.setSchoolName(userVoJson.getString("SchoolAddress"));
							userVo.setSex(userVoJson.getString("Sex"));
							userVo.setUserName(et_account.getText().toString());
							MyApplication app=(MyApplication)getApplicationContext();
							app.setUserVo(userVo);
							Log.i("liuhaoxian","LoginActivity登录成功后的用户信息---->"+app.getUserVo().toString());
							Message msg=new Message();
							msg.obj="success";
							myHandler.sendMessage(msg);
							Log.i("liuhaoxian","登录成功");
							
						}
						else
						{
							//登录失败
							Message msg=new Message();
							msg.obj="fail";
							myHandler.sendMessage(msg);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//登录失败
						Message msg=new Message();
						msg.obj="fail";
						myHandler.sendMessage(msg);
					}
					
				};
				
				
				
			}.start();
		}
		else
		{
			//跳转到注册界面
			Intent intent=new Intent(this,RegisterActivity.class);
			startActivity(intent);
			
		}
	}
	
	public void gotoMainView(){
		Intent intent=new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在登录验证...");
		mDialog.show();
	}

	private void dismissRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
	

}
