package dianfan.service.teacher;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.teacher.TeaNewsPointMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Infomation;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName TeaNewsPointService
 * @Description 牛人观点服务
 * @author cjy
 * @date 2018年3月27日 下午6:28:34
 */
@Service
public class TeaNewsPointService {
	@Autowired
	private TeaNewsPointMapper newsPointMapper;
	
	/**
	 * @Title: findViewpoint
	 * @Description: 获取牛人观点列表
	 * @param start
	 * @param length
	 * @param infomationtitle
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午11:01:19
	 */
	public DataTable findViewpoint(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 1、获取总条数
		int count = newsPointMapper.findViewpointCount(param);
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
		List<Infomation> model = newsPointMapper.findViewpoint(param);

		// 设置数据
		dt.setData(model);
		return dt;
	}

	/**
	 * @Title: addNewsInfo
	 * @Description: 添加咨讯数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午12:57:38
	 */
	@Transactional
	public void addNewsInfo(Infomation info) throws SQLExecutorException {
		newsPointMapper.addNewsPointInfo(info);
	}
	
	/**
	 * @Title: editViewpointInfo
	 * @Description: 修改观点数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月6日 上午10:12:18
	 */
	@Transactional
	public void editViewpointInfo(Infomation info) throws SQLExecutorException {
		newsPointMapper.editViewpointInfo(info);
	}
	
	/**
	 * @Title: delNews
	 * @Description: 根据id删除咨讯数据
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午1:56:51
	 */
	@Transactional
	public void delNews(List<String> ids) throws SQLExecutorException {
		newsPointMapper.delNewsPointByIds(ids);
	}

}