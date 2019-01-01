package org.afive.wecheck.user.bean;

public class SnsLoginBean {
	
	private int snsLoginID;
	private int userID;
	private int snsType;
	private String snsToken;
	private String registeredTime;
	
	
	public int getSnsLoginID() {
		return snsLoginID;
	}
	public void setSnsLoginID(int snsLoginID) {
		this.snsLoginID = snsLoginID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getSnsType() {
		return snsType;
	}
	public void setSnsType(int snsType) {
		this.snsType = snsType;
	}
	public String getSnsToken() {
		return snsToken;
	}
	public void setSnsToken(String snsToken) {
		this.snsToken = snsToken;
	}
	
	public String getRegisteredTime() {
		return registeredTime;
	}
	
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
}
