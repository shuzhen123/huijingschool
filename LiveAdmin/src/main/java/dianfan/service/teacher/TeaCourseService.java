package dianfan.service.teacher;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.teacher.TeaCourseMapper;
import dianfan.entities.Course;
import dianfan.entities.CourseCommont;
import dianfan.entities.DataTable;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName TeaCourseService
 * @Description 讲师课程服务
 * @author cjy
 * @date 2018年3月10日 下午5:06:42
 */
@Service 
public class TeaCourseService {
	@Autowired
	private TeaCourseMapper teaCourseMapper;

	/**
	 * @Title: findCourses
	 * @Description: 课程列表
	 * @param teacherid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 下午5:12:03
	 */
	public DataTable findCourses(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = teaCourseMapper.findCourseCount(param);
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
		List<Course> courses = teaCourseMapper.findCourses(param);
		
		//设置数据
		dt.setData(courses);
		return dt;
	}

	/**
	 * @Title: editCourse
	 * @Description: 课程修改
	 * @param course
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月8日 上午10:10:00
	 */
	@Transactional
	public void editCourse(Course course) throws SQLExecutorException {
		if(course.getCourselimit() == 1) {
			//课程免费，设置价格为0
			course.setCoursemoney(new BigDecimal(0));
		}
		//检测是否进行再次审核
		if(course.getAuth() != null && course.getAuth() == 3) {
			course.setAuth(1);
		}
		teaCourseMapper.editCourse(course);
	}
	
	/**
	 * @Title: addCourse
	 * @Description: 课程添加
	 * @param course
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 上午11:02:33
	 */
	@Transactional
	public void addCourse(Course course) throws SQLExecutorException {
		course.setCourseid(UUIDUtil.getUUID());
		if(course.getCourselimit() == 1) {
			//课程免费，设置价格为0
			course.setCoursemoney(new BigDecimal(0));
		}
		teaCourseMapper.addCourse(course);
	}
	
	/* *************************课程视频*********************** */
	/**
	 * @Title: addCourseVideo
	 * @Description: 添加课程视频
	 * @param video
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月19日 下午1:38:47
	 */
	@Transactional
	public Video addCourseVideo(Video video) throws SQLExecutorException {
		video.setVideoid(UUIDUtil.getUUID());
		video.setEnableflag(1);
		teaCourseMapper.addCourseVideo(video);
		return video;
	}
	
	/**
	 * @Title: updateCourseVideoUrl
	 * @Description: 更新课程视频地址
	 * @param video
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月1日 上午11:52:19
	 */
	@Transactional
	public void updateCourseVideoUrl(String videoid, String filename) throws SQLExecutorException {
		teaCourseMapper.updateCourseVideoUrl(videoid, filename);
	}
	
	/**
	 * @Title: editCourseVideo
	 * @Description: 修改课程视频
	 * @param video
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月29日 下午1:09:49
	 */
	@Transactional
	public void editCourseVideo(Video video) throws SQLExecutorException {
		teaCourseMapper.editCourseVideo(video);
	}
	
	/**
	 * @Title: delCourseVideo
	 * @Description: 删除课程视频
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月26日 下午3:26:48
	 */
	@Transactional
	public void delCourseVideo(List<String> ids) throws SQLExecutorException {
		teaCourseMapper.delCourseVideoByIds(ids);
	}
	
	/* ************************课程评价************************ */
	
	
	public DataTable findCourseCommontList(String courseid, int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();
		//条件
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		param.put("courseid", courseid);
		
		// 1、获取总条数
		int count = teaCourseMapper.getCourseCommontCountById(courseid);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<CourseCommont> commonts = teaCourseMapper.findCourseCommontById(param);

		//屏蔽手机号码
		ListIterator<CourseCommont> iterator = commonts.listIterator();
		StringBuffer bf = new StringBuffer();
		while(iterator.hasNext()) {
			CourseCommont commont = iterator.next();
			
			bf.append(commont.getTelno());
			commont.setTelno(bf.replace(3, 7, "****").toString());
			bf.delete(0, bf.length());
		}
		// 设置数据
		dt.setData(commonts);
		return dt;
	}

	/**
	 * @Title: delCourseCommont
	 * @Description: 删除评价信息
	 * @param commentid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月12日 上午11:27:57
	 */
	@Transactional
	public void delCourseCommont(String commentid) throws SQLExecutorException {
		teaCourseMapper.delCourseCommontById(commentid);
	}
}