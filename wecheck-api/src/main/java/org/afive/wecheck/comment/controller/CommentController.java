package org.afive.wecheck.comment.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.mapper.ArticleMapper;
import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.bean.CommentResult;
import org.afive.wecheck.comment.mapper.CommentMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.mapper.CommentLikeMapper;
import org.afive.wecheck.service.PushNotificationService;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.FcmBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.bean.UserResult;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.FcmMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "comments")
public class CommentController {
	

	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	ConfirmRequestMapper confirmRequestMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private FcmMapper fcmMapper;
	
	@RequestMapping(value ="/{articleID}/{parentID}/{lastItemID}/{size}/{sort}/{privacy}",method = RequestMethod.GET)
	private Map<String,Object> getComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID ,
			@PathVariable(value = "parentID") String parentID ,
			@PathVariable(value = "lastItemID") String lastItemIDStr ,
			@PathVariable(value = "size") String sizeStr ,
			@PathVariable(value = "sort") String sort ,
			@PathVariable(value = "privacy") String privacyStr) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		/*
		 * article에 대한 처리
		 */
		ArticleBean articleBean = articleMapper.get(articleID);
		if(articleBean == null) {
			System.out.println("댓글작성하려는데 articleID : " + articleID + "인 article이 없다");
			result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
			return result;
		}
		
		int articleState = articleBean.getState();
		if(articleState != Data.ARTICLE_STATE_DEFAULT) {
			System.out.println("댓글작성하려는데 articleID : " + articleID + "인 article은 삭제(state가 deleted)됨");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
		}
		
		/*
		 * 필요없으면 삭제
		 */
		/*-- 접근하는 유저가 승인된 사람인지 아닌지 --*/
		Integer userID = accessTokenBean.getUserID();
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
//		else {	// 승인받아 userID가 있는경우
//			result.put("isApproved",1);
//		}
		
		
		int lastItemID = Integer.parseInt(lastItemIDStr);
		int parentSize = Integer.parseInt(sizeStr);
		int privacy = Integer.parseInt(privacyStr);
		
		HashMap<String,Object> commentMap = new HashMap<String,Object>();
		commentMap.put("articleID", articleID);
		commentMap.put("parentID", parentID);
		commentMap.put("lastItemID", lastItemID);
		commentMap.put("sort",sort);
		commentMap.put("privacy",privacy);
		commentMap.put("size", parentSize);
		
		List<CommentBean> commentList = commentMapper.getListByArticleAndParent(commentMap);
		
		if(commentList.isEmpty()) {
			/*
			 * 댓글이 없는 경우일때 (따로 처리해서 바로 종료하게 할 수 있음.)
			 */
			//result.put("responseCode",ResponseCode.SUCCESS);

		}
		
		List<CommentResult> results = new ArrayList<>();
		HashMap<String,Object> commentCountMap = new HashMap<String,Object>();
		HashMap<String,Object> checkMap = new HashMap<String,Object>();
		
		for(int i=0; i<commentList.size(); i++) {
//			checkMap = null;
//			commentCountMap = null;
			
			CommentBean commentBean = commentList.get(i);
			UserBean commenterBean = userMapper.get(String.valueOf(commentBean.getUserID()));
//			if(commenterBean == null || commenterBean.getState() != 0) {
//				System.out.println("comment 불러오는데 작성자userBean이 NULL");
//				result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
//				return result;
//			}
			
			UserResult commenterResult = new UserResult();
			commenterResult.setUserID(commenterBean.getUserID());
			commenterResult.setUserType(commenterBean.getUserType());
			commenterResult.setState(commenterBean.getState());
			commenterResult.setLastName(commenterBean.getLastName());
			commenterResult.setFirstName(commenterBean.getFirstName());
			commenterResult.setProfileImage(commenterBean.getProfileImage());
			
			CommentResult commentResult = new CommentResult();
			commentResult.setComment(commentBean);
			commentResult.setCommenter(commenterResult);
			
			//댓글 좋아요 수
			String commentLikeCount = commentLikeMapper.getCountByCommentID(String.valueOf(commentBean.getCommentID()));
			commentResult.setLikeCount(commentLikeCount);
			
			commentCountMap.put("articleID", articleID);
			commentCountMap.put("parentID", String.valueOf(commentBean.getCommentID()));
			
			//대댓글 개수
			String commentCount = commentMapper.getCountByArticleAndParent(commentCountMap);
			if(commentCount == null) {
				commentCount = "0";
			}
			commentResult.setCommentCount(commentCount);
			
			//좋아요 눌럿는지 체크
			checkMap.put("userID", userID);
			checkMap.put("commentID", commentBean.getCommentID());
			
			Integer isChecked = commentLikeMapper.isCheckedByUserAndComment(checkMap);
			
			if(isChecked == null) {
				isChecked = 0;
			}
			commentResult.setLikeIsChecked(String.valueOf(isChecked));

			
			results.add(commentResult);
		}
		
		result.put("comments", results);
		result.put("responseCode",ResponseCode.SUCCESS);
		
		return result;
		
	}
	
	/**
	 * insert에 대한 POST요청
	 */
	@RequestMapping(value ="/insert",method = RequestMethod.POST)
	private Map<String,Object> setComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@RequestParam(value = "articleID") String articleID ,
			@RequestParam(value = "parentID") String parentID ,
			@RequestParam(value = "contents") String contents) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		/*
		 * article 상태에 대한 처리
		 */
		ArticleBean articleBean = articleMapper.get(articleID);
		if(articleBean == null) {
			System.out.println("댓글작성하려는데 articleID : " + articleID + "인 article이 없다");
			result.put("responseCode", ResponseCode.FAILED_NO_MATCH);
			return result;
		}
		
		int articleState = articleBean.getState();
		if(articleState != Data.ARTICLE_STATE_DEFAULT) {
			System.out.println("댓글작성하려는데 articleID : " + articleID + "인 article은 삭제(state가 deleted)됨");
			result.put("responseCode", ResponseCode.ARTICLE_STATE_DELETED);
			return result;
		}
		
		Integer userID = accessTokenBean.getUserID();
		
		CommentResult commentResult = new CommentResult();
		
		
		CommentBean commentBean = new CommentBean();
		commentBean.setUserID(userID);
		commentBean.setArticleID(Integer.parseInt(articleID));
		commentBean.setParentID(Integer.parseInt(parentID));
		commentBean.setContents(contents);
		
		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		commentBean.setRegisteredTime(localDateTime);
		commentBean.setLastEditedTime(localDateTime);

		commentResult.setComment(commentBean);
		
		UserBean commenterBean = userMapper.get(String.valueOf(commentBean.getUserID()));
		UserResult commenterResult = new UserResult();
		commenterResult.setUserID(commenterBean.getUserID());
		commenterResult.setUserType(commenterBean.getUserType());
		commenterResult.setLastName(commenterBean.getLastName());
		commenterResult.setFirstName(commenterBean.getFirstName());
		commenterResult.setProfileImage(commenterBean.getProfileImage());
		

		commentMapper.register(commentBean);
		commentResult.setCommenter(commenterResult);
		
		commentResult.setLikeIsChecked(String.valueOf(Data.COMMENT_STATE_DEFAULT));
		
		if(commentBean.getParentID() == 0) {
			//부모 댓글 개수
			String commentCount = commentMapper.getCountByArticleID(articleID);
			commentResult.setCommentCount(commentCount);
		}
		else {
			//대댓글 개수
			HashMap<String,Object> commentCountMap = new HashMap<String,Object>();
			commentCountMap.put("articleID", articleID);
			commentCountMap.put("parentID", parentID);
			//대댓글 개수
			String commentCount = commentMapper.getCountByArticleAndParent(commentCountMap);
			if(commentCount == null) {
				commentCount = "0";
			}
			commentResult.setCommentCount(commentCount);
		}
		
		commentResult.setLikeCount(String.valueOf(0));
		result.put("comment", commentResult);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		/*
		 * 이제 댓 푸시 날리는거 할것이다./////////////////////////////
		 * */
		
		if(articleBean.getUserID()!=userID) {
			UserBean userBean = userMapper.get(userID+"");
			FcmBean publisherFcmBean = fcmMapper.getByUserID(articleBean.getUserID()+"");
			
			JSONObject jsonBody = new JSONObject();
			
			String pushType = Data.PUSH_TYPE_NEW_COMMENT+"";
			String idx=articleBean.getArticleID()+"";
			String title = "댓글이 달렸어요";
			String body=userBean.getLastName()+" "+userBean.getFirstName()+"님께서 [";
			
			if(articleBean.getTitle()!=null && articleBean.getContents().length()>0) {
				if(articleBean.getTitle().length()>10) {
					body+=articleBean.getTitle().substring(0,  10)+"...] 글에 댓글을 달았습니다";
				}else {
					body+=articleBean.getTitle()+"] 글에 댓글을 달았습니다";
				}
			}else {
				if(articleBean.getContents().length()>10) {
					body+=articleBean.getContents().substring(0,  10)+"...] 글에 댓글을 달았습니다";
				}else {
					body+=articleBean.getContents()+"] 글에 댓글을 달았습니다";
				}
			}
			
			body+=" ["+commentBean.getContents()+"]";
			
			jsonBody.put("to", publisherFcmBean.getFcmToken());
			
			if(publisherFcmBean.getDeviceType() == Data.DEVICE_TYPE_ANDROID) {
				JSONObject data = new JSONObject();
				data.put("title", title);
				data.put("body", body);
				data.put("pushType", pushType);
				data.put("idx", idx);
				
				jsonBody.put("data", data);
			}else if(publisherFcmBean.getDeviceType()==Data.DEVICE_TYPE_IOS) {
				JSONObject notification=new JSONObject();
				notification.put("title", title);
				notification.put("body", body);
				notification.put("pushType", pushType);
				notification.put("idx", idx);
				
				jsonBody.put("notification", notification);
				
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
			
			System.out.println("body : "+body.toString());
			HttpEntity<String> request = new HttpEntity<>(jsonBody.toString(), headers);
	
		
			CompletableFuture<String> pushNotification = PushNotificationService.send(request);
			CompletableFuture.allOf(pushNotification).join();
		}
		/////////푸시 날리기 끝 ////////////////////////////////////
		
		
		return result;
	}
	
	/**
	 * update에 대한 POST요청
	 */
	@RequestMapping(value ="/update",method = RequestMethod.POST)
	private Map<String,Object> updateComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@RequestParam(value = "commentID") String commentID ,
			@RequestParam(value = "contents") String contents) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		int userID = accessTokenBean.getUserID();
		CommentBean parentBean = commentMapper.get(commentID);
		int commenterID = parentBean.getUserID();
		
		/**
		 *  접근자와 댓글 작성자가 같은지 판별
		 */
		if(commenterID != userID) {
			result.put("responseCode", ResponseCode.ACCESS_DENIED_USERID_DOESNT_MATCH);
			return result;
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("commentID",Integer.parseInt(commentID));
		map.put("contents", contents);
		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("lastEditedTime",localDateTime);
		
		commentMapper.updateComment(map);
		
		result.put("editedComment",map);
		result.put("resonseCode", ResponseCode.SUCCESS);
		return result;
	}
	
	/**
	 * delete에 대한 POST요청
	 */
	@RequestMapping(value ="/delete",method = RequestMethod.POST)
	private Map<String,Object> deleteComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@RequestParam(value = "commentID") String commentID ) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		 
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		int userID = accessTokenBean.getUserID();
		CommentBean parentBean = commentMapper.get(commentID);
		int commenterID = parentBean.getUserID();
		
		/**
		 *  접근자와 댓글 작성자가 같은지 판별
		 */
		if(commenterID != userID) {
			result.put("responseCode", ResponseCode.ACCESS_DENIED_USERID_DOESNT_MATCH);
			return result;
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("commentID", commentID);
		map.put("state",Data.COMMENT_STATE_DELETED);
		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("deletedTime",localDateTime);
		
		commentMapper.deleteComment(map);
		
		result.put("deletedComment",map);
		result.put("responseCode",ResponseCode.SUCCESS);
		return result;
	}
	
	/**
	 * 하나의 댓글에 대한 정보 가져오기
	 */
	@RequestMapping(value ="/{commentID}",method = RequestMethod.GET)
	private Map<String,Object> updateComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "commentID") String commentID ) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		CommentBean commentBean = commentMapper.get(commentID);
		
		Integer commenterID = commentBean.getUserID();
		UserBean commenterBean = userMapper.get(String.valueOf(commenterID));
		
		UserResult commenterResult = new UserResult();
		commenterResult.setUserID(commenterBean.getUserID());
		commenterResult.setUserType(commenterBean.getUserType());
		commenterResult.setLastName(commenterBean.getLastName());
		commenterResult.setFirstName(commenterBean.getFirstName());
		commenterResult.setProfileImage(commenterBean.getProfileImage());
		
		CommentResult commentResult = new CommentResult();
		
		commentResult.setComment(commentBean);
		commentResult.setCommenter(commenterResult);
		
		
		//댓글 좋아요 수
		String commentLikeCount = commentLikeMapper.getCountByCommentID(String.valueOf(commentBean.getCommentID()));
		commentResult.setLikeCount(commentLikeCount);;
		
		
		HashMap<String,Object> commentCountMap = new HashMap<String,Object>();
		
		commentCountMap.put("articleID", String.valueOf(commentBean.getArticleID()));
		commentCountMap.put("parentID", String.valueOf(commentBean.getCommentID()));
		//대댓글 개수
		String commentCount = commentMapper.getCountByArticleAndParent(commentCountMap);
		if(commentCount == null) {
			commentCount = "0";
		}
		commentResult.setCommentCount(commentCount);
		
		//좋아요 눌렀는지 체크
		HashMap<String,Object> checkMap = new HashMap<String,Object>();
		

		Integer userID = accessTokenBean.getUserID();
		
		checkMap.put("userID", userID);
		checkMap.put("commentID", commentBean.getCommentID());
		
		Integer isChecked = commentLikeMapper.isCheckedByUserAndComment(checkMap);
		
		if(isChecked == null) {
			isChecked = 0;
		}
		commentResult.setLikeIsChecked(String.valueOf(isChecked));
		
		result.put("comment", commentResult);
		result.put("responseCode", ResponseCode.SUCCESS);
		return result;
	}

}
