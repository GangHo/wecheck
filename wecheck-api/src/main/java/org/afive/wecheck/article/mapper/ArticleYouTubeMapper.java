package org.afive.wecheck.article.mapper;

import java.util.List;

import org.afive.wecheck.article.bean.ArticleYouTubeBean;
import org.afive.wecheck.common.CommonMapper;

public interface ArticleYouTubeMapper extends CommonMapper<ArticleYouTubeBean,String>{
	
	public List<ArticleYouTubeBean> getListByArticleID(String articleID);

}
