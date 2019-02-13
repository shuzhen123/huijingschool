package dianfan.service.mp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.mp.MpIndexMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.BannerInfo;
import dianfan.entities.IndexCourseBean;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName MpIndexService
 * @Description 微信公众号首页相关服务
 * @author cjy
 * @date 2018年1月26日 下午1:37:01
 */
@Service
public class MpIndexService {
	@Autowired
	private MpIndexMapper mpIndexMapper;

	/**
	 * @Title: findBannerList
	 * @Description: 轮播图列表
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月26日 下午1:38:14
	 */
	public List<BannerInfo> findBannerList() throws SQLExecutorException {
		List<BannerInfo> list = mpIndexMapper.findBannerList();
		if(list != null && list.size() > 0) {
			for(BannerInfo bi : list) {
				bi.setPicurl(ConstantIF.PROJECT + bi.getPicurl());
			}
		}else {
			list = null;
		}
		return list;
	}

	/**
	 * @Title: findCourseListByType
	 * @Description: 首页课程分类推荐列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月5日 上午9:43:20
	 */
	public List<IndexCourseBean> findCourseListByType() throws SQLExecutorException {
		List<IndexCourseBean> lists = mpIndexMapper.findCourseListByType();
		if(lists != null) {
			for(IndexCourseBean  icb : lists) {
				List<AppRecommendCourse> courses = icb.getCourses();
				for(AppRecommendCourse  arc : courses) {
					if(arc.getCoursepic() != null) {
						arc.setCoursepic(ConstantIF.PROJECT + arc.getCoursepic());
					}
				}
			}
		}
		return lists;
	}
	
	
}
