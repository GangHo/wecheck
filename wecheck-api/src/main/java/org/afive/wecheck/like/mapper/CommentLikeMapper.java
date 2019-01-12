package org.afive.wecheck.like.mapper;

import java.util.HashMap;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.like.bean.CommentLikeBean;

public interface CommentLikeMapper extends CommonMapper<CommentLikeBean,String>{
	
	public String getCountByCommentID(String commentID);
	public Integer isCheckedByUserAndComment(HashMap<String,Object> map);
	public void registerIsChecked(CommentLikeBean commentLikeBean);
	public void updateIsChecked(CommentLikeBean commentLikeBean);
}
