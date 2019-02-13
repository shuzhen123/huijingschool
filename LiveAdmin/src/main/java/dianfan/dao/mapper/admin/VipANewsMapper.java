package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName VipNewsMapper
 * @Description vip咨讯dao
 * @author cjy
 * @date 2018年2月27日 下午1:39:16
 */
@Repository
public interface VipANewsMapper {
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
	 * @Title: checkVipNews
	 * @Description: vip咨讯审核
	 * @param checkMap
	 * @throws:
	 * @time: 2018年2月27日 下午2:51:48
	 */
	void checkVipNews(Map<String, Object> checkMap) throws SQLExecutorException;
}