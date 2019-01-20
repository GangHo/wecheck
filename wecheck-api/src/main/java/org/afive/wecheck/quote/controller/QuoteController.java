package org.afive.wecheck.quote.controller;

import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.quote.bean.QuoteBean;
import org.afive.wecheck.quote.mapper.QuoteMapper;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "quotes")
public class QuoteController {

	@Autowired
	private QuoteMapper quoteMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
//	테스트용 전체출력
//	@RequestMapping(value = "/all", method = RequestMethod.GET)
//	public Map<String,Object> getList(){
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		result.put("data",quoteMapper.getList());
//		return result;
//	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String, Object> getRandomQuote(
			@RequestHeader("Authorization") String accessTokenID ) {
		
		
		Map<String,Object> result= new HashMap<String,Object>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		QuoteBean quoteBean =  new QuoteBean();
		quoteBean = quoteMapper.getRandomQuote();
		
		result.put("quote",quoteBean);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}
	
}
