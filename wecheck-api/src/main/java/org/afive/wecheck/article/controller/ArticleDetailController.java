package org.afive.wecheck.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.bean.ArticleFileBean;
import org.afive.wecheck.article.bean.ArticleGroupBean;
import org.afive.wecheck.article.mapper.ArticleFileMapper;
import org.afive.wecheck.article.mapper.ArticleGroupMapper;
import org.afive.wecheck.article.mapper.ArticleMapper;
import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.mapper.CommentMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.bean.ArticleLikeBean;
import org.afive.wecheck.like.mapper.ArticleLikeMapper;
import org.afive.wecheck.like.mapper.CommentLikeMapper;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.bean.UserResult;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "article")
public class ArticleDetailController {
	
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	ConfirmRequestMapper confirmRequestMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleGroupMapper articleGroupMapper;
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	ArticleLikeMapper articleLikeMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	@Autowired
	ArticleFileMapper articleFileMapper;

	@RequestMapping(value ="/{articleID}",method = RequestMethod.GET)
	private Map<String,Object> getArticle(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*
		 * article 상태에 대한 처리
		 */
		ArticleBean articleBean = articleMapper.get(articleID);
		if(articleBean == null) {
			System.out.println("article누르려는데 articleID : " + articleID + "인 article이 없다");
			result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
		}
		
		int articleState = articleBean.getState();
		if(articleState == Data.ARTICLE_STATE_DELETED) {
			System.out.println("article 누르려는데 articleID : " + articleID + "인 article은 삭제(state가 deleted)됨");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
		}

		result.put("article", articleBean);
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		/*-- 접근하는 유저가 승인된 사람인지 아닌지 --*/
		Integer userID = accessTokenBean.getUserID();
		if( userID == null || userID == 0) {
			Integer confirmRequestID = accessTokenBean.getConfirmRequestID();

			if( confirmRequestID == null || confirmRequestID == 0) {
				result.put("isApproved", null);
			}
			else {
				ConfirmRequestBean crBean = confirmRequestMapper.get(Integer.toString(confirmRequestID));
				result.put("isApproved", crBean.getIsApproved());
			}
		}
		else {
			result.put("isApproved",1);
		}

		HashMap<String,Object> map = new HashMap<>();
		map.put("userID",userID);
		map.put("articleID",articleBean.getArticleID());
		
		Integer isChecked = articleLikeMapper.isCheckedByUserAndArticle(map);
		if(isChecked == null) {
			isChecked = 0;
		}
		result.put("likeIsChecked",isChecked);
		

		int articleGroupID = articleBean.getArticleGroupID();
		ArticleGroupBean articleGroupBean = articleGroupMapper.get(String.valueOf(articleGroupID));
		
//		result.put("articleGroupID", articleGroupID);
		result.put("articleGroupName", articleGroupBean.getName());
		

		UserBean publisherBean = userMapper.get(String.valueOf(articleBean.getUserID()));
		if(publisherBean == null) {
			System.out.println("article 불러오는데 작성자userBean이 NULL");
			result.put("responseCode", String.valueOf(ResponseCode.FAILED_NO_MATCH));
			return result;
		}
		/**
		 * ID, TYPE, LASTNAME,FIRSTNAME, PROFILEIMAGE 만 불러오는 form
		 */
		UserResult userResult = new UserResult();
		userResult.setUserID(publisherBean.getUserID());
		userResult.setUserType(publisherBean.getUserType());
		userResult.setLastName(publisherBean.getLastName());
		userResult.setFirstName(publisherBean.getFirstName());
		userResult.setProfileImage(publisherBean.getProfileImage());

		result.put("publisher", userResult); 
//		result.put("user",userBean); //전체 불러올 경우
		
		/**
		 * articleFile 불러올때 불러오는 순서에 대한 처리 ( -mapper.xml 에서 그냥 불러오고있음)
		 */
		List<ArticleFileBean> articleFiles = articleFileMapper.getByArticleID(String.valueOf(articleBean.getArticleID()));
		if(articleFiles.isEmpty()) {
			System.out.println("no file");
			/**
			 * 업로드파일없는 게시물일 경우
			 */
		}
		
		result.put("articleFilePath",articleFiles);
		
		String articleLikeCount = articleLikeMapper.getCountByArticleID(articleBean.getArticleID());
		result.put("articleLikeCount",articleLikeCount);

		String commentCount = commentMapper.getTotalCountByArticleID(articleID);
		result.put("commentCount",commentCount);
		
		return result;
		
	}
}
