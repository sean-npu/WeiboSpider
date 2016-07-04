package cn.edu.npu.sean.spider.bean;

public class User {
	private long uid;
	private String nickname;
	private String sex;
	private String regTime;
	private String education;
	private String birthday;
	private String work;
	private String region;
	private String tag;
	private String hobby;
	private int followNum;
	private int fansNum;
	private int weiboNum;
	public User(long uid) {
		this.uid = uid;
	}
	
	public User() {
		super();
	}

	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public int getFollowNum() {
		return followNum;
	}
	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}
	public int getFansNum() {
		return fansNum;
	}
	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}
	public int getWeiboNum() {
		return weiboNum;
	}
	public void setWeiboNum(int weiboNum) {
		this.weiboNum = weiboNum;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "User [uid:" + uid + ", nickname:" + nickname + ", sex:" + sex
				+ ", regTime:" + regTime + ", education:" + education
				+ ", birthday:" + birthday + ", work:" + work + ", region:"
				+ region + ", tag:" + tag + ", hobby:" + hobby + ", followNum:"
				+ followNum + ", fansNum:" + fansNum + ", weiboNum:" + weiboNum
				+ "]";
	}
	
}
