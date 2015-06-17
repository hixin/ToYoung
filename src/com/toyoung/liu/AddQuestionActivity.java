package com.toyoung.liu;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.toyoung.R;
import com.example.toyoung.R.layout;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddQuestionActivity extends Activity implements OnClickListener {

	private Button btn_narrow,btn_back,btn_submit;
	private TextView tv_content,tv_title;
	private RelativeLayout rl_tags;
	private boolean isNarrowOpen=false;
	private Button tag0,tag1,tag2,
	               tag3,tag4,tag5,
	               tag6,tag7,tag8;
	private String[] tags={"交通住宿","考试心得","学校申请",
			                "吃喝指南","留学情感地","文化观察",
			                "寻找小伙伴","就业打工","其他问题"};
	private int tag=0;
	private TextView tv_tag;
	private Handler myHandler=new Handler(){
	      public void handleMessage(Message msg) {
	    	  String reCode=(String)msg.obj;
	    	  if(reCode!=null&&reCode.equals("success")){
	    		  Toast.makeText(AddQuestionActivity.this,"问题发布成功", Toast.LENGTH_SHORT).show();
	    	  }
	    	  else{
	    		  Toast.makeText(AddQuestionActivity.this,"问题发布失败", Toast.LENGTH_SHORT).show();
	    	  }
	    	  
	    	  
	    	  
	      };
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_question);
		initView();
	}
    private void initView(){
    	tv_tag=(TextView)findViewById(R.id.tv_tag);
    	btn_narrow=(Button)findViewById(R.id.btn_narrow);
    	btn_back=(Button)findViewById(R.id.btn_back);
    	btn_submit=(Button)findViewById(R.id.btn_ok);
    	tv_content=(EditText)findViewById(R.id.tv_content);
    	tv_title=(EditText)findViewById(R.id.tv_title);
    	rl_tags=(RelativeLayout)findViewById(R.id.rl_tags);
    	btn_narrow.setOnClickListener(this);
    	btn_back.setOnClickListener(this);
    	btn_submit.setOnClickListener(this);
    	//初始化tag
    	tag0=(Button)findViewById(R.id.btn_tag_0);
    	tag0.setOnClickListener(this);
    	tag1=(Button)findViewById(R.id.btn_tag_1);
    	tag1.setOnClickListener(this);
    	tag2=(Button)findViewById(R.id.btn_tag_2);
    	tag2.setOnClickListener(this);
    	tag3=(Button)findViewById(R.id.btn_tag_3);
    	tag3.setOnClickListener(this);
    	tag4=(Button)findViewById(R.id.btn_tag_4);
    	tag4.setOnClickListener(this);
    	tag5=(Button)findViewById(R.id.btn_tag_5);
    	tag5.setOnClickListener(this);
    	tag6=(Button)findViewById(R.id.btn_tag_6);
    	tag6.setOnClickListener(this);
    	tag7=(Button)findViewById(R.id.btn_tag_7);
    	tag7.setOnClickListener(this);
    	tag8=(Button)findViewById(R.id.btn_tag_8);
    	tag8.setOnClickListener(this);
    	
    		
    }
    private boolean checkInfo(){
    	if(tv_title.equals("")){
    		Toast.makeText(this,"问题标题未填写",Toast.LENGTH_SHORT).show();
    		
    		
    		return false;
    	}
    	if(tv_content.equals("")){
    		Toast.makeText(this,"问题内容未填写",Toast.LENGTH_SHORT).show();
    		
    		
    		return false;
    	}
    	return true;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.btn_back:{
			finish();
		}
			break;
		case R.id.btn_ok:
		{
		
			if(!checkInfo()){
				return;
			}
			//开启线程，发布问题
			new Thread(){
				public void run() {
					MyApplication app=(MyApplication)getApplicationContext();
					PostParameter[] params=new PostParameter[4];
					params[0]=new PostParameter("userID",app.getUserVo().getUserID());
					params[1]=new PostParameter("title",tv_title.getText().toString());
					params[2]=new PostParameter("content",tv_content.getText().toString());
					params[3]=new PostParameter("subjectTag",""+tag);
					String jsonString=ConnectUtil.httpRequest(ConnectUtil.addQuestionUrl, params, ConnectUtil.POST);
					if(jsonString!=null){
						
							JSONObject result;
							try {
								result = new JSONObject(jsonString);
								String status=result.getString("Status");
								if(status.equalsIgnoreCase("success")){
									Message msg=new Message();
									msg.obj="success";
									myHandler.sendMessage(msg);
									Log.i("liuhaoxian", "问题发布成功");
								}
								else
								{
									Message msg=new Message();
									msg.obj="fail";
									myHandler.sendMessage(msg);
									
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Message msg=new Message();
								msg.obj=null;
								myHandler.sendMessage(msg);
								
							}
							
					}
					else
					{
						Message msg=new Message();
						msg.obj=null;
						myHandler.sendMessage(msg);
					}
					
					
					
				};
			}.start();
		}
			break;
		case R.id.btn_narrow:{
			btn_narrow.setBackgroundResource(R.drawable.narrow_blue);
			rl_tags.setVisibility(View.VISIBLE);		
			}
			break;
		
		case R.id.btn_tag_0:{
			tag=6;
			tv_tag.setText(tags[0]);
		}
		break;
        case R.id.btn_tag_1:{
			tag=0;
			tv_tag.setText(tags[1]);
		}
		break;
        case R.id.btn_tag_2:{
        	tag=1;
        	tv_tag.setText(tags[2]);
		}
		break;
        case R.id.btn_tag_3:{
        	tag=2;
        	tv_tag.setText(tags[3]);
        }
        break;
        case R.id.btn_tag_4:{
        	tag=4;
        	tv_tag.setText(tags[4]);
        }
        break;
        case R.id.btn_tag_5:{
        	tag=8;
        	tv_tag.setText(tags[5]);
        }
        break;
        case R.id.btn_tag_6:{
        	tag=7;
        	tv_tag.setText(tags[6]);
        }
        break;
        case R.id.btn_tag_7:{
        	tag=5;
        	tv_tag.setText(tags[7]);
        }
        break;
        case R.id.btn_tag_8:{
        	tag=9;
        	tv_tag.setText(tags[8]);
        }
        break;
		
		}
		//点击了标签
		if(v.getId()!=R.id.btn_back&&v.getId()!=R.id.btn_ok&&v.getId()!=R.id.btn_narrow)
		{
			btn_narrow.setBackgroundResource(R.drawable.narrow);
			rl_tags.setVisibility(View.GONE);
			Log.i("liuhaoxian","click Tag="+tag);
		}
		
	}
	

}
