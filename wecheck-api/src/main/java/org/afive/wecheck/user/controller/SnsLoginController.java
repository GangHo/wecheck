package org.afive.wecheck.user.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.afive.wecheck.configuration.BaseTool;
import org.afive.wecheck.configuration.MemberType;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.FcmBean;
import org.afive.wecheck.user.bean.SnsLoginBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.FcmMapper;
import org.afive.wecheck.user.mapper.SnsLoginMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
public class SnsLoginController {
	
	@Autowired
	private SnsLoginMapper snsLoginMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FcmMapper  fcmMapper;
	
	@Autowired
	private ConfirmRequestMapper confirmRequestMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
//테스트용	
	@RequestMapping(value ="/sns",method = RequestMethod.GET) 
	private Map<String, Object> getList(){
		Map<String, Object> result = new HashMap<>();
		result.put("data", snsLoginMapper.getList());
		return result;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	private Map<String, Object> register(
			@RequestParam("snsType") int snsType, 
			@RequestParam("snsToken") String snsToken, 
			@RequestParam("fcmToken") String fcmToken, 
			@RequestParam("uuid") String uuid, 
			@RequestParam("deviceType") int deviceType){
		
		System.out.println("데이터 도착 snsType : "+snsType+", snsToken : "+snsToken+", fcmToken : "+fcmToken+", uuid : "+uuid+", deviceType : "+deviceType);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("snsType", snsType);
		map.put("snsToken",snsToken);
	
		SnsLoginBean snsUserBean = snsLoginMapper.getSnsLoginUserBean(map);
		
		Map<String, Object> result = new HashMap<>();
		
		//일단 snsLogin에 저장된 snsLogin 유저가 아니면 새롭게 만들어서 저장한다.
		//새롭게 만들어진 경우 user에 있다던가 confirmRequest에 있을리가 만무함으로 login_state : none으로 바로 리턴한다.
		if(snsUserBean == null) {
			
			System.out.println("SNS login 레코드 없다.");
			
			snsUserBean=new SnsLoginBean();
			snsUserBean.setSnsToken(snsToken);
			snsUserBean.setSnsType(snsType);
			
			snsLoginMapper.register(snsUserBean);
			
			snsUserBean=snsLoginMapper.get(String.valueOf(snsUserBean.getSnsLoginID()));
			
			result.put("snsLogin", snsUserBean);
			result.put("memberType", MemberType.MEMBER_TYPE_NONE);
			
			System.out.println("snsLogin 생성 완료 : "+snsUserBean.getSnsLoginID());
			
			//FCM 삭제 / REGISTER 작업
			FcmBean fcmParamBean=new FcmBean();
			fcmParamBean.setDeviceType(deviceType);
			fcmParamBean.setFcmToken(fcmToken);
			fcmParamBean.setSnsLoginID(snsUserBean.getSnsLoginID());
			fcmParamBean.setUuid(uuid);
			fcmMapper.deleteIfExists(fcmParamBean);
			fcmMapper.registerWithOutUserID(fcmParamBean);

			
			result.put("responseCode", ResponseCode.SUCCESS);
			
			//일단 있는지 확인 있으면 삭제		        
			AccessTokenBean accessToken = BaseTool.createAccessToken(snsUserBean.getSnsLoginID(), uuid, deviceType);

	        accessTokenMapper.updateIfExists(accessToken);
	        accessTokenMapper.register(accessToken);
	        
	        result.put("accessToken", accessToken.getAccessTokenID());
			
			return result;
		}
		
		System.out.println("snsLogin 레코드 있다. 널이아니다.");
		
		result.put("snsLogin", snsUserBean);
		
		//sns에 이미 있다면 user쪽에 이미 등록이 되었는지를 확인한다.
		if(snsUserBean.getUserID()!=0) {//0이 아니면 이미 등록되었다는 뜻이다.
			System.out.println("USER에 이미 등록되어있"+snsUserBean.getUserID());

			//유저가 있으니 유저 객체를 받아온다.
			UserBean userLoginBean=userMapper.get(snsUserBean.getUserID()+"");
			
		
			result.put("user", userLoginBean);
			result.put("memberType", "1");
			
			//FCM 삭제 / REGISTER 작업
			FcmBean fcmParamBean=new FcmBean();
			fcmParamBean.setDeviceType(deviceType);
			fcmParamBean.setUserID(userLoginBean.getUserID());
			fcmParamBean.setFcmToken(fcmToken);
			fcmParamBean.setSnsLoginID(snsUserBean.getSnsLoginID());
			fcmParamBean.setUuid(uuid);
			fcmMapper.deleteIfExists(fcmParamBean);
			fcmMapper.register(fcmParamBean);

			
			System.out.println("FCM 레코드 있다." + fcmParamBean);
			result.put("responseCode",ResponseCode.SUCCESS);
			
			//일단 있는지 확인 있으면 삭제		        
			AccessTokenBean accessToken = BaseTool.createAccessToken(snsUserBean.getSnsLoginID(), uuid, deviceType);

	        accessTokenMapper.updateIfExists(accessToken);
	        accessToken.setUserID(userLoginBean.getUserID());
	        accessTokenMapper.registerWithUserID(accessToken);
	        
	        result.put("accessToken", accessToken.getAccessTokenID());
	        
			return result;
		}
		
		//requestConfirm에 있는지 확인한다.
		ConfirmRequestBean confirmRequestBean=confirmRequestMapper.getBySnsLoginID(snsUserBean.getSnsLoginID()+"");
		
		if(confirmRequestBean!=null) {//이 리스트에 존재한다면
			System.out.println("confirmRequest에 이미 등록되어있"+confirmRequestBean.getConfirmRequestID());
			
			//FCM 삭제 / REGISTER 작업
			FcmBean fcmParamBean=new FcmBean();
			fcmParamBean.setDeviceType(deviceType);
			fcmParamBean.setFcmToken(fcmToken);
			fcmParamBean.setSnsLoginID(snsUserBean.getSnsLoginID());
			fcmParamBean.setUuid(uuid);
			fcmMapper.deleteIfExists(fcmParamBean);
			fcmMapper.registerWithOutUserID(fcmParamBean);

			result.put("responseCode", ResponseCode.SUCCESS);
			result.put("confirmRequest", confirmRequestBean);
			result.put("memberType",MemberType.MEMBER_TYPE_CONFIRM_REQUEST);
			
			//일단 있는지 확인 있으면 삭제		        
			AccessTokenBean accessToken = BaseTool.createAccessToken(snsUserBean.getSnsLoginID(), uuid, deviceType);

	        accessTokenMapper.updateIfExists(accessToken);
	        accessToken.setConfirmRequestID(confirmRequestBean.getConfirmRequestID());
	        accessTokenMapper.registerWithConfirmRequestID(accessToken);
	        
	        result.put("accessToken", accessToken.getAccessTokenID());
			
			return result;
		}
		
		
		result.put("snsLogin", snsUserBean);
		result.put("memberType", MemberType.MEMBER_TYPE_NONE);
		
		System.out.println("snsLogin 생성 완료 : "+snsUserBean.getSnsLoginID());
		
		//FCM 삭제 / REGISTER 작업
		FcmBean fcmParamBean=new FcmBean();
		fcmParamBean.setDeviceType(deviceType);
		fcmParamBean.setFcmToken(fcmToken);
		fcmParamBean.setSnsLoginID(snsUserBean.getSnsLoginID());
		fcmParamBean.setUuid(uuid);
		fcmMapper.deleteIfExists(fcmParamBean);
		fcmMapper.registerWithOutUserID(fcmParamBean);
		
		//일단 있는지 확인 있으면 삭제		        
		AccessTokenBean accessToken = BaseTool.createAccessToken(snsUserBean.getSnsLoginID(), uuid, deviceType);

        accessTokenMapper.updateIfExists(accessToken);
        accessTokenMapper.register(accessToken);
        
        result.put("accessToken", accessToken.getAccessTokenID());
		
		return result;
	}
	
}
