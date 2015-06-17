package com.toyuong.util;

import java.util.Date;

public class TimeHelper {
	
	 public static String getminusTimeFormat(long date) {
	        Long minustime = new Date().getTime() - date;
	        int seconds = minustime.intValue() / 1000;//转化为秒
	        if (seconds < 60) {
	            return seconds + "S前";
	        } else {
	            int minutes = seconds / 60;//精确到分钟
	            //  seconds=seconds%60;
	            if (minutes < 60) {
	                return minutes + "分钟前";
	            } else {
	                int hours = minutes / 60;//精确到小时
	                //    minustime=minustime%60;
	                if (hours < 24) {
	                    return hours + "小时前";
	                } else {
	                    int days = hours / 24;//精确到天
	                    //   hours=hours%24;
	                    if (days < 30) {
	                        return days + "天前";
	                    } else {
	                        int months = days / 30;//精确到月份
	                        //   days=days%30;
	                        return months + "月前";
	                    }
	                }
	            }
	        }
	    }
}
