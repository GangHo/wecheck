package org.afive.wecheck.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.res.FilePathResource;
import org.afive.wecheck.service.FileBean;
import org.afive.wecheck.service.FileService;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "users")
public class UserController {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Autowired
	private ConfirmRequestMapper confirmRequestMapper;
//	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	private Map<String, Object> getList(){
//		Map<String, Object> result = new HashMap<>();
//		result.put("data", userMapper.getList());
//		return result;
//	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST,consumes = { "multipart/form-data" }, headers = "content-type=application/x-www-form-urlencoded")
	private Map<String,Object> profileUpdate(
			@RequestHeader("Authorization") String accessTokenID,
			@RequestParam(value="profileImage", required=false)MultipartFile profileImageFile) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		Integer snsLoginID = accessTokenBean.getSnsLoginID();
		Integer userID = accessTokenBean.getUserID();
		
		/*
		 * snsLogin 한 경우
		 */
		if(snsLoginID!=null) {
			ConfirmRequestBean confirmRequestBean = confirmRequestMapper.getBySnsLoginID(String.valueOf(snsLoginID));
			
			FileService fs = new FileService();
			//바꾸려는 이미지를 보내줄때
			if(profileImageFile!=null) {
//				MultipartFile imageFile = profileImageFile;
				
//				FileService fs = new FileService();
				
				if(confirmRequestBean.getCrProfileImage() != null && confirmRequestBean.getCrProfileImage().length() > 0) {
					if(fs.fileDelete(confirmRequestBean.getCrProfileImage())) {
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
					}
				}

			} // 바꾸려는 이미지로 null 을 보낼 때
			else {
//				FileService fs = new FileService();
				
				if(confirmRequestBean.getCrProfileImage() != null && confirmRequestBean.getCrProfileImage().length() > 0) {
					if(fs.fileDelete(confirmRequestBean.getCrProfileImage())) {
						System.out.println("삭제 성공");
						result.put("profileImagePath", confirmRequestBean.getCrProfileImage());
						result.put("responseCode", ResponseCode.SUCCESS);
					} else {
						System.out.println("삭제 실패");
						result.put("profileImagePath", confirmRequestBean.getCrProfileImage());
						result.put("responseCode", ResponseCode.FAILED_FILE_UPLOAD);
					}
				}
				else {
					// 이미 프로필이 null인 상태에서 또 null로 바꿈
					result.put("profileImagePath", confirmRequestBean.getCrProfileImage());
					result.put("responseCode", ResponseCode.SUCCESS);
				}
			}
			
			
			try {
				FileBean fileBean = fs.fileUpload(profileImageFile, FilePathResource.PROFILE_IMAGE_PATH, String.valueOf(confirmRequestBean.getConfirmRequestID()));
				
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("confirmRequestID", String.valueOf(confirmRequestBean.getConfirmRequestID()));
				inputMap.put("crProfileImage", fileBean.getFilePath());
				confirmRequestMapper.updateImageFile(inputMap);
				
				confirmRequestBean.setCrProfileImage(fileBean.getFilePath());
				
				if(userID != null) {
					inputMap.put("userID", String.valueOf(userID));
					inputMap.put("profileImage", fileBean.getFilePath());
					
					userMapper.updateImageFile(inputMap);
				}
									
				result.put("profileImagePath", confirmRequestBean.getCrProfileImage());
				result.put("responseCode", ResponseCode.SUCCESS);
				
			}catch (NullPointerException e) {
				Map<String, String> inputMap = new HashMap<String, String>();
				inputMap.put("confirmRequestID", String.valueOf(confirmRequestBean.getConfirmRequestID()));
				inputMap.put("crProfileImage",null);
				confirmRequestMapper.updateImageFile(inputMap);
				
				confirmRequestBean.setCrProfileImage(null);
				
				if(userID != null) {
					inputMap.put("userID", String.valueOf(userID));
					inputMap.put("profileImage",null);
					
					userMapper.updateImageFile(inputMap);
				}
				
				result.put("profileImagePath", confirmRequestBean.getCrProfileImage());
				result.put("responseCode", ResponseCode.SUCCESS);
			}
			catch( Exception e) {
				
				e.printStackTrace();					
				result.put("responseCode", ResponseCode.FAILED_FILE_UPLOAD);
				new ResponseEntity<Object>(confirmRequestBean, HttpStatus.NO_CONTENT); 
			}
		}
		return result;
	}
//	
//	@RequestMapping(value = "/{userID}", method = RequestMethod.GET)
//	private UserBean get(@PathVariable(value = "userID") String userID){
//		return userMapper.get(userID);
//	}
//	
//	@RequestMapping(value = "", method = RequestMethod.PUT)
//	private void update(UserBean userBean){
//		
//		if(userBean.getUserType()==0) userBean.setUserType(1);
//		userMapper.update(userBean);
//
//	}
//	
	
//	@RequestMapping(value = "/{userID}", method = RequestMethod.DELETE)
//	private void delete(@PathVariable(value = "userID") String userID){
//		userMapper.delete(userID);
//	}
	
	
	
}
