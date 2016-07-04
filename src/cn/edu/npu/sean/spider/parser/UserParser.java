package cn.edu.npu.sean.spider.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.User;

public class UserParser {

	public static Document getPageDocument(String content) {
		return Jsoup.parse(content);
	}

	public static void parseInfo(User user,Document doc) throws CookieInvalidException{
		Elements elements = doc.select("div");
		for(int i = 0; i < elements.size(); i++){
			Element e = elements.get(i);
			Element baseInfoEl = null;
			Element eduInfoEl = null;
			Element workInfoEl = null;
			switch(e.text()){
			case "基本信息":baseInfoEl = elements.get(i+1);break;
			case "学习经历":eduInfoEl = elements.get(i+1);break;
			case "工作经历":workInfoEl = elements.get(i+1);break;
			}
			if(baseInfoEl != null){
				String html = baseInfoEl.html();
				String[] attr = html.split("<br />");
				for(String a:attr){
					String[] sa = a.split(":");
					switch(sa[0]){
					case "昵称":user.setNickname(sa[1]);break;
					case "性别":user.setSex(sa[1]);break;
					case "生日":user.setBirthday(sa[1]);break;
					case "地区":user.setRegion(sa[1]);break;
					case "达人":user.setHobby(sa[1]);break;
					case "标签":
						String tag = Jsoup.parse(sa[1]).text();
						tag = tag.replaceAll("  ", " ");
						tag = tag.replace("更多>>","");
						user.setTag(tag);break;
					}
				}
			}
			if(eduInfoEl != null){
				String html = eduInfoEl.html();
				html = html.replace("&middot;", "");
				html = html.replace("&nbsp;", " ");
				html = html.replace("<br />", "|");
				user.setEducation(html);
			}
			if(workInfoEl != null){
				String html = workInfoEl.html();
				html = html.replace("&middot;", "");
				html = html.replace("&nbsp;", " ");
				html = html.replace("<br />", "|");
				user.setWork(html);
			}
		}
		if(user.getNickname()==null){
			throw new CookieInvalidException("Cookie is Invalid!");
		}
	}

	public static void parseSnsInfo(User user, Document doc) {
		Elements elements = doc.select("body");
		String s = elements.get(0).text();
		Pattern pattern = Pattern.compile("微博\\[([0-9]+)\\] 关注\\[([0-9]+)\\] 粉丝\\[([0-9]+)\\]");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()){
			user.setWeiboNum(Integer.parseInt(matcher.group(1)));
			user.setFollowNum(Integer.parseInt(matcher.group(2)));
			user.setFansNum(Integer.parseInt(matcher.group(3)));
		}
	}
}
