package dianfan.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.CourseMapper;
import dianfan.entities.Course;
import dianfan.entities.CourseType;
import dianfan.entities.DataTable;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName CourseService
 * @Description 课程服务
 * @author cjy
 * @date 2018年1月6日 下午1:19:40
 */
@Service 
public class CourseService {
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * @Title: findCourses
	 * @Description: 课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月21日 下午3:11:56
	 */
	public DataTable findCourses(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = courseMapper.findCourseCount(param);
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<Course> courses = courseMapper.findCourses(param);
		
		//设置数据
		dt.setData(courses);
		return dt;
	}

	/**
	 * @Title: editCourse
	 * @Description: 课程修改
	 * @param course
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月8日 上午10:10:00
	 */
	@Transactional
	public void editCourse(Course course) throws SQLExecutorException {
		//根据课程免、付费，设置课程价格与折扣率
		if(course.getCourselimit() == 1) {
			//免费
			course.setCoursemoney(new BigDecimal(0));
			course.setDiscountrate("0");
		}
		
		//课程审核
		if(course.getAuth() != null && course.getAuth() != 2) {
			//审核非通过状态，各种推荐均至为不推荐
			course.setRecommendflag(0);
			course.setBannerflag(0);
		}
		courseMapper.editCourse(course);
	}

	/**
	 * @Title: delCourse
	 * @Description: 根据课程id删除课程
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 上午11:33:47
	 */
	@Transactional
	public void delCourse(List<String> lids) throws SQLExecutorException {
		courseMapper.delCourseByIds(lids);
	}
	
	/* ********************课程分类******************** */

	/**
	 * @Title: findCourseTypeList
	 * @Description: 获取课程分类列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 下午1:34:50
	 */
	public DataTable findCourseTypeList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = courseMapper.findCourseTypeCount();
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<CourseType> types = courseMapper.findCourseType(param);
		
		//设置数据
		dt.setData(types);
		return dt;
	}

	/**
	 * @Title: delCourseType
	 * @Description: 删除课程类型
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月31日 上午10:45:10
	 */
	@Transactional
	public void delCourseType(List<String> lids) throws SQLExecutorException {
		//删除课程类型
		courseMapper.delCourseType(lids);
		//删除课程类型下的所有课程
		courseMapper.delCourseByType(lids);
	}

	/**
	 * @Title: addCourseType
	 * @Description: 课程类型添加
	 * @param name
	 * @param userid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月31日 上午11:13:31
	 */
	@Transactional
	public void addCourseType(String name) throws SQLExecutorException {
		CourseType type = new CourseType();
		type.setCoursetypename(name);
		courseMapper.addCourseType(type);
	}

	/**
	 * @Title: editCourseType
	 * @Description: 课程类型修改
	 * @param type
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月31日 上午11:28:10
	 */
	@Transactional
	public void editCourseType(CourseType type) throws SQLExecutorException {
		courseMapper.editCourseType(type);
	}
	
}