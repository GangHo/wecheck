package org.afive.wecheck.article.bean;

public class ArticleFileBean {
	private int ArticleFileID;
	private int ArticleID;
	private int fileType;
	private String articleFilePath;
	private String registeredTime;
	
	
	public int getArticleFileID() {
		return ArticleFileID;
	}
	public void setArticleFileID(int articleFileID) {
		ArticleFileID = articleFileID;
	}
	public int getArticleID() {
		return ArticleID;
	}
	public void setArticleID(int articleID) {
		ArticleID = articleID;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getArticleFilePath() {
		return articleFilePath;
	}
	public void setArticleFilePath(String articleFilePath) {
		this.articleFilePath = articleFilePath;
	}
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	
	
}
