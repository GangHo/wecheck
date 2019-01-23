package org.afive.wecheck.attendance;

public class AttendanceResult { 
	private AttendanceBean attendance;
	private String regionName;
	private String unitName;
	private String startTime;
	public AttendanceBean getAttendance() {
		return attendance;
	}
	public void setAttendance(AttendanceBean attendance) {
		this.attendance = attendance;
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
