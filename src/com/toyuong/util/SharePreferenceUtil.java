package com.toyuong.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtil{

	private static String saveUser="saveUser";//存储基本信息的文件
	private SharedPreferences sp;
	private Editor editor;
	//用于持久化存储
	public SharePreferenceUtil(Context context){
		sp=context.getSharedPreferences(saveUser, Context.MODE_PRIVATE);
		editor=sp.edit();
	}
	//存储、获取用户名
	public void setUserName(String userName){
		editor.putString("userName",userName);
		editor.commit();
	}
	public String getUserName(){
		return sp.getString("userName", "");
		
	}
	//存储、获取密码
    public void setPassword(String password){
			editor.putString("password",password);
			editor.commit();
		}
    public String getPassword(){
			return sp.getString("password", "");
			
		}
}
