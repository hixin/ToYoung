package com.toyuong.util;

import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.utils.KJLoger;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.toyoung.liu.MainMeActivity.NumChangeReceiver;
import com.tuyoung.vo.UserVo;
import com.tuyoung.wu.HttpReqData;
import com.tuyoung.wu.StringUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class MyApplication extends Application {

	
	// 用于存储一些全局数据
	private UserVo userVo;// 用户基本信息


    private SharedPreferences mysPreferences;// 临时存储

	
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	
	//存储我的界面的4个栏目的个数
	public int quesnum;
	public int ansnum;
	public int follnum;
	public int favonum;

	public static final int PAGE_SIZE = 15;// 默认分页大小
	static Context _context;

	@Override
	public void onCreate() {
		init();
		super.onCreate();
		mysPreferences=getSharedPreferences("userdata", Activity.MODE_PRIVATE);
		_context = getApplicationContext();
	}

	
	public static synchronized MyApplication context() {
		return (MyApplication) _context;
	}
	
	
	
	 private void init() {
	        // 初始化网络请求
	        AsyncHttpClient client = new AsyncHttpClient();

	        HttpReqData.setHttpClient(client);
	        
	        
	      
	        // Bitmap缓存地址
	        BitmapConfig.CACHEPATH = "OSChina/imagecache";
	}
	 
	 
	private static String PREF_NAME = "creativelocker.pref";
	private static String LAST_REFRESH_TIME = "last_refresh_time.pref";
	
	/**
	 * 添加本次刷新时间
	 * */
	public void setLastRefreshTime(long time) {
		mysPreferences.edit().putLong("lastRefreshTime", time).commit();
	}

	public long getLastRefreshTime() {
		if (mysPreferences.contains("lastRefreshTime")) {
			return mysPreferences.getLong("lastRefreshTime", 0);
		}
		return 0;
	}
	/**
	 * 获取刷新时间间隔
	 * */
	public String getDiffTime() {
		if (getLastRefreshTime() != 0) {
			return TimeHelper.getminusTimeFormat(getLastRefreshTime());
		}
		return null;
	}
	 /**
     * 放入已读文章列表中
     * 
     * @param id
     */
    public static void putReadedPostList(String prefFileName, String key,
	    String value) {
	SharedPreferences preferences = getPreferences(prefFileName);
	int size = preferences.getAll().size();
	Editor editor = preferences.edit();
	if (size >= 100) {
	    editor.clear();
	}
	editor.putString(key, value);
	apply(editor);
    }
    
    /**
     * 读取是否是已读的文章列表
     * 
     * @param id
     * @return
     */
    public static boolean isOnReadedPostList(String prefFileName, String key) {
	return getPreferences(prefFileName).contains(key);
    }
    
    /***
     * 记录列表上次刷新时间
     * @author 火蚁
     * 2015-2-9 下午2:21:37
     *
     * @return void
     * @param key
     * @param value
     */
    public static void putToLastRefreshTime(String key, String value) {
	SharedPreferences preferences = getPreferences(LAST_REFRESH_TIME);
	Editor editor = preferences.edit();
	editor.putString(key, value);
	apply(editor);
    }
    
    /***
     * 获取列表的上次刷新时间
     * @author 火蚁
     * 2015-2-9 下午2:22:04
     *
     * @return String
     * @param key
     * @return
     */
    public static String getLastRefreshTime(String key) {
	return getPreferences(LAST_REFRESH_TIME).getString(key, StringUtils.getCurTimeStr());
    }

    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
	SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
		Context.MODE_MULTI_PROCESS);
	return pre;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences(String prefName) {
	return context().getSharedPreferences(prefName,
		Context.MODE_MULTI_PROCESS);
    }
    
    private static boolean sIsAtLeastGB;

    static {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
	    sIsAtLeastGB = true;
	}
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
	if (sIsAtLeastGB) {
	    editor.apply();
	} else {
	    editor.commit();
	}
    }
	
}
