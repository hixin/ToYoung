package com.tuyoung.wu;
import java.io.File;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;
import org.kymjs.kjframe.utils.StringUtils;

import com.toyuong.util.MyApplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TDevice {

    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;

    private static int _pageSize = -1;
    public static float displayDensity = 0.0F;

    static {
	GTE_ICS = Build.VERSION.SDK_INT >= 14;
	GTE_HC = Build.VERSION.SDK_INT >= 11;
	PRE_HC = Build.VERSION.SDK_INT >= 11 ? false : true;
    }

    public TDevice() {
    }

    public static float dpToPixel(float dp) {
	return dp * (getDisplayMetrics().densityDpi / 160F);
    }
    public static int getVersionCode() {
    	int versionCode = 0;
    	try {
    	    versionCode = MyApplication
    		    .context()
    		    .getPackageManager()
    		    .getPackageInfo(MyApplication.context().getPackageName(),
    			    0).versionCode;
    	} catch (PackageManager.NameNotFoundException ex) {
    	    versionCode = 0;
    	}
    	return versionCode;
        }
    
    public static int getDefaultLoadFactor() {
	if (_loadFactor == null) {
	    Integer integer = Integer.valueOf(0xf & MyApplication.context()
		    .getResources().getConfiguration().screenLayout);
	    _loadFactor = integer;
	    _loadFactor = Integer.valueOf(Math.max(integer.intValue(), 1));
	}
	return _loadFactor.intValue();
    }

    public static float getDensity() {
	if (displayDensity == 0.0)
	    displayDensity = getDisplayMetrics().density;
	return displayDensity;
    }

    public static DisplayMetrics getDisplayMetrics() {
	DisplayMetrics displaymetrics = new DisplayMetrics();
	((WindowManager) MyApplication.context().getSystemService(
		Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
		displaymetrics);
	return displaymetrics;
    }

    public static float getScreenHeight() {
	return getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
	return getDisplayMetrics().widthPixels;
    }

    public static int[] getRealScreenSize(Activity activity) {
	int[] size = new int[2];
	int screenWidth = 0, screenHeight = 0;
	WindowManager w = activity.getWindowManager();
	Display d = w.getDefaultDisplay();
	DisplayMetrics metrics = new DisplayMetrics();
	d.getMetrics(metrics);
	// since SDK_INT = 1;
	screenWidth = metrics.widthPixels;
	screenHeight = metrics.heightPixels;
	// includes window decorations (statusbar bar/menu bar)
	if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
	    try {
		screenWidth = (Integer) Display.class.getMethod("getRawWidth")
			.invoke(d);
		screenHeight = (Integer) Display.class
			.getMethod("getRawHeight").invoke(d);
	    } catch (Exception ignored) {
	    }
	// includes window decorations (statusbar bar/menu bar)
	if (Build.VERSION.SDK_INT >= 17)
	    try {
		Point realSize = new Point();
		Display.class.getMethod("getRealSize", Point.class).invoke(d,
			realSize);
		screenWidth = realSize.x;
		screenHeight = realSize.y;
	    } catch (Exception ignored) {
	    }
	size[0] = screenWidth;
	size[1] = screenHeight;
	return size;
    }

    public static int getStatusBarHeight() {
	Class<?> c = null;
	Object obj = null;
	Field field = null;
	int x = 0;
	try {
	    c = Class.forName("com.android.internal.R$dimen");
	    obj = c.newInstance();
	    field = c.getField("status_bar_height");
	    x = Integer.parseInt(field.get(obj).toString());
	    return MyApplication.context().getResources()
		    .getDimensionPixelSize(x);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return 0;
    }

 
    public static boolean hasBigScreen() {
	boolean flag = true;
	if (_hasBigScreen == null) {
	    boolean flag1;
	    if ((0xf & MyApplication.context().getResources()
		    .getConfiguration().screenLayout) >= 3)
		flag1 = flag;
	    else
		flag1 = false;
	    Boolean boolean1 = Boolean.valueOf(flag1);
	    _hasBigScreen = boolean1;
	    if (!boolean1.booleanValue()) {
		if (getDensity() <= 1.5F)
		    flag = false;
		_hasBigScreen = Boolean.valueOf(flag);
	    }
	}
	return _hasBigScreen.booleanValue();
    }

    public static final boolean hasCamera() {
	if (_hasCamera == null) {
	    PackageManager pckMgr = MyApplication.context()
		    .getPackageManager();
	    boolean flag = pckMgr
		    .hasSystemFeature("android.hardware.camera.front");
	    boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
	    boolean flag2;
	    if (flag || flag1)
		flag2 = true;
	    else
		flag2 = false;
	    _hasCamera = Boolean.valueOf(flag2);
	}
	return _hasCamera.booleanValue();
    }

    public static boolean hasHardwareMenuKey(Context context) {
	boolean flag = false;
	if (PRE_HC)
	    flag = true;
	else if (GTE_ICS) {
	    flag = ViewConfiguration.get(context).hasPermanentMenuKey();
	} else
	    flag = false;
	return flag;
    }

    public static boolean hasInternet() {
	boolean flag;
	if (((ConnectivityManager) MyApplication.context().getSystemService(
		"connectivity")).getActiveNetworkInfo() != null)
	    flag = true;
	else
	    flag = false;
	return flag;
    }

    
    public static void hideAnimatedView(View view) {
	if (PRE_HC && view != null)
	    view.setPadding(view.getWidth(), 0, 0, 0);
    }

    public static void hideSoftKeyboard(View view) {
	if (view == null)
	    return;
	((InputMethodManager) MyApplication.context().getSystemService(
		Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
		view.getWindowToken(), 0);
    }

    public static boolean isLandscape() {
	boolean flag;
	if (MyApplication.context().getResources().getConfiguration().orientation == 2)
	    flag = true;
	else
	    flag = false;
	return flag;
    }

    public static boolean isPortrait() {
	boolean flag = true;
	if (MyApplication.context().getResources().getConfiguration().orientation != 1)
	    flag = false;
	return flag;
    }

    public static boolean isTablet() {
	if (_isTablet == null) {
	    boolean flag;
	    if ((0xf & MyApplication.context().getResources()
		    .getConfiguration().screenLayout) >= 3)
		flag = true;
	    else
		flag = false;
	    _isTablet = Boolean.valueOf(flag);
	}
	return _isTablet.booleanValue();
    }

    public static float pixelsToDp(float f) {
	return f / (getDisplayMetrics().densityDpi / 160F);
    }

    public static void showAnimatedView(View view) {
	if (PRE_HC && view != null)
	    view.setPadding(0, 0, 0, 0);
    }

    public static void showSoftKeyboard(Dialog dialog) {
	dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(View view) {
	((InputMethodManager) MyApplication.context().getSystemService(
		Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
		InputMethodManager.SHOW_FORCED);
    }

    public static void toogleSoftKeyboard(View view) {
	((InputMethodManager) MyApplication.context().getSystemService(
		Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
		InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSdcardReady() {
	return Environment.MEDIA_MOUNTED.equals(Environment
		.getExternalStorageState());
    }

    public static String getCurCountryLan() {
	return MyApplication.context().getResources().getConfiguration().locale
		.getLanguage()
		+ "-"
		+ MyApplication.context().getResources().getConfiguration().locale
			.getCountry();
    }

    public static boolean isZhCN() {
	String lang = MyApplication.context().getResources()
		.getConfiguration().locale.getCountry();
	if (lang.equalsIgnoreCase("CN")) {
	    return true;
	}
	return false;
    }



    public static String getVersionName() {
	String name = "";
	try {
	    name = MyApplication
		    .context()
		    .getPackageManager()
		    .getPackageInfo(MyApplication.context().getPackageName(),
			    0).versionName;
	} catch (PackageManager.NameNotFoundException ex) {
	    name = "";
	}
	return name;
    }

    public static boolean isScreenOn() {
	PowerManager pm = (PowerManager) MyApplication.context()
		.getSystemService(Context.POWER_SERVICE);
	return pm.isScreenOn();
    }

  

    public static boolean isWifiOpen() {
	boolean isWifiConnect = false;
	ConnectivityManager cm = (ConnectivityManager) MyApplication
		.context().getSystemService(Context.CONNECTIVITY_SERVICE);
	// check the networkInfos numbers
	NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
	for (int i = 0; i < networkInfos.length; i++) {
	    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
		if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
		    isWifiConnect = false;
		}
		if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
		    isWifiConnect = true;
		}
	    }
	}
	return isWifiConnect;
    }

  

    /**
     * 调用系统安装了的应用分享
     * 
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context,
	    final String title, final String url) {
	Intent intent = new Intent(Intent.ACTION_SEND);
	intent.setType("text/plain");
	intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
	intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
	context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 获取当前网络类型
     * 
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
	int netType = 0;
	ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.context()
		.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	if (networkInfo == null) {
	    return netType;
	}
	int nType = networkInfo.getType();
	if (nType == ConnectivityManager.TYPE_MOBILE) {
	    String extraInfo = networkInfo.getExtraInfo();
	    if (!StringUtils.isEmpty(extraInfo)) {
		if (extraInfo.toLowerCase().equals("cmnet")) {
		    netType = NETTYPE_CMNET;
		} else {
		    netType = NETTYPE_CMWAP;
		}
	    }
	} else if (nType == ConnectivityManager.TYPE_WIFI) {
	    netType = NETTYPE_WIFI;
	}
	return netType;
    }
}
