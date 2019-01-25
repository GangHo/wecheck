package org.afive.wecheck.article.bean;

public class ArticleYouTubeBean {
	private int articleYouTubeID;
	private int articleID;
	private String movieID;
	private String registeredTime;
	private int state;
	private String deletedTime;
	
	public int getArticleYouTubeID() {
		return articleYouTubeID;
	}
	public void setArticleYouTubeID(int articleYouTubeID) {
		this.articleYouTubeID = articleYouTubeID;
	}
	public int getArticleID() {
		return articleID;
	}
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	public String getMovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDeletedTime() {
		return deletedTime;
	}
	public void setDeletedTime(String deletedTime) {
		this.deletedTime = deletedTime;
	}
}
