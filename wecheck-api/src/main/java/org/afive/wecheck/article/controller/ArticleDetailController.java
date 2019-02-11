package org.afive.wecheck.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.bean.ArticleFileBean;
import org.afive.wecheck.article.bean.ArticleGroupBean;
import org.afive.wecheck.article.bean.ArticleYouTubeBean;
import org.afive.wecheck.article.mapper.ArticleFileMapper;
import org.afive.wecheck.article.mapper.ArticleGroupMapper;
import org.afive.wecheck.article.mapper.ArticleMapper;
import org.afive.wecheck.article.mapper.ArticleYouTubeMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "articles")
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
	
	@Autowired
	ArticleYouTubeMapper articleYouTubeMapper;

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
			return result;
		}
		
		int articleState = articleBean.getState();
		if(articleState != Data.ARTICLE_STATE_DEFAULT) {
			System.out.println("article 누르려는데 articleID : " + articleID + "인 article state가 default가 아님");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
			return result;
		}
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		Integer userID = accessTokenBean.getUserID();
		/*
		 * 승인안된 사람 처리 API 에서 할 경우 주석 해제
		 */
//		/*-- 접근하는 유저가 승인된 사람인지 아닌지 --*/
//		
//		if( userID == null || userID == 0) {
//			Integer confirmRequestID = accessTokenBean.getConfirmRequestID();
//
//			if( confirmRequestID == null || confirmRequestID == 0) {
//				result.put("isApproved", null);
//				result.put("responseCode", ResponseCode.COMFIRMREQUESTID_IS_NULL);
//				return result;
//			}
//			else {
//				ConfirmRequestBean crBean = confirmRequestMapper.get(Integer.toString(confirmRequestID));
//				result.put("isApproved", crBean.getIsApproved());
//				result.put("responseCode", ResponseCode.USER_IS_NOT_APPROVED);
//				return result;
//			}
//		}
////	else {	// 승인받아 userID가 있는경우
////		result.put("isApproved",1);
////	}
		
		//게시글 정보 넣음
		result.put("article", articleBean);

		HashMap<String,Object> map = new HashMap<>();
		map.put("userID",userID);
		map.put("articleID",articleBean.getArticleID());
		
		Integer isChecked = articleLikeMapper.isCheckedByUserAndArticle(map);
		if(isChecked == null) {
			isChecked = 0;
		}
		result.put("likeIsChecked",isChecked);
//		
//		//return 값으로 group정보까지 넘길 경우 주석 해제
////		int articleGroupID = articleBean.getArticleGroupID();
////		ArticleGroupBean articleGroupBean = articleGroupMapper.get(String.valueOf(articleGroupID));
////		result.put("articleGroupID", articleGroupID);
////		result.put("articleGroupName", articleGroupBean.getName());
		

		UserBean publisherBean = userMapper.get(String.valueOf(articleBean.getUserID()));

		if(publisherBean == null) {
			System.out.println("article 불러오는데 작성자userBean이 NULL");
			result.put("responseCode", String.valueOf(ResponseCode.FAILED_NO_MATCH));
			return result;
		}
		/**
		 * ID, TYPE, LASTNAME,FIRSTNAME, PROFILEIMAGE 만 불러오는 form
		 */
		UserResult publisherResult = new UserResult();
		publisherResult.setUserID(publisherBean.getUserID());
		publisherResult.setUserType(publisherBean.getUserType());
		publisherResult.setState(publisherBean.getState());
		publisherResult.setLastName(publisherBean.getLastName()); 
		publisherResult.setFirstName(publisherBean.getFirstName());
		publisherResult.setProfileImage(publisherBean.getProfileImage());

		result.put("publisher", publisherResult); 
//		result.put("user",userBean); //전체 불러올 경우
		
		/**
		 * articleFile 불러올때 불러오는 순서 ( PK 오름차순으로 불러오는중 )
		 */
		List<ArticleFileBean> articleFiles = articleFileMapper.getByArticleID(String.valueOf(articleBean.getArticleID()));
		if(articleFiles.isEmpty()) {
			System.out.println("no file");
			/**
			 * 업로드파일없는 게시물일 경우
			 */
		}
		
		result.put("articleFiles",articleFiles);
		
		List<ArticleYouTubeBean> articleYoutubes = articleYouTubeMapper.getListByArticleID(articleID);
		result.put("articleYouTubes", articleYoutubes);
		
		
		String articleLikeCount = articleLikeMapper.getCountByArticleID(String.valueOf(articleBean.getArticleID()));
		result.put("likeCount",articleLikeCount);

		String commentCount = commentMapper.getCountByArticleID(articleID);
		result.put("commentCount",commentCount);
		
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
		
	}
}
