package dianfan.dao.mapper.mp;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.BannerInfo;
import dianfan.entities.IndexCourseBean;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName MpIndexMapper
 * @Description 微信公众号首页相关dao
 * @author cjy
 * @date 2018年1月26日 下午1:39:05
 */
@Repository
public interface MpIndexMapper {
	
	/**
	 * @Title: findBannerList
	 * @Description: 轮播图列表
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月26日 下午1:39:50
	 */
	@Select("select picurl, content, link from t_banner where entkbn = 0 order by createtime desc")
	List<BannerInfo> findBannerList() throws SQLExecutorException;

	/**
	 * @Title: findCourseListByType
	 * @Description: 首页课程分类推荐列表
	 * @throws:
	 * @time: 2018年2月5日 上午9:13:40
	 */
	List<IndexCourseBean> findCourseListByType() throws SQLExecutorException;
	
}
