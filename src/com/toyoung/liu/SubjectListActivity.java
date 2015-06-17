package com.toyoung.liu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.toyoung.R;
import com.example.toyoung.R.layout;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.DialogFactory;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
import com.tuyuong.ge.HotAnswerDetailActivity;
import com.tuyuong.ge.MainHotItemActivity;
import com.tuyuong.ge.hotInfors;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectListActivity extends Activity implements OnItemClickListener {

	private QuestionListAdapter adapter;
	private ArrayList<hotInfors> questions=new ArrayList();
	private ListView listView;
	private TextView tv_title;
	private ImageView imageView;
	private Button btn_back;
	private int tag=0;
	private String[] tags={"考试心得","学校申请",
            "吃喝指南","购物秘籍","留学情感地","就业打工","交通住宿",
            "寻找小伙伴","文化观察","其他问题"};
	private int[] picIDs={R.drawable.tag_pic_0,R.drawable.tag_pic_1,R.drawable.tag_pic_2,
			             R.drawable.tag_pic_3,R.drawable.tag_pic_4,R.drawable.tag_pic_5,
			             R.drawable.tag_pic_6,R.drawable.tag_pic_7,R.drawable.tag_pic_8,R.drawable.tag_pic_9
			            };
	private Dialog mDialog;
	private Handler myHandler=new Handler(){
		public void handleMessage(Message msg) {
			dismissRequestDialog();
			if(msg.what==1){
				ArrayList<hotInfors> temp=(ArrayList<hotInfors> )msg.obj;
				questions.clear();
				if(temp.size()==0){
					
					Toast.makeText(SubjectListActivity.this, "该专题目前没有问题", Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					for(hotInfors question:temp){
						questions.add(question);
					}
					
				}
				adapter=new QuestionListAdapter(SubjectListActivity.this, questions);
				listView.setAdapter(adapter);
				
			}
			else
			{
				//网络连接异常
				Toast.makeText(SubjectListActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
			
			
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_list);
		initView();
		initData();
	}
    private void initView(){
    	tv_title=(TextView)findViewById(R.id.tv_title);
    	imageView=(ImageView)findViewById(R.id.im_pic);
    	listView=(ListView)findViewById(R.id.listview);
    	btn_back=(Button)findViewById(R.id.btn_back);
    	btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    	listView.setOnItemClickListener(this);
    }
	private void initData(){
		//获取专题tag
		tag=getIntent().getIntExtra("subjectTag", 0);
		tv_title.setText(tags[tag]);
		imageView.setImageResource(picIDs[tag]);
		showRequestDialog();
		MyApplication app=(MyApplication)getApplication();
		getQuestions(app.getUserVo().getUserID(), ""+tag);
		
		
//		for(int i=0;i<10;i++)
//		{
//			datas.add(i);
//		}
//		adapter=new QuestionListAdapter(this, datas);
//		listview.setAdapter(adapter);
		
		
	}
	private void getQuestions(final String userID,final String subjectTag){
		new Thread(){
			public void run() {
				PostParameter[] params=new PostParameter[2];
				params[0]=new PostParameter("userID",userID);
				params[1]=new PostParameter("subjectTag",subjectTag);
				String jsonString=ConnectUtil.httpRequest(ConnectUtil.getSubjectListUrl, params, ConnectUtil.POST);
				if(jsonString!=null){
					
					try {
						JSONObject result = new JSONObject(jsonString);
						String status=result.getString("Status");
						if(status.equalsIgnoreCase("success")){
							
							JSONArray jsonQuestions=result.getJSONArray("QuestionList");
							
							//JSONArray jsonQuestions=questionList.getJSONArray(questionList.toString());
							ArrayList <hotInfors> tmps=new ArrayList<hotInfors>();
							for(int i=0;i<jsonQuestions.length();i++){
								hotInfors infors=new hotInfors();
								JSONObject obj=(JSONObject)jsonQuestions.get(i);
								infors.setQuserid(Integer.parseInt(obj.getString("UserID")));
								infors.setTitle(obj.getString("Title"));
								infors.setId(Integer.parseInt(obj.getString("ProblemID")));
								tmps.add(infors);
								
							}
							Message msg=new Message();
							msg.what=1;//代表成功
							msg.obj=tmps;
							myHandler.sendMessage(msg);
						}
						else
						{
							
							Message msg=new Message();
							msg.what=1;
							msg.obj=new ArrayList<hotInfors>();
							myHandler.sendMessage(msg);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Message msg=new Message();
						msg.what=0;
						myHandler.sendMessage(msg);
					}
					
					
				}
			
			};
		}.start();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,MainHotItemActivity.class);
		intent.setAction("SubjectListActivity");
		intent.putExtra("hotInfors", questions.get(arg2));
		startActivity(intent);
	}

	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在加载...");
		mDialog.show();
	}

	private void dismissRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
