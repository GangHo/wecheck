package org.afive.wecheck.article.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.FcmBean;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends CommonMapper<ArticleBean, String>{
	

	/**
	 * @return
	 */
	public List<ArticleBean> getList(@Param("privacy") int privacy,@Param("lastItemID") int lastItemID, @Param("size") int size, @Param("sort") String sort);
	public List<ArticleBean> getListFromArticleGroup(@Param("articleGroupID") int articleGroupID,@Param("privacy") int privacy,@Param("lastItemID") int lastItemID, @Param("size") int size, @Param("sort") String sort);
	public ArticleBean get(String articleID);
	//	public FcmBean getBySnsLoginID(String snsLoginID);
//	public void registerWithOutUserID(FcmBean fcmBean);
//	public void register(FcmBean fcmBean);
//	public void update(FcmBean fcmBean);
//	public void deleteIfExists(FcmBean fcmBean);
	
}
