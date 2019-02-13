package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.BannerInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminSelfMapper
 * @Description 轮播图dao
 * @author cjy
 * @date 2018年1月9日 下午3:48:14
 */
@Repository
public interface BannerMapper {

	/**
	 * @Title: findBannerCount
	 * @Description: 根据条件获取轮播图总数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月25日 下午5:30:30
	 */
	@Select("select count(*) from t_banner where entkbn = 0")
	int findBannerCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findBanners
	 * @Description: 根据条件获取轮播图数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月25日 下午5:31:44
	 */
	@Select("select * from t_banner where entkbn = 0 order by createtime desc limit #{start}, #{length}")
	List<BannerInfo> findBanners(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addBanner
	 * @Description: 添加轮播图
	 * @param bi
	 * @throws:
	 * @time: 2018年1月26日 上午10:23:06
	 */
	@Insert("insert into t_banner (id, picurl, content, link, createtime, entkbn) values (#{id}, #{picurl}, #{content}, #{link}, now(), 0)")
	void addBanner(BannerInfo bi) throws SQLExecutorException;

	/**
	 * @Title: delBannerByIds
	 * @Description: 根据轮播图id批量删除轮播图
	 * @param lids
	 * @throws:
	 * @time: 2018年1月26日 上午11:22:03
	 */
	void delBannerByIds(List<String> lids);

	/**
	 * @Title: editBanner
	 * @Description: 修改轮播图
	 * @param bi
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月26日 上午11:49:58
	 */
	void editBanner(BannerInfo bi) throws SQLExecutorException;

	/**
	 * @Title: getBannerInfo
	 * @Description: 根据id获取图文详情
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 下午1:02:00
	 */
	@Select("select * from t_banner where id = #{id}")
	BannerInfo getBannerInfo(String id) throws SQLExecutorException;

	
	
}