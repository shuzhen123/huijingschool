package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppMyInfoMapper;
import dianfan.entities.CourseList;
import dianfan.entities.CourseSutdy;
import dianfan.entities.FeedBack;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;

/**
 * @ClassName AppMyInfoService
 * @Description 我的部分相关服务
 * @author cjy
 * @date 2018年3月29日 上午9:47:17
 */
@Service
public class AppMyInfoService {
	@Autowired
	private AppMyInfoMapper appMyInfoMapper;
	@Autowired
	private RedisService redisService;

	
	public ResultBean myBaseInfo(String userId) throws SQLExecutorException {
		Map<String, Object> data = new HashMap<>();
		//获取个人信息
		UserInfo info = appMyInfoMapper.getUserInfo(userId);
		data.put("nickname", info.getNickname());
		data.put("avator", info.getIconurl() == null ? null : ConstantIF.PROJECT + info.getIconurl());
		
		//获取监督投诉电话
		String supervision_phone = redisService.get(ConstantIF.SUPERVISION_PHONE);
		data.put("supervision", supervision_phone);
		//获取联系我们电话
		String contact_phone = redisService.get(ConstantIF.CONTACT_PHONE);
		data.put("contact", contact_phone);
		
		return new ResultBean(data);
	}


	/**
	 * @Title: passEvaluating
	 * @Description: 通过风险评测检测
	 * @param userId
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月29日 下午5:25:54
	 */
	@Transactional
	public void passEvaluating(String userId) throws SQLExecutorException {
		appMyInfoMapper.passEvaluating(userId);
	}

	/**
	 * @Title: saveFeedBack
	 * @Description: 用户意见反馈
	 * @param fb
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月30日 下午12:23:22
	 */
	@Transactional
	public void saveFeedBack(FeedBack fb) throws SQLExecutorException {
		appMyInfoMapper.saveFeedBack(fb);
	}


	/**
	 * @Title: changeAvator
	 * @Description: 修改头像
	 * @param data
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月30日 下午2:28:03
	 */
	@Transactional
	public void changeAvator(Map<String, String> data) throws SQLExecutorException {
		appMyInfoMapper.changeAvator(data);
	}

	/**
	 * @Title: findMyCourseList
	 * @Description: 我购买的课程列表
	 * @param userid
	 * @param type
	 * @param page
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月12日 下午3:27:11
	 */
	public Map<String, Object> findMyCourseList(String userid, int type, int page) throws SQLExecutorException {
		//响应数据
		Map<String, Object> data = new HashMap<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("type", type);
		//根据条件获取总条数
		int count = appMyInfoMapper.getMyCourseCount(param);
		
		//总页数
		int totalPage;
		if(count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count/ConstantIF.PAGE_OFFSET + 1;
		}else {
			totalPage = count/ConstantIF.PAGE_OFFSET;
		}
		
		//总页数
		data.put("totalpage", totalPage);
		//当前页
		data.put("currentpage", page);
			
		//查看是否已超总页数
		if(totalPage < page) {
			data.put("courselist", new ArrayList<>());
			return data;
		}
		
		//设置请求起始位置
		param.put("start", (page-1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		
		//1、获取课程信息列表
		List<CourseList> courseList = appMyInfoMapper.getMyCourseList(param);
		
		for(CourseList cl : courseList) {
			cl.setCoursepic(ConstantIF.PROJECT + cl.getCoursepic());
		}
		
		data.put("courselist", courseList);
		return data;
	}
	
	/**
	 * @Title: findMyCourseStudy
	 * @Description: 我的学习进度表
	 * @param userid
	 * @param page
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:55:10
	 */
	public Map<String, Object> findMyCourseStudy(String userid, int page) throws SQLExecutorException {
		//响应数据
		Map<String, Object> data = new HashMap<>();
		
		//根据条件获取总条数
		int count = appMyInfoMapper.getMyCourseStudyCount(userid);
		
		//总页数
		int totalPage;
		if(count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count/ConstantIF.PAGE_OFFSET + 1;
		}else {
			totalPage = count/ConstantIF.PAGE_OFFSET;
		}
		
		//总页数
		data.put("totalpage", totalPage);
		//当前页
		data.put("currentpage", page);
		
		//查看是否已超总页数
		if(totalPage < page) {
			data.put("courselist", new ArrayList<>());
			return data;
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		//设置请求起始位置
		param.put("start", (page-1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		
		//1、获取课程信息列表
		List<CourseSutdy> courseList = appMyInfoMapper.getMyCourseStudyList(param);
		
		for(CourseSutdy cl : courseList) {
			cl.setCoursepic(ConstantIF.PROJECT + cl.getCoursepic());
		}
		
		data.put("courselist", courseList);
		return data;
	}

}
