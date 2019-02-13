package dianfan.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.SharesService;

/**
 * @ClassName SharesManage
 * @Description 诊股
 * @author cjy
 * @date 2018年3月28日 下午1:20:22
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class SharesManage {
	@Autowired
	private SharesService sharesService;
	
	/**
	 * @Title: dashboardShares
	 * @Description: 诊股管理页
	 * @return
	 * @throws:
	 * @time: 2018年3月28日 下午1:21:13
	 */
	@LogOp(method = "dashboardShares", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "诊股管理页")
	@RequestMapping(value = "shares", method = {RequestMethod.GET})
	public String dashboardShares() {
		return ConstantIF.ADMIN_SHARES + "shares-list";
	}
	
	/**
	 * @Title: sharesList
	 * @Description: 诊股列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月28日 下午1:24:45
	 */
	@LogOp(method = "sharesList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "诊股列表")
	@RequestMapping(value = "shareslist", method = {RequestMethod.POST})
	public @ResponseBody DataTable sharesList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			
			// 诊股列表
			table = sharesService.findShares(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: sharesAnswer
	 * @Description: 诊股设置已回答
	 * @param sharesid
	 * @return
	 * @throws:
	 * @time: 2018年5月3日 下午5:42:16
	 */
	@LogOp(method = "sharesAnswer", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "诊股设置已回答")
	@RequestMapping(value = "sharesanswer", method = {RequestMethod.POST})
	public @ResponseBody ResultBean sharesAnswer(String sharesid) {
		try {
			sharesService.sharesAnswer(sharesid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
