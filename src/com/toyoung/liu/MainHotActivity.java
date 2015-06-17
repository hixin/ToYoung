package com.toyoung.liu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.toyoung.MainActivity;
import com.example.toyoung.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.toyuong.util.MyApplication;
import com.toyuong.util.ShowToast;
import com.tuyuong.ge.HotFirstItemActivity;
import com.tuyuong.ge.MainHotItemActivity;
import com.tuyuong.ge.hotInfors;
import com.tuyuong.ge.hotlistAdapter;
import com.tuyuong.ge.hotlistCreater;
import com.tuyuong.ge.hotlistCreater.netnearDataHandler;

/**
 * 热门标签页面
 * */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainHotActivity extends Fragment implements OnScrollListener,
		OnClickListener, netnearDataHandler {

	public static DisplayImageOptions imageLodeoptions;// 展示图片属性
	public static List<hotInfors> allhotinfors;
	public static PullToRefreshScrollView refreshableView;

	private ListView lv_hotlist;

	private hotlistAdapter mAdapter;

	public static final String HOT_ITEM = "com.tuyoung.hot.item";
	public static final String INTENTEXTRA = "hotitem_position";

	private MyApplication app;

	private int index = 0;
	
	TextView child;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (MyApplication) getActivity().getApplication();
		allhotinfors = new ArrayList<hotInfors>();

		hotlistCreater.mListeners = this;

		getDataThread.start();

		initImageLoader();// 初始化异步加载图片
	}

	// �ȵ���ҳ��
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.activity_main_hot, container,
				false);
		initView(root);

		return root;
	}

	@SuppressWarnings("deprecation")
	private void initView(View root) {

		refreshableView = (PullToRefreshScrollView) root
				.findViewById(R.id.refreshView);

		root.findViewById(R.id.ll_firstitem).setOnClickListener(this);

		lv_hotlist = (ListView) root.findViewById(R.id.lv_hotlist);

		mAdapter = new hotlistAdapter(getActivity(), allhotinfors, lv_hotlist);
		lv_hotlist.setAdapter(mAdapter);
		lv_hotlist.setFocusable(false);

		refreshableView.setMode(Mode.PULL_FROM_START);
		refreshableView.setScrollingWhileRefreshingEnabled(true);

		ILoadingLayout startLabels = refreshableView.getLoadingLayoutProxy();
		startLabels.setPullLabel("你可劲拉，拉...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("好嘞，正在刷新...");// 刷新时
		startLabels.setReleaseLabel("你敢放，我就敢刷新...");// 下来达到一定距离时，显示的提示

		refreshableView
				.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

					@Override
					public void onPullEvent(
							PullToRefreshBase<ScrollView> refreshView,
							State state, Mode direction) {
						// TODO Auto-generated method stub
						if (state == State.PULL_TO_REFRESH
								&& ((MyApplication) getActivity()
										.getApplication()).getDiffTime() != null)
							refreshableView
									.getLoadingLayoutProxy()
									.setLastUpdatedLabel(
											"距离上次刷新:"
													+ ((MyApplication) getActivity()
															.getApplication())
															.getDiffTime());
					}
				});

		refreshableView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						// TODO Auto-generated method stub
						// 设置上一次刷新的提示标签
						((MyApplication) getActivity().getApplication())
								.setLastRefreshTime(new Date().getTime());
						new GetDataTask().execute();
					}
				});

		lv_hotlist.setOnScrollListener(this);
		
	

		// item点击事件，进入具体的热门消息详情页面
		lv_hotlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MainHotItemActivity.class);
				intent.setAction(HOT_ITEM);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("hotInfors", allhotinfors.get(position));
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});

		new Thread(new Runnable() {
			public void run() {
				// sv_container.fullScroll(ScrollView.FOCUS_UP);
				refreshableView.scrollTo(0, 0);
			}
		}).start();

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {

			try {
				Thread.sleep(2000);// 睡眠2秒，延迟加载数据
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new hotlistCreater().startgetTenhotinfors(app,
					String.valueOf(index));

			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			refreshableView.onRefreshComplete();
			MainActivity.setBottomVisiable(true);
	//		refreshableView.removeView(child);
			super.onPostExecute(result);
		}
	}

	private void initImageLoader() {
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(getActivity()));
		imageLodeoptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.userlogo)
				.showImageOnFail(R.drawable.userlogo)
				/* .resetViewBeforeLoading(true) */
				.cacheOnDisk(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(100)).build();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		switch (scrollState) {
		// 当不滚动时
		case OnScrollListener.SCROLL_STATE_IDLE:
			// 判断滚动到底部
			if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
				child =new TextView(getActivity()); 
				child.setText("正在加载...");
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
          //    child.setLayoutParams(layoutParams);
			//	refreshableView.addView(child, scrollState, layoutParams);
				MainActivity.setBottomVisiable(false);
			//	index = index+10;
				new GetDataTask().execute();
			}
			break;
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.ll_firstitem:
			Intent intent = new Intent(getActivity(),
					HotFirstItemActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取十条记录
	 * */
	Thread getDataThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			new hotlistCreater().startgetTenhotinfors(app,
					String.valueOf(index));
		}
	});

	@SuppressWarnings("unchecked")
	@Override
	public void ongetDataChange(Message msg) {
		// TODO Auto-generated method stub
		Log.v("inteface", "inteface");
		if (msg.what == 1) {
			final ArrayList<hotInfors> temp = (ArrayList<hotInfors>) msg.obj;

			if (temp.size() == 0) {
				msg.arg1 = 0;
			} else {
				if (index == 0) {// 执行刷新
			//		allhotinfors.clear();
					allhotinfors.addAll(temp);
				} else
					allhotinfors.addAll(temp);
				msg.arg1 = 1;
			}
		} else {
			msg.arg1 = 2;

		}
		mhandler.sendMessage(msg);
	}

	@SuppressLint("HandlerLeak")
	Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1)
				mAdapter.notifyDataSetChanged();
			else if (msg.arg1 == 0)
				new ShowToast().shortshow(getActivity(), "该专题目前没有问题");
			else
				new ShowToast().shortshow(getActivity(), "网络异常");// 网络连接异常
		}
	};

}
