package org.afive.wecheck.quote.bean;

public class QuoteBean {
	private int quoteID;
	private int adminUserID;
	private String source;
	private String words;
	private String postedTime;
	private int state;
	private String deletedTime;
	
	
	public int getQuoteID() {
		return quoteID;
	}
	public void setQuoteID(int quoteID) {
		this.quoteID = quoteID;
	}
	public int getAdminUserID() {
		return adminUserID;
	}
	public void setAdminUserID(int adminUserID) {
		this.adminUserID = adminUserID;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getPostedTime() {
		return postedTime;
	}
	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
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
