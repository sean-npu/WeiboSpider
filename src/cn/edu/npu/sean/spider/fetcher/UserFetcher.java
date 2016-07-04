package cn.edu.npu.sean.spider.fetcher;

import org.jsoup.nodes.Document;

import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.User;
import cn.edu.npu.sean.spider.parser.UserParser;
import cn.edu.npu.sean.spider.utils.Utils;

public class UserFetcher {
	/**
	 * 根据url爬取网页内容
	 * @param url
	 * @return
	 * @throws CookieInvalidException 
	 */
	public static void fetchUserInfo(User user,String url,String cookie) throws CookieInvalidException{
		String content = Utils.getContent(url, cookie);
		if(content == null){
			throw new CookieInvalidException("content is null from "+url);
		}
		//将content字符串转换成Document对象
		Document contentDoc = UserParser.getPageDocument(content);
		//判断是否符合下载网页源代码到本地的条件
		UserParser.parseInfo(user,contentDoc);
	}
	public static void fetchUserSNSInfo(User user, String url,String cookie) {
		String content = Utils.getContent(url, cookie);
		//将content字符串转换成Document对象
		Document contentDoc = UserParser.getPageDocument(content);
		//判断是否符合下载网页源代码到本地的条件
		UserParser.parseSnsInfo(user,contentDoc);
	}

}
