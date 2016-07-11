package cn.edu.npu.sean.spider.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.npu.sean.spider.Exception.CookieInvalidException;
import cn.edu.npu.sean.spider.bean.Account;
import cn.edu.npu.sean.spider.bean.Comment;
import cn.edu.npu.sean.spider.bean.CommentTask;
import cn.edu.npu.sean.spider.container.AccountQueue;
import cn.edu.npu.sean.spider.container.CommentTaskCompleteSet;
import cn.edu.npu.sean.spider.container.CommentTaskQueue;
import cn.edu.npu.sean.spider.fetcher.CommentFetcher;
import cn.edu.npu.sean.spider.model.CommentModel;
import cn.edu.npu.sean.spider.model.TaskModel;

public class CommentTaskWorker implements Runnable{
	private static final Logger Log = Logger.getLogger(CommentTaskWorker.class.getName());

	@Override
	public void run() {
		Log.info("---------------Start Thread "+Thread.currentThread().getName()+"---------------");
		Account a = AccountQueue.outElement();
		AccountQueue.addElement(a);
		Log.info("["+Thread.currentThread().getName()+"] Movie Num: "+CommentTaskQueue.size());
		
		while(!CommentTaskQueue.isEmpty()){
			CommentTask ct = CommentTaskQueue.outElement();
			List<Comment> clist = new ArrayList<Comment>();
			for(int i=0;i<2;i++){
				if(i==1){
					ct.setGood(false);
					ct.setPage(1);
					ct.setMaxPage(1);
				}
				while(ct.getPage()<=ct.getMaxPage()){
					try {
						CommentFetcher.fetchCommentList(clist, ct,a.getCookie());
					} catch (CookieInvalidException e) {
						a = AccountQueue.outElement();
						AccountQueue.addElement(a);
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					ct.addPage();
					Log.info("["+Thread.currentThread().getName()+"] list_size:"+clist.size());
					if(clist.size()>100){
						if(CommentModel.insertBatch(clist)){
							Log.info("["+Thread.currentThread().getName()+"] Insert "+clist.size()+" comments");
							clist.clear();
						}else{
							Log.info("["+Thread.currentThread().getName()+"] DB error!");
							return;
						}
					}
				}
				if(CommentModel.insertBatch(clist)){
					Log.info("["+Thread.currentThread().getName()+"] Insert "+clist.size()+" comments");
					clist.clear();
				}else{
					Log.info("["+Thread.currentThread().getName()+"] DB error!");
					return;
				}
			}
			CommentTaskCompleteSet.add(ct.getFilmId()+"");
			TaskModel.update(CommentTaskCompleteSet.toMyString(), "gWC_java");
		}
	}

}
