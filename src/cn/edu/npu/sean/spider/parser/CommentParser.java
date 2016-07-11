package cn.edu.npu.sean.spider.parser;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;






import cn.edu.npu.sean.spider.Exception.CommentJSONException;
import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.Comment;
import cn.edu.npu.sean.spider.bean.CommentTask;
import cn.edu.npu.sean.spider.model.CommentModel;
import cn.edu.npu.sean.spider.worker.CommentTaskWorker;

public class CommentParser {
	private static final Logger Log = Logger.getLogger(CommentTaskWorker.class.getName());

	public static void parse(List<Comment> clist,String content,CommentTask ct) throws CookieInvalidException, CommentJSONException{
		JSONObject cardObject = null;
		try{
			JSONObject jsonObject = JSONObject.fromObject(content);
			int maxPage = jsonObject.getInt("maxPage");
			if(maxPage==0){
				throw new CommentJSONException("Comment JSON error!");
			}
			ct.setMaxPage(maxPage);
			cardObject = JSONObject.fromObject(jsonObject.getJSONArray("cards").get(0).toString());
		}catch(JSONException |IndexOutOfBoundsException e){
			throw new CommentJSONException("Comment JSON error!");
		}
		JSONArray comments = cardObject.getJSONArray("card_group");
		for(Iterator<JSONObject> it = comments.iterator();it.hasNext();){
			JSONObject comment = it.next();
			Comment c = new Comment();
			c.setScheme(comment.getString("scheme"));
			JSONObject mblog = comment.getJSONObject("mblog");
			c.setTime(mblog.getString("created_at"));
			c.setContent(mblog.getString("text"));
			c.setWeiboId(mblog.getString("idstr"));
			c.setSource(mblog.getString("source"));
			c.setUid(mblog.getJSONObject("user").getLong("id"));
			c.setRepostsCount(mblog.getInt("reposts_count"));
			c.setAttitudesCount(mblog.getInt("attitudes_count"));
			c.setWeiboAttitude((ct.isGood()?1:0));
			c.setFilmId(ct.getFilmId());
			if(clist.contains(c)||CommentModel.hasWeibo(c.getWeiboId())){
				Log.info("["+Thread.currentThread().getName()+"] film_id:"+c.getFilmId()+" scheme:"+comment.getString("scheme")+"已存在，跳过...");
				continue;
			}
			clist.add(c);
			Log.info("["+Thread.currentThread().getName()+"] film_id:"+c.getFilmId()+" scheme:"+comment.getString("scheme"));
		}
	}
}
