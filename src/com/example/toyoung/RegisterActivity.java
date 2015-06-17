package com.example.toyoung;

import org.json.JSONException;
import org.json.JSONObject;

import com.toyuong.util.ConnectUtil;
import com.toyuong.util.DialogFactory;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
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
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private TextView ev_username,ev_password,ev_confrim_psw,ev_address,ev_schoolAddress,ev_nickName;
	private Button btn_submit,btn_cancel,btn_man,btn_women;
	private ImageView im_account_message;
	private Dialog mDialog;
	private int sex=0;//0 for man,1 for women
	static int CHECK_USERNAME=0;
	static int REGISTER=1;
	private String lastAccount="";
	private boolean isUserNameOk=false;
	private Handler myHandler=new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String reCode=(String)msg.obj;
			if(msg.what==REGISTER){
				
				dismissRequestDialog();
				if(reCode!=null&&reCode.equals("success")){
					//跳转到主界面
					Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					//DialogFactory.ToastDialog(RegisterActivity.this,"ToYoung","注册失败");
					Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				im_account_message.setVisibility(View.VISIBLE);
				if(reCode!=null&&reCode.equals("success")){
					//用户名还没有被注册
					im_account_message.setImageResource(R.drawable.login_checked_success);
					isUserNameOk=true;
					
				}
				else
				{
					//用户名已经存在
					im_account_message.setImageResource(R.drawable.login_checked_fail);
					isUserNameOk=false;
				} 
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
    private void initView(){
    	ev_username=(EditText)findViewById(R.id.et_account);
    	ev_password=(EditText)findViewById(R.id.et_password);
    	ev_confrim_psw=(EditText)findViewById(R.id.tv_confrim_password);
    	ev_address=(EditText)findViewById(R.id.ev_address);
    	ev_schoolAddress=(EditText)findViewById(R.id.ev_schoolAddress);
    	ev_nickName=(EditText)findViewById(R.id.ev_nickName);
    	btn_submit=(Button)findViewById(R.id.btn_OK);
    	btn_submit.setOnClickListener(this);
    	btn_cancel=(Button)findViewById(R.id.btn_cancel);
    	btn_cancel.setOnClickListener(this);
    	btn_man=(Button)findViewById(R.id.btn_man);
    	btn_man.setOnClickListener(this);
    	btn_women=(Button)findViewById(R.id.btn_women);
    	btn_women.setOnClickListener(this);
    	ev_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!ev_username.getText().toString().equals(""))
				 {
					lastAccount=ev_username.getText().toString();
					Log.i("liuhaoxian", "---------focusChange");
					new Thread(){
					    public void run() {
					    	try{
					    	PostParameter[] params=new PostParameter[1];
						    params[0]=new PostParameter("userName",ev_username.getText().toString());
						    String jsonString=ConnectUtil.httpRequest(ConnectUtil.isUserNameExistUrl, params, ConnectUtil.POST);
						    if(jsonString!=null){
						    	JSONObject result=new JSONObject(jsonString);
								String status=result.getString("Status");
								if(status.equalsIgnoreCase("success")){
									
									Message msg=new Message();
									msg.what=CHECK_USERNAME;
									msg.obj="success";
									myHandler.sendMessage(msg);
								}
								else
								{
									//用户名存在
									Message msg=new Message();
									msg.what=CHECK_USERNAME;
									msg.obj="fail";
									myHandler.sendMessage(msg);
								}
						    }
						    else
						    {
						    	Message msg=new Message();
						    	msg.what=CHECK_USERNAME;
						    	msg.obj="fail";
						    	myHandler.sendMessage(msg);
						    }
					    	
					    	}
					    	catch(Exception e){
					    		e.printStackTrace();
					    	}
					    	
					    };
					    
					    
						
					}.start();
					
				 }
				
			}
		});
    	im_account_message=(ImageView)findViewById(R.id.im_account_message);
    	im_account_message.setVisibility(View.GONE);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_cancel){
			finish();
		}
		else if(v.getId()==R.id.btn_man){
			sex=0;
			btn_man.setBackgroundResource(R.drawable.man_selected_icon);
			btn_women.setBackgroundResource(R.drawable.women_normal_icon);
		}
		else if(v.getId()==R.id.btn_women){
			sex=1;
			btn_man.setBackgroundResource(R.drawable.man_normal_icon);
			btn_women.setBackgroundResource(R.drawable.women_selected_icon);
		}
		else if(v.getId()==R.id.btn_OK){
			//检验信息
			if(!infoCheck())
			    return ;
			
			showRequestDialog();
			//提交到服务器
			new Thread(){
				public void run() {
					try {
						final PostParameter[] params=new PostParameter[6];
						params[0]=new PostParameter("userName",ev_username.getText().toString());
						params[1]=new PostParameter("password",ConnectUtil.getEncode("MD5", ev_password.getText().toString()));
						params[2]=new PostParameter("sex",sex+"");
						params[3]=new PostParameter("address",ev_address.getText().toString());
						params[4]=new PostParameter("schoolAddress",ev_schoolAddress.getText().toString());
						params[5]=new PostParameter("nickName",ev_nickName.getText().toString());
						String jsonString=ConnectUtil.httpRequest(ConnectUtil.registerUrl, params, ConnectUtil.POST);
						if(jsonString==null){
							Message msg=new Message();
							msg.what=REGISTER;
							msg.obj="fail";
							myHandler.sendMessage(msg);
							return ;
						}
						JSONObject result=new JSONObject(jsonString);
						String status=result.getString("Status");
						if(status.equalsIgnoreCase("success")){
							Log.i("liuhaoxian","注册成功");
							UserVo userVo=new UserVo();
							userVo.setUserID(result.getString("UserID"));
							userVo.setAddress(ev_address.getText().toString());
							userVo.setNickName(ev_nickName.getText().toString());
							userVo.setSchoolName(ev_schoolAddress.getText().toString());
							userVo.setSex(""+sex);
							userVo.setUserName(ev_username.getText().toString());
							userVo.setPassword(ev_password.getText().toString());
							MyApplication app=(MyApplication)getApplicationContext();
							app.setUserVo(userVo);
							Log.i("liuhaoxian","注册成功后---->userVo="+userVo.toString());
							
							
							Message msg=new Message();
							msg.what=REGISTER;
							msg.obj="success";
							myHandler.sendMessage(msg);
						}
						else
						{
							//用户名存在
							Message msg=new Message();
							msg.what=REGISTER;
							msg.obj="fail";
							myHandler.sendMessage(msg);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.i("liuhaoxian","出现异常");
						//出现异常
						Message msg=new Message();
						msg.what=REGISTER;
						msg.obj="fail";
						myHandler.sendMessage(msg);
					}
				};
			}.start();
			
			
		}
			
	}
	//校验注册信息
	private boolean infoCheck(){
		String account=ev_username.getText().toString();
		String pws=ev_password.getText().toString();
		String confrim_psw=ev_confrim_psw.getText().toString();
		String address=ev_address.getText().toString();
		String schoolAddress=ev_schoolAddress.getText().toString();
		String nickName=ev_nickName.getText().toString();
		if(!isUserNameOk){
			Toast.makeText(this,"用户名已存在", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(account.equals("")||pws.equals("")||confrim_psw.equals("")||address.equals("")||schoolAddress.equals("")||nickName.equals("")){
			Toast.makeText(this,"注册信息不完整", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!pws.equals(confrim_psw)){
			Toast.makeText(this,"密码与确认密码不一致", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在注册...");
		mDialog.show();
	}

	private void dismissRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

}
