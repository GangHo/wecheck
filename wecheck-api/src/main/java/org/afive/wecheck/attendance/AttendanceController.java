package org.afive.wecheck.attendance;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.attendance.AttendanceBean;
import org.afive.wecheck.attendance.AttendanceMapper;
import org.afive.wecheck.churchService.ChurchServiceBean;
import org.afive.wecheck.churchService.ChurchServiceMapper;
import org.afive.wecheck.configuration.BaseTool;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.RegionBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="attendance")
public class AttendanceController {
	
	@Autowired
	private ChurchServiceMapper churchServiceMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	@Autowired
	private RegionMapper regionMapper;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String,Object> attendChurchService(
			@RequestHeader("Authorization")		String accessToken,
			@RequestParam("churchServiceID")	int churchServiceID,
			@RequestParam("lat")				String lat,
			@RequestParam("lon")				String lon
			){
		
		HashMap<String, Object> result = new HashMap<>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessToken);
		
		/*-- accessToken이 유효하지 않은 경우 --*/
		if(accessTokenBean==null) {
			System.out.println("accessToken이 NULL이다.");
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}

		/*-- 해당 예배가 존재하는지 확인 --*/
		ChurchServiceBean churchServiceBean = churchServiceMapper.get(churchServiceID+"");
		
		
		/*-- 해당 예배가 존재하지 않음 --*/
		if(churchServiceBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.CHURCHSERVICE_NULL));
			return result;
		}
		
		if(churchServiceBean.getState()==Data.SERVICE_STATE_BEFORE) {
			result.put("responseCode", String.valueOf(ResponseCode.CHURCHSERVICE_BEFORE));
			return result;
			
		}else if(churchServiceBean.getState()==Data.SERVICE_STATE_STARTED) {
	
			//걍 넘긴다.
			
			
		}else if(churchServiceBean.getState()==Data.SERVICE_STATE_FINISHED) {
			result.put("responseCode",String.valueOf(ResponseCode.CHURCHSERVICE_FINISHED));
			return result;
		}else {
			result.put("responseCode", String.valueOf(ResponseCode.FAILED_SERVER_ERROR));
			return result;
		}
		
		System.out.println("regionID를 churchService에서 뽑아왔다 : "+churchServiceBean.getRegionID());
		
		/*-- 거리체크 --*/
		RegionBean regionBean = regionMapper.get(churchServiceBean.getRegionID()+"");
		System.out.println("regionBean lat : "+regionBean.getRegionLat()+", long : "+regionBean.getRegionLong());
		
		
		
		
		
		double myLat=Double.parseDouble(lat);
		double myLon = Double.parseDouble(lon);
		
		double churchLat = Double.parseDouble(regionBean.getRegionLat());
		double churchLon = Double.parseDouble(regionBean.getRegionLong());
		
		
		// 미터(Meter) 단위
        double distanceMeter = BaseTool.distance(myLat, myLon, churchLat, churchLon, "meter");

        /*-- 10미터안에 존 --*/
        if(distanceMeter<10) {
        	
        	AttendanceBean attendanceBean = attendanceMapper.getIfExists(churchServiceID, accessTokenBean.getUserID());
        	
        	//이미 있다.
        	if(attendanceBean!=null) {
        		result.put("responseCode", String.valueOf(ResponseCode.CHURCHSERVICE_ALREADY_ATTENDED));
        		return result;
        	}
        	
        	attendanceBean=new AttendanceBean();
        	
        	attendanceBean.setUserID(accessTokenBean.getUserID());
        	attendanceBean.setChurchServiceID(churchServiceID);
        	
        	attendanceMapper.register(attendanceBean);
        	
        	result.put("attendance", attendanceBean);
        	result.put("responseCode", String.valueOf(ResponseCode.SUCCESS));
        	
        }
		
		return result;
	}
	
}
