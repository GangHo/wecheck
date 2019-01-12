package org.afive.wecheck.like.controller;

import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.bean.CommentLikeBean;
import org.afive.wecheck.like.mapper.CommentLikeMapper;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "like")
public class CommentLikeController {
	
	
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	
	@RequestMapping(value = "/comments/{commentID}" ,method = RequestMethod.POST)
	private Map<String,Object> updateLike(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "commentID") String commentID ) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}

		Integer userID = accessTokenBean.getUserID();
		
		if(userID == null) {
			System.out.println("userID 없는 사람이 commentID : "+commentID+" 에 Like시도");
			result.put("responseCode", ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE);
			return result;
		}

		CommentLikeBean commentLikeBean = new CommentLikeBean();
		commentLikeBean.setCommentID(Integer.parseInt(commentID));
		commentLikeBean.setUserID(userID);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userID",userID);
		map.put("commentID",commentID);
		
		Integer isChecked = commentLikeMapper.isCheckedByUserAndComment(map);
		
		if(isChecked == null) {
			commentLikeMapper.registerIsChecked(commentLikeBean);
		}
		else if(isChecked == 0) {
			commentLikeBean.setIsChecked(Data.COMMENTLIKE_CHECKED);
			commentLikeMapper.updateIsChecked(commentLikeBean);
		}
		else {
			commentLikeBean.setIsChecked(Data.COMMENTLIKE_DEFAULT);
			commentLikeMapper.updateIsChecked(commentLikeBean);
		}
		
		result.put("commentLike", commentLikeBean);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}

}
