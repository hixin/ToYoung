package com.toyuong.util;

import android.annotation.SuppressLint;
import java.util.HashMap;

@SuppressLint("UseSparseArrays")
public class QuestionTypeSelector {
	
	public static HashMap<Integer,String> typemap=new HashMap<Integer, String>();//类别
	
	public static void initMap(){
		typemap.put(0, "考试心得");
		typemap.put(1, "学校申请");
		typemap.put(2, "吃喝指南");
		typemap.put(3, "购物秘籍");
		typemap.put(4, "留学情感地");
		typemap.put(5, "就业打工");
		typemap.put(6, "交通住宿");
		typemap.put(7, "寻找小伙伴");
		typemap.put(8, "文化观察");
		typemap.put(9, "其他问题");
	}
	
	private static String[] typetags={"考试心得","学校申请",
            "吃喝指南","购物秘籍","留学情感地","就业打工","交通住宿",
            "寻找小伙伴","文化观察","其他问题"};
	
	public static String getType(int position){
		
		return typetags[position];	
	}

}
