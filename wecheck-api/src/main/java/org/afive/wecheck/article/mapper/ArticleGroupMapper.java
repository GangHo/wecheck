package org.afive.wecheck.article.mapper;

import java.util.List;
import java.util.Map;

import org.afive.wecheck.article.bean.ArticleBean;
import org.afive.wecheck.article.bean.MainGroupResult;
import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.FcmBean;
import org.apache.ibatis.annotations.Param;

public interface ArticleGroupMapper extends CommonMapper<ArticleBean, String>{
	

	/**
	 * @return
	 */
	public List<MainGroupResult>  getMainGroupList();
	
}
