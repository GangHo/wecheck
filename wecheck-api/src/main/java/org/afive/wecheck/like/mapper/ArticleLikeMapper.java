package org.afive.wecheck.like.mapper;

import java.util.HashMap;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.like.bean.ArticleLikeBean;

public interface ArticleLikeMapper extends CommonMapper<ArticleLikeBean,String>{
	
	public String getCountByArticleID(int articleID);
	public Integer isCheckedByUserAndArticle(HashMap<String,Object> map);
	public void registerArticleLike(ArticleLikeBean articleLikeBean);
	public void updateArticleLike(ArticleLikeBean articleLikeBean);
}
