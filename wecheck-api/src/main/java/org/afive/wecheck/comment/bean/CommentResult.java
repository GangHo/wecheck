package org.afive.wecheck.comment.bean;

import org.afive.wecheck.user.bean.UserResult;

public class CommentResult {
	private UserResult commenter;
	private CommentBean comment;
	private String likeIsChecked;
	private String commentCount;
	private String likeCount;
	
	public UserResult getCommenter() {
		return commenter;
	}
	public void setCommenter(UserResult commenter) {
		this.commenter = commenter;
	}
	public CommentBean getComment() {
		return comment;
	}
	public void setComment(CommentBean comment) {
		this.comment = comment;
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
	public String getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}
	
}
