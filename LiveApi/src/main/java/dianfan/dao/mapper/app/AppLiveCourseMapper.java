package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.ChannelBean;
import dianfan.entities.CourseList;
import dianfan.entities.LiveCourse;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppLiveCourseMapper
 * @Description 直播
 * @author cjy
 * @date 2018年5月4日 下午2:25:17
 */
@Repository
public interface AppLiveCourseMapper {

	/**
	 * @Title: findLiveCourseLiving
	 * @Description: 直播页顶部正在直播的课程
	 * @param type
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午2:29:57
	 */
	@Select("select  " +
			"	course.courseid, course.coursepic, course.coursename,  " +
			"	notice.starttime, notice.endtime, user.realname " +
			"from  " +
			"	t_course course, t_course_live_notice notice, t_userinfo user " +
			"where  " +
			"	course.courseid=notice.courseid and course.userid=user.userid and course.coursekind=#{type} and course.auth=2 and " +
			"	course.flag=1 and course.liveflag=1 and course.entkbn=0 order by course.recommendflag desc limit 0,1")
	LiveCourse findLiveCourseLiving(int type) throws SQLExecutorException;

	/**
	 * @Title: getPastLiveCourse
	 * @Description: 获取往期直播课程列表数量
	 * @param type
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午4:05:01
	 */
	int getPastLiveCourse(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getPastLiveCourseList
	 * @Description: 获取往期直播课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午4:25:37
	 */
	List<CourseList> getPastLiveCourseList(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findWeekLiveCourse
	 * @Description: 本周某天直播节目单列表
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月4日 下午5:29:29
	 */
	List<LiveCourse> findWeekLiveCourse(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getLiveCourseDateByCourseid
	 * @Description: 根据直播课程id获取课程数据
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月5日 上午10:40:56
	 */
	@Select("select * from t_course where courseid=#{courseid}")
	LiveCourse getLiveCourseDateByCourseid(String courseid) throws SQLExecutorException;

	/**
	 * @Title: getPullUrlByTeaid
	 * @Description: 
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月5日 上午11:01:32
	 */
	@Select("select * from t_channel where accid=#{userid}")
	ChannelBean getPullUrlByTeaid(String userid) throws SQLExecutorException;

	/**
	 * @Title: getCourseVideo
	 * @Description: 根据课程id获取视频地址
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月5日 下午3:18:45
	 */
	@Select("select videourl from t_videos where courseid=#{courseid} limit 0, 1")
	String getCourseVideo(String courseid) throws SQLExecutorException;

	/**
	 * @Title: getUserVCloudToken
	 * @Description: 获取用户网易云token
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月7日 下午6:15:25
	 */
	@Select("select tokenid from t_userinfo where userid=#{userid}")
	String getUserVCloudToken(String userid) throws SQLExecutorException;
}
