package org.afive.wecheck.attendance;

public class AttendanceResult { 
	private Integer attendanceID;
	private int churchServiceID;
	private int churchServiceState;
	private String regionName;
	private String unitName;
	private String startTime;
	
	public Integer getAttendanceID() {
		return attendanceID;
	}
	public void setAttendanceID(Integer attendanceID) {
		this.attendanceID = attendanceID;
	}
	public int getChurchServiceID() {
		return churchServiceID;
	}
	public void setChurchServiceID(int churchServiceID) {
		this.churchServiceID = churchServiceID;
	}
	public int getChurchServiceState() {
		return churchServiceState;
	}
	public void setChurchServiceState(int churchServiceState) {
		this.churchServiceState = churchServiceState;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	

}
