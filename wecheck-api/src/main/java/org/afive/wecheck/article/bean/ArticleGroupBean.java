package org.afive.wecheck.article.bean;

public class ArticleGroupBean {
	private int articleGroupID;
	private String name;
	private int isApproved;
	private String iconImage;
	private int adminUserID;
	private String createdTime;
	private String lastEditedTime;
	public int getArticleGroupID() {
		return articleGroupID;
	}
	public void setArticleGroupID(int articleGroupID) {
		this.articleGroupID = articleGroupID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public String getIconImage() {
		return iconImage;
	}
	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}
	public int getAdminUserID() {
		return adminUserID;
	}
	public void setAdminUserID(int adminUserID) {
		this.adminUserID = adminUserID;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getLastEditedTime() {
		return lastEditedTime;
	}
	public void setLastEditedTime(String lastEditedTime) {
		this.lastEditedTime = lastEditedTime;
	}
	
	

}
