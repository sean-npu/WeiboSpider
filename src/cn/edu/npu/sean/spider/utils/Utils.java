package cn.edu.npu.sean.spider.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.bean.Account;
import cn.edu.npu.sean.spider.bean.UserUrl;
import cn.edu.npu.sean.spider.queue.AccountQueue;
import cn.edu.npu.sean.spider.queue.UserUrlQueue;

public class Utils {
	private static final Logger Log = Logger.getLogger(Utils.class.getName());
	public static Connection conn = DBConn.getConnection();
	public static void readAccountFromFile(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Constants.ACCOUNT_COOKIE_FILE));
			String accountLine = null;
			while(((accountLine = reader.readLine()) != null)){
				AccountQueue.addElement(new Account(accountLine));
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
			Log.error(e);
		}
		catch (IOException e) {
			Log.error(e);
		}
	}
	public static void initUserUrlQueue() {
		String sql = "SELECT DISTINCT weibo_comment.uid FROM weibo_comment LEFT JOIN weibo_user ON weibo_comment.uid = weibo_user.id WHERE weibo_user.id IS NULL";
		Statement st = null;
		ResultSet rs = null;
		long uid = 0l;
		try {			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				uid = rs.getLong("uid");
				if(uid != 0){
					UserUrl uu = new UserUrl();
					uu.setInfoUrl(Constants.USER_BASE_URL+uid+"/info");
					uu.setSnsInfoUrl(Constants.USER_BASE_URL+"u/"+uid);
					uu.setUid(uid);
					UserUrlQueue.addElement(uu);
				}
			}
			rs.close();
			st.close();
			
		} 
		catch (SQLException e) {
			Log.error(e);
			// 鎻愪氦澶辫触 roll back锛屽苟灏嗘斁鍏ラ槦鍒楃殑URL鎷垮嚭鏉�
		}
		finally{
			try {
				conn.close();
			} 
			catch (SQLException e) {
				Log.error(e);
			}
		}
		
	}
	public static String getContent(String url,String cookie){
		String content = null;
		CloseableHttpClient  httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
			    .setConnectionRequestTimeout(10 * 1000)
			    .setConnectTimeout(10 * 1000)
			    .setSocketTimeout(10 * 1000)
			    .build();
		try{
			HttpGet getHttp = new HttpGet(url);
			getHttp.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
			getHttp.setHeader("Cookie", cookie);
			getHttp.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(getHttp);
			try{
				HttpEntity entity = response.getEntity();
				if(entity != null){
					content = EntityUtils.toString(entity, "UTF-8");
				}
			}finally{
				response.close();
			}
		}catch(Exception e){
			
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(content);
		return content;
	}
}
