package org.afive.wecheck.banner.bean;

public class BannerBean {
	private int bannerID;
	private int articleID;
	private int state;
	private String bannerImagePath;
	private String postedTime;
	private String endTime;
	
	public int getBannerID() {
		return bannerID;
	}
	public void setBannerID(int bannerID) {
		this.bannerID = bannerID;
	}
	public int getArticleID() {
		return articleID;
	}
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getBannerImagePath() {
		return bannerImagePath;
	}
	public void setBannerImagePath(String bannerImagePath) {
		this.bannerImagePath = bannerImagePath;
	}
	public String getPostedTime() {
		return postedTime;
	}
	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
