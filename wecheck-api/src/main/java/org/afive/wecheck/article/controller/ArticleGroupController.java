package org.afive.wecheck.article.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.afive.wecheck.article.bean.MainGroupResult;
import org.afive.wecheck.article.mapper.ArticleGroupMapper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "groups")
public class ArticleGroupController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ArticleGroupMapper articleGroupMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	//테스트용	
	@RequestMapping(value ="",method = RequestMethod.GET) 
	private Map<String, Object> getMainGroupList(
			@RequestHeader("Authorization") String accessTokenID
			){
		Map<String, Object> result = new HashMap<String, Object>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		List<MainGroupResult> list = articleGroupMapper.getMainGroupList();
		
		result.put("groups", list);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}
	
	@RequestMapping(value ="/{articleGroupID}",method = RequestMethod.GET) 
	private Map<String, Object> getList(
			@RequestHeader("Authorization") String accessTokenID,
			@PathVariable(value = "articleGroupID") String articleGroupID
			){
		Map<String, Object> result = new HashMap<String, Object>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		
		
		
		
		return result;
	}
	
}
