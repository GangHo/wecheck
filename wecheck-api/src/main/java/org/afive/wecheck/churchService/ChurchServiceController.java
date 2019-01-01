package org.afive.wecheck.churchService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.afive.wecheck.configuration.ResponseCode;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="churchService")
public class ChurchServiceController {
	
	@Autowired
	private ChurchServiceMapper churchServiceMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String,Object> getList(){
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("data",churchServiceMapper.getList());
		return result;
	}
	
	@RequestMapping(value="",method = RequestMethod.GET)
	private Map<String,Object> getChurchService(
			@RequestHeader("Authorization") String accessTokenID) {
		
		HashMap<String, Object> result = new HashMap<>();
		
		AccessTokenBean accessTokenBean = accessTokenMapper.get(accessTokenID);
		
		if(accessTokenBean==null) {
			result.put("responseCode", String.valueOf(ResponseCode.ACCESS_DENIED_WRONG_ACCESSCODE));
			return result;
		}
		
		ChurchServiceBean churchServiceBean = new ChurchServiceBean();
		
		churchServiceBean = churchServiceMapper.getChurchService(accessTokenID);
		
		result.put("churchService",churchServiceBean);
		result.put("responseCode",ResponseCode.SUCCESS);
		
		return result;
	}
}
