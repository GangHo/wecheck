package org.afive.wecheck.churchService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.attendance.AttendanceBean;
import org.afive.wecheck.attendance.AttendanceMapper;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="churchService")
public class ChurchServiceController {
	
	@Autowired
	private ChurchServiceMapper churchServiceMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String,Object> getList(){
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("data",churchServiceMapper.getChurchServiceList());
		return result;
	}
	
	@RequestMapping(value="",method = RequestMethod.GET)
	private Map<String,Object> getChurchService(
			@RequestHeader("Authorization") String accessTokenID) {
		
		HashMap<String, Object> result = new HashMap<>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		/*-- accessToken이 유효하지 않은 경우 --*/
		if(accessTokenBean==null) {
			System.out.println("accessToken이 NULL이다.");
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}

		ChurchServiceBean churchServiceBean = churchServiceMapper.getChurchService(accessTokenID);
		
		/*-- 현재 예배시간이 아닌경우 --*/
		if(churchServiceBean==null) {
			result.put("churchService", null);
			result.put("responseCode", ResponseCode.SUCCESS);
			return result;
		}
		
		int userID = accessTokenBean.getUserID(); // accessToeknID로 뽑은 UserID
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		map.put("churchServiceID",churchServiceBean.getChurchServiceID());
		
		AttendanceBean attendanceBean = attendanceMapper.getAttendance(map);
		
		/*-- 예배시간이지만 출석을 하지 않은 경우 --*/
		if(attendanceBean==null) {
			result.put("attendance",null);
			result.put("churchService", churchServiceBean);
			result.put("responseCode",ResponseCode.SUCCESS);
			return result;
		}
		
		/*-- 출석을 한 경우 --*/
		result.put("attendance",attendanceBean);
		result.put("churchService",churchServiceBean);
		result.put("responseCode",ResponseCode.SUCCESS);
		
		return result;
	}
}
