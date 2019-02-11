package org.afive.wecheck.comment.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.common.CommonMapper;

public interface CommentMapper extends CommonMapper<CommentBean,String>{
	
	public String getCountByArticleID(String articleID);
	public List<CommentBean> getListByArticleAndParent(HashMap<String,Object> map);
	public void updateComment(HashMap<String,Object> map);
	public void deleteComment(HashMap<String,Object> map);
	/**
	 * @return 댓글에대한 대댓글 수
	 */
	public String getCountByArticleAndParent(HashMap<String,Object> map);
 
	public CommentBean getRecentComment(String articleID);
}
