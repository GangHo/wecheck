package org.afive.wecheck.like.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.mapper.CommentMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.bean.CommentLikeBean;
import org.afive.wecheck.like.mapper.CommentLikeMapper;
import org.afive.wecheck.service.PushNotificationService;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.FcmBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
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
@RequestMapping(value = "like")
public class CommentLikeController {
	
	
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	CommentMapper commemtMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private FcmMapper fcmMapper;
	
	
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
			isChecked = 0;
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
		
		/*
		 * 이제 좋아요 푸시 날리는거 할것이다./////////////////////////////
		 * */
		if(isChecked==0 && commentBean.getUserID()!=userID) {
			UserBean userBean = userMapper.get(userID+"");
			FcmBean publisherFcmBean = fcmMapper.getByUserID(commentBean.getUserID()+"");
			
			if(publisherFcmBean == null) {
				System.out.println("publisher fcmBean 을 불러오지 못함");
				result.put("responseCode", ResponseCode.SUCCESS);
				
				return result;
			}
			
			JSONObject jsonBody = new JSONObject();
			
			String pushType = Data.PUSH_TYPE_COMMENT_LIKE+"";
			
			String idx = null;
			if(commentBean.getParentID() == 0) {
				 idx = commentBean.getCommentID()+"";
			}
			else {
				 idx = commentBean.getParentID()+"";
			}
			String title = "좋아요가 눌렸어요 💕";
			String body=userBean.getLastName()+" "+userBean.getFirstName()+"님께서 [";
			
			if(commentBean.getContents().length()>10) {
				body+=commentBean.getContents().substring(0,  10)+"...] 댓글에 좋아요를 눌렀습니다";
			}else {
				body+=commentBean.getContents()+"] 댓글에 좋아요를 눌렀습니다";
			}
			
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
		
		
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}

}
