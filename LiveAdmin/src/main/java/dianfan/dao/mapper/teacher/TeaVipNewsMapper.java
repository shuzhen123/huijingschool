package dianfan.dao.mapper.teacher;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName VipNewsMapper
 * @Description VIP咨讯dao
 * @author cjy
 * @date 2018年2月26日 下午5:42:47
 */
@Repository
public interface TeaVipNewsMapper {

	/**
	 * @Title: findNewsCount
	 * @Description: 根据条件查找咨讯总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午10:54:57
	 */
	int findNewsCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findNews
	 * @Description: 根据条件查找咨讯列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午10:56:09
	 */
	List<VipInfomation> findNews(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addVipNewsInfo
	 * @Description: 添加咨讯数据
	 * @param info
	 * @throws:
	 * @time: 2018年1月11日 下午12:58:30
	 */
	void addVipNewsInfo(VipInfomation info) throws SQLExecutorException;

	/**
	 * @Title: delVipNewsByIds
	 * @Description: 根据id删除咨讯数据
	 * @param ids
	 * @throws:
	 * @time: 2018年1月11日 下午1:57:38
	 */
	void delVipNewsByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: findNewsInfoById
	 * @Description: 获取咨讯数据
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午2:21:57
	 */
	@Select("select * from t_vipstrategy where articalid = #{id}")
	VipInfomation findNewsInfoById(String id) throws SQLExecutorException;

	/**
	 * @Title: editNewsInfo
	 * @Description: 修改咨讯数据
	 * @param info
	 * @throws:
	 * @time: 2018年1月11日 下午2:44:25
	 */
	void editVipNewsInfo(VipInfomation info) throws SQLExecutorException;

	

	
	
	
}