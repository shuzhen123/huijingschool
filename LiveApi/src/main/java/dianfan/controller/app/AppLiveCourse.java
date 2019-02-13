package dianfan.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppLiveCourseMapper;
import dianfan.entities.LiveCourse;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppLiveCourseService;

/**
 * @ClassName AppLiveCourse
 * @Description 直播
 * @author cjy
 * @date 2018年5月4日 下午2:15:18
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppLiveCourse {
	/**
	 * 注入：AppCourseService
	 */
	@Autowired
	private AppLiveCourseService appLiveCourseService;
	/**
	 * 注入：AppCourseMapper
	 */
	@Autowired
	private AppLiveCourseMapper appLiveCourseMapper;
	/**
	 * 注入：RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入：RedisService
	 */
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: liveCourseLiving
	 * @Description: 
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年5月4日 下午2:28:02
	 */
	@LogOp(method = "liveCourseLiving", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播页顶部正在直播的课程")
	@ApiOperation(value = "直播页顶部正在直播的课程", httpMethod = "GET", notes = "直播页顶部正在直播的课程", response = ResultBean.class)
	@RequestMapping(value = "livecourseliving", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean liveCourseLiving(@ApiParam(value = "直播课程类型（1:免费直播，2:vip实战直播）") @RequestParam(value = "type") int type) {
		try {
			LiveCourse liveCourse = appLiveCourseMapper.findLiveCourseLiving(type);
			if (liveCourse != null) {
				liveCourse.setCoursepic(ConstantIF.PROJECT + liveCourse.getCoursepic());
			}else {
				liveCourse = new LiveCourse();
				if(type == 1) {
					liveCourse.setCoursepic(ConstantIF.PROJECT + redisService.get("livelogol"));
				}else {
					liveCourse.setCoursepic(ConstantIF.PROJECT + redisService.get("livelogor"));
				}
			}
			return new ResultBean(liveCourse);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: pastLiveCourseList
	 * @Description: 往期直播列表
	 * @param type
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年5月4日 下午3:09:03
	 */
	@LogOp(method = "pastLiveCourseList",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "往期直播列表")
	@ApiOperation(value = "往期直播列表", httpMethod = "GET", notes = "往期直播列表", response = ResultBean.class)
	@RequestMapping(value = "pastlivecourselist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean pastLiveCourseList(
			@ApiParam("直播课程类型（1:免费直播，2:vip实战直播）") @RequestParam(value = "type") int type,
		@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("type", type);
			param.put("page", page);
			Map<String, Object> liveCourse = appLiveCourseService.findPastLiveCourse(param);
			return new ResultBean(liveCourse);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: weekLiveCourseList
	 * @Description: 本周某天直播节目单列表
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年5月4日 下午3:09:03
	 */
	@LogOp(method = "weekLiveCourseList",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "本周某天直播节目单列表")
	@ApiOperation(value = "本周某天直播节目单列表", httpMethod = "GET", notes = "本周某天直播节目单列表", response = ResultBean.class)
	@RequestMapping(value = "weeklivecourselist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean weekLiveCourseList(
			@ApiParam("直播课程类型（1:免费直播，2:vip实战直播）") @RequestParam(value = "type") int type,
			@ApiParam("本周某天的日期") @RequestParam(value = "date") String date) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("type", type);
			param.put("date", date);
			List<LiveCourse> liveCourseList = appLiveCourseMapper.findWeekLiveCourse(param);
			return new ResultBean(liveCourseList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: liveCourseRoom
	 * @Description: 直播间
	 * @param accesstoken
	 * @param courseid
	 * @return
	 * @throws:
	 * @time: 2018年5月5日 上午10:31:31
	 */
	@LogOp(method = "liveCourseRoom",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播间")
	@ApiOperation(value = "直播间", httpMethod = "POST", notes = "直播间", response = ResultBean.class)
	@RequestMapping(value = "livecourseroom", method=RequestMethod.POST)
	public @ResponseBody ResultBean liveCourseRoom(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("直播课程id") @RequestParam(value = "courseid") String courseid,
			@ApiParam("客户端类型") @RequestParam(value = "clienttype", defaultValue="1", required = false) String clienttype) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			return appLiveCourseService.liveCourseRoom(token.getUserId(), courseid, clienttype);
		} catch (SQLExecutorException | ParseException | IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: liveCourseProgram
	 * @Description: 直播间内节目单列表
	 * @param teacherid
	 * @return
	 * @throws:
	 * @time: 2018年5月5日 下午12:07:11
	 */
	@LogOp(method = "liveCourseProgram",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播间内节目单列表")
	@ApiOperation(value = "直播间内节目单列表", httpMethod = "GET", notes = "直播间内节目单列表", response = ResultBean.class)
	@RequestMapping(value = "livecourseprogram", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean liveCourseProgram(@ApiParam("讲师id") @RequestParam(value = "teacherid") String teacherid) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("teacherid", teacherid);
			List<LiveCourse> liveCourseList = appLiveCourseMapper.findWeekLiveCourse(param);
			return new ResultBean(liveCourseList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: pastLiveCourseList
	 * @Description: 直播间内往期直播列表
	 * @param type
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年5月4日 下午3:09:03
	 */
	@LogOp(method = "teaPastLiveCourseList",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播间内往期直播列表")
	@ApiOperation(value = "直播间内往期直播列表", httpMethod = "GET", notes = "直播间内往期直播列表", response = ResultBean.class)
	@RequestMapping(value = "teapastlivecourselist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean teaPastLiveCourseList(
			@ApiParam("讲师id") @RequestParam(value = "teacherid") String teacherid,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("teacherid", teacherid);
			param.put("page", page);
			Map<String, Object> liveCourse = appLiveCourseService.findPastLiveCourse(param);
			return new ResultBean(liveCourse);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
