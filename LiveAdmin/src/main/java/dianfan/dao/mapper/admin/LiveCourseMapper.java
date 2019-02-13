package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.LiveCourse;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminCourseMapper
 * @Description 课程dao
 * @author cjy
 * @date 2018年1月6日 下午1:20:40
 */
@Repository
public interface LiveCourseMapper {

	/**
	 * @Title: findLiveCourseCount
	 * @Description: 根据条件获取直播课程总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月8日 上午11:33:54
	 */
	int findLiveCourseCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findLiveCourses
	 * @Description: 根据条件获取直播课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月8日 上午11:34:37
	 */
	List<LiveCourse> findLiveCourses(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getLiveCourseDataById
	 * @Description: 获取直播课程详情
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月9日 上午11:45:45
	 */
	LiveCourse getLiveCourseDataById(String courseid) throws SQLExecutorException;

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月9日 下午1:42:35
	 */
	void editLiveCourse(LiveCourse course) throws SQLExecutorException;

	/**
	 * @Title: updateLiveCourseNotice
	 * @Description: 直播课程预告时间修改
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月9日 下午2:25:07
	 */
	@Update("update t_course_live_notice set starttime = #{starttime}, endtime = #{endtime} where courseid = #{courseid}")
	void updateLiveCourseNotice(LiveCourse course) throws SQLExecutorException;

}