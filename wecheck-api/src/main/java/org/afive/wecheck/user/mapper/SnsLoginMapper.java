package org.afive.wecheck.user.mapper;

import java.util.HashMap;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.SnsLoginBean;
import org.afive.wecheck.user.bean.UserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface SnsLoginMapper extends CommonMapper<SnsLoginBean, String> {

	
	/**
	 * SNS Login시 user정보 가져오기
	 * @return
	 */
	public SnsLoginBean getSnsLoginUserBean(HashMap<String, Object> map);
	
	public void register(SnsLoginBean snsLoginBean);
}
