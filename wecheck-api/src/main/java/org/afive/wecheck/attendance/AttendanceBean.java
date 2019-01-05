package org.afive.wecheck.attendance;

public class AttendanceBean {
	private int attendanceID;
	private int churchServiceID;
	private int userID;
	private String attendanceTime;
	public int getAttendanceID() {
		return attendanceID;
	}
	public void setAttendanceID(int attendanceID) {
		this.attendanceID = attendanceID;
	}
	public int getChurchServiceID() {
		return churchServiceID;
	}
	public void setChurchServiceID(int churchServiceID) {
		this.churchServiceID = churchServiceID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}
}
