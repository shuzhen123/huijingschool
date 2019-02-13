package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.BannerMapper;
import dianfan.entities.BannerInfo;
import dianfan.entities.DataTable;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

@Service
public class BannerService {

	@Autowired
	private BannerMapper bannerMapper;
	
	/**
	 * @Title: findBanners
	 * @Description: 获取轮播图列表
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月25日 下午5:28:32
	 */
	public DataTable findBanners(String search, int start, int length) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = bannerMapper.findBannerCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<BannerInfo> banners = bannerMapper.findBanners(param);
		
		//设置图片地址
		for(BannerInfo bi : banners) {
			bi.setPicurl(ConstantIF.PROJECT + bi.getPicurl());
		}
		
		//设置数据
		dt.setData(banners);
		return dt;
	}

	/**
	 * @Title: addBanner
	 * @Description: 添加轮播图
	 * @param bi
	 * @throws:
	 * @time: 2018年1月26日 上午10:11:52
	 */
	@Transactional
	public void addBanner(BannerInfo bi) throws SQLExecutorException {
		bi.setId(UUIDUtil.getUUID());
		bannerMapper.addBanner(bi);
	}
	
	/**
	 * @Title: editBanner
	 * @Description: 修改轮播图
	 * @param bi
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月26日 上午11:49:17
	 */
	@Transactional
	public void editBanner(BannerInfo bi) throws SQLExecutorException {
		bannerMapper.editBanner(bi);
	}


	/**
	 * @Title: delBanner
	 * @Description: 根据轮播图id批量删除轮播图
	 * @param lids
	 * @throws:
	 * @time: 2018年1月26日 上午11:21:04
	 */
	@Transactional
	public void delBanner(List<String> lids) throws SQLExecutorException {
		bannerMapper.delBannerByIds(lids);
	}

	/**
	 * @Title: getBannerInfo
	 * @Description: 获取图文详情
	 * @param id
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月26日 下午1:01:09
	 */
	public BannerInfo getBannerInfo(String id) throws SQLExecutorException {
		BannerInfo bannerInfo = bannerMapper.getBannerInfo(id);
		bannerInfo.setPicurl(ConstantIF.PROJECT + bannerInfo.getPicurl());
		return bannerInfo;
	}

	
}
