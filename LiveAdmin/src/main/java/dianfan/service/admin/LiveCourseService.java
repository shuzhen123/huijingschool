package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.LiveCourseMapper;
import dianfan.entities.Course;
import dianfan.entities.DataTable;
import dianfan.entities.LiveCourse;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName LiveCourseService
 * @Description 直播课程服务
 * @author cjy
 * @date 2018年3月8日 上午11:33:06
 */
@Service 
public class LiveCourseService {
	@Autowired
	private LiveCourseMapper liveCourseMapper;

	/**
	 * @Title: findLiveCourses
	 * @Description: 直播课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月8日 上午11:33:21
	 */
	public DataTable findLiveCourses(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = liveCourseMapper.findLiveCourseCount(param);
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
		List<LiveCourse> liveCourses = liveCourseMapper.findLiveCourses(param);
		
		//设置数据
		dt.setData(liveCourses);
		return dt;
	}

	/**
	 * @Title: getLiveCourseData
	 * @Description: 获取直播课程详情
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @time: 2018年3月9日 上午11:46:11
	 */
	public LiveCourse getLiveCourseData(String courseid) throws SQLExecutorException {
		LiveCourse course = liveCourseMapper.getLiveCourseDataById(courseid);
		course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
		return course;
	}

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param course
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月9日 下午1:41:38
	 */
	@Transactional
	public void editLiveCourse(LiveCourse course) throws SQLExecutorException {
		liveCourseMapper.editLiveCourse(course);
		if(course.getLiveflag() == 3) {
			//直播预告时间更改
			liveCourseMapper.updateLiveCourseNotice(course);
		}
	}



	
}