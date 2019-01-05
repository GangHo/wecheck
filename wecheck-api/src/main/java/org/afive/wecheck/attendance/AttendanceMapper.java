package org.afive.wecheck.attendance;

import java.util.HashMap;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.UserBean;

public interface AttendanceMapper extends CommonMapper<AttendanceBean,String>{

	public UserBean getUserByToken(String accessTokenID);
	public AttendanceBean getAttendance(HashMap<String,Object> map);
}
