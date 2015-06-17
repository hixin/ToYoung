package com.tuyoung.wu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toyoung.R;
import com.toyoung.liu.MainMeActivity.NumChangeReceiver;
import com.toyoung.wu.bean.Answer;
import com.toyuong.util.MyApplication;
import com.tuyuong.ge.hotInfors;


public class MyAnswer extends BaseListFragment<Answer>{
	private String[] tags = { "交通住宿", "考试心得", "学校申请", "吃喝指南", "留学情感地", "文化观察",
			"寻找小伙伴", "就业打工", "其他问题" };
	protected static final String TAG = MyAnswer.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "anslist_";

	public static List<hotInfors> allhotinfors;
	
	public static final String INTENTEXTRA = "hotitem_position";
	
	private MyApplication app;

	@Override
	protected ListBaseAdapter<Answer> getListAdapter() {
		return new AnswerListAdapter();
	}

	@Override
	protected String getCacheKeyPrefix() {
		return CACHE_KEY_PREFIX + mCatalog;
	}

	@Override
	protected List<Answer> parseList(String is) throws Exception {
		Log.i("child","execute");
		List<Answer> list = null;
		try {
		    list = jsonToAns(is);
		    Log.i("list",list.toString());
		} catch (NullPointerException e) {
			
		}
		return list;
	}

	@Override
	protected List<Answer> readList(Serializable seri) {
		return (List<Answer>) (seri);
	}

	@Override
	protected void sendRequestData() {
	
		HttpReqData.getAnswerList("answer", "15", mHandler);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Answer ans = mAdapter.getItem(position);
		if (ans != null) {
//			UIHelper.showNewsRedirect(view.getContext(), ques);

			// 放入已读列表
//			saveToReadedList(view, QuestionList.PREF_READED_NEWS_LIST, ques.getId()
//					+ "");
		}
	}

	@Override
	protected void executeOnLoadDataSuccess(List<Answer> data) {
		if (mCatalog > 10) {
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
			if (mState == STATE_REFRESH)
				mAdapter.clear();
			mAdapter.addData(data);
			mState = STATE_NOMORE;
			mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
			return;
		}
		super.executeOnLoadDataSuccess(data);
	}

	@Override
	protected long getAutoRefreshTime() {
		// 最新资讯两小时刷新一次
		if (mCatalog < 10) {
			return 2 * 60 * 60;
		}
		return super.getAutoRefreshTime();
	}

	class AnswerListAdapter extends ListBaseAdapter<Answer> {
		@Override
		protected View getRealView(int position, View convertView,
				ViewGroup parent) {
			ViewHolder2 viewHolder = null;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder2();
				convertView = getLayoutInflater(parent.getContext()).inflate(
						R.layout.list_cell_answer, parent, false);
				viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.tv_title);
		        viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.tv_question);
		        viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.tv_answer);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder2) convertView.getTag();
			}

			Answer ans = mDatas.get(position);
			viewHolder.mTextView1.setText(tags[ans.getLable() % 8]);
			viewHolder.mTextView2.setText(ans.getQues_content());
			viewHolder.mTextView3.setText(ans.getAnws_content());
			return convertView;
		}

	}

	public class ViewHolder2{
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;

	}
	
	private List<Answer> jsonToAns(String jsons){
		
		try {			
			JSONObject jsonObject = new JSONObject(jsons);
			String status = jsonObject.getString("Status");
			String type = jsonObject.getString("type");
			if(status.equals("success")  && type.equals("answer")){
				String snum = jsonObject.getString("num");
				Log.i("num",snum);
				int num = Integer.parseInt(snum);
				if(num != 0) {
					MyApplication.context().ansnum =num;
					Intent broadcast = new Intent("myInfoNumChange");  
		            getActivity().sendBroadcast(broadcast);  
					
				}
				String sanswers = jsonObject.getString("answers");
				Log.i("sanswers",sanswers);
				JSONArray ansArray = new JSONArray(sanswers);
				List<Answer> answers = new ArrayList<Answer>();
				for(int i=0; i< ansArray.length(); i++){
				    JSONObject ansObject = ansArray.getJSONObject(i);
				    String sques = ansObject.getString("question_content");
				    String sans = ansObject.getString("answer_content");
				    Log.i("sques", sques);
				    String stag = ansObject.getString("Tag");
				    String squesid = ansObject.getString("problemID");
				    int id = ansObject.getInt("answerID");
				    int quesid  = Integer.parseInt(squesid);
				    int lable = Integer.parseInt(stag);
				    Answer ans= new Answer(id, quesid, lable, sques ,sans);
				    Log.i("ans", ans.toString());
				    answers.add(ans);
				}
				Log.i("anslist", answers.toString());
				return answers;
			}						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		List<Answer> answers = new ArrayList<Answer>();
		return answers;
	}

}
