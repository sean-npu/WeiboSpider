package cn.edu.npu.sean.spider;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;





import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.utils.Constants;
import cn.edu.npu.sean.spider.utils.DBConn;
import cn.edu.npu.sean.spider.utils.Utils;
import cn.edu.npu.sean.spider.worker.CommentTaskWorker;
import cn.edu.npu.sean.spider.worker.UserTaskWorker;

public class SpiderStarter {
	private static final Logger Log = Logger.getLogger(SpiderStarter.class.getName());

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initParams();
		//fetchUser();
		fetchComment();
	}

	private static void initParams() {
		Log.info("---------------initParams---------------");
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("conf/spider.properties"));
			Properties properties = new Properties();
			properties.load(in);
			
			
			DBConn.CONN_URL = properties.getProperty("DB.connUrl");
			DBConn.USERNAME = properties.getProperty("DB.username");
			DBConn.PASSWORD = properties.getProperty("DB.password");
			
			Constants.ROOT_DIR = properties.getProperty("spider.rootDir");
			Constants.ACCOUNT_COOKIE_FILE = Constants.ROOT_DIR+"account_cookie.txt";
			
			Constants.WORKER_NUM = Integer.parseInt(properties.getProperty("spider.worker_num"));
			Constants.USER_BASE_URL = properties.getProperty("spider.user_base_url");
			Constants.MOVIE_COMMENT_URL = properties.getProperty("spider.movie_comment_url");
			in.close();
		} 
		catch (FileNotFoundException e) {
			Log.error(e);
		} 
		catch (IOException e) {
			Log.error(e);
		}
	}
	
	private static void fetchUser(){
		Log.info("---------------FetchUser---------------");
		initAccount();
		Utils.initUserTaskQueue();
		for(int i = 0; i < Constants.WORKER_NUM; i++){
			new Thread(new UserTaskWorker()).start();
		}
	}
	private static void fetchComment(){
		Log.info("---------------FetchComment------------");
		initAccount();
		Utils.initCommentTaskCompleteSet();
		Utils.initCommentTaskQueue();
		for(int i = 0; i < Constants.WORKER_NUM; i++){
			new Thread(new CommentTaskWorker()).start();
		}
	}
	private static void initAccount(){
		Utils.readAccountFromFile();
	}
}
