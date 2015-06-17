package com.tuyuong.ge;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toyoung.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.toyoung.liu.MainHotActivity;
import com.toyuong.util.BitmapHelper;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
import com.toyuong.util.QuestionTypeSelector;
import com.tuyoung.wu.MyQuestion;

/**
 * 热门问题的详情页面
 * */
@SuppressLint("HandlerLeak")
public class MainHotItemActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	private ListView lv_hotanswerslist;
	private TextView tv_qusername;
	private TextView tv_answerNum;
	private TextView tv_addnum;
	private TextView tv_time;
	private TextView tv_question;
	private ImageView iv_quserlogo;

	public static hotInfors hotinfor;// 问题加所有回答的hotinfor对象
	public static List<hotInfors.hotAnswers> hotanswers;
	private int position;
	private static hotanswerslistAdapter mAdapter;

	private String TAG = "MainHotItemActivity";
	public static String INTENTEXTRA = "com.toyoung.hot_item_answer_detail";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot_item_details);

		if (getIntent().getAction().equals("com.tuyoung.hot.item")) {
			// position = getIntent().getExtras().getInt(
			// MainHotActivity.INTENTEXTRA);
			hotinfor = (hotInfors) getIntent()
					.getSerializableExtra("hotInfors");

			initData();// 获取热门问题的所有回答数据

		} else if (getIntent().getAction() == "SubjectListActivity") {
			hotinfor = (hotInfors) getIntent()
					.getSerializableExtra("hotInfors");

			initData();// 获取热门问题的所有回答数据
		} else if (getIntent().getAction()
				.equals("com.tuyoung.myquestion.item")) {
			position = getIntent().getExtras().getInt(
					MainHotActivity.INTENTEXTRA);
			Log.v(TAG, position + "");
			initData2();
		}

		initView();
	}

	/**
	 * 由hot进来的数据
	 * */
	private void initData() {
		// hotinfor = MainHotActivity.allhotinfors.get(position);
		hotanswers = hotinfor.getAnswers();
		String problemid = String.valueOf(hotinfor.getId());
		String userid = ((MyApplication) getApplication()).getUserVo()
				.getUserID();

		httpgetAnswers(problemid, userid);

	}

	/**
	 * 由我的进来的数据
	 * */
	private void initData2() {
		hotinfor = MyQuestion.allhotinfors.get(position);
		hotanswers = hotinfor.getAnswers();
	}

	private void initView() {
		iv_quserlogo = (ImageView) findViewById(R.id.iv_quserlogo);
		tv_answerNum = (TextView) findViewById(R.id.tv_answerNum);
		tv_addnum = (TextView) findViewById(R.id.tv_addNum);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_question = (TextView) findViewById(R.id.tv_question);
		tv_qusername = (TextView) findViewById(R.id.tv_qusername);

		findViewById(R.id.iv_isadd).setOnClickListener(this);
		findViewById(R.id.iv_chat).setOnClickListener(this);
		findViewById(R.id.v_back).setOnClickListener(this);
		findViewById(R.id.rl_add).setOnClickListener(this);
		findViewById(R.id.rl_chat).setOnClickListener(this);

		if (hotinfor.getAddshow() == true)
			((ImageView) findViewById(R.id.iv_isadd))
					.setImageBitmap(new BitmapHelper().readBitMap(this,
							R.drawable.question_add));
		else
			((ImageView) findViewById(R.id.iv_isadd))
					.setImageBitmap(new BitmapHelper().readBitMap(this,
							R.drawable.question_added));

		lv_hotanswerslist = (ListView) findViewById(R.id.lv_answeritems);
		mAdapter = new hotanswerslistAdapter(this, hotanswers,
				lv_hotanswerslist);
		lv_hotanswerslist.setAdapter(mAdapter);

		lv_hotanswerslist.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int itemposition,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HotAnswerDetailActivity.class);
		intent.setAction("MainHotItemActivity");
		intent.putExtra(INTENTEXTRA, itemposition);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.v_back:
			finish();
			break;
		case R.id.rl_add:
			if (hotinfor.getAddshow() == true) {
				int num2 = hotinfor.getQsamenum();
				hotinfor.setQsamenum(num2 + 1);

				addSameQuestion(String.valueOf(hotinfor.getId()));// 服务器交互增加同问数量

				tv_addnum.setText(num2 + 1 + "");
				((ImageView) findViewById(R.id.iv_isadd))
						.setImageBitmap(new BitmapHelper().readBitMap(this,
								R.drawable.question_added));
				hotinfor.setAddshow(false);
			} else {
				int num2 = hotinfor.getQsamenum();
				hotinfor.setQsamenum(num2 - 1);
				tv_addnum.setText(num2 - 1 + "");
				((ImageView) findViewById(R.id.iv_isadd))
						.setImageBitmap(new BitmapHelper().readBitMap(this,
								R.drawable.question_add));
				hotinfor.setAddshow(true);
			}
			break;
		case R.id.rl_chat:
			Intent intent = new Intent(this, SendAnswerActivity.class);
			intent.putExtra("QUESTION_ID", hotinfor.getId());
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	private void httpgetAnswers(final String problemid, final String userid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[2];
				params[0] = new PostParameter("userID", userid);
				params[1] = new PostParameter("problemID", problemid);

				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.getCeratinQuestionAnswers, params,
						ConnectUtil.POST);
				Message msg = new Message();
				if (jsonString != null) {

					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {
							JSONObject jsonQuestions = result
									.getJSONObject("QuestionList");
							hotinfor.setQuestiontype(jsonQuestions
									.getInt("problem_tag"));
							hotinfor.setQuserid(jsonQuestions
									.getInt("problem_userID"));
							hotinfor.setQusername(jsonQuestions
									.getString("problem_userName"));// 提问者姓名
							hotinfor.setTitle(jsonQuestions
									.getString("problem_title"));// 问题标题
							hotinfor.setQtime(jsonQuestions
									.getString("problem_date"));// 问题时间
							hotinfor.setQuestion(jsonQuestions
									.getString("problem_content"));// 问题内容
							hotinfor.setquserlogo("http://a3.att.hudong.com/48/16/11300162102116132162169086208_180.jpg");

							hotinfor.setQsamenum(jsonQuestions
									.getInt("problem_same"));// 同问数量

							if (!jsonQuestions.get("answers").equals("")) {
								JSONArray jsonAnswers = jsonQuestions
										.getJSONArray("answers");

								for (int j = 0; j < jsonAnswers.length(); j++) {
									hotInfors.hotAnswers answers = hotinfor.new hotAnswers();
									JSONObject ansobj = (JSONObject) jsonAnswers
											.get(j);
									answers.setId(ansobj.getInt("answer_id"));// 回答id
									answers.setAusername(ansobj
											.getString("answer_userName"));// 回答者昵称
									answers.setAnswser(ansobj
											.getString("answer_content"));// 回答内容
									answers.setAtime(ansobj
											.getString("answer_date"));// 回答时间
									answers.setAuserloge("http://img2.imgtn.bdimg.com/it/u=3776587950,2250145698&fm=21&gp=0.jpg");
									answers.setAgoodnum(ansobj
											.getInt("answer_support"));// 支持数量

									Log.v("answer", ansobj.getInt("answer_id")
											+ "");

									hotanswers.add(answers);
								}
							}
							hotinfor.setAnswers(hotanswers);
							msg.what = 1;// 代表成功
						} else {
							msg.what = 2;
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = 0;
					}
				} else {
					msg.what = 0;
				}
				myHandler.sendMessage(msg);
			}
		}).start();
	}

	// 点击同问
	private void addSameQuestion(final String questionID) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[1];
				params[0] = new PostParameter("questionID", questionID);

				Message msg = new Message();
				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.addSameQusetion, params, ConnectUtil.POST);
				if (jsonString != null) {

					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {

						} else {
							msg.what = 3;
							myHandler.sendMessage(msg);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = 0;
						myHandler.sendMessage(msg);
					}
				} else {
					msg.what = 0;
					myHandler.sendMessage(msg);
				}

			}
		}).start();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 1) {

				((TextView) findViewById(R.id.tv_class))
						.setText(QuestionTypeSelector.getType(hotinfor
								.getQuestiontype()));
				tv_qusername.setText(hotinfor.getQusername());
				ImageLoader.getInstance().displayImage(hotinfor.getQuserlogo(),
						iv_quserlogo, MainHotActivity.imageLodeoptions);
				tv_addnum.setText(hotinfor.getQsamenum() + "");
				tv_time.setText(hotinfor.getQtime());
				tv_answerNum.setText("共" + hotanswers.size() + "条回答");
				tv_question.setText(hotinfor.getQuestion());

				if (hotanswers.size() == 0) {
					Toast.makeText(MainHotItemActivity.this, "目前没有回答内容",
							Toast.LENGTH_SHORT).show();
				} else {
					mAdapter.notifyDataSetChanged();
				}
			} else if (msg.what == 2) {
				Toast.makeText(MainHotItemActivity.this, "加载失败",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(MainHotItemActivity.this, "连接失败",
						Toast.LENGTH_SHORT).show();
			} else {
				// 网络连接异常
				Toast.makeText(MainHotItemActivity.this, "网络异常",
						Toast.LENGTH_SHORT).show();
			}

		};
	};
}
