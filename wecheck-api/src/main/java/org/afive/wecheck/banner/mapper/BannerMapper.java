package org.afive.wecheck.banner.mapper;

import java.util.List;

import org.afive.wecheck.banner.bean.BannerBean;
import org.afive.wecheck.common.CommonMapper;

public interface BannerMapper extends CommonMapper<BannerBean,String> {

	public List<BannerBean> getBanners();
	public void changeStateToEnd();
}

