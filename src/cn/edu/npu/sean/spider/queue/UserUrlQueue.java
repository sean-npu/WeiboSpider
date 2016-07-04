package cn.edu.npu.sean.spider.queue;

import java.util.LinkedList;

import cn.edu.npu.sean.spider.bean.UserUrl;

public class UserUrlQueue {
public static LinkedList<UserUrl> userUrlQueue = new LinkedList<UserUrl>();
	
	public synchronized static void addElement(UserUrl uu){
		userUrlQueue.add(uu);
	}
	
	public synchronized static UserUrl outElement(){
		return userUrlQueue.removeFirst();
	}
	
	public synchronized static boolean isEmpty(){
		return userUrlQueue.isEmpty();
	}
	
	public static int size(){
		return userUrlQueue.size();
	}
	
	public static boolean isContains(UserUrl uu){
		return userUrlQueue.contains(uu);
	}
}
