package org.afive.wecheck.comment.bean;

public class CommentBean {
	private int commentID;
	private int userID;
	private int articleID;
	private int parentID;
	private int privacy;
	private int state;
	private String contents;
	private String registeredTime;
	private String lastEditedTime;
	private String deletedTime;
	
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	public String getLastEditedTime() {
		return lastEditedTime;
	}
	public void setLastEditedTime(String lastEditedTime) {
		this.lastEditedTime = lastEditedTime;
	}
	
	public int getArticleID() {
		return articleID;
	}
	
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	public int getPrivacy() {
		return privacy;
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
