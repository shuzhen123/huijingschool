package dianfan.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppTeacherMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.Course;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName BannerService
 * @Description Banner图相关服务
 * @author cjy
 * @date 2018年1月23日 下午2:22:37
 */
@Service
public class AppTeacherService {
	@Autowired
	private AppTeacherMapper appAgentMapper;
	

	/**
	 * @Title: getTeacherDetail
	 * @Description: 名师详情页
	 * @param teacherid
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月7日 下午5:24:28
	 */
	public UserInfo getTeacherDetail(String teacherid) throws SQLExecutorException {
		UserInfo info = appAgentMapper.getTeacherDetail(teacherid);
		info.setTeacherurl(ConstantIF.PROJECT + info.getTeacherurl());
		info.setIconurl(ConstantIF.PROJECT + info.getIconurl());
		return info;
	}


	/**
	 * @Title: findTeacherCourseList
	 * @Description: 教师的课程列表
	 * @param teacherid
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月7日 下午5:28:03
	 */
	public List<AppRecommendCourse> findTeacherCourseList(String teacherid, int type) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("teacherid", teacherid);
		param.put("type", type);
		List<AppRecommendCourse> list = appAgentMapper.findCourseListByTeacherid(param);
		for(AppRecommendCourse arc : list) {
			arc.setCoursepic(ConstantIF.PROJECT + arc.getCoursepic());
		}
		return list;
	}


}
