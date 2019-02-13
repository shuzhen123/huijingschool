package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.AppRecommendCourse;
import dianfan.entities.BashMap;
import dianfan.entities.CourseAndStudyCount;
import dianfan.entities.CourseCommentInfo;
import dianfan.entities.CourseCondition;
import dianfan.entities.CourseDetail;
import dianfan.entities.CourseDirectory;
import dianfan.entities.CourseList;
import dianfan.entities.CourseSutdy;
import dianfan.entities.PriceRange;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppAgentMapper
 * @Description 直播人员相关dao
 * @author cjy
 * @date 2018年1月23日 下午4:08:30
 */
@Repository
public interface AppCourseMapper {

	/**
	 * @Title: findCoursesByType
	 * @Description: 首页课程列表
	 * @param type
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月24日 上午9:58:53
	 */
	List<AppRecommendCourse> findCoursesByType(int type) throws SQLExecutorException;

	/**
	 * @Title: findRecommendCourse
	 * @Description: 获取与当前课程相同讲师的推荐课程列表
	 * @param courseid
	 * @throws:
	 * @time: 2018年1月25日 下午3:12:10
	 */
	void findRecommendCourse(String courseid) throws SQLExecutorException;

	/**
	 * @Title: getCountByParam
	 * @Description: 根据条件获取课程总条数
	 * @param courseList_param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 上午11:53:49
	 */
	int getCourseCountByParam(CourseCondition courseList_param) throws SQLExecutorException;

	/**
	 * @Title: getCourseList
	 * @Description: 根据类型获取课程列表
	 * @param courseList_param
	 *            课程类型及分页信息
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月21日 下午4:48:56
	 */
	List<CourseList> getCourseList(CourseCondition courseList_param) throws SQLExecutorException;

	/**
	 * @Title: getStudyCount
	 * @Description: 课程id对应的学习量
	 * @param courseids
	 *            课程id列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月21日 下午5:50:23
	 */
	List<CourseAndStudyCount> getStudyCount(List<String> courseids) throws SQLExecutorException;

	/**
	 * @Title: getPraiseStatus
	 * @Description: 获取用户与课程间点赞关系(对应列表信息)
	 * @param praise_param
	 *            (Map<用户id, 课程id列表>)
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月21日 下午6:26:27
	 */
	List<Map<String, String>> getPraiseStatus(Map<String, Object> praise_param) throws SQLExecutorException;

	/**
	 * @Title: findCourseThumbsup
	 * @Description: 查看用户是否赞过课程
	 * @param param
	 *            (Map<用户id, 课程id>)
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:20:55
	 */
	@Select("select count(*) from  t_course_thumbsup where couseid=#{courseid} and userid=#{userid} and entkbn=0")
	boolean findCourseThumbsup(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: addCourseThumbsup
	 * @Description: 添加点赞记录
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午1:30:37
	 */
	@Insert("insert into t_course_thumbsup (id, couseid, userid, entkbn) values (replace(uuid(),'-',''), #{courseid}, #{userid}, 0)")
	void addCourseThumbsup(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: coursePraiseInc
	 * @Description: 课程点赞量+1
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月13日 下午2:17:35
	 */
	@Update("update t_course set thumbsupcount = thumbsupcount + 1 where courseid=#{courseid}")
	void coursePraiseInc(String courseid) throws SQLExecutorException;

	/**
	 * @Title: getCourseDetailByCourseid
	 * @Description: 获取课程详情
	 * @param courseid
	 *            课程id
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午2:40:05
	 */
	CourseDetail getCourseDetailByCourseid(String courseid) throws SQLExecutorException;
	
	/**
	 * @Title: courseStydyCountInc
	 * @Description: 学习量+1
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月26日 下午12:32:07
	 */
	@Update("update t_course set browsingcount = browsingcount+1 where courseid=#{courseid}")
	void courseStydyCountInc(String courseid) throws SQLExecutorException;

	/**
	 * @Title: findCourseDirectoryByTeacherid
	 * @Description: 讲师的课程目录
	 * @param courseid
	 *            讲师id
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午4:04:25
	 */
	@Select("select courseid, videoid, videoname, hits, videoppicurl, videourl from t_videos where courseid=#{courseid} and enableflag=1 and entkbn=0 order by createtime asc")
	List<CourseDirectory> findCourseDirectoryByCourseid(String courseid) throws SQLExecutorException;

	/**
	 * @Title: updateCourseVideoHits
	 * @Description: 课程视频播放量+1
	 * @param videoid
	 * @throws:
	 * @time: 2018年1月25日 下午2:13:43
	 */
	@Update("update t_videos set hits = hits +1 where videoid = #{videoid}")
	void updateCourseVideoHits(String videoid) throws SQLExecutorException;

	/**
	 * @Title: getCourseCommentCountByCourseid
	 * @Description: 获取课程评论总条数
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午5:33:14
	 */
	int getCourseCommentCountByCourseid(String courseid) throws SQLExecutorException;

	/**
	 * @Title: getCourseCommentByCourseid
	 * @Description: 获取课程评价列表
	 * @param param(Map<courseid:课程id,
	 *            page: 起始页, offset: 偏移量>)
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午5:08:17
	 */
	List<CourseCommentInfo> getCourseCommentByCourseid(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findUsedTeacher
	 * @Description: 获取所有可用讲师
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月26日 上午9:25:16
	 */
	@Select("select userid id, realname name from t_userinfo where role=5 and entkbn=0")
	List<BashMap> findUsedTeacher() throws SQLExecutorException;

	/**
	 * @Title: findUsedCourseType
	 * @Description: 获取所有分类
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月26日 上午10:21:16
	 */
	@Select("select id, coursetypename name from t_coursekind where entkbn=0")
	List<BashMap> findUsedCourseType() throws SQLExecutorException;

	/**
	 * @Title: findPriceRange
	 * @Description: 获取价格区间列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月26日 下午6:05:39
	 */
	@Select("select id, lprice, hprice from t_price_range where entkbn=0")
	TreeSet<PriceRange> findPriceRange() throws SQLExecutorException;

	/**
	 * @Title: getCourseVideo
	 * @Description: 获取课程第一课
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 下午2:33:21
	 */
	@Select("select videoid, videourl, videoppicurl from t_videos where courseid = #{courseid} and enableflag = 1 and entkbn=0 order by createtime asc limit 0, 1")
	Video getCourseVideo(String courseid) throws SQLExecutorException;
	
	/**
	 * @Title: checkUserIsSaler
	 * @Description: 判断当前用户是不是业务员
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月15日 下午1:54:21
	 */
	@Select("select count(*) from t_userinfo where telno=(select telno from t_userinfo where userid=#{userid}) and role <> 1 and entkbn=0")
	boolean checkUserIsSaler1(String userid) throws SQLExecutorException;

	/**
	 * @Title: findTeacherRecommendCourse
	 * @Description: 课程详情内推荐课程
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午6:20:47
	 */
	List<AppRecommendCourse> findTeacherRecommendCourse(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: courseWriteComment
	 * @Description: 课程评价
	 * @param userId
	 * @param courseid
	 * @param content
	 * @param star
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月30日 下午5:00:17
	 */
	@Insert("insert into t_course_comment (id, userid, courseid, commentcontent, coursescore, createtime, entkbn) "
			+ "values (replace(uuid(),'-',''), #{userId}, #{courseid}, #{content}, #{star}, now(), 0)")
	void courseWriteComment(@Param("userId") String userId, @Param("courseid") String courseid, @Param("content") String content, @Param("star") Integer star) throws SQLExecutorException;

	/**
	 * @Title: checkcourseStudyInfo
	 * @Description: 查看用户对应的学习课程
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:31:11
	 */
	@Select("select * from t_studyprogress where courseid=#{courseid} and studyuserid=#{userid} limit 0,1")
	CourseSutdy checkcourseStudyInfo(@Param("userid") String userid, @Param("courseid") String courseid) throws SQLExecutorException;

	/**
	 * @Title: addStudyCourseInfo
	 * @Description: 第一次学习，添加学习进度
	 * @param userid
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:33:57
	 */
	@Insert("insert into t_studyprogress (id, courseid, studyuserid, time) values (replace(uuid(),'-',''), #{courseid}, #{userid}, now())")
	void addStudyCourseInfo(@Param("userid") String userid, @Param("courseid") String courseid) throws SQLExecutorException;

	/**
	 * @Title: updateStudyCourseInfo
	 * @Description: 更新学习时间
	 * @param userid
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:34:24
	 */
	@Update("update t_studyprogress set time=now() where courseid=#{courseid} and studyuserid=#{userid}")
	void updateStudyCourseInfo(@Param("userid") String userid, @Param("courseid") String courseid) throws SQLExecutorException;


}
