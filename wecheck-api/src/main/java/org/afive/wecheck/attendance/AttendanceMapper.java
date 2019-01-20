package org.afive.wecheck.attendance;

import java.util.HashMap;
import java.util.List;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.UserBean;
import org.apache.ibatis.annotations.Param;

public interface AttendanceMapper extends CommonMapper<AttendanceBean,String>{

//	public UserBean getUserByToken(String accessTokenID);
	public AttendanceBean checkAttendance(HashMap<String,Object> map);
	public AttendanceBean getIfExists(@Param("churchServiceID") int churchServiceID, @Param("userID") int userID);
	
	/*
	 * 2019-01-19 by gangho
	 */
	public List<AttendanceBean> getAttendanceByUserID(String userID);
}
