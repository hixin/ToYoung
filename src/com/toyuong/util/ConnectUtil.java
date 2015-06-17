package com.toyuong.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 发起HTTP请求的工具类
 
 */
public class ConnectUtil {
	
	
	public static final String HOST_ERROR = "HOST_ERROR";
	public static String HTTP_RE_ERROE_CODE = "";
	
	public static final String UTF_8 = "UTF-8";
	public static final String POST = "POST";
	public static final String GET = "GET";
	
	private final static int READ_TIMEOUT = 6000;
	private final static int CONNECT_TIMEOUT = 6000;
	private final static int TEST_TIMEOUT = 6000;
	
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";

	
	public final static String servelUrl="http://112.124.64.64:8080/Toyoung/";
	//登录接口
	public final static String loginUrl=servelUrl+"Login";
	//注册接口
	public final static String registerUrl=servelUrl+"Register";
	//用户名是否存在
	public final static String isUserNameExistUrl=servelUrl+"IsUserNameExist";
	//添加问题
	public final static String addQuestionUrl=servelUrl+"AddQuestion";
	//点击专题
	public final static String getSubjectListUrl=servelUrl+"GetItemInSubject";
	//搜索
	public final static String searchUrl=servelUrl+"Search";
	
	//热门专题的数据获取
	public final static String gethotQuestions=servelUrl+"hotQuestion";
	
	//问题所有答案获取
	public final static String getCeratinQuestionAnswers = servelUrl+"GetProblem";
	
	//详细回答
	public final static String getDetailedAnswers = servelUrl+"GetAnswer";
	
	//回复问题，添加回答
	public final static String addCertainAnswer = servelUrl+"AddAnswer";
	
	//同问
	public final static String addSameQusetion = servelUrl+"sameQuestion";
	
	//支持
	public final static String addSupportAnswer = servelUrl+"supportAnswer";
	
	//收藏
	public final static String collectQuestion = servelUrl+"collectQuestion";
	
	
	//通过设置参数请求，统一使用post方式,若String为null，则说明网络获取数据失败
	public static String httpRequest(String url, PostParameter[] postParams,String httpMethod) {

		
		
        InputStream is = null;
        String jsonSource ="";
        try {
            HttpURLConnection con = null;
            OutputStream osw = null;
            try {
                con = (HttpURLConnection) new URL(url).openConnection();
                con.setDoInput(true);
                if (null != postParams || POST.equals(httpMethod))
                {
                	
                   con.setRequestMethod(POST);
                   con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                   con.setRequestProperty("Charset", "UTF-8");
                   con.setDoOutput(true);
                   con.setDoInput(true);
                   
                   con.setReadTimeout(READ_TIMEOUT);
                   con.setConnectTimeout(CONNECT_TIMEOUT);
                   String postParam = "";
                   if (postParams != null) {
                	   //将参数进行编码防止中文乱码问题
                        postParam = encodeParameters(postParams);
                   }
                   byte[] bytes = postParam.getBytes("UTF-8");
           
                   con.setRequestProperty("Content-Length",Integer.toString(bytes.length));
                   
                   Log.i("liuhaoxian", "url----"+con.getURL()+"?"+postParam);
                   osw = con.getOutputStream();
                   osw.write(bytes);
                   osw.flush();
                   osw.close();
                }
                
                	
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
					is = con.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
				    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				    String temp = "";
				   
				    //一行一行的读取
				    while ((temp = bufferedReader.readLine()) != null)
				    {
				                jsonSource += temp;
				    }
				    //关闭
				    bufferedReader.close();
				    is.close();
				    Log.i("liuhaoxian","jsonString="+jsonSource);
				    return jsonSource;

				}else {
					HTTP_RE_ERROE_CODE = String.valueOf(con.getResponseCode());
				}
            }
            catch (Exception e){
                e.printStackTrace();
                jsonSource=null;
            }finally {
            	return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonSource=null;
        }finally {

        	return jsonSource;
        }
        
    }
	
	public static String encodeParameters(PostParameter[] postParams) {
	        StringBuffer buf = new StringBuffer();
	        for (int j = 0; j < postParams.length; j++) {
	            if (j != 0) {
	                buf.append("&");
	            }
	            try {
	            	if(null!=postParams[j])
	            	{
	            		if(null!=postParams[j].getName())
	                	{
	                		buf.append(URLEncoder.encode(postParams[j].getName(), "utf-8")).append("=");
	                	}
	                	if(null!=postParams[j].getValue())
	                	{
	                		buf.append(URLEncoder.encode(postParams[j].getValue(), "utf-8"));
	                	}
	            	}
	            } catch (java.io.UnsupportedEncodingException neverHappen) {
	            }
	          }
	        return buf.toString();
	    }
		
	public static String getEncode(String codeType, String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance(codeType);// 获取一个实例，并传入加密方式
			digest.reset();// 清空一下
			digest.update(content.getBytes());// 写入内容,可以指定编码方式content.getBytes("utf-8");
			StringBuilder builder = new StringBuilder();
			for (byte b : digest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));
				builder.append(Integer.toHexString(b & 0xf));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	  	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		System.out.println("connect: " + connect);
		if (connect == null) {
			return false;
		} else// get all network info
		{
			NetworkInfo[] info = connect.getAllNetworkInfo();
//			System.out.println("info: " + info);
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
