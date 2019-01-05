package org.afive.wecheck.article.bean;

import java.util.List;

public class MainGroupResult {
	private int articleGroupID;
	private String name;
	private String iconImage;
	private int order;
	
	List<ArticleBean> articleList;
	
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
	public String getIconImage() {
		return iconImage;
	}
	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public List<ArticleBean> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<ArticleBean> articleList) {
		this.articleList = articleList;
	}
	
	
	
	

}
