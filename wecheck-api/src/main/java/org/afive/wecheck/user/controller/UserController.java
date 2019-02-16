package org.afive.wecheck.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	
//	@RequestMapping(value = "/profile", method = RequestMethod.POST,consumes = { "multipart/form-data" }, headers = "content-type=application/x-www-form-urlencoded")
//	private Map<String,Object> profileUpdate(
//			@RequestHeader("Authorization") String accessTokenID,
//			@RequestParam(value="profileImage", required=false)MultipartFile profileImageFile) {
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		/*-- accessToken Data --*/
//		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
//		if(accessTokenBean==null) {
//			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
//			return result;
//		}
//		
//		Integer snsLoginID = accessTokenBean.getSnsLoginID();
//		Integer userID = accessTokenBean.getUserID();
//		
//		MultipartFile imageFile = profileImageFile;
//		
//		if(snsLoginID!=null && userID == null) {
//			ConfirmRequestBean confirmRequestBean = confirmRequestMapper.getBySnsLoginID(String.valueOf(snsLoginID));
//			if(confirmRequestBean.getIsApproved() != 1) {
//				
//				Map<String, String> inputMap = new HashMap<String, String>();
//				inputMap.put("confirmRequestID", String.valueOf(confirmRequestBean.getConfirmRequestID()));
//				inputMap.put("crProfileImage", confirmRequestBean.getCrProfileImage());
//
//				
//				confirmRequestMapper.updateImageFile(inputMap);
//				confirmRequestBean.setCrProfileImage();
//			}
//			
//		}		
//		
//		
//		return result;
//		
//	}
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
