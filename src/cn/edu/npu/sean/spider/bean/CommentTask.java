package cn.edu.npu.sean.spider.bean;

import cn.edu.npu.sean.spider.utils.Constants;

public class CommentTask {
	private int page;
	private int maxPage;
	private long filmId;
	private boolean isGood;
	
	public CommentTask() {
		this.page = 1;
		this.maxPage = 1;
		this.isGood = true;
	}

	public String getCommentUrl() {
		return Constants.MOVIE_COMMENT_URL+"containerid=100120"+this.filmId+"_-_cardlist_"+this.getType()+"weibo&count=20&retcode=6102&page="+this.page;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public void addPage(){
		++this.page;
	}
	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public long getFilmId() {
		return filmId;
	}

	public void setFilmId(long filmId) {
		this.filmId = filmId;
	}

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}
	public String getType(){
		return isGood?"good":"bad";
	}
}
