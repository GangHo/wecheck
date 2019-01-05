package org.afive.wecheck.churchService;

import java.util.HashMap;
import java.util.List;

import org.afive.wecheck.common.CommonMapper;

public interface ChurchServiceMapper extends CommonMapper<ChurchServiceBean,String>{

	public List<ChurchServiceBean> getChurchServiceList();
	public void changeStateToBegin();
	public ChurchServiceBean getChurchService(String accessTokenID);
	public void changeStateToEnd();
}
