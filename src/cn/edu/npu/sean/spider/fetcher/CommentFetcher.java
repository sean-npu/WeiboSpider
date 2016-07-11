package cn.edu.npu.sean.spider.fetcher;

import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.Exception.CommentJSONException;
import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.Comment;
import cn.edu.npu.sean.spider.bean.CommentTask;
import cn.edu.npu.sean.spider.parser.CommentParser;
import cn.edu.npu.sean.spider.utils.Utils;
import cn.edu.npu.sean.spider.worker.CommentTaskWorker;

public class CommentFetcher {
	private static final Logger Log = Logger.getLogger(CommentTaskWorker.class.getName());
	/**
	 * 根据url爬取网页内容
	 * @param url
	 * @return
	 * @throws CookieInvalidException 
	 * @throws InterruptedException 
	 */
	public static void fetchCommentList(List<Comment> clist,CommentTask ct,String cookie) throws CookieInvalidException, InterruptedException{
		boolean retry = true;
		int expNum = 0;
		while(retry){
			if(expNum>10){
				Log.info("["+Thread.currentThread().getName()+"] Skip");
				ct.addPage();
				break;
			}
			Log.info("["+Thread.currentThread().getName()+"] filmid:"+ct.getFilmId()+" type:"+ct.getType()+" Page:"+ct.getPage()+" MaxPage:"+ct.getMaxPage());
			String content = Utils.getContent(ct.getCommentUrl(), cookie);
			if(content == null){
				throw new CookieInvalidException("content is null from "+ct.getCommentUrl());
			}
			try {
				CommentParser.parse(clist,content,ct);
			} catch (CommentJSONException e) {
				retry = true;
				expNum++;
				Log.info("["+Thread.currentThread().getName()+"] network error! "+expNum+"s");
				Log.info("["+Thread.currentThread().getName()+"] Sleep "+" "+(expNum*2)*1+"s");
				Thread.sleep((expNum*2)*1000);
				continue;
			}
			retry = false;
		}
	}
}
