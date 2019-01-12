package org.afive.wecheck.comment.bean;

import org.afive.wecheck.user.bean.UserResult;

public class CommentResult {
	private UserResult commenterResult;
	private CommentBean commentBean;
	private String likeIsChecked;
	private String commentCount;
	private String	commentLikeCount;
	
	public UserResult getCommenterResult() {
		return commenterResult;
	}
	public void setCommenterResult(UserResult commenterResult) {
		this.commenterResult = commenterResult;
	}
	public CommentBean getCommentBean() {
		return commentBean;
	}
	public void setCommentBean(CommentBean commentBean) {
		this.commentBean = commentBean;
	}
	public String getLikeIsChecked() {
		return likeIsChecked;
	}
	public void setLikeIsChecked(String likeIsChecked) {
		this.likeIsChecked = likeIsChecked;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getCommentLikeCount() {
		return commentLikeCount;
	}
	public void setCommentLikeCount(String commentLikeCount) {
		this.commentLikeCount = commentLikeCount;
	}
	
	
}
