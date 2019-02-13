package dianfan.dao.mapper.teacher;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.Course;
import dianfan.entities.CourseCommont;
import dianfan.entities.Video;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminCourseMapper
 * @Description 课程dao
 * @author cjy
 * @date 2018年1月6日 下午1:20:40
 */
@Repository
public interface TeaCourseMapper {

	/**
	 * @Title: findCourseCount
	 * @Description: 根据条件获取课程总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 下午1:31:07
	 */
	int findCourseCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findCourses
	 * @Description: 根据条件获取课程列表
	 * @param param
	 * @throws:
	 * @time: 2018年1月6日 下午1:25:51
	 */
	List<Course> findCourses(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCourseKind
	 * @Description: 获取课程分类
	 * @return
	 * @throws:
	 * @time: 2018年1月6日 下午5:03:36
	 */
	@Select("select id, coursetypename name from t_coursekind where entkbn=0")
	List<BashMap> findCourseKind() throws SQLExecutorException;
	
	/**
	 * @Title: findVipLevel
	 * @Description: 获取vip等级分类
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 上午11:10:11
	 */
	@Select("select id, levelname, money from t_viplevel where entkbn=0 order by money")
	List<VipLevel> findVipLevel() throws SQLExecutorException;
	
	/**
	 * @Title: findCourseInfoById
	 * @Description: 根据课程id获取课程信息
	 * @param courseid
	 * @throws:
	 * @time: 2018年1月6日 下午2:37:24
	 */
	Course findCourseInfoById(String courseid) throws SQLExecutorException;

	/**
	 * @Title: editCourse
	 * @Description: 课程修改
	 * @param course
	 * @return
	 * @throws:
	 * @time: 2018年1月8日 上午10:10:37
	 */
	void editCourse(Course course) throws SQLExecutorException;

	/**
	 * @Title: addCourse
	 * @Description: 课程添加
	 * @param course
	 * @throws:
	 * @time: 2018年1月18日 下午6:22:18
	 */
	void addCourse(Course course) throws SQLExecutorException;
	
	/* *************************课程视频*********************** */
	/**
	 * @Title: findCourseVideoByCourseId
	 * @Description: 根据课程id获取视频列表
	 * @param courseid
	 * @throws:
	 * @time: 2018年1月19日 下午3:22:31
	 */
	@Select("select videoid, videoppicurl, videourl, videoname, videointroduce, videocontent, enableflag, entkbn"
			+ " from t_videos where courseid = #{courseid} and entkbn <> 9 order by createtime asc")
	List<Video> findCourseVideoByCourseId(String courseid) throws SQLExecutorException;
	
	/**
	 * @Title: addCourseVideo
	 * @Description: 添加课程视频
	 * @param video
	 * @throws:
	 * @time: 2018年1月19日 下午1:47:22
	 */
	void addCourseVideo(Video video) throws SQLExecutorException;
	
	/**
	 * @Title: delCourseVideoByIds
	 * @Description: 根据id删除课程视频
	 * @param ids
	 * @throws:
	 * @time: 2018年1月26日 下午3:28:04
	 */
	void delCourseVideoByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: updateCourseVideoUrl
	 * @Description: 更新课程视频地址
	 * @param video
	 * @throws:
	 * @time: 2018年2月1日 上午11:52:54
	 */
	@Update("update t_videos set vid=null, videourl = #{filename}, entkbn=1 where videoid = #{videoid}")
	void updateCourseVideoUrl(@Param(value="videoid") String videoid, @Param(value="filename") String filename) throws SQLExecutorException;
	
	/**
	 * @Title: editCourseVideo
	 * @Description: 修改课程视频
	 * @param video
	 * @throws:
	 * @time: 2018年1月29日 下午1:09:55
	 */
	void editCourseVideo(Video video) throws SQLExecutorException;
	
	/**
	 * @Title: videoTranscodeStatus
	 * @Description: 获取视频转码状态
	 * @param vids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月17日 下午4:10:23
	 */
	List<Video> videoTranscodeStatus(String[] vids) throws SQLExecutorException;
	
	/* ************************课程评价************************ */
	
	/**
	 * @Title: CourseCommontCountById
	 * @Description: 根据课程id获取评论总条数
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午11:54:37
	 */
	int getCourseCommontCountById(String courseid) throws SQLExecutorException;
	
	/**
	 * @Title: findCourseCommontById
	 * @Description: 根据课程id获取评论列表
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月12日 上午10:55:56
	 */
	List<CourseCommont> findCourseCommontById(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: delCourseCommontById
	 * @Description: 根据id删除评论
	 * @param courseid
	 * @throws:
	 * @time: 2018年1月12日 下午1:46:03
	 */
	@Update("update t_course_comment set entkbn = 9 where id=#{commontid}")
	void delCourseCommontById(String commontid) throws SQLExecutorException;

}