package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppCourseMapper;
import dianfan.dao.mapper.app.AppMyInfoMapper;
import dianfan.entities.AppRecommendCourse;
import dianfan.entities.BashMap;
import dianfan.entities.CourseCommentInfo;
import dianfan.entities.CourseCondition;
import dianfan.entities.CourseDetail;
import dianfan.entities.CourseDirectory;
import dianfan.entities.CourseList;
import dianfan.entities.CourseSutdy;
import dianfan.entities.PriceRange;
import dianfan.entities.ResultBean;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;

/**
 * @ClassName CourseService
 * @Description 课程相关服务
 * @author cjy
 * @date 2017年12月21日 下午4:19:29
 */

@Service
public class AppCourseService {
	@Autowired
	private AppCourseMapper appCourseMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private AppMyInfoMapper appMyInfoMapper;

	/**
	 * @Title: getCourseList
	 * @Description: 根据条件获取课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月25日 下午6:00:22
	 */
	public Map<String, Object> getCourseList(CourseCondition param) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		// 如果第一次请求，获取所有数据，其它只获取列表数据
		if (param.getPage() == 1) {
			// 获取讲师列表
			List<BashMap> teacherlist = appCourseMapper.findUsedTeacher();
			data.put("teacherlist", teacherlist);

			// 获取类型列表
			List<BashMap> type = appCourseMapper.findUsedCourseType();
			data.put("type", type);

			// 获取价格区间列表
			Set<PriceRange> range = appCourseMapper.findPriceRange();
			List<Map<String, String>> price_range = new ArrayList<>();
			for (PriceRange pr : range) {
				Map<String, String> map = new HashMap<>();
				map.put("id", pr.getId());
				if (pr.getLprice() == null) {
					map.put("name", "小于" + pr.getHprice());
				} else if (pr.getHprice() == null) {
					map.put("name", "大于" + pr.getLprice());
				} else {
					map.put("name", pr.getLprice() + " - " + pr.getHprice());
				}
				price_range.add(map);
			}
			data.put("price", price_range);
		} else {
			data.put("teacherlist", null);
			data.put("type", null);
			data.put("price", null);
		}

		// 根据条件获取课程总条数
		int count = appCourseMapper.getCourseCountByParam(param);

		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", param.getPage());

		// 查看是否已超总页数
		if (totalPage < param.getPage()) {
			data.put("courselist", new ArrayList<>());
			return data;
		}

		// 设置请求起始位置
		param.setPage((param.getPage() - 1) * ConstantIF.PAGE_OFFSET);
		param.setOffset(ConstantIF.PAGE_OFFSET);
		// 1、获取课程信息列表
		List<CourseList> courseList = appCourseMapper.getCourseList(param);
		// 课程id 集合
		List<String> courseids = new ArrayList<>();
		for (CourseList course : courseList) {
			courseids.add(course.getCourseid());
		}

		// 3、 获取点过赞的courseid
		if (param.getUserid() != null && !"".equals(param.getUserid().trim())) {
			Map<String, Object> praise_param = new HashMap<>();
			praise_param.put("userid", param.getUserid());
			praise_param.put("courseids", courseids);
			List<Map<String, String>> praiseStatus = appCourseMapper.getPraiseStatus(praise_param);
			if (praiseStatus != null) {
				// 点赞列表
				List<String> praiseList = new ArrayList<>();
				for (Map<String, String> map : praiseStatus) {
					praiseList.add(map.get("couseid"));
				}
				// 遍历课程信息列表，置入点赞状态
				ListIterator<CourseList> listIterator = courseList.listIterator();
				while (listIterator.hasNext()) {
					CourseList course = listIterator.next();

					if (praiseList.contains(course.getCourseid())) {
						course.setPraise("y");
					} else {
						course.setPraise("n");
					}
					if (course.getCoursepic() != null)
						course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
				}
			}
		} else {
			// 遍历课程信息列表，置入点赞状态
			ListIterator<CourseList> listIterator = courseList.listIterator();
			while (listIterator.hasNext()) {
				CourseList course = listIterator.next();
				course.setPraise("n");
				if (course.getCoursepic() != null)
					course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
			}
		}

		data.put("courselist", courseList);
		return data;
	}

	/**
	 * @Title: coursePraise
	 * @Description: 课程点赞
	 * @param courseid
	 *            课程id
	 * @param userid
	 *            用户id
	 * @return
	 * @throws SQLExecutorException
	 * @time: 2017年12月22日 下午1:15:24
	 */
	@Transactional
	public void coursePraise(String courseid, String userid) throws SQLExecutorException {
		// 检测当前课程是否被赞过
		Map<String, String> param = new HashMap<>();
		param.put("courseid", courseid);
		param.put("userid", userid);
		boolean status = appCourseMapper.findCourseThumbsup(param);
		if (!status) {
			// 未点过赞，点赞操作
			appCourseMapper.addCourseThumbsup(param);
			// 课程点赞量+1
			appCourseMapper.coursePraiseInc(courseid);
		}
	}

	/**
	 * @Title: getCourseDetail
	 * @Description: 获取课程详情
	 * @param courseid
	 *            课程id
	 * @param userid
	 *            用户id
	 * @param videoid
	 *            视频id
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午2:12:17
	 */
	public ResultBean getCourseDetailByCourseid(String courseid, String userid) throws SQLExecutorException {
		// 获取课程详情
		CourseDetail courseDetail = appCourseMapper.getCourseDetailByCourseid(courseid);
		if(courseDetail == null) {
			return new ResultBean("033", ResultMsg.C_033);
		}
		courseDetail.setIconurl(ConstantIF.PROJECT + courseDetail.getIconurl());
		//课程首个视频
		Video video = appCourseMapper.getCourseVideo(courseid);
		if (video != null) {
			// 有视频
			courseDetail.setVideoppicurl(ConstantIF.PROJECT + video.getVideoppicurl());
		}
		
		if (userid != null) {
			//已登录
			courseDetail.setLoginstatus('y');
			if (courseDetail.getCourselimit() == 2) {
				// 收费课程，判断有没有购买此课程
				boolean is_byed = redisService.Sismember(userid + ConstantIF.USER_COURSE_CACHE_SUFFIX, courseid);
				if (is_byed) {
					// 已购买此课程
					// 获取课程第一课
					if (video != null) {
						// 有视频
						courseDetail.setVideoid(video.getVideoid());
						courseDetail.setVideourl(video.getVideourl());
					}
	 				courseDetail.setBuy('y');
				} else {
					//未购买
					//判断当前用户是不是内部员工
					boolean b = appMyInfoMapper.checkUserIsSaler(userid);
					if(b) {
						if (video != null) {
							// 有视频
							courseDetail.setVideoid(video.getVideoid());
							courseDetail.setVideourl(video.getVideourl());
						}
						courseDetail.setBuy('y');
					}else {
						courseDetail.setBuy('n');
					}
				}
			} else {
				//课程免费
				courseDetail.setVideoid(video.getVideoid());
				courseDetail.setVideourl(video.getVideourl());
				courseDetail.setBuy('y');
			}
			
			// 获取点赞情况
			Map<String, String> param = new HashMap<>();
			param.put("courseid", courseid);
			param.put("userid", userid);
			boolean status = appCourseMapper.findCourseThumbsup(param);
			if (status) {
				// 已点过赞
				courseDetail.setPraise('y');
			} else {
				// 未点过赞
				courseDetail.setPraise('n');
			}
			
			//学习量+1
			appCourseMapper.courseStydyCountInc(courseid);
			//添加学习进度
			courseStudyProgress(userid, courseid);
		} else {
			//未登录
			courseDetail.setLoginstatus('n');
			// 未点过赞
			courseDetail.setPraise('n');
			courseDetail.setBuy('n');
		}
		return new ResultBean(courseDetail);
	}

	/**
	 * @Title: courseStudyProgress
	 * @Description: 学习进度
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:16:32
	 */
	@Transactional
	public void courseStudyProgress(String userid, String courseid) throws SQLExecutorException {
		//检测课程在不在学习进度中
		CourseSutdy study = appCourseMapper.checkcourseStudyInfo(userid, courseid);
		if(study == null) {
			//第一次学习，添加学习进度
			appCourseMapper.addStudyCourseInfo(userid, courseid);
		}else {
			//已在学习进度表中，更新学习时间
			appCourseMapper.updateStudyCourseInfo(userid, courseid);
		}
	}
	
	
	/**
	 * @Title: findRecommendCourse
	 * @Description: 课程详情内推荐课程
	 * @param teacherid
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午6:19:32
	 */
	public List<AppRecommendCourse> findRecommendCourse(String teacherid, String courseid) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("teacherid", teacherid);
		param.put("courseid", courseid);
		List<AppRecommendCourse> list = appCourseMapper.findTeacherRecommendCourse(param);
		for (AppRecommendCourse arc : list) {
			arc.setCoursepic(ConstantIF.PROJECT + arc.getCoursepic());
		}
		return list;
	}

	/**
	 * @Title: findCourseDirectory
	 * @Description: 获取课程视频列表
	 * @param courseid
	 *            当前课程id
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午3:54:04
	 */
	public List<CourseDirectory> findCourseDirectory(String courseid) throws SQLExecutorException {
		// 获取当前讲师的当前课程的视频列表
		List<CourseDirectory> list = appCourseMapper.findCourseDirectoryByCourseid(courseid);
		if (list != null && list.size() > 0) {
			for (CourseDirectory cd : list) {
				cd.setVideoppicurl(ConstantIF.PROJECT + cd.getVideoppicurl());
				cd.setVideourl(cd.getVideourl());
			}
		}
		return list;
	}

	/**
	 * @Title: courseVideoHitsTime
	 * @Description: 课程视频播放量+1
	 * @param videoid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月25日 下午2:12:45
	 */
	@Transactional
	public void courseVideoHitsTime(String videoid) throws SQLExecutorException {
		appCourseMapper.updateCourseVideoHits(videoid);
	}

	/**
	 * @Title: getCourseComment
	 * @Description: 课程评价
	 * @param courseid
	 *            课程id
	 * @param requestPage
	 *            请求的分页数
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月22日 下午5:06:24
	 */
	public ResultBean getCourseComment(String courseid, int requestPage) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		// 获取总条数
		int count = appCourseMapper.getCourseCommentCountByCourseid(courseid);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", requestPage);

		// 查看是否已超总页数
		if (totalPage < requestPage) {
			data.put("coursecomment", new ArrayList<>());
			return new ResultBean(data);
		}

		Map<String, Object> param = new HashMap<>();
		param.put("courseid", courseid);
		param.put("page", (requestPage - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		// 获取课程评价
		List<CourseCommentInfo> list = appCourseMapper.getCourseCommentByCourseid(param);
		
		for(CourseCommentInfo cci : list) {
			cci.setIconurl(ConstantIF.PROJECT + cci.getIconurl());
		}

		data.put("coursecomment", list);
		return new ResultBean(data);
	}

	/**
	 * @Title: courseWriteComment
	 * @Description: 课程评价
	 * @param userId
	 * @param courseid
	 * @param content
	 * @param star
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月30日 下午4:57:23
	 */
	@Transactional
	public void courseWriteComment(String userId, String courseid, String content, Integer star) throws SQLExecutorException {
		appCourseMapper.courseWriteComment(userId, courseid, content, star);
	}
}
