package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.Course;
import dianfan.entities.CourseType;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AdminCourseMapper
 * @Description 课程dao
 * @author cjy
 * @date 2018年1月6日 下午1:20:40
 */
@Repository
public interface CourseMapper {

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
	 * @Description: 课程列表
	 * @param param
	 * @throws:
	 * @time: 2018年1月6日 下午1:25:51
	 */
	List<Course> findCourses(Map<String, Object> param) throws SQLExecutorException;

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
	 * @Title: delCourseById
	 * @Description: 根据课程id删除课程
	 * @param ids
	 * @throws:
	 * @time: 2018年1月6日 下午2:06:28
	 */
	void delCourseByIds(List<String> ids) throws SQLExecutorException;


	/* ********************课程分类******************** */

	/**
	 * @Title: findCourseTypeCount
	 * @Description: 获取课程分类总数量
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午10:26:26
	 */
	@Select("select count(*) from t_coursekind where entkbn = 0")
	int findCourseTypeCount() throws SQLExecutorException;
	
	/**
	 * @Title: findCourseType
	 * @Description: 获取课程分类
	 * @throws:
	 * @time: 2018年1月31日 上午10:20:38
	 */
	@Select("select id, coursetypename, createtime from t_coursekind where entkbn = 0 order by createtime desc limit #{start}, #{length}")
	List<CourseType> findCourseType(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: delCourseType
	 * @Description: 删除课程类型
	 * @param lids
	 * @throws:
	 * @time: 2018年1月31日 上午10:45:41
	 */
	void delCourseType(List<String> lids) throws SQLExecutorException;

	/**
	 * @Title: delCourseByType
	 * @Description: 删除课程类型下的所有课程
	 * @param lids
	 * @throws:
	 * @time: 2018年1月31日 下午3:22:58
	 */
	void delCourseByType(List<String> lids) throws SQLExecutorException;
	
	/**
	 * @Title: checkCourseTypeName
	 * @Description: 类型名称重复性检测 
	 * @param name
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午2:33:11
	 */
	@Select("select count(*) from t_coursekind where coursetypename=#{name} and entkbn=0")
	Boolean checkCourseTypeName(String name) throws SQLExecutorException;
	@Select("select count(*) from t_coursekind where coursetypename=#{coursetypename} and id!=#{id} and entkbn=0")
	Boolean checkCourseTypeId(CourseType type) throws SQLExecutorException;
	
	/**
	 * @Title: addCourseType
	 * @Description: 课程类型添加
	 * @param type
	 * @throws:
	 * @time: 2018年1月31日 上午11:15:55
	 */
	@Insert("insert into t_coursekind (id, coursetypename, createtime, entkbn) values (replace(uuid(),'-',''), #{coursetypename}, now(), 0)")
	void addCourseType(CourseType type) throws SQLExecutorException;

	/**
	 * @Title: editCourseType
	 * @Description: 课程类型修改
	 * @param type
	 * @throws:
	 * @time: 2018年1月31日 上午11:28:51
	 */
	@Update("update t_coursekind set coursetypename = #{coursetypename} where id = #{id}")
	void editCourseType(CourseType type) throws SQLExecutorException;

}