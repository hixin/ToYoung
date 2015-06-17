package com.tuyuong.ge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.RemoteControlClient.OnGetPlaybackPositionListener;
import android.os.Message;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.toyoung.liu.MainHotActivity;
import com.toyuong.util.ConnectUtil;
import com.toyuong.util.MyApplication;
import com.toyuong.util.PostParameter;

/**
 * 获取hot页面的的热门提问列表的类
 * */
@SuppressLint("SimpleDateFormat")
public class hotlistCreater {
	private static List<hotInfors> tenhotinfors = new ArrayList<hotInfors>();// 需要加载的最新的十条状态\
	public static netnearDataHandler mListeners;

	/**
	 * http返回最新的十条hot问题集合
	 * */
	public void startgetTenhotinfors(MyApplication app,String index) {
		// 通过http请求获取最近的十条附近状态数据
		inithttptenData(app,index);
	}
	
	/**
	 * 返回最新的十条hot问题集合
	 * */
	public List<hotInfors> getTenhotinfors(MyApplication app) {
		if (tenhotinfors.size() > 0)
			tenhotinfors.clear();
		// 通过http请求获取最近的十条附近状态数据
		// initnowtenData();
		Log.v("times==","time!!!");
		initnowtenData();

		return tenhotinfors;
	}

	/**
	 * 本地测试生成十条记录
	 */
	private void initnowtenData() {
		hotInfors hotinfor = new hotInfors();// 实例化附近信息类;
		// 10条记录
		for (int i = 0; i < 10; i++) {
			hotInfors clonehotinfor = hotinfor.clone();

			List<hotInfors.hotAnswers> hotanswers = new ArrayList<hotInfors.hotAnswers>();

			hotInfors.hotAnswers hotanswer;
			for (int i2 = 0; i2 < 8; i2++) {
				hotanswer = clonehotinfor.new hotAnswers();// enclosing class实例化方法
				hotanswer
						.setId(i2)
						.setAuserid(i2)
						.setAuserloge(
								"http://img2.imgtn.bdimg.com/it/u=3776587950,2250145698&fm=21&gp=0.jpg")
						.setAusername("葛兵" + i2)
						.setAnswser(
								"很简单啦，一共有两种方法，第一章是的数据库的健康饿就饿 的低价读书的话id思考对上杜绝今天天气不错哦，打算下午饿 的低价读书的话id思考对上杜绝今天天气不错哦，打算下午去体育馆打篮球去体育馆打篮球，有一起去的吗今天天气不错哦，打算下午去")
						.setAtime(
								new SimpleDateFormat("yyyy-MM-dd HH:mm")
										.format(new Date()))
						.setAgoodnum(i2 + 2001);
				hotanswers.add(hotanswer);
			}

			clonehotinfor
					.setId(i)
					.setInforclass(i)
					.setQuserid(i)
					.setQusername("yeran" + i)
					.setquserlogo(
							"http://a3.att.hudong.com/48/16/11300162102116132162169086208_180.jpg")
					.setQuestion("美国怎么打出租车，和中国一样吗？")
					.setQtime(
							new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.format(new Date().getTime() - 2000 * i))
					.setQsamenum(i + 1006).setAnswers(hotanswers);
			Log.v("clone===", clonehotinfor.getQusername());
			tenhotinfors.add(clonehotinfor);
		}
	}

	/**
	 * http请求获取10条数据
	 * */
	private void inithttptenData(final MyApplication app,final String index) {
		new Thread(new Runnable() {
			@SuppressWarnings("null")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PostParameter[] params = new PostParameter[2];
				params[0] = new PostParameter("userID", app.getUserVo()
						.getUserID());
				params[1] = new PostParameter("index", index);
				String jsonString = ConnectUtil
						.httpRequest(ConnectUtil.gethotQuestions, params,
								ConnectUtil.POST);
				if (jsonString != null) {

					try {
						JSONObject result = new JSONObject(jsonString);
						String status = result.getString("Status");
						if (status.equalsIgnoreCase("success")) {
							JSONArray jsonQuestions = result
									.getJSONArray("QuestionList");

							ArrayList<hotInfors> hotinfors = new ArrayList<hotInfors>();
							for (int i = 0; i < jsonQuestions.length(); i++) {
								hotInfors infors = new hotInfors();
								JSONObject obj = (JSONObject) jsonQuestions
										.get(i);
								infors.setId(obj
										.getInt("problem_id"));//问题id
								infors.setQuestiontype(obj.getInt("problem_tag"));//问题的类别
								infors.setQuserid(obj
										.getInt("problem_userID"));//提问者id
								infors.setTitle(obj.getString("problem_title"));//问题的标题
								infors.setQuestion(obj.getString("problem_content"));//问题的具体内容
								JSONObject hotanswer = obj.getJSONObject("answers");
								
								int lastanswerid = hotanswer
										 .getInt("answer_id");//回复id
								String lastanswercontent = hotanswer
										 .getString("answer_content");//回复内容
								String lastansweruserlogo="http://img2.imgtn.bdimg.com/it/u=3776587950,2250145698&fm=21&gp=0.jpg";//回复者头像url
								String ausername = hotanswer.getString("answer_userName");//回复者昵称
								
//								int lastanswerid = 1;//回复id
//								String lastanswercontent = "很简单啦，一共有两种方法，第一章是的数据库的健康饿就饿 的低价读书的话id思考对上杜绝今天天气不错哦，打算下午饿 的低价读书的话id思考对上杜绝今天天气不错哦，打算下午去体育馆打篮球去体育馆打篮球，有一起去的吗今天天气不错哦，打算下午去";
//								//回复内容
//								String lastansweruserlogo="http://img2.imgtn.bdimg.com/it/u=3776587950,2250145698&fm=21&gp=0.jpg";//回复者头像url
//								String ausername = "葛兵";//回复者昵称
								
								infors.setHotanswer(infors.new hotAnswers()//设置热门回答
								.setId(lastanswerid)
								.setAuserid(hotanswer.getInt("answer_userID"))
								.setAnswser(lastanswercontent)
								.setAuserloge(lastansweruserlogo)
								.setAusername(ausername));

								hotinfors.add(infors);								
							}
							Message msg = new Message();
							msg.what = 1;// 代表成功
							msg.obj = hotinfors;

							mListeners.ongetDataChange(msg);

						} else {

							Message msg = new Message();
							msg.what = 1;
							msg.obj = new ArrayList<hotInfors>();
							mListeners.ongetDataChange(msg);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Message msg = new Message();
						msg.what = 0;

						mListeners.ongetDataChange(msg);
					}
				}
			}
		}).start();
	}
	
	

	public abstract interface netnearDataHandler {
		/**
		 * 网络状态改变，后期执行部分
		 */
		public abstract void ongetDataChange(Message msg);
	}
}
