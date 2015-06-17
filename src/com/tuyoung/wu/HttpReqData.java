package com.tuyoung.wu;


import java.util.Locale;
import org.apache.http.client.params.ClientPNames;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpReqData {
	public static AsyncHttpClient client;
	private static String API_URL = "http://112.124.64.64:8080/Toyoung/MainMeActivity";
//	http://112.124.64.64:8080/Toyoung/MainMeActivity?type=question&userID=3
//	http://112.124.64.64:8080/Toyoung/MainMeActivity?type=follow&userID=15
	 
	 public static AsyncHttpClient getHttpClient() {
	        return client;
	    }

	 public static void setHttpClient(AsyncHttpClient c) {
	        client = c;
	  }
	 
	/**
     * 获取item列表
     * 
     *
     * @param handler
     */
		 
    public static void getQuestionList(String type, String userID,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("userID", userID);     
        client.post(API_URL, params, handler);
    }
    
    public static void getAnswerList(String type, String userID,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("userID", userID);     
        client.post(API_URL, params, handler);
    }
    public static void getFollowList(String type, String userID,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("userID", userID);     
        client.post(API_URL, params, handler);
    }
    
    public static void getFavoriateList(String type, String userID,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("userID", userID);     
        client.post(API_URL, params, handler);
    }
}
