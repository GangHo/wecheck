package org.afive.wecheck.scheduler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.afive.wecheck.churchService.ChurchServiceBean;
import org.afive.wecheck.churchService.ChurchServiceMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.service.PushNotificationService;
import org.afive.wecheck.user.bean.FcmBean;
import org.afive.wecheck.user.mapper.FcmMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronTable {
	
	@Autowired
	ChurchServiceMapper churchServiceMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	FcmMapper fcmMapper;
	
	@Scheduled(cron = "59 * * * * *")
	public void checkChurchService() {
		/**
		 * changeStateToEnd() -> state가 1인걸 2로 바꿔줌
		 */
		churchServiceMapper.changeStateToEnd();
//		churchServiceMapper.changeStateToBegin();
		
		List<ChurchServiceBean> churchServiceList = null;
		
		/**
		 *  getChurchServiceList() -> state가 0이면서 예배시간인 경우
		 */
		churchServiceList = churchServiceMapper.getChurchServiceList(); 
		
		if(churchServiceList.size() == 0) {
			System.out.println("예배시간인 곳이 없습니다.");
			return;
		}
		else {
			System.out.println("예배시간인 곳이 있습니다");
			
			String title = "예배시간입니다";
			String body = "GPS를 켜 주세요!";
			
			for(int i=0; i<churchServiceList.size(); i++) {
				ChurchServiceBean churchServiceBean = null;
				churchServiceBean = churchServiceList.get(i);
				
				System.out.println( churchServiceBean.getChurchServiceID());
				
				int regionID = churchServiceBean.getRegionID();
				int unitID = churchServiceBean.getUnitID();
				
				HashMap<String,Object> map = new HashMap<>();
				map.put("regionID",regionID);
				map.put("unitID",unitID);
				
				List<String> userIDList = null;
				userIDList = userMapper.getIdByRegionAndUnit(map);
				
//				List<String> fcmTokenList = new ArrayList<>();
				JSONArray tokenArray = new JSONArray();
				JSONObject jsonBody = new JSONObject();
				
				for(int j=0; j<userIDList.size(); j++) {
					
					System.out.println("check : " + userIDList.get(j));
					FcmBean fcmBean = null;
					fcmBean = fcmMapper.getByUserID(userIDList.get(j));
					 
					if(fcmBean == null) {
						System.out.println("fcmToken is NULL.");
						/**
						 * 추가처리 필요
						 */
					}
					else {
						
						if(fcmBean.getDeviceType()==Data.DEVICE_TYPE_ANDROID) {
							JSONObject data = new JSONObject();
							data.put("title", title);
							data.put("body", body);
							
							jsonBody.put("data", data);
							
						}else if(fcmBean.getDeviceType()==Data.DEVICE_TYPE_IOS) {
							JSONObject notification=new JSONObject();
							notification.put("title", title);
							notification.put("body", body);
							
							jsonBody.put("notification", notification);
							
						}
						
						tokenArray.put(fcmBean.getFcmToken());
					}
				}
				
				jsonBody.put("registration_ids", tokenArray);
				
				System.out.println("title : "+title+", body : "+body);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
				
				System.out.println("body : "+body.toString());
				HttpEntity<String> request = new HttpEntity<>(jsonBody.toString(), headers);

			
				CompletableFuture<String> pushNotification = PushNotificationService.send(request);
				CompletableFuture.allOf(pushNotification).join();
				
				try {
					String firebaseResponse = pushNotification.get();
					System.out.println("성공했다!! "+firebaseResponse);
//					result.put("responseCode", ResponseCode.SUCCESS);
//					return result;
				}catch(InterruptedException e) {
			
					e.printStackTrace();
//					result.put("responseCode", ResponseCode.FAILED_SERVER_ERROR);
//					return result;
				}catch(ExecutionException e) {
					e.printStackTrace();
//					result.put("responseCode", ResponseCode.FAILED_SERVER_ERROR);
//					return result;
				}
				
				/**
				 * changeStateToBegin() -> state가 0이면서 예배시간인걸 state를 1로 바꿈
				 */
				churchServiceMapper.changeStateToBegin();
			}
			
		}
	}
}
