package org.afive.wecheck.churchService;

import java.util.List;

import org.afive.wecheck.common.CommonMapper;

public interface ChurchServiceMapper extends CommonMapper<ChurchServiceBean,String>{

//	public List<ChurchServiceBean> checkState();
	public void changeState();
//	public void changeStateToRunning(ChurchServiceBean churchServiceBean);
	public ChurchServiceBean getChurchService(String accessTokenID);
}
