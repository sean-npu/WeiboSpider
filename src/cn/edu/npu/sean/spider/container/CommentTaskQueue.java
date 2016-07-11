package cn.edu.npu.sean.spider.container;

import java.util.LinkedList;

import cn.edu.npu.sean.spider.bean.CommentTask;

public class CommentTaskQueue {
public static LinkedList<CommentTask> commentUrlQueue = new LinkedList<CommentTask>();
	
	public synchronized static void addElement(CommentTask cu){
		commentUrlQueue.add(cu);
	}
	
	public synchronized static CommentTask outElement(){
		return commentUrlQueue.removeFirst();
	}
	
	public synchronized static boolean isEmpty(){
		return commentUrlQueue.isEmpty();
	}
	
	public static int size(){
		return commentUrlQueue.size();
	}
	
	public static boolean isContains(CommentTask cu){
		return commentUrlQueue.contains(cu);
	}
}
