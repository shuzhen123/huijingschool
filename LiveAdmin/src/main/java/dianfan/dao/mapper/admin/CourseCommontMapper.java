package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.CourseCommont;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName CourseCommontMapper
 * @Description 课程评论dao
 * @author cjy
 * @date 2018年1月12日 上午10:49:21
 */
@Repository
public interface CourseCommontMapper {

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