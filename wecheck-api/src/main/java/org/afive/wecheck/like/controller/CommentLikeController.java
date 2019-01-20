package org.afive.wecheck.like.controller;

import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.mapper.CommentMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "like")
public class CommentLikeController {
	
	
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	CommentMapper commemtMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	
	@RequestMapping(value = "/comments" ,method = RequestMethod.POST)
	private Map<String,Object> updateLike(
			@RequestHeader("Authorization") String accessTokenID ,
			@RequestParam(value = "commentID") String commentID ) {
		
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
		
		
		CommentBean commentBean = commemtMapper.get(commentID);
		if(commentBean == null) {
			System.out.println("commentLike 누르려는데commentID : " + commentID + "인 comment가 없다");
			result.put("responseCode", ResponseCode.COMMENT_STATE_DELETED);
			return result;
		}
		
		int commentState = commentBean.getState();
		if(commentState != Data.COMMENT_STATE_DEFAULT) {
			System.out.println("articleLike 누르려는데 articleID : " + commentID + "인 article은 state 가 default가 아님");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
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
			commentLikeBean.setIsChecked(Data.COMMENTLIKE_CHECKED);
			commentLikeMapper.registerCommentLike(commentLikeBean);
		}
		else if(isChecked == 0) {
			commentLikeBean.setIsChecked(Data.COMMENTLIKE_CHECKED);
			commentLikeMapper.updateCommentLike(commentLikeBean);
		}
		else {
			commentLikeBean.setIsChecked(Data.COMMENTLIKE_DEFAULT);
			commentLikeMapper.updateCommentLike(commentLikeBean);
		}
		
		String likeCount = commentLikeMapper.getCountByCommentID(commentID);
		result.put("likeCount", likeCount);
		
		result.put("commentLike", commentLikeBean);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}

}
