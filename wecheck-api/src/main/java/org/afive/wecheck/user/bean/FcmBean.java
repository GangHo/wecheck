package org.afive.wecheck.user.bean;

public class FcmBean {
	
	private int fcmID;
	private Integer userID; // *userID 에 NULL 값을 넣어야 하는 상황이면 Integer 형으로 선언해야 함 int는 0로 저장됨
	private int snsLoginID;
	private String fcmToken;
	private int deviceType;
	private String uuid;
	private String fcmRegisteredTime;
	
	
	public int getSnsLoginID() {
		return snsLoginID;
	}
	public void setSnsLoginID(int snsLoginID) {
		this.snsLoginID = snsLoginID;
	}
	public int getFcmID() {
		return fcmID;
	}
	public void setFcmID(int fcmID) {
		this.fcmID = fcmID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
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
	public String getFcmRegisteredTime() {
		return fcmRegisteredTime;
	}
	public void setFcmRegisteredTime(String fcmRegisteredTime) {
		this.fcmRegisteredTime = fcmRegisteredTime;
	}
	
	

}
