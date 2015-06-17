package com.tuyuong.ge;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toyoung.R;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;

@SuppressLint("HandlerLeak")
public class SendAnswerActivity extends Activity implements OnClickListener {

	private int problem_id;
	private EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_answer);

		problem_id = getIntent().getIntExtra("QUESTION_ID", 0);

		initView();
	}

	private void initView() {
		et_content = (EditText) findViewById(R.id.et_content);

		findViewById(R.id.v_back).setOnClickListener(this);
		findViewById(R.id.btn_ok).setOnClickListener(this);

		// 如果没有输入，那么无法点击发送
		et_content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (et_content.getText().length() > 0) {
					findViewById(R.id.btn_ok).setClickable(true);
				} else {
					findViewById(R.id.btn_ok).setClickable(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.v_back:
			finish();
			break;
		case R.id.btn_ok:

			new AlertDialog.Builder(this)
					.setTitle("确认发布吗？")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							})
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									// TODO Auto-generated method stub
									sendMyAnswer();
									dialog.dismiss();
								}
							}).show();

			break;
		default:
			break;
		}
	}

	private void sendMyAnswer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();

				PostParameter[] params = new PostParameter[3];
				params[0] = new PostParameter("userID",
						((MyApplication) getApplication()).getUserVo()
								.getUserID());
				params[1] = new PostParameter("problemID", String
						.valueOf(problem_id));
				params[2] = new PostParameter("content", et_content.getText()
						.toString());
				String jsonString = ConnectUtil.httpRequest(
						ConnectUtil.addCertainAnswer, params, ConnectUtil.POST);
				if (jsonString != null) {
					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {
							MainHotItemActivity.hotanswers.add(new hotInfors().new hotAnswers()
									.setId(result.getInt("answerID"))
									// 设置此回答的id
									.setAnswser(et_content.getText().toString())
									.setAuserid(
											Integer.parseInt(((MyApplication) getApplication())
													.getUserVo().getUserID()))
									.setAusername(
											((MyApplication) getApplication())
													.getUserVo().getNickName())
									.setAuserloge(
											"http://img2.imgtn.bdimg.com/it/u=3776587950,2250145698&fm=21&gp=0.jpg"));
							msg.what = 1;// 代表成功
						} else {
							msg.what = 2;
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = 0;
					}
					myHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 1) {

				finish();
			} else if (msg.what == 2) {
				Toast.makeText(SendAnswerActivity.this, "上传失败",
						Toast.LENGTH_SHORT).show();
			} else {
				// 网络连接异常
				Toast.makeText(SendAnswerActivity.this, "网络异常",
						Toast.LENGTH_SHORT).show();
			}
		};
	};

}
