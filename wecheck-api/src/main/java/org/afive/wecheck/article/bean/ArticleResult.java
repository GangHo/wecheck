package org.afive.wecheck.article.bean;

import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.bean.CommentResult;
import org.afive.wecheck.user.bean.UserResult;

public class ArticleResult {
	private UserResult publisher;
	private ArticleBean article;
	private String rpImage;
	private boolean isThumbnail;
	private String likeIsChecked;
	private String commentCount;
	private String likeCount;
	private CommentResult comment;
	private String movieID;
	
	public UserResult getPublisher() {
		return publisher;
	}
	public void setPublisher(UserResult publisher) {
		this.publisher = publisher;
	}
	public ArticleBean getArticle() {
		return article;
	}
	public void setArticle(ArticleBean article) {
		this.article = article;
	}
	public String getRpImage() {
		return rpImage;
	}
	public void setRpImage(String rpImage) {
		this.rpImage = rpImage;
	}
	public boolean isThumbnail() {
		return isThumbnail;
	}
	public void setThumbnail(boolean isThumbnail) {
		this.isThumbnail = isThumbnail;
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
	public CommentResult getComment() {
		return comment;
	}
	public void setComment(CommentResult comment) {
		this.comment = comment;
	}
	public String getMovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
}
