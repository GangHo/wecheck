package org.afive.wecheck.user.bean;

public class AccessTokenBean {
	
	private String accessTokenID;
	private int snsLoginID;
	private int deviceType;
	private String uuid;
	private String registeredTime;
	private int isExpired;
	private int userID;
	private int confirmRequestID;
	
	public String getAccessTokenID() {
		return accessTokenID;
	}
	public void setAccessTokenID(String accessTokenID) {
		this.accessTokenID = accessTokenID;
	}
	public int getSnsLoginID() {
		return snsLoginID;
	}
	public void setSnsLoginID(int snsLoginID) {
		this.snsLoginID = snsLoginID;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	public int getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getConfirmRequestID() {
		return confirmRequestID;
	}
	public void setConfirmRequestID(int confirmRequestID) {
		this.confirmRequestID = confirmRequestID;
	}
	
	
	
	
	
	
	

}
