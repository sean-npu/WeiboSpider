package cn.edu.npu.sean.spider.bean;

public class Comment {
	private long id;
	private String scheme;
	private long filmId;
	private String time;
	private String weiboId;
	private String content;
	private int textLength;
	private String source;
	private long uid;
	private int repostsCount;
	private int commentsCount;
	private int attitudesCount;
	private int weiboAttitude;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public long getFilmId() {
		return filmId;
	}
	public void setFilmId(long filmId) {
		this.filmId = filmId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTextLength() {
		return textLength;
	}
	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(int repostsCount) {
		this.repostsCount = repostsCount;
	}
	public int getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public int getAttitudesCount() {
		return attitudesCount;
	}
	public void setAttitudesCount(int attitudesCount) {
		this.attitudesCount = attitudesCount;
	}
	public int getWeiboAttitude() {
		return weiboAttitude;
	}
	public void setWeiboAttitude(int weiboAttitude) {
		this.weiboAttitude = weiboAttitude;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Comment c = (Comment) obj;
		if(c.getWeiboId()==this.weiboId) return true;
		return false;
	}
	
	
}
