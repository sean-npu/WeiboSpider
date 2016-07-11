package cn.edu.npu.sean.spider.worker;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.Account;
import cn.edu.npu.sean.spider.bean.User;
import cn.edu.npu.sean.spider.bean.UserTask;
import cn.edu.npu.sean.spider.container.AccountQueue;
import cn.edu.npu.sean.spider.container.UserTaskQueue;
import cn.edu.npu.sean.spider.fetcher.UserFetcher;
import cn.edu.npu.sean.spider.model.UserModel;

public class UserTaskWorker implements Runnable{
	private static final Logger Log = Logger.getLogger(UserTaskWorker.class.getName());

	@Override
	public void run() {
		Log.info("---------------Start Thread "+Thread.currentThread().getName()+"---------------");
		Account a = AccountQueue.outElement();
		AccountQueue.addElement(a);
		while(!UserTaskQueue.isEmpty()){
			UserTask uu = UserTaskQueue.outElement();
			User user = new User();
			user.setUid(uu.getUid());
			int expNum = 0;
			boolean retry = false;
			try {
				UserFetcher.fetchUserInfo(user,uu.getInfoUrl(), a.getCookie());
			} catch (CookieInvalidException e) {
				retry = true;
				expNum++;
				Log.info("["+Thread.currentThread().getName()+"] network error! "+expNum+"s");
				Log.info("["+Thread.currentThread().getName()+"] Sleep "+" "+(expNum*2)*1+"s");
				try {
					Thread.sleep((expNum*2)*1000);
				} catch (InterruptedException e1) {
					UserTaskQueue.addElement(uu);
					e1.printStackTrace();
				}
			}
			while(retry){
				try {
					UserFetcher.fetchUserInfo(user,uu.getInfoUrl(), a.getCookie());
					retry = false;
				} catch (CookieInvalidException e) {
					if(expNum>=1){
						//AccountQueue.remove(a);
						a = AccountQueue.outElement();
						AccountQueue.addElement(a);
						Log.info("["+Thread.currentThread().getName()+"] Switch Cookie");
						//Log.info("["+Thread.currentThread().getName()+"] Stop");
						UserTaskQueue.addElement(uu);
						expNum = 0;
						continue;
					}
					expNum++;
					Log.info("["+Thread.currentThread().getName()+"] network error! "+expNum+"s");
					Log.info("["+Thread.currentThread().getName()+"] Sleep "+(Math.pow(2,expNum))+"s");
					try {
						Thread.sleep(((int)Math.pow(2,expNum)*1000));
					} catch (InterruptedException e1) {
						UserTaskQueue.addElement(uu);
						e1.printStackTrace();
					}
				}
			}
			UserFetcher.fetchUserSNSInfo(user,uu.getSnsInfoUrl(), a.getCookie());
			if(UserModel.insert(user)){
				Log.info("["+Thread.currentThread().getName()+"] Succesfully Import One User:" + user.getUid());
			}else{
				UserTaskQueue.addElement(uu);
				Log.info("["+Thread.currentThread().getName()+"] Failed Import One User:" + user.getUid());
			}
		}
		
	}

}
