package dianfan.controller.app;

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
import dianfan.dao.mapper.app.AppGeniusViewMapper;
import dianfan.entities.Infomation;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.app.AppGeniusViewService;

/**
 * @ClassName AppGeniusView
 * @Description 跟牛人
 * @author cjy
 * @date 2018年4月2日 上午10:49:16
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppGeniusView {
	@Autowired
	private AppGeniusViewService appGeniusViewService;
	@Autowired
	private AppGeniusViewMapper appGeniusViewMapper;

	/**
	 * @Title: stockInfoList
	 * @Description: 牛人观点列表
	 * @param accesstoken
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年4月2日 上午9:22:51
	 */
	@LogOp(method = "geniusViewList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "牛人观点列表")
	@ApiOperation(value = "牛人观点列表", httpMethod = "GET", notes = "牛人观点列表", response = ResultBean.class)
	@RequestMapping(value = "/geniusviewlist", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean geniusViewList(
			/*@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,*/
			@ApiParam("请求页") @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
		try {
			Map<String, Object> list = appGeniusViewService.findGeniusViewList(page);
			return new ResultBean(list);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: geniusViewDetail
	 * @Description: 牛人观点详情
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年4月2日 下午12:04:24
	 */
	@LogOp(method = "geniusViewDetail", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "牛人观点详情")
	@ApiOperation(value = "牛人观点详情", httpMethod = "GET", notes = "牛人观点详情", response = ResultBean.class)
	@RequestMapping(value = "/geniusviewdetail", method = {RequestMethod.GET})
	@UnCheckedFilter
	public @ResponseBody ResultBean geniusViewDetail(@ApiParam("观点id") @RequestParam(value = "id") String id) {
		try {
			Infomation info = appGeniusViewMapper.appGeniusViewMapper(id);
			return new ResultBean(info);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	
}
