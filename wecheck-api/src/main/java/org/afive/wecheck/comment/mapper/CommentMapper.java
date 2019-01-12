package org.afive.wecheck.comment.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.comment.bean.CommentBean;
import org.afive.wecheck.common.CommonMapper;

public interface CommentMapper extends CommonMapper<CommentBean,String>{
	
	public String getTotalCountByArticleID(String articleID);
	public List<CommentBean> getParentListByArticleID(HashMap<String, Object> map);
	public List<CommentBean> getChildListByParentID(HashMap<String,Object> map);
	public void updateComment(HashMap<String,Object> map);
	public void deleteComment(HashMap<String,Object> map);
	/**
	 * parentID로 전체탐색중 // 검색속도 개선 방법 찾으면 수정 
	 * @param commentID
	 * @return 댓글에대한 대댓글 수
	 */
	public String getChildCountByParentID(String parentID);
 
}
