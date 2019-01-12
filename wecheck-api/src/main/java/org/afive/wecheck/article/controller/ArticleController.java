package org.afive.wecheck.article.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.mapper.ArticleMapper;
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
@RequestMapping(value = "articles")
public class ArticleController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
//	//테스트용	
//	@RequestMapping(value ="",method = RequestMethod.GET) 
//	private Map<String, Object> getList(){
//		Map result = new HashMap();
//		
//		return result;
//	}
	
	@RequestMapping(value ="/{articleGroupID}/{pageNo}/{size}/{sort}/{privacy}/{regionID}/{unitID}",method = RequestMethod.GET) 
	private Map<String, Object> getList(
			@RequestHeader("Authorization") String accessTokenID,
			@PathVariable(value = "articleGroupID") String articleGroupIDstr,
			@PathVariable(value = "pageNo") String pageNoStr,
			@PathVariable(value = "size") String sizeStr,
			@PathVariable(value = "sort") String sortStr,
			@PathVariable(value = "privacy") String privacyStr,
			@PathVariable(value = "regionID") String regionID,
			@PathVariable(value = "unitID") String unitID
			){	
		Map<String, Object> result = new HashMap<String, Object>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		System.out.println("articleGroupID : "+articleGroupIDstr+", pageNo : "+pageNoStr+", size : "+sizeStr+", sortStr : "+sortStr+", privacyStr : "+privacyStr+", regionID : "+regionID+", unitID : "+unitID);
		
		int pageNo=Integer.parseInt(pageNoStr);
		int size = Integer.parseInt(sizeStr);
		int privacy = Integer.parseInt(privacyStr);
		int articleGroupID=Integer.parseInt(articleGroupIDstr);
		
		
		pageNo=((pageNo-1)*size);
		
		List<ArticleBean> articleList;
		
		System.out.println("넣는 값, privacy : "+privacy+", pageNo : "+pageNo+", size : "+size);
			
		//articleGroup으로 나눠져 있지 않
		if(articleGroupID==0) {
			
			articleList=articleMapper.getList(privacy, pageNo, size);
			
		//articleGroup으로 나눠져있음
		}else{
			articleList=articleMapper.getListFromArticleGroup(articleGroupID, privacy, pageNo, size);
		}
		
		result.put("articleList", articleList);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		
		
		
		
		return result;
	}
	
}
