package org.afive.wecheck.user.bean;

import java.sql.Date;
import java.util.ArrayList;

public class UserBean {
	
	private int userID;
	private int userType;
	private String firstName;
	private String lastName;
	private int gender;
	private int regionID;
	private int unitID;
	private Date birthDay;
	private String profileImage;
	private int isRegistered;
	private int isOpened;
	private String createdTime;
	private String registeredTime;
	private int state;
	private String deletedTime;
	private String regionName;
	private String unitName;
	
	private ArrayList snsLoginList;
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
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
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public int getUnitID() {
		return unitID;
	}
	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}
	public int getIsRegistered() {
		return isRegistered;
	}
	public void setIsRegistered(int isRegistered) {
		this.isRegistered = isRegistered;
	}
	public int getIsOpened() {
		return isOpened;
	}
	public void setIsOpened(int isOpened) {
		this.isOpened = isOpened;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getRegisteredTime() {
		return registeredTime;
	}
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}
	public ArrayList getSnsLoginList() {
		return snsLoginList;
	}
	public void setSnsLoginList(ArrayList snsLoginList) {
		this.snsLoginList = snsLoginList;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getState() {
		return state;
	}
	public void setDeletedTime(String deletedTime) {
		this.deletedTime = deletedTime;
	}
	public String getDeletedTime() {
		return deletedTime;
	}
	public String getRegionName() {
		return regionName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
