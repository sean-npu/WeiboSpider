package cn.edu.npu.sean.spider.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.fetcher.UserFetcher;
import cn.edu.npu.sean.spider.utils.DBConn;

public class TaskModel {
	public static Connection conn = DBConn.getConnection();
	private static final Logger Log = Logger.getLogger(UserFetcher.class.getName());
	public static boolean update(String config,String type){
		PreparedStatement ps;
		try{
			ps = conn.prepareStatement("UPDATE task SET config = ? WHERE type=? ");
			ps.setString(1, config);
			ps.setString(2, type);
			ps.execute();
			ps.close();
		} 
		catch (SQLException e) {
			Log.error(e);
			return false;
		}
		return true;
	}

}
