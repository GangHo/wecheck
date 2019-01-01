package org.afive.wecheck.user.mapper;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.AccessTokenBean;
import org.afive.wecheck.user.bean.FcmBean;
import org.apache.ibatis.annotations.Param;

public interface AccessTokenMapper extends CommonMapper<AccessTokenBean, String>{
	

	/**
	 * @return
	 */
	public void register(AccessTokenBean accessTokenBean);
	public void registerWithUserID(AccessTokenBean accessTokenBean);
	public void registerWithConfirmRequestID(AccessTokenBean accessTokenBean);
	public void update(AccessTokenBean accessTokenBean);
	public void delete(AccessTokenBean accessTokenBean);
	public AccessTokenBean get(String accessTokenID);
}
