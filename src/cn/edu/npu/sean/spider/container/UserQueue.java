package cn.edu.npu.sean.spider.container;

import java.util.LinkedList;

import cn.edu.npu.sean.spider.bean.User;

public class UserQueue {
public static LinkedList<User> userQueue = new LinkedList<User>();
	
	public synchronized static void addElement(User u){
		userQueue.add(u);
	}
	
	public synchronized static User outElement(){
		return userQueue.removeFirst();
	}
	
	public synchronized static boolean isEmpty(){
		return userQueue.isEmpty();
	}
	
	public static int size(){
		return userQueue.size();
	}
	
	public static boolean isContains(User u){
		return userQueue.contains(u);
	}
}
