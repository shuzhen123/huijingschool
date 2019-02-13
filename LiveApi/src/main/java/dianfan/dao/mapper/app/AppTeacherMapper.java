package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.AppRecommendCourse;
import dianfan.entities.RecommendTeacherInfo;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppAgentMapper
 * @Description 直播人员相关dao
 * @author cjy
 * @date 2018年1月23日 下午4:08:30
 */
@Repository
public interface AppTeacherMapper {

	/**
	 * @Title: findRecommendTeacher
	 * @Description: 获取首页名师推荐列表
	 * @param isAll
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月23日 下午4:20:56
	 */
	@Select("select userid, teacherurl from t_userinfo where famousteacherflag = 1 and "
			+ "role = 5 and entkbn = 0 order by famousteacherord asc, createtime desc")
	List<RecommendTeacherInfo> findIndexRecommendTeacher() throws SQLExecutorException;

	/**
	 * @Title: findRecommendTeacher
	 * @Description: 获取名师列表
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 下午4:51:25
	 */
	TreeSet<RecommendTeacherInfo> findRecommendTeacher() throws SQLExecutorException;

	/**
	 * @Title: getTeacherDetail
	 * @Description: 名师详情
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午4:55:50
	 */
	@Select("select introduction, teacherurl, realname, iconurl from t_userinfo where userid=#{teacherid}")
	UserInfo getTeacherDetail(String teacherid) throws SQLExecutorException;

	/**
	 * @Title: findCourseListByTeacherid
	 * @Description: 教师的课程列表
	 * @param teacherid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午5:27:58
	 */
	List<AppRecommendCourse> findCourseListByTeacherid(Map<String, Object> param) throws SQLExecutorException;
	

}
