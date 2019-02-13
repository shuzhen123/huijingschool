package dianfan.service.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.teacher.TeaLiveCourseMapper;
import dianfan.entities.Course;
import dianfan.entities.DataTable;
import dianfan.entities.LiveCourse;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName TeaLiveCourseService
 * @Description 讲师直播课程服务
 * @author cjy
 * @date 2018年3月26日 上午10:57:42
 */
@Service 
public class TeaLiveCourseService {
	@Autowired
	private TeaLiveCourseMapper teaLiveCourseMapper;

	/**
	 * @Title: findLiveCourses
	 * @Description: 直播课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午10:56:06
	 */
	public DataTable findLiveCourses(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = teaLiveCourseMapper.findLiveCourseCount(param);
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
		List<LiveCourse> courses = teaLiveCourseMapper.findLiveCourses(param);
		
		//设置数据
		dt.setData(courses);
		return dt;
	}

	/**
	 * @Title: addLiveCourse
	 * @Description: 直播课程添加
	 * @param course
	 * @param starttime
	 * @param endtime
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月27日 上午9:59:46
	 */
	@Transactional
	public void addLiveCourse(Course course, String starttime, String endtime) throws SQLExecutorException {
		course.setCourseid(UUIDUtil.getUUID());
		//添加直播课程
		teaLiveCourseMapper.addLiveCourse(course);
		//添加预告时间
		Map<String, String> data = new HashMap<>();
		data.put("courseid", course.getCourseid());
		data.put("starttime", starttime);
		data.put("endtime", endtime);
		teaLiveCourseMapper.addLiveCourseNotice(data);
	}

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param course
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月27日 下午3:12:14
	 */
	@Transactional
	public void editLiveCourse(Course course, String starttime, String endtime) throws SQLExecutorException {
		//如果课程为审核失败课程，修改后需重新发起审核请求
		if(course.getAuth() == 3) {
			course.setAuth(1);
		}
		teaLiveCourseMapper.editLiveCourse(course);
		if(course.getLiveflag() == 1 || course.getLiveflag() == 3) {
			//修改直播时间
			Map<String, String> data = new HashMap<>();
			data.put("courseid", course.getCourseid());
			data.put("starttime", starttime);
			data.put("endtime", endtime);
			teaLiveCourseMapper.editLiveCourseNotice(data);
		}
	}

}