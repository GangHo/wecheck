 package org.afive.wecheck.quote.mapper;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.quote.bean.QuoteBean;

public interface QuoteMapper extends CommonMapper<QuoteBean,String>{
	
	public QuoteBean getRandomQuote();
	
}
