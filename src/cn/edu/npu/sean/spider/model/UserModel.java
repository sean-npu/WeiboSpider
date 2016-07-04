package cn.edu.npu.sean.spider.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.bean.User;
import cn.edu.npu.sean.spider.fetcher.UserFetcher;
import cn.edu.npu.sean.spider.utils.DBConn;

public class UserModel {
	public static Connection conn = DBConn.getConnection();
	private static final Logger Log = Logger.getLogger(UserFetcher.class.getName());
	public static boolean insert(User user){
		PreparedStatement ps;
		try{
			ps = conn.prepareStatement(
					"INSERT INTO "
					+ "weibo_user "
					+ "(id, nickname, sex, reg_time, edu, birth, work, region, hobby, tag, follow_num, fan_num, weibo_num) "
					+ "VALUES "
					+ "(?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?, ?)"
					);
			if(user != null){
				ps.setLong(1, user.getUid());
				ps.setString(2, user.getNickname());
				ps.setString(3, user.getSex());
				ps.setString(4, user.getRegTime());
				ps.setString(5, user.getEducation());
				ps.setString(6, user.getBirthday());
				ps.setString(7, user.getWork());
				ps.setString(8, user.getRegion());
				ps.setString(9, user.getHobby());
				ps.setString(10, user.getTag());
				ps.setInt(11, user.getFollowNum());
				ps.setInt(12, user.getFansNum());
				ps.setInt(13, user.getWeiboNum());
				ps.execute();
			}
			ps.close();
		} 
		catch (SQLException e) {
			Log.error(e);
			return false;
		}
		finally{
//			try {
//				conn.close();
//			} 
//			catch (SQLException e) {
//				Log.error(e);
//			}
		}
		return true;
	}

}
