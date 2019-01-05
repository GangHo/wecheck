package org.afive.wecheck.user.mapper;


import java.util.HashMap;
import java.util.List;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.UserBean;

public interface UserMapper extends CommonMapper<UserBean, String> {
	
	/**
	 * SNS Login시 user정보 가져오기
	 * @return
	 */
	public UserBean getLoginUserBean(UserBean userLoginBean);
	public List<String> getIdByRegionAndUnit(HashMap<String,Object> map);
//	public UserBean getUserByToken(String accessToeknID);
}
