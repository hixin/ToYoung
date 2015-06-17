package com.tuyoung.wu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.toyoung.R;
import com.toyoung.wu.bean.Favoriate;
import com.toyoung.wu.bean.Favoriate;
import com.toyoung.liu.MainMeActivity.*;
import com.toyuong.util.MyApplication;
import com.tuyuong.ge.MainHotItemActivity;
import com.tuyuong.ge.hotInfors;
import com.tuyuong.ge.hotlistCreater;
public class MyFavoriate extends BaseListFragment<Favoriate> {
	private String[] tags = { "交通住宿", "考试心得", "学校申请", "吃喝指南", "留学情感地", "文化观察",
			"寻找小伙伴", "就业打工", "其他问题" };
	protected static final String TAG = MyFavoriate.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "queslist_";
	private TextView quesnum;
	private NumChangeReceiver numChangeReceiver;
	private IntentFilter intentFilter;
	public static List<hotInfors> allhotinfors;
	public static final String HOT_ITEM = "com.tuyoung.myquestion.item";
	public static final String INTENTEXTRA = "hotitem_position";
	@Override
	protected ListBaseAdapter<Favoriate> getListAdapter() {
		return new AnswerListAdapter();
	}

	@Override
	protected String getCacheKeyPrefix() {
		return CACHE_KEY_PREFIX + mCatalog;
	}

	@Override
	protected List<Favoriate> parseList(String is) throws Exception {
		Log.i("child","execute");
		List<Favoriate> list = null;
		try {
		    list = jsonToQues(is);
		} catch (NullPointerException e) {
		}
		return list;
	}
	public void initData() {
		allhotinfors = new ArrayList<hotInfors>();
		// 获取十条记录
		List<hotInfors> hotinfors = new hotlistCreater().getTenhotinfors((MyApplication)getActivity().getApplication());
		allhotinfors.addAll(hotinfors);
	}


	@Override
	protected List<Favoriate> readList(Serializable seri) {
		return (List<Favoriate>) (seri);
	}

	@Override
	protected void sendRequestData() {
	
		HttpReqData.getFavoriateList("favoriate", "15", mHandler);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Favoriate ques = mAdapter.getItem(position);
		if (ques != null) {
			initData();
			Intent intent = new Intent(getActivity(), MainHotItemActivity.class);
			intent.setAction(HOT_ITEM);
			intent.putExtra(INTENTEXTRA, position);
			startActivity(intent);
//			UIHelper.showNewsRedirect(view.getContext(), ques);

			// 放入已读列表
		/*	saveToReadedList(view, FavoriateList.PREF_READED_NEWS_LIST, ques.getId()
					+ "");*/
		}
	}

	@Override
	protected void executeOnLoadDataSuccess(List<Favoriate> data) {
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

	class AnswerListAdapter extends ListBaseAdapter<Favoriate> {
		@Override
		protected View getRealView(int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater(parent.getContext()).inflate(
						R.layout.list_cell_favoriate, parent, false);

				viewHolder.mTextView1 = (TextView) convertView
						.findViewById(R.id.tv_title);
				viewHolder.mTextView2 = (TextView) convertView
						.findViewById(R.id.tv_description);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			Favoriate qs = mDatas.get(position);
			viewHolder.mTextView1.setText(tags[qs.getLable() % 8]);
			viewHolder.mTextView2.setText(qs.getQuestion_content());
			return convertView;
		}

	}

	public class ViewHolder {
		TextView mTextView1;
		TextView mTextView2;

	}
	
	private List<Favoriate> jsonToQues(String jsons){
		
		try {			
			JSONObject jsonObject = new JSONObject(jsons);
			String status = jsonObject.getString("Status");
			String type = jsonObject.getString("type");
			if(status.equals("success")  && type.equals("favoriate")){
				int num = jsonObject.getInt("num");
				if(num != 0) {
					MyApplication.context().favonum =num;
					Intent broadcast = new Intent("myInfoNumChange");  
		            getActivity().sendBroadcast(broadcast);  
					
				}
				String sFavoriates = jsonObject.getString("questions");
				Log.i("sFavoriates",sFavoriates);
				JSONArray quesArray = new JSONArray(sFavoriates);
				List<Favoriate> Favoriates = new ArrayList<Favoriate>();
				for(int i=0; i< quesArray.length(); i++){
				    JSONObject quesObject = quesArray.getJSONObject(i);
				    String sques = quesObject.getString("question_content");
				    String stitle = quesObject.getString("Title");
				    Log.i("sques", sques);
				    String stag = quesObject.getString("Tag");
				    int id = quesObject.getInt("favoriateID");
				    int quesid = quesObject.getInt("problemID");
				    int lable = Integer.parseInt(stag);
				    Favoriate ques= new Favoriate(id, stitle, lable, sques, quesid);
				    Log.i("ques", ques.toString());
				    Favoriates.add(ques);
				}
				Log.i("queslist", Favoriates.toString());
				return Favoriates;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Favoriate> Favoriates = new ArrayList<Favoriate>();
		return Favoriates;
	}



}
