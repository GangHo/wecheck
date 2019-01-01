package org.afive.wecheck.user.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.service.PushNotificationService;
import org.afive.wecheck.user.bean.FcmBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.FcmMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "fcms")
public class FcmController {
	
	@Autowired
	private FcmMapper fcmMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Autowired
	private PushNotificationService aushhNotificationService;
	
	@RequestMapping(value="", method = RequestMethod.POST,produces= {"application/json"})
	private Map<String, Object> send( 
			@RequestParam("pushType")String pushType,
			@RequestParam("snsLoginID")String snsLoginID,
			@RequestParam(value="idx", required=false) String idx
			) throws JSONException{
		
		System.out.println("pushType : "+pushType+", snsLoginID : "+snsLoginID+", idx : "+idx);
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		//fcmToken받아오
		FcmBean fcmBean = fcmMapper.getBySnsLoginID(snsLoginID);
		
		if(fcmBean==null) {
			
			System.out.println("fcmToken뽑아왔는데널입니다.");
			
			result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
			return result;
		}
	

		
		System.out.println("fcmToken 결과, fcmToken : "+fcmBean.getFcmToken()+", deviceType : "+fcmBean.getDeviceType());
		
		JSONObject jsonBody = new JSONObject();
		
		//제목 정하
		String title = null;	
		String body = null;
		switch(Integer.parseInt(pushType)) {
		
			//회원인증완료 건 인경
			case Data.PUSH_TYPE_CONFIRM_REQUEST_RESULT:
				
				switch(Integer.parseInt(idx)) {
					case Data.PUSH_IDX_ACCEPTED:
						title="인증 성공";
						body="등록 인증에 성공하셨습니다";
						break;
					case Data.PUSH_IDX_DENIED:
						title="인증 실패";
						body="등록이 실패되었습니다 정보를 수정한뒤 재요청을하거나 카카오플친으로 연락해주세요";	
				}
				
				
				break;
		}
		
		
		
		
		//보내는 fcmToken설정하
		jsonBody.put("to", fcmBean.getFcmToken());
		
		if(fcmBean.getDeviceType()==Data.DEVICE_TYPE_ANDROID) {
			JSONObject data = new JSONObject();
			data.put("title", title);
			data.put("body", body);
			data.put("pushType", pushType);
			data.put("idx", idx);
			
			jsonBody.put("data", data);
		}else if(fcmBean.getDeviceType()==Data.DEVICE_TYPE_IOS) {
			JSONObject notification=new JSONObject();
			notification.put("title", title);
			notification.put("body", body);
			notification.put("pushType", pushType);
			notification.put("idx", idx);
			
			jsonBody.put("notification", notification);
			
		}
		
		System.out.println("title : "+title+", body : "+body+", pushType : "+pushType+", idx : "+idx);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		
		System.out.println("body : "+body.toString());
		HttpEntity<String> request = new HttpEntity<>(jsonBody.toString(), headers);

	
		CompletableFuture<String> pushNotification = PushNotificationService.send(request);
		CompletableFuture.allOf(pushNotification).join();
		
		try {
			String firebaseResponse = pushNotification.get();
			System.out.println("성공했다!! "+firebaseResponse);
			result.put("responseCode", ResponseCode.SUCCESS);
			return result;
		}catch(InterruptedException e) {
			e.printStackTrace();
			result.put("responseCode", ResponseCode.FAILED_SERVER_ERROR);
			return result;
		}catch(ExecutionException e) {
			e.printStackTrace();
			result.put("responseCode", ResponseCode.FAILED_SERVER_ERROR);
			return result;
		}
		
		
	}
	
//	private Map<String,Object> register(FcmBean fcmBean) {
//		Map<String, Object> result = new HashMap<>();
//		
//		//같은 uuid와 deviceType을 가진 fcm이 있는지 확인한다
//		FcmBean priorFcm = fcmMapper.getByDeviceTypeAndUuid(fcmBean);
//		
//		//같은게 없다면 새로 만들어서 넣는다.
//		if(priorFcm==null) {
//			System.out.println("없어서 새로 만들어 넣었다.");
//			fcmMapper.register(fcmBean);
//			
//			result.put("responseCode", this.responseCode.FCM_INSERT_SUCCESS);
//			return result;
//		}else {//같은게 있다면 그것의 fcm과 userID를 수정한다.
//			System.out.println("있길래 수정했다.");
//			
//			fcmBean.setFcmID(priorFcm.getFcmID());
//			fcmMapper.update(fcmBean);
//			result.put("responseCode", this.responseCode.FCM_UPDATE_SUCCESS);
//			return result;
//		}
//	}
	

}
