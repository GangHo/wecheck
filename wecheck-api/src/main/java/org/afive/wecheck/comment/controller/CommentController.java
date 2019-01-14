package org.afive.wecheck.comment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.comment.bean.CommentResult;
import org.afive.wecheck.comment.mapper.CommentMapper;
import org.afive.wecheck.configuration.Data;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.like.mapper.CommentLikeMapper;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.bean.UserResult;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.afive.wecheck.user.mapper.ConfirmRequestMapper;
import org.afive.wecheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "article")
public class CommentController {
	

	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	ConfirmRequestMapper confirmRequestMapper;
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	CommentLikeMapper commentLikeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping(value ="/{articleID}/parents",method = RequestMethod.GET)
	private Map<String,Object> getParentComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID ,
			@RequestParam(value = "pageNo") String pageNoStr) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		/*
		 * 필요없으면 삭제
		 */
		/*-- 접근하는 유저가 승인된 사람인지 아닌지 --*/
		Integer userID = accessTokenBean.getUserID();
		if( userID == null || userID == 0) {
			Integer confirmRequestID = accessTokenBean.getConfirmRequestID();

			if( confirmRequestID == null || confirmRequestID == 0) {
				result.put("isApproved", null);
				result.put("responseCode", ResponseCode.COMFIRMREQUESTID_IS_NULL);
				return result;
			}
			else {
				ConfirmRequestBean crBean = confirmRequestMapper.get(Integer.toString(confirmRequestID));
				result.put("isApproved", crBean.getIsApproved());
				result.put("responseCode", ResponseCode.USER_IS_NOT_APPROVED);
				return result;
			}
		}
//		else {	// 승인받아 userID가 있는경우
//			result.put("isApproved",1);
//		}
		
		int pageNo=Integer.parseInt(pageNoStr);
		int start=((pageNo-1)* Data.COMMENT_PARENT_SIZE);
		
		HashMap<String,Object> commentMap = new HashMap<String,Object>();
		commentMap.put("articleID", articleID);
		commentMap.put("start", start);
		commentMap.put("size", Data.COMMENT_PARENT_SIZE);
		
		List<CommentBean> parentList = commentMapper.getParentListByArticleID(commentMap);
		System.out.println("parentList Size ->" + parentList.size());
		if(parentList.isEmpty()) {
			/*
			 * 댓글이 없는 경우일때
			 */
			result.put("responseCode",ResponseCode.SUCCESS);
		}
		
		List<CommentResult> results = new ArrayList<>();
		HashMap<String,Object> checkMap = new HashMap<String,Object>();
		
		for(int i=0; i<parentList.size(); i++) {
//			checkMap = null;
			CommentBean commentBean = parentList.get(i);
			UserBean commenterBean = userMapper.get(String.valueOf(commentBean.getUserID()));
			if(commenterBean == null) {
				System.out.println("comment 불러오는데 작성자userBean이 NULL");
				result.put("responseCode", String.valueOf(ResponseCode.FAILED_NO_MATCH));
				return result;
			}
			
			UserResult commenterResult = new UserResult();
			commenterResult.setUserID(commenterBean.getUserID());
			commenterResult.setUserType(commenterBean.getUserType());
			commenterResult.setLastName(commenterBean.getLastName());
			commenterResult.setFirstName(commenterBean.getFirstName());
			commenterResult.setProfileImage(commenterBean.getProfileImage());
			
			CommentResult commentResult = new CommentResult();
			commentResult.setCommentBean(commentBean);
			commentResult.setCommenterResult(commenterResult);
			
			String commentLikeCount = commentLikeMapper.getCountByCommentID(String.valueOf(commentBean.getCommentID()));
			commentResult.setCommentLikeCount(commentLikeCount);
			
			String commentCount = commentMapper.getChildCountByParentID(String.valueOf(commentBean.getCommentID()));
			if(commentCount == null) {
				commentCount = "0";
			}
			commentResult.setCommentCount(commentCount);
			
			
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
		
		return result;
		
	}
	
	/**
	 * insert에 대한 POST요청
	 */
	@RequestMapping(value ="/{articleID}/parents/insert",method = RequestMethod.POST)
	private Map<String,Object> setParentComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID ,
			@RequestParam("contents") String contents) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		Integer userID = accessTokenBean.getUserID();
		
		CommentBean commentBean = new CommentBean();
		commentBean.setUserID(userID);
		commentBean.setArticleID(Integer.parseInt(articleID));
		commentBean.setParentID(Data.COMMENT_PARENTID_DEFAULT);
		commentBean.setContents(contents);
		
		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		commentBean.setRegisteredTime(localDateTime);
		commentBean.setLastEditedTime(localDateTime);

		result.put("comment", commentBean);
		
		UserBean commenterBean = userMapper.get(String.valueOf(commentBean.getUserID()));
		UserResult commenterResult = new UserResult();
		commenterResult.setUserID(commenterBean.getUserID());
		commenterResult.setUserType(commenterBean.getUserType());
		commenterResult.setLastName(commenterBean.getLastName());
		commenterResult.setFirstName(commenterBean.getFirstName());
		commenterResult.setProfileImage(commenterBean.getProfileImage());
		

		commentMapper.register(commentBean);
		result.put("commenter", commenterResult);
		result.put("resonseCode", ResponseCode.SUCCESS);
		
		return result;
	}
	
	/**
	 * update에 대한 POST요청
	 */
	@RequestMapping(value ="/{articleID}/parents/{parentID}/update",method = RequestMethod.POST)
	private Map<String,Object> updateParentComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID ,
			@PathVariable(value = "parentID") String parentID ,
			@RequestParam("contents") String contents) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		int userID = accessTokenBean.getUserID();
		CommentBean parentBean = commentMapper.get(parentID);
		int commenterID = parentBean.getUserID();
		
		/**
		 *  접근자와 댓글 작성자가 같은지 판별
		 */
		if(commenterID != userID) {
			result.put("responseCode", ResponseCode.ACCESS_DENIED_USERID_DOESNT_MATCH);
			return result;
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("commentID",Integer.parseInt(parentID));
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
	@RequestMapping(value ="/{articleID}/parents/{parentID}/delete",method = RequestMethod.POST)
	private Map<String,Object> deleteParentComment(
			@RequestHeader("Authorization") String accessTokenID ,
			@PathVariable(value = "articleID") String articleID ,
			@PathVariable(value = "parentID") String parentID ,
			@RequestParam("contents") String contents) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		 
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		int userID = accessTokenBean.getUserID();
		CommentBean parentBean = commentMapper.get(parentID);
		int commenterID = parentBean.getUserID();
		
		/**
		 *  접근자와 댓글 작성자가 같은지 판별
		 */
		if(commenterID != userID) {
			result.put("responseCode", ResponseCode.ACCESS_DENIED_USERID_DOESNT_MATCH);
			return result;
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("commentID", parentID);
		map.put("state",Data.COMMENT_STATE_DELETED);
		String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("deletedTime",localDateTime);
		
		commentMapper.deleteComment(map);
		
		result.put("deletedComment",map);
		result.put("responseCode",ResponseCode.SUCCESS);
		return result;
	}

}
