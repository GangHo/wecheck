package org.afive.wecheck.user.bean;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class ConfirmRequestBean {
	
	private int confirmRequestID;
	private int snsLoginID;
	private String firstName;
	private String lastName;
	private int gender;
	private int regionID;
	private int unitID;
	private String birthDay;
	private String crProfileImage;
	private int isApproved;
	private String approvedTime;
	private String requestedTime;
	private int isApprovedPopNo;
	
	
	
	
	public ConfirmRequestBean() {
		super();
	}
	
	public void updateValues(String firstName, String lastName, int gender, int regionID, int unitID,String birthDay) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.gender=gender;
		this.regionID=regionID;
		this.unitID=unitID;
		this.birthDay=birthDay;
	}
	
	public ConfirmRequestBean(int snsLoginID, String firstName, String lastName, int gender, int regionID, int unitID,
			String birthDay) {
		super();
		this.snsLoginID = snsLoginID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.regionID = regionID;
		this.unitID = unitID;
		this.birthDay = birthDay;
		
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getConfirmRequestID() {
		return confirmRequestID;
	}
	public void setConfirmRequestID(int confirmRequestID) {
		this.confirmRequestID = confirmRequestID;
	}
	public int getSnsLoginID() {
		return snsLoginID;
	}
	public void setSnsLoginID(int snsLoginID) {
		this.snsLoginID = snsLoginID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getRegionID() {
		return regionID;
	}
	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}
	public int getUnitID() {
		return unitID;
	}
	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}
	public String getCrProfileImage() {
		return crProfileImage;
	}
	public void setCrProfileImage(String crProfileImage) {
		this.crProfileImage = crProfileImage;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public String getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	public String getRequestedTime() {
		return requestedTime;
	}
	public void setRequestedTime(String requestedTime) {
		this.requestedTime = requestedTime;
	}
	public int getIsApprovedPopNo() {
		return isApprovedPopNo;
	}
	public void setIsApprovedPopNo(int isApprovedPopNo) {
		this.isApprovedPopNo = isApprovedPopNo;
	}

	
	
	
	
	

}
