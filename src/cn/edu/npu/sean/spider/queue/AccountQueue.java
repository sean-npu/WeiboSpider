package cn.edu.npu.sean.spider.queue;

import java.util.LinkedList;

import cn.edu.npu.sean.spider.bean.Account;

public class AccountQueue {
public static LinkedList<Account> accountQueue = new LinkedList<Account>();
	
	public synchronized static void addElement(Account a){
		accountQueue.add(a);
	}
	
	public synchronized static Account outElement(){
		return accountQueue.removeFirst();
	}
	
	public synchronized static boolean isEmpty(){
		return accountQueue.isEmpty();
	}
	
	public static int size(){
		return accountQueue.size();
	}
	
	public static boolean isContains(Account a){
		return accountQueue.contains(a);
	}
	public static boolean remove(Account a){
		return accountQueue.remove(a);
	}
	public static void print(){
		for(Account a : accountQueue){
			System.out.println(a.getCookie());
		}
	}
}
