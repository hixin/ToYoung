package com.toyoung.liu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.toyoung.R;
import com.example.toyoung.R.layout;
import com.example.toyoung.R.menu;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.DialogFactory;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
import com.tuyoung.vo.UserVo;
import com.tuyuong.ge.HotAnswerDetailActivity;
import com.tuyuong.ge.hotInfors;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnItemClickListener {

	private QuestionListAdapter adapter;
	private ArrayList<hotInfors> questions=new ArrayList<hotInfors>();
	private Button btn_cancel,btn_search;
	private TextView tv_keyword;
	private ListView listView;
	private Dialog mDialog;
	
	
	private Handler myHandler=new Handler(){
		public void handleMessage(Message msg) {
			dismissRequestDialog();
			if(msg.what==1){
				ArrayList<hotInfors> temp=(ArrayList<hotInfors> )msg.obj;
				questions.clear();
				if(temp.size()==0){
					//没有搜索到结果
					Toast.makeText(SearchActivity.this, "没有相关结果", Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					for(hotInfors question:temp){
						questions.add(question);
					}
					
				}
				adapter=new QuestionListAdapter(SearchActivity.this, questions);
				listView.setAdapter(adapter);
				
			}
			else
			{
				//网络连接异常
				Toast.makeText(SearchActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}
			
			
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		
	}
    private void initView(){
    	btn_cancel=(Button)findViewById(R.id.btn_cancel);
    	btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    	btn_search=(Button)findViewById(R.id.btn_search);
    	btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("liuhaoxian", "doSearch");
				String keyword=tv_keyword.getText().toString();
				if(!keyword.equals("")){
					showRequestDialog();
					getQuestions(keyword);
				}
				
			}
		});
    	tv_keyword=(EditText)findViewById(R.id.et_keyword);
    	
    	listView=(ListView)findViewById(R.id.listview);
    	listView.setOnItemClickListener(this);
    	
    }
	private void getQuestions(final String keyword){
		new Thread(){
			public void run() {
				PostParameter[] params=new PostParameter[1];
				params[0]=new PostParameter("keyword",keyword);
				String jsonString=ConnectUtil.httpRequest(ConnectUtil.searchUrl, params, ConnectUtil.POST);
				if(jsonString!=null){
					
					try {
						JSONObject result = new JSONObject(jsonString);
						String status=result.getString("Status");
						if(status.equalsIgnoreCase("success")){
							JSONArray jsonQuestions=result.getJSONArray("QuestionList");
							//JSONObject questionList=result.getJSONObject("QuestionList");
							//JSONArray jsonQuestions=questionList.getJSONArray("Question");
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
		//adapter=new QuestionListAdapter(this, que);
		//listView.setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,HotAnswerDetailActivity.class);
		intent.putExtra("hotInfors", questions.get(arg2));
		startActivity(intent);
	}
	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在搜索...");
		mDialog.show();
	}

	private void dismissRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

}
