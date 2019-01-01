package org.afive.wecheck.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.res.FilePathResource;
import org.afive.wecheck.service.FileBean;
import org.afive.wecheck.service.FileService;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "requests")
public class ConfirmRequestController {
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Autowired
	private ConfirmRequestMapper confirmRequestMapper;
	
	@RequestMapping(value="", method = RequestMethod.POST, consumes = { "multipart/form-data" }, headers = "content-type=application/x-www-form-urlencoded")
	private Map<String, Object> register(
					@RequestHeader("Authorization") String accessToken, 
					@RequestParam("firstName")String firstName,
					@RequestParam("lastName")String lastName,
					@RequestParam("gender")String intGender,
					@RequestParam("regionID")String intRegionID,
					@RequestParam("unitID")String intUnitID,
					@RequestParam("birthDay")String birthDay,
					@RequestParam("profileImage")MultipartFile profileImageFile
					) {
				
		int gender= Integer.parseInt(intGender);
		int regionID=Integer.parseInt(intRegionID);
		int unitID = Integer.parseInt(intUnitID);
		
		Map<String, Object> result=new HashMap<String, Object>();
		System.out.println("result 객체 생성..");
		
		System.out.println("넣어둔 accessToken : "+accessToken);
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessToken);
		
		System.out.println("accessToken가져왔다.");
		
		//잘못된 accessToken!
		if(accessTokenBean==null) {
			System.out.println("accessToken null이다.");
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		System.out.println("accessToken 잘 있다."+accessTokenBean.getAccessTokenID());
		
		ConfirmRequestBean confirmRequestBean=new ConfirmRequestBean(accessTokenBean.getSnsLoginID(), firstName, lastName, gender, regionID, unitID, birthDay);
		
		System.out.println("confirmRequest 객체 생성..!");
		
		confirmRequestMapper.register(confirmRequestBean);
		
		System.out.println("confirmRequest 등록했다, 아이디 : "+confirmRequestBean.getConfirmRequestID());
		
		MultipartFile imageFile = profileImageFile;
		
		if(!imageFile.isEmpty()) {
			
			System.out.println("imageFile들어있다.");
			
			System.out.println("파일명 : " + imageFile.getOriginalFilename());
			FileService fs = new FileService();
			try {
				FileBean fileBean = fs.fileUpload(imageFile, FilePathResource.PROFILE_IMAGE_PATH, String.valueOf(confirmRequestBean.getConfirmRequestID()));
				
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("confirmRequestID", String.valueOf(confirmRequestBean.getConfirmRequestID()));
				inputMap.put("crProfileImage", fileBean.getFilePath());

				
				confirmRequestMapper.updateImageFile(inputMap);
				confirmRequestBean.setCrProfileImage("http://www.we-check.org"+fileBean.getFilePath());
				
				result.put("confirmRequest", confirmRequestBean);
		
				result.put("responseCode", ResponseCode.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				
				result.put("confirmRequest", confirmRequestBean);
				
				result.put("responseCode", ResponseCode.FAILED_FILE_UPLOAD);
				
				new ResponseEntity<Object>(confirmRequestBean, HttpStatus.NO_CONTENT); 
			}
			
		}else {
			result.put("responseCode", result.put("responseCode", ResponseCode.FAILED_FILE_NOT_FOUND));
		}
		
		
		return result;
	}
	
	
	
}
