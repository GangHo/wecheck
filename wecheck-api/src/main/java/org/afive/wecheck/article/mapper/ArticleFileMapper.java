package org.afive.wecheck.article.mapper;

import java.util.List;

import org.afive.wecheck.article.bean.ArticleFileBean;
import org.afive.wecheck.common.CommonMapper;

public interface ArticleFileMapper extends CommonMapper<ArticleFileBean,String>{
	
	public List<ArticleFileBean> getByArticleID(String articleID);
}
