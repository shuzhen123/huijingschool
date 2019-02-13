package dianfan.service.saler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.saler.SalerStatisticsMapper;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.DataTable;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName SalerStatisticsService
 * @Description 业务员报表服务
 * @author cjy
 * @date 2018年4月23日 下午3:08:02
 */
@Service
public class SalerStatisticsService {
	@Autowired
	private SalerStatisticsMapper salerStatisticsMapper;

	/**
	 * @Title: findCourseOrderList
	 * @Description: 课程消费订单列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月14日 上午9:21:58
	 */
	public DataTable findCourseOrderList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = salerStatisticsMapper.findCourseOrderCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<CourseOrderCollect> vipOrder = salerStatisticsMapper.findCourseOrder(param);

		for (CourseOrderCollect coc : vipOrder) {
			if (coc.getPaystatus() == 1 && coc.getValiditytime().getTime() < System.currentTimeMillis()) {
				coc.setPaystatus(2);
			}
		}

		// 设置数据
		dt.setData(vipOrder);
		return dt;
	}

}