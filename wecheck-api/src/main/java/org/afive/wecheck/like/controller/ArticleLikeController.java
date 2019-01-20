package org.afive.wecheck.like.controller;

import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.mapper.ArticleMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.bean.ArticleLikeBean;
import org.afive.wecheck.like.bean.CommentLikeBean;
import org.afive.wecheck.like.mapper.ArticleLikeMapper;
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
public class ArticleLikeController {
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleLikeMapper articleLikeMapper;
	
	
	@RequestMapping(value = "/articles" ,method = RequestMethod.POST)
	private Map<String,Object> updateLike(
			@RequestHeader("Authorization") String accessTokenID ,
			@RequestParam(value = "articleID") String articleID ) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}

		Integer userID = accessTokenBean.getUserID();
		
		if(userID == null) {
			System.out.println("userID 없는 사람이 articleID : "+articleID+" 에 Like시도");
			result.put("responseCode", ResponseCode.ACCESS_DENIED_USERID_DOESNT_MATCH);
			return result;
		}
		
		/*
		 * article 상태에 대한 처리
		 */
		ArticleBean articleBean = articleMapper.get(articleID);
		if(articleBean == null) {
			System.out.println("articleLike 누르려는데 articleID : " + articleID + "인 article이 없다");
			result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
			return result;
		}
		
		int articleState = articleBean.getState();
		if(articleState != Data.ARTICLE_STATE_DEFAULT) {
			System.out.println("articleLike 누르려는데 articleID : " + articleID + "인 article은 삭제(state가 deleted)됨");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
			return result;
		}
		
		ArticleLikeBean articleLikeBean = new ArticleLikeBean();
		articleLikeBean.setArticleID(Integer.parseInt(articleID));
		articleLikeBean.setUserID(userID);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userID",userID);
		map.put("articleID",articleID);
		
		Integer isChecked = articleLikeMapper.isCheckedByUserAndArticle(map);
		
		if(isChecked == null) {
			articleLikeBean.setIsChecked(Data.ARTICLELIKE_CHECKED);
			articleLikeMapper.registerArticleLike(articleLikeBean);
		}
		else if(isChecked == 0) {
			articleLikeBean.setIsChecked(Data.ARTICLELIKE_CHECKED);
			articleLikeMapper.updateArticleLike(articleLikeBean);
		}
		else {
			articleLikeBean.setIsChecked(Data.ARTICLELIKE_DEFAULT);
			articleLikeMapper.updateArticleLike(articleLikeBean);
		}
		
		String likeCount = articleLikeMapper.getCountByArticleID(articleID);
		result.put("likeCount", likeCount);
		
		result.put("articleLike", articleLikeBean);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}
}
