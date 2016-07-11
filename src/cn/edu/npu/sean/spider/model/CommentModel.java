package cn.edu.npu.sean.spider.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.bean.Comment;
import cn.edu.npu.sean.spider.fetcher.UserFetcher;
import cn.edu.npu.sean.spider.utils.DBConn;

public class CommentModel {
	public static Connection conn = DBConn.getConnection();
	private static final Logger Log = Logger.getLogger(UserFetcher.class.getName());
	public static boolean insertBatch(List<Comment> clist){
		PreparedStatement ps;
		try{
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"INSERT INTO "
					+ "weibo_comment "
					+ "(scheme, film_id, time, weibo_id, content, textLength, source, uid, reposts_count, comments_count, attitudes_count, weibo_attitude) "
					+ "VALUES "
					+ "(?, ?, ?,?, ?, ?, ?,?, ?, ?, ?, ?)"
					);
			ps.clearBatch();
			for(Iterator<Comment> it = clist.iterator();it.hasNext();){
				Comment c = it.next();
				ps.setString(1, c.getScheme());
				ps.setLong(2, c.getFilmId());
				ps.setString(3, c.getTime());
				ps.setString(4, c.getWeiboId());
				ps.setString(5, c.getContent());
				ps.setInt(6, 0);
				ps.setString(7,c.getSource());
				ps.setLong(8, c.getUid());
				ps.setInt(9, c.getRepostsCount());
				ps.setInt(10, c.getCommentsCount());
				ps.setInt(11, c.getAttitudesCount());
				ps.setInt(12, c.getWeiboAttitude());
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
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
	public static boolean hasWeibo(String weiboID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT weibo_id from weibo_comment where weibo_id=?");
			ps.setString(1, weiboID);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
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
		return false;
	}
}
