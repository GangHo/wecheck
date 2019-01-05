package org.afive.wecheck.user.mapper;

import java.util.List;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.FcmBean;
import org.apache.ibatis.annotations.Param;

public interface FcmMapper extends CommonMapper<FcmBean, String>{
	

	/**
	 * @return
	 */
	public FcmBean getByDeviceTypeAndUuid(@Param("deviceType") int deviceType, @Param("uuid") String uuid);
	public FcmBean getBySnsLoginID(String snsLoginID);
	public void registerWithOutUserID(FcmBean fcmBean);
	public void register(FcmBean fcmBean);
	public void update(FcmBean fcmBean);
	public void deleteIfExists(FcmBean fcmBean);

	/**
	 * getByUserID by gangho
	 */
	public FcmBean getByUserID(String userID);
	
}
