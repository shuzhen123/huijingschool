package dianfan.controller.app;

import java.util.List;
import java.util.Map;

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
import dianfan.dao.mapper.app.AppNewsMapper;
import dianfan.entities.BashMap;
import dianfan.entities.Infomation;
import dianfan.entities.NewsList;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppNewsService;

/**
 * @ClassName AppNews
 * @Description 资讯
 * @author cjy
 * @date 2018年3月1日 上午10:57:02
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppNews {
	@Autowired
	private AppNewsMapper  appNewsMapper;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private AppNewsService appNewsService;
	
	/**
	 * @Title: indexNewsList
	 * @Description: 首页咨讯列表
	 * @return
	 * @throws:
	 * @time: 2018年1月24日 上午10:44:12
	 */
	@LogOp(method = "indexNewsList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "首页咨讯列表")
	@ApiOperation(value = "首页咨讯列表", httpMethod = "GET", notes = "首页咨讯列表", response = ResultBean.class)
	@RequestMapping(value = "indexnewslist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean indexNewsList() {
		try {
			List<NewsList> list = appNewsMapper.findNewsLimit();
			for(NewsList nl : list) {
				nl.setPicurl(ConstantIF.PROJECT + nl.getPicurl());
			}
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: newsModelList
	 * @Description: 咨讯类型列表
	 * @return
	 * @throws:
	 * @time: 2018年3月1日 上午10:59:02
	 */
	@LogOp(method = "newsModelList",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "咨讯类型列表")
	@ApiOperation(value = "咨讯类型列表", httpMethod = "GET", notes = "咨讯类型列表", response = ResultBean.class)
	@RequestMapping(value = "newsmodellist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean newsModelList() {
		try {
			List<BashMap> modelList =  appNewsMapper.findNewsModelList();
			return new ResultBean(modelList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: newsList
	 * @Description: 咨讯列表
	 * @param infoid
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年3月1日 上午11:56:10
	 */
	@LogOp(method = "newsList",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "咨讯列表")
	@ApiOperation(value = "咨讯列表", httpMethod = "GET", notes = "咨讯列表", response = ResultBean.class)
	@RequestMapping(value = "newslist", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean newsList(
			@ApiParam("咨讯类型id") @RequestParam(value = "modelid") String modelid,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) String page) {
		try {
			// 分页数据
			Integer request_page = Integer.parseInt(page);
			if (request_page == null || request_page <= 1) {
				request_page = 1;
			}
			Map<String, Object> newsList = appNewsService.findNewsList(modelid, request_page);
			return new ResultBean(newsList);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: newsDetail
	 * @Description: 咨讯详情
	 * @param infoid
	 * @return
	 * @throws:
	 * @time: 2018年3月1日 上午11:57:43
	 */
	@LogOp(method = "newsDetail",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "咨讯详情")
	@ApiOperation(value = "咨讯详情", httpMethod = "GET", notes = "咨讯详情", response = ResultBean.class)
	@RequestMapping(value = "newsdetail", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean newsDetail(@ApiParam("咨讯id") @RequestParam(value = "infoid") String infoid) {
		try {
			Infomation newsDetail = appNewsService.findNewsDetail(infoid);
			return new ResultBean(newsDetail);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: newsComment
	 * @Description: 咨讯评论列表
	 * @param infoid
	 * @return
	 * @throws:
	 * @time: 2018年3月1日 下午1:23:35
	 */
	@LogOp(method = "newsComment",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "咨讯评论列表")
	@ApiOperation(value = "咨讯评论列表", httpMethod = "GET", notes = "咨讯评论列表", response = ResultBean.class)
	@RequestMapping(value = "newscomment", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean newsComment(@ApiParam("咨讯id") @RequestParam(value = "infoid") String infoid,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			Map<String, Object> comment = appNewsService.findNewsComment(infoid, page);
			return new ResultBean(comment);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: sendNewsComment
	 * @Description: 发表咨讯评论
	 * @param infoid
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年3月5日 上午9:34:41
	 */
	@LogOp(method = "sendNewsComment",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "发表咨讯评论")
	@ApiOperation(value = "发表咨讯评论", httpMethod = "POST", notes = "发表咨讯评论", response = ResultBean.class)
	@RequestMapping(value = "sendnewscomment", method=RequestMethod.POST)
	public @ResponseBody ResultBean sendNewsComment(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("咨讯id") @RequestParam(value = "infoid") String infoid,
			@ApiParam("评论id") @RequestParam(value = "commid", required = false) String commid,
			@ApiParam("评论内容") @RequestParam(value = "content") String content) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		boolean pass = redisTokenService.checkToken(token);
		if(!pass) {
			//token验证失败
			return new ResultBean("010", ResultMsg.C_010);
		}
		
		//评论内容空值检测
		if(content == null || content.trim().isEmpty()) {
			return new ResultBean("025", ResultMsg.C_025);
		}
		
		try {
			//发表评论
			appNewsService.sendNewsComment(token.getUserId(), infoid, commid, content);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: vipNewsList
	 * @Description: vip咨讯列表
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年3月2日 上午9:11:13
	 */
	@LogOp(method = "vipNewsList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "vip咨讯列表")
	@ApiOperation(value = "vip咨讯列表", httpMethod = "POST", notes = "vip咨讯列表", response = ResultBean.class)
	@RequestMapping(value = "vipnewslist", method=RequestMethod.POST)
	public @ResponseBody ResultBean vipNewsList(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			TokenModel token = redisTokenService.getToken(accesstoken);
			//根据页数获取vip咨讯列表
			Map<String, Object> newsInfo = appNewsService.findVipNewsInfo(token.getUserId(), page);
			return new ResultBean(newsInfo);
		} catch (SQLExecutorException e) {
			return new ResultBean("022", ResultMsg.C_022);
		}
	}
	
	/**
	 * @Title: vipNewsDetail
	 * @Description: vip咨讯详情
	 * @param accesstoken
	 * @param infoid
	 * @return
	 * @throws:
	 * @time: 2018年3月5日 上午9:32:07
	 */
	@LogOp(method = "vipNewsDetail",  logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "vip咨讯详情")
	@ApiOperation(value = "vip咨讯详情", httpMethod = "POST", notes = "vip咨讯详情", response = ResultBean.class)
	@RequestMapping(value = "vipnewsdetail", method=RequestMethod.POST)
	public @ResponseBody ResultBean vipNewsDetail(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("vip咨讯id") @RequestParam(value = "infoid") String infoid) {
		try {
			VipInfomation newsDetail = appNewsService.findVipNewsDetail(infoid);
			return new ResultBean(newsDetail);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
}
