package dianfan.dao.mapper.teacher;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Infomation;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName TeaNewsPointMapper
 * @Description 牛人观点dao
 * @author cjy
 * @date 2018年3月28日 上午9:43:21
 */
@Repository
public interface TeaNewsPointMapper {
	
	/**
	 * @Title: findViewpointCount
	 * @Description: 根据条件获取观点数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午9:57:00
	 */
	int findViewpointCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findViewpoint
	 * @Description: 根据条件获取观点数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午9:58:27
	 */
	List<Infomation> findViewpoint(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: addNewsInfo
	 * @Description: 添加观点数据
	 * @param info
	 * @throws:
	 * @time: 2018年1月11日 下午12:58:30
	 */
	void addNewsPointInfo(Infomation info) throws SQLExecutorException;
	
	/**
	 * @Title: findNewsInfoById
	 * @Description: 获取咨讯数据
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午2:21:57
	 */
	@Select("select id, infomationtitle, content, descrption from t_infomation where id = #{id}")
	Infomation findNewsPointInfoById(String id) throws SQLExecutorException;
	
	/**
	 * @Title: editViewpointInfo
	 * @Description: 修改观点数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午10:13:24
	 */
	@Update("update t_infomation set infomationtitle = #{infomationtitle}, "
			+ "descrption = #{descrption}, content = #{content} where id =#{id}")
	void editViewpointInfo(Infomation info) throws SQLExecutorException;
	
	/**
	 * @Title: delNewsPointByIds
	 * @Description: 批量删除观点数据
	 * @param ids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月28日 上午9:44:54
	 */
	void delNewsPointByIds(List<String> ids) throws SQLExecutorException;

}