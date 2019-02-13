package dianfan.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppMyInfoMapper;
import dianfan.entities.Coupon;
import dianfan.entities.FeedBack;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppMyInfoService;
import dianfan.util.FileUploadUtils;
import dianfan.util.wx.Sign;

/**
 * @ClassName AppMyInfo
 * @Description 我的部分
 * @author cjy
 * @date 2018年3月29日 上午9:45:14
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppMyInfo {
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private AppMyInfoService appMyInfoService;
	@Autowired
	private AppMyInfoMapper appMyInfoMapper;
	
	public static Logger log = Logger.getLogger(AppMyInfo.class);
	
	/**
	 * @Title: myBaseInfo
	 * @Description: 我的基本信息
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 上午9:57:21
	 */
	@LogOp(method = "myBaseInfo", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的基本信息")
	@ApiOperation(value = "我的基本信息", httpMethod = "GET", notes = "我的基本信息", response = ResultBean.class)
	@RequestMapping(value = "/mybaseinfo", method = {RequestMethod.GET})
	public @ResponseBody ResultBean myBaseInfo(@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			return appMyInfoService.myBaseInfo(token.getUserId());
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: checkEvaluating
	 * @Description: 风险评测检测
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 下午2:14:12
	 */
	@LogOp(method = "checkEvaluating", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "风险评测检测")
	@ApiOperation(value = "风险评测检测", httpMethod = "GET", notes = "风险评测检测", response = ResultBean.class)
	@RequestMapping(value = "/checkevaluating", method = {RequestMethod.GET})
	public @ResponseBody ResultBean checkEvaluating(@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			Integer status = appMyInfoMapper.checkEvaluating(token.getUserId());
			return new ResultBean((status == null || status == 0) ? false : true);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: passEvaluating
	 * @Description: 通过风险评测检测
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 下午5:25:42
	 */
	@LogOp(method = "passEvaluating", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "通过风险评测检测")
	@ApiOperation(value = "通过风险评测检测", httpMethod = "GET", notes = "通过风险评测检测", response = ResultBean.class)
	@RequestMapping(value = "/passevaluating", method = {RequestMethod.GET})
	public @ResponseBody ResultBean passEvaluating(@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			appMyInfoService.passEvaluating(token.getUserId());
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: myCoupon
	 * @Description: 我的代金券
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 上午10:59:18
	 */
	@LogOp(method = "myCoupon", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的代金券")
	@ApiOperation(value = "我的代金券", httpMethod = "POST", notes = "我的代金券", response = ResultBean.class)
	@RequestMapping(value = "/mycoupon", method = {RequestMethod.POST})
	public @ResponseBody ResultBean myCoupon(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("类型(1:体验券, 2:代金券)") @RequestParam(value = "type") Integer type) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("userid", token.getUserId());
			param.put("type", type);
			List<Coupon> coupon = appMyInfoMapper.myCouponList(param);
			return new ResultBean(coupon);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: shareReg
	 * @Description: 我的分享-注册(微信)
	 * @param accesstoken
	 * @param type
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws:
	 * @time: 2018年3月29日 下午3:18:57
	 */
	@LogOp(method = "shareReg", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的分享-注册(微信)")
	@ApiOperation(value = "我的分享-注册(微信)", httpMethod = "GET", notes = "我的分享-注册(微信)", response = ResultBean.class)
	@RequestMapping(value = "/sharempreg", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean shareMpReg(@ApiParam("当前网页的URL，不包含#及其后面部分") @RequestParam(value = "url") String url) {
		//获取wx_jsapi_ticket
		try {
			String ticketToken = redisTokenService.getWxJsapiTicketToken();
			Map<String, String> sign = Sign.sign(ticketToken, url );
			sign.put("appId", PropertyUtil.getProperty("appid"));
			return new ResultBean(sign);
		} catch (IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: submitFeedBack
	 * @Description: 用户意见反馈
	 * @param accesstoken
	 * @param url
	 * @return
	 * @throws:
	 * @time: 2018年3月30日 上午10:07:37
	 */
	@LogOp(method = "submitFeedBack", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "用户意见反馈")
	@ApiOperation(value = "用户意见反馈", httpMethod = "POST", notes = "用户意见反馈", response = ResultBean.class)
	@RequestMapping(value = "/submitfeedback", method = {RequestMethod.POST})
	@UnCheckedFilter
	public @ResponseBody ResultBean submitFeedBack(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = false) String accesstoken,
			@ApiParam("反馈内容") @RequestParam(value = "content", required = true) String content,
			@ApiParam("图片（多张图片全路径使用英文“,”分隔）") @RequestParam(value = "img", required = false) String img,
			@ApiParam("联系方式") @RequestParam(value = "phone", required = false) String phone) {
		
		FeedBack fb = new FeedBack();
		if(!StringUtils.isEmpty(accesstoken)) {
			TokenModel token = redisTokenService.getToken(accesstoken);
			fb.setUserid(token.getUserId());
		}
		fb.setContent(content);
		fb.setTeloremail(phone);
		fb.setPicurl(img);
		
		try {
			appMyInfoService.saveFeedBack(fb);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: changeAvator
	 * @Description: 修改头像
	 * @param accesstoken
	 * @param img
	 * @return
	 * @throws:
	 * @time: 2018年3月30日 下午2:20:39
	 */
	@LogOp(method = "changeAvator", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "修改头像")
	@ApiOperation(value = "修改头像", httpMethod = "POST", notes = "修改头像", response = ResultBean.class)
	@RequestMapping(value = "/changeavator", method = {RequestMethod.POST})
	@UnCheckedFilter
	public @ResponseBody ResultBean changeAvator(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = false) String accesstoken,
			@ApiParam("头像图片") @RequestParam(value = "avator") String avator) {
		Map<String, String> data = new HashMap<>();
		TokenModel token = redisTokenService.getToken(accesstoken);
		data.put("userid", token.getUserId());
		
		if(StringUtils.isEmpty(avator)) {
			return new ResultBean("501", ResultMsg.C_501 + ": 头像数据不存在");
		}else {
			data.put("iconurl", avator.replaceAll(ConstantIF.PROJECT, ""));
		}
		
		try {
			appMyInfoService.changeAvator(data);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: myCourse
	 * @Description: 我购买的课程列表
	 * @param accesstoken
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年4月12日 下午3:13:35
	 */
	@LogOp(method = "myCourse", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我购买的课程列表")
	@ApiOperation(value = "我购买的课程列表", httpMethod = "POST", notes = "我购买的课程列表", response = ResultBean.class)
	@RequestMapping(value = "mycourse", method = {RequestMethod.POST})
	@UnCheckedFilter
	public @ResponseBody ResultBean myCourse(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("课程类型（3：精品课4：私教课）") @RequestParam(value = "type") int type,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		try {
			Map<String, Object> map = appMyInfoService.findMyCourseList(token.getUserId(), type, page);
			return new ResultBean(map);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
	}
	
	/**
	 * @Title: myCourseStudy
	 * @Description: 我的课程学习进度
	 * @param accesstoken
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年6月6日 上午11:45:27
	 */
	@LogOp(method = "myCourseStudy", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "我的课程学习进度")
	@ApiOperation(value = "我的课程学习进度", httpMethod = "POST", notes = "我的课程学习进度", response = ResultBean.class)
	@RequestMapping(value = "mycoursestudy", method = {RequestMethod.POST})
	public @ResponseBody ResultBean myCourseStudy(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			Map<String, Object> course = appMyInfoService.findMyCourseStudy(token.getUserId(), page);
			return new ResultBean(course);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
	}
	
	/**
	 * @Title: checkUserVip
	 * @Description: vip等级检测
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年5月17日 上午11:15:59
	 */
	@LogOp(method = "checkUserVip", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "vip等级检测")
	@ApiOperation(value = "vip等级检测", httpMethod = "POST", notes = "vip等级检测", response = ResultBean.class)
	@RequestMapping(value = "checkuservip", method = {RequestMethod.POST})
	public @ResponseBody ResultBean checkUserVip(@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			int vip = 0;
			boolean is_vip = appMyInfoMapper.checkUserVip(token.getUserId());
			if(!is_vip) {
				//判断是否为内部员工
				boolean is_saler = appMyInfoMapper.checkUserIsSaler(token.getUserId());
				if(is_saler) {
					vip = 1;
				}
			}else {
				vip = 1;
			}
			return new ResultBean(vip);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
	}
	
	/**
	 * @Title: uploadFile
	 * @Description: 上传文件
	 * @param accesstoken
	 * @param type
	 * @param file
	 * @return
	 * @throws JsonProcessingException 
	 * @throws:
	 * @time: 2018年5月31日 下午3:26:11
	 */
	@LogOp(method = "uploadFile", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "上传文件")
	@ApiOperation(value = "上传文件", httpMethod = "POST", notes = "上传文件", response = ResultBean.class)
	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
	@UnCheckedFilter
	public @ResponseBody ResultBean uploadFile(
			@ApiParam("类型") @RequestParam(value = "type") String type,
			@ApiParam("图片") @RequestParam(value = "file") MultipartFile file) throws JsonProcessingException {
		if(file != null && !file.isEmpty()) {
			
			log.error(file.getOriginalFilename());
			log.error(file.getName());
			
			//上传
			try {
				String sys_path = PropertyUtil.getProperty("domain");
				String file_path;
				if(StringUtils.equals("avator", type)) {
					//头像上传
					file_path = PropertyUtil.getProperty("uploadavatorpath");
				}else if(StringUtils.equals("feedback", type)) {
					//反馈图片
					file_path = PropertyUtil.getProperty("uploadfeedbackpath");
				}else {
					//其它
					file_path = PropertyUtil.getProperty("uploadimgpath");
				}
				String newfilename = FileUploadUtils.uploadOne(file, sys_path + file_path);
				return new ResultBean(ConstantIF.PROJECT + file_path + newfilename);
			} catch (IOException e) {
				//图片上传失败，程序继续
				return new ResultBean("500", ResultMsg.C_500);
			}
		}else {
			return new ResultBean("501", ResultMsg.C_501 + ": 数据不存在");
		}
		
	}
}
