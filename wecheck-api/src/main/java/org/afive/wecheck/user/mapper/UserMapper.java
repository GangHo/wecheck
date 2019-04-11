package org.afive.wecheck.user.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.bean.UserResult;

public interface UserMapper extends CommonMapper<UserBean, String> {
	
	/**
	 * SNS Login시 user정보 가져오기
	 * @return
	 */
	public UserBean getLoginUserBean(UserBean userLoginBean);
	public List<String> getIdByRegionAndUnit(HashMap<String,Object> map);
	public UserResult getUserResult(String userID);
	
	public void updateImageFile(Map<String, String> inputMap);
}
