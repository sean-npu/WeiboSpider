package cn.edu.npu.sean.spider.container;

import java.util.LinkedList;

import cn.edu.npu.sean.spider.bean.UserTask;

public class UserTaskQueue {
public static LinkedList<UserTask> userUrlQueue = new LinkedList<UserTask>();
	
	public synchronized static void addElement(UserTask uu){
		userUrlQueue.add(uu);
	}
	
	public synchronized static UserTask outElement(){
		return userUrlQueue.removeFirst();
	}
	
	public synchronized static boolean isEmpty(){
		return userUrlQueue.isEmpty();
	}
	
	public static int size(){
		return userUrlQueue.size();
	}
	
	public static boolean isContains(UserTask uu){
		return userUrlQueue.contains(uu);
	}
}
