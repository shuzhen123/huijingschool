package dianfan.dao.mapper.teacher;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.Course;
import dianfan.entities.LiveCourse;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName TeaLiveCourseMapper
 * @Description 直播课程相关dao
 * @author cjy
 * @date 2018年3月26日 上午10:59:45
 */
@Repository
public interface TeaLiveCourseMapper {

	/**
	 * @Title: findLiveCourseCount
	 * @Description: 根据条件获取直播课程总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午10:58:59
	 */
	int findLiveCourseCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findLiveCourses
	 * @Description: 根据条件获取直播课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午10:59:12
	 */
	List<LiveCourse> findLiveCourses(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkLiveCourseTime
	 * @Description: 直播课程时间冲突性检测
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午6:16:25
	 */
	@Select("select count(*) from t_course course, t_course_live_notice notice " +
			"where  " +
			"	course.courseid=notice.courseid and course.userid=#{userid} and (course.coursekind=1 or course.coursekind=2) and  " +
			"	(course.auth=1 or course.auth=2) and course.flag=1 and (course.liveflag=1 or course.liveflag=3) and  " +
			"	course.entkbn=0 and notice.starttime <= #{endtime} and notice.endtime >= #{starttime} ")
	boolean checkLiveCourseTime(@Param(value="userid") String userid, @Param(value="starttime") String starttime, @Param(value="endtime") String endtime) throws SQLExecutorException;
	
	/**
	 * @Title: checkLiveCourseTimeExcCourseid
	 * @Description: 直播课程时间冲突性检测(排除当前直播课程)
	 * @param userid
	 * @param courseid
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午6:40:19
	 */
	@Select("select count(*) from t_course course, t_course_live_notice notice " +
			"where  " +
			"	course.courseid=notice.courseid and course.userid=#{userid} and course.courseid <> #{courseid} and " +
			"	(course.coursekind=1 or course.coursekind=2) and (course.auth=1 or course.auth=2) and course.flag=1 and " +
			"	(course.liveflag=1 or course.liveflag=3) and course.entkbn=0 and notice.starttime <= #{endtime} and notice.endtime >= #{starttime} ")
	boolean checkLiveCourseTimeExcCourseid(
			@Param(value="userid") String userid, 
			@Param(value="courseid") String courseid, 
			@Param(value="starttime") String starttime,
			@Param(value="endtime") String endtime) throws SQLExecutorException;
	
	/**
	 * @Title: addLiveCourse
	 * @Description: 直播课程添加
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 上午10:03:02
	 */
	void addLiveCourse(Course course) throws SQLExecutorException;

	/**
	 * @Title: addLiveCourseNotice
	 * @Description: 添加预告时间
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 上午10:10:32
	 */
	void addLiveCourseNotice(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: getLiveCourseInfo
	 * @Description: 根据课程id获取直播课程数据
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 下午1:32:16
	 */
	@Select("select " +
			"	 cour.courseid, cour.userid, cour.coursekind, cour.coursename, cour.coursepic, cour.coursedes, cour.coursetypecode, " +
			"	 cour.auth, cour.cause, cour.bannerflag, cour.thumbsupcount, cour.flag, cour.liveflag, cour.createtime,  " +
			"	 noti.starttime, noti.endtime " +
			"from " +
			"	t_course cour, t_course_live_notice noti " +
			"where  " +
			"	cour.courseid=noti.courseid and cour.courseid=#{courseid} ")
	LiveCourse getLiveCourseInfo(String courseid) throws SQLExecutorException;

	/**
	 * @Title: editLiveCourse
	 * @Description: 直播课程修改
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 下午3:12:57
	 */
	void editLiveCourse(Course course) throws SQLExecutorException;

	/**
	 * @Title: editLiveCourseNotice
	 * @Description: 修改直播时间
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 下午3:18:03
	 */
	void editLiveCourseNotice(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: getLiveCourseInfoAndVideo
	 * @Description: 直播课程详情页
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月27日 下午4:15:16
	 */
	LiveCourse getLiveCourseInfoAndVideo(String courseid) throws SQLExecutorException;

}