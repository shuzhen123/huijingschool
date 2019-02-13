package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import dianfan.entities.DiagnoseStock;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppDiagnoseStockMapper
 * @Description 诊股相关dao
 * @author cjy
 * @date 2018年3月8日 下午3:19:52
 */
@Repository
public interface AppDiagnoseStockMapper {

	/**
	 * @Title: findStockCount
	 * @Description: 按类型获取诊股数据总数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午9:32:07
	 */
	int findStockCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findStockByType
	 * @Description: 按类型获取诊股数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午9:34:13
	 */
	List<DiagnoseStock> findStockByType(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: sendStockQuestion
	 * @Description: 诊股提问
	 * @param ds
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午10:05:22
	 */
	@Insert("insert into t_clinicshares (id, questiontitle, questiondes, flag, createtime, createid, entkbn) "
			+ "values (replace(uuid(),'-',''), #{questiontitle}, #{questiondes}, 0, now(), #{createid}, 0)")
	void sendStockQuestion(DiagnoseStock ds) throws SQLExecutorException;
}
