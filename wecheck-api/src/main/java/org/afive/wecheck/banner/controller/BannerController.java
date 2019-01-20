package org.afive.wecheck.banner.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.banner.bean.BannerBean;
import org.afive.wecheck.banner.mapper.BannerMapper;
import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ( value ="banners")
public class BannerController {

	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	BannerMapper bannerMapper;
	
	@RequestMapping(value ="",method = RequestMethod.GET)
	private Map<String,Object> getBanners(
			@RequestHeader("Authorization") String accessTokenID) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		/*-- accessToken Data --*/
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		
		List<BannerBean> bannerList = bannerMapper.getBanners();
		
		result.put("banners", bannerList);
		result.put("responseCode", ResponseCode.SUCCESS);
		
		return result;
	}
	
}
