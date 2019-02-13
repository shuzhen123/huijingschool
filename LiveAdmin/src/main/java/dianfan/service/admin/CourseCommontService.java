package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.CourseCommontMapper;
import dianfan.entities.CourseCommont;
import dianfan.entities.DataTable;
import dianfan.entities.FeedBack;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName CourseCommontService
 * @Description 课程评论服务
 * @author cjy
 * @date 2018年1月12日 上午10:46:22
 */
@Service 
public class CourseCommontService {
	@Autowired
	private CourseCommontMapper commontMapper;

	/**
	 * @Title: findCourseCommontList
	 * @Description: 根据课程id获取评论列表
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月12日 上午10:57:08
	 */
	public DataTable findCourseCommontList(String courseid, String search, int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();
		//条件
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		param.put("courseid", courseid);
		
		// 1、获取总条数
		int count = commontMapper.getCourseCommontCountById(courseid);
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
		List<CourseCommont> commonts = commontMapper.findCourseCommontById(param);

		//屏蔽手机号码
		ListIterator<CourseCommont> iterator = commonts.listIterator();
		StringBuffer bf = new StringBuffer();
		while(iterator.hasNext()) {
			CourseCommont commont = iterator.next();
			
			bf.append(commont.getTelno());
			commont.setTelno(bf.replace(3, 7, "****").toString());
			bf.delete(0, bf.length());
		}
		// 设置数据
		dt.setData(commonts);
		return dt;
	}

	/**
	 * @Title: delCourseCommont
	 * @Description: 删除评论
	 * @param commontid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月12日 下午1:45:19
	 */
	@Transactional
	public void delCourseCommont(String commontid) throws SQLExecutorException {
		commontMapper.delCourseCommontById(commontid);
	}

	
}