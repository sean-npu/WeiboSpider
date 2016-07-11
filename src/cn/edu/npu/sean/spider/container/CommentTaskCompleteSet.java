package cn.edu.npu.sean.spider.container;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public class CommentTaskCompleteSet {
	public static Set<String> completeFilm = new HashSet<String>();
	public synchronized static void add(String filmId){
		completeFilm.add(filmId);
	}
	
	public static boolean contains(String filmId){
		return completeFilm.contains(filmId);
	}
	public static String toMyString(){
		return StringUtils.join(completeFilm.toArray(), ",");
	}
}
