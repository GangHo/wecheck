package org.afive.wecheck.article.controller;

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
@RequestMapping(value = "articles")
public class ArticleController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ConfirmRequestMapper confirmRequestMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
//	//테스트용	
//	@RequestMapping(value ="",method = RequestMethod.GET) 
//	private Map<String, Object> getList(){
//		Map result = new HashMap();
//		
//		return result;
//	}
	
}
