package org.afive.wecheck.user.mapper;

import java.util.Map;

import org.afive.wecheck.common.CommonMapper;
import org.afive.wecheck.user.bean.ConfirmRequestBean;
import org.afive.wecheck.user.bean.UserBean;

public interface ConfirmRequestMapper extends CommonMapper<ConfirmRequestBean, String> {

	/**
	 * SNS Login시 user정보 가져오기
	 * @return
	 */
	public ConfirmRequestBean getConfirmRequestBean(ConfirmRequestBean confirmRequestBean);
	
	/**
	 * @return
	 */
	public ConfirmRequestBean getBySnsLoginID(String snsLoginID);
	
	public void register(ConfirmRequestBean confirmRequestBean);
	
	/**
	 * 프로필사진 변경
	 * @return
	 */
	public void updateImageFile(Map<String, String> inputMap);
}
