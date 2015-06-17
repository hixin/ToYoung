package com.tuyuong.ge;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toyoung.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.toyoung.liu.MainHotActivity;
import com.toyuong.util.BitmapHelper;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;
import com.toyuong.util.QuestionTypeSelector;
import com.toyuong.util.ShowToast;
import com.tuyoung.wu.MyAnswer;

public class HotAnswerDetailActivity extends Activity implements
		OnClickListener {
	private TextView tv_question;
	private TextView tvName;
	private TextView tv_goodNum;
	private TextView tv_time;
	private TextView tv_answerdetail;

	private RelativeLayout v_back;
	private ImageView logo;
	private ImageView iv_isgood;
	private ImageView iv_issave;

	private int position;
	private int itemposition;
	private hotInfors hotinfor;
	private hotInfors.hotAnswers hotanswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_answer_detail);

		initData();
		initView();
	}

	/**
	 * 获取具体回答的数据
	 * */
	private void initData() {
		if (getIntent().getAction() == "MainHotItemActivity") {
			itemposition = getIntent().getExtras().getInt(
					MainHotItemActivity.INTENTEXTRA);
			hotinfor = MainHotItemActivity.hotinfor;
			hotanswer = MainHotItemActivity.hotanswers.get(itemposition);

		} else if (getIntent().getAction() == "MainHotActivity") {
			hotinfor = (hotInfors) getIntent()
					.getSerializableExtra("hotInfors");
			hotanswer = hotinfor.getHotanswer();

		} else if (getIntent().getAction() == "MyAnswer") {
			position = getIntent().getExtras().getInt("QUESTION_POSITION");
			itemposition = getIntent().getExtras().getInt(
					"com.toyoung.hot_item_answer_detail");
			hotinfor = MyAnswer.allhotinfors.get(position);
			hotanswer = hotinfor.getAnswers().get(itemposition);
		}
		httpGetdetailedAnswer(((MyApplication) getApplication()).getUserVo()
				.getUserID(), String.valueOf(hotanswer.getId()));
	}

	/**
	 * 初始化布局
	 * */
	private void initView() {
		tv_question = (TextView) findViewById(R.id.tv_question);
		tvName = (TextView) findViewById(R.id.tvName);
		tv_goodNum = (TextView) findViewById(R.id.tv_goodNum);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_answerdetail = (TextView) findViewById(R.id.tv_answerdetail);

		v_back = (RelativeLayout) findViewById(R.id.v_back);

		logo = (ImageView) findViewById(R.id.iv_quserlogo);
		iv_isgood = (ImageView) findViewById(R.id.iv_isgood);
		iv_issave = (ImageView) findViewById(R.id.iv_issave);

		((TextView) findViewById(R.id.tv_class)).setText(QuestionTypeSelector
				.getType(hotinfor.getQuestiontype()));
		tv_question.setText(hotinfor.getQuestion());
		tvName.setText(hotanswer.getAusername());

		tv_answerdetail.setText(hotanswer.getAnswser());

		ImageLoader.getInstance().displayImage(hotanswer.getAuserloge(), logo,
				MainHotActivity.imageLodeoptions);

		if (hotanswer.getGoodshow() == true)
			iv_isgood.setImageBitmap(new BitmapHelper().readBitMap(this,
					R.drawable.answer_support));
		else
			iv_isgood.setImageBitmap(new BitmapHelper().readBitMap(this,
					R.drawable.answer_supported));
		if (hotanswer.getSaveshow() == true)
			iv_issave.setImageBitmap(new BitmapHelper().readBitMap(this,
					R.drawable.question_save));
		else
			iv_issave.setImageBitmap(new BitmapHelper().readBitMap(this,
					R.drawable.question_saved));

		logo.setOnClickListener(this);
		iv_isgood.setOnClickListener(this);
		iv_issave.setOnClickListener(this);
		v_back.setOnClickListener(this);
		findViewById(R.id.rl_good).setOnClickListener(this);
		findViewById(R.id.rl_save).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.v_back:
			this.finish();
			break;
		case R.id.iv_quserlogo:
			break;
		case R.id.rl_good:
			if (hotanswer.getGoodshow() == true) {
				int num2 = hotanswer.getAgoodnum();
				hotanswer.setAgoodnum(num2 + 1);
				// 增加支持
				addSupportAnswer(String.valueOf(hotanswer.getId()));

				tv_goodNum.setText(num2 + 1 + "");
				iv_isgood.setImageBitmap(new BitmapHelper().readBitMap(this,
						R.drawable.answer_supported));
				hotanswer.setGoodshow(false);
			} else {
				int num2 = hotanswer.getAgoodnum();
				hotanswer.setAgoodnum(num2 - 1);
				tv_goodNum.setText(num2 - 1 + "");
				iv_isgood.setImageBitmap(new BitmapHelper().readBitMap(this,
						R.drawable.answer_support));
				hotanswer.setGoodshow(true);
			}
			break;
		case R.id.rl_save:
			if (hotanswer.getSaveshow() == true) {
				// 收藏
				collectQuestion(((MyApplication) getApplication()).getUserVo()
						.getUserID(), String.valueOf(hotinfor.getId()));

				iv_issave.setImageBitmap(new BitmapHelper().readBitMap(this,
						R.drawable.question_saved));
				hotanswer.setSaveshow(false);
			} else {
				iv_issave.setImageBitmap(new BitmapHelper().readBitMap(this,
						R.drawable.question_save));
				hotanswer.setSaveshow(true);
			}
			break;
		default:
			break;
		}
	}

	private void httpGetdetailedAnswer(final String userid,
			final String Answerid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[2];
				params[0] = new PostParameter("userID", userid);
				params[1] = new PostParameter("answerID", Answerid);

				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.getDetailedAnswers, params,
						ConnectUtil.POST);
				Message msg = new Message();
				if (jsonString != null) {

					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {

							hotanswer.setAgoodnum(result
									.getInt("answer_support"));
							hotanswer.setAtime(result.getString("answer_date"));

							msg.what = 1;// 代表成功
						} else {
							msg.what = 2;
							msg.obj = "加载失败";
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

	/**
	 * 增加支持
	 * */
	private void addSupportAnswer(final String answerID) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[1];
				params[0] = new PostParameter("answerID", answerID);
				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.addSupportAnswer, params, ConnectUtil.POST);
				Message msg = new Message();
				msg.what = 3;
				if (jsonString != null) {
					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {
							msg.obj = "+1";
						} else
							msg.obj = "点赞失败";
					} catch (JSONException e) {
						msg.what = 0;
					}
				} else
					msg.what = 0;
				myHandler.sendMessage(msg);
			}
		}).start();
	}

	private void collectQuestion(final String userID, final String problemID) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[2];
				params[0] = new PostParameter("userID", userID);
				params[1] = new PostParameter("problemID", problemID);
				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.collectQuestion, params, ConnectUtil.POST);
				Message msg = new Message();
				msg.what = 3;
				if (jsonString != null) {
					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {
							msg.obj = "收藏成功";
						} else {
							msg.obj = "收藏失败";
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = 0;
					}
				} else
					msg.what = 0;
				myHandler.sendMessage(msg);
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 1) {

				tv_goodNum.setText(hotanswer.getAgoodnum() + "");
				tv_time.setText(hotanswer.getAtime());

			}  else if (msg.what == 0) {
				// 网络连接异常
				new ShowToast().shortshow(HotAnswerDetailActivity.this, "网络异常");
			}else
				new ShowToast().shortshow(HotAnswerDetailActivity.this, String.valueOf(msg.obj));
			}
	};
}
