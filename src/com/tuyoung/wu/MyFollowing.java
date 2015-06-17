package com.tuyoung.wu;

import java.io.Serializable;
import java.util.List;
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
import com.toyoung.wu.bean.Follow;
import com.toyoung.wu.bean.Question;
import com.toyuong.util.MyApplication;
import com.tuyoung.wu.MyQuestion.ViewHolder;

public class MyFollowing extends BaseListFragment<Follow>{
	protected static final String TAG = MyFollowing.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "followlist_";
	
	@Override
	protected ListBaseAdapter<Follow> getListAdapter() {
		return new FollowListAdapter();
	}

	@Override
	protected String getCacheKeyPrefix() {
		return CACHE_KEY_PREFIX + mCatalog;
	}

	@Override
	protected List<Follow> parseList(String is) throws Exception {
		Log.i("child","execute");
		List<Follow> list = null;
		try {
		    list = jsonToFollow(is);
		} catch (NullPointerException e) {
		}
		return list;
	}

	@Override
	protected List<Follow> readList(Serializable seri) {
		return (List<Follow>) (seri);
	}

	@Override
	protected void sendRequestData() {
	
		HttpReqData.getFollowList("follow", "15", mHandler);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Follow fols = mAdapter.getItem(position);
		if (fols != null) {
//			UIHelper.showNewsRedirect(view.getContext(), ques);

			// 放入已读列表
//			saveToReadedList(view, QuestionList.PREF_READED_NEWS_LIST, ques.getId()
//					+ "");
		}
	}

	@Override
	protected void executeOnLoadDataSuccess(List<Follow> data) {
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
	
	class FollowListAdapter extends ListBaseAdapter<Follow> {
		@Override
		protected View getRealView(int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater(parent.getContext()).inflate(
						R.layout.list_cell_follow, parent, false);

				 viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.tv_name);
		            viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.tv_location);
		            viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.tv_activity);
		            viewHolder.userphoto = (CircleImageView) convertView.findViewById(R.id.iv_avatar1);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			Follow follow = mDatas.get(position);
			viewHolder.mTextView1.setText(follow.getUsername());
			viewHolder.mTextView2.setText(follow.getLocation());
			viewHolder.mTextView3.setText(follow.getPerson_signa());
			viewHolder.userphoto.setImageResource(R.drawable.userphoto0);
			return convertView;
		}

	}
	

	private  class ViewHolder  
	{ 
		CircleImageView userphoto;
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;

	}
	
	private List<Follow> jsonToFollow(String jsons){
		
		try {			
			JSONObject jsonObject = new JSONObject(jsons);
			String status = jsonObject.getString("Status");
			String type = jsonObject.getString("type");
			if(status.equals("success")  && type.equals("follow")){
				int num = jsonObject.getInt("num");
				if(num != 0) {
					MyApplication.context().follnum =num;
					Intent broadcast = new Intent("myInfoNumChange");  
		            getActivity().sendBroadcast(broadcast);  
					
				}
				String sfollows = jsonObject.getString("concern");
				Log.i("sfollows",sfollows);
				JSONArray ansArray = new JSONArray(sfollows);
				List<Follow> follows = new ArrayList<Follow>();
				for(int i=0; i< ansArray.length(); i++){
				    JSONObject ansObject = ansArray.getJSONObject(i);			    
				    String ssex = ansObject.getString("sex");
				    int sex = Integer.parseInt(ssex);				    
				    String slocation = ansObject.getString("location");
				    Log.i("sques", slocation);
				    String snickName = ansObject.getString("nickName");
				    String sperson_signa = ansObject.getString("person_signa");
				    String sname = ansObject.getString("userName");		
				    int id = ansObject.getInt("followID");
			
				    Follow follow= new Follow(id, sex, sname, snickName, slocation, sperson_signa);
				    Log.i("follow", follow.toString());
				    follows.add(follow);
				}
				Log.i("followlist", follows.toString());
				return follows;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Follow> follows = new ArrayList<Follow>();
		return follows;
	}

}
