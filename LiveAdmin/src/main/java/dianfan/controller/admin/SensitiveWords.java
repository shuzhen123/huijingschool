package dianfan.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.SensitiveWordMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.SensitiveWordService;

/**
 * @ClassName SensitiveWords
 * @Description 敏感文字
 * @author cjy
 * @date 2018年3月10日 上午9:53:21
 */

@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class SensitiveWords {
	@Autowired
	private SensitiveWordMapper sensitiveWordMapper;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	/**
	 * @Title: dashboardSensitive
	 * @Description: 敏感文字列表页 
	 * @return
	 * @throws:
	 * @time: 2018年3月10日 上午10:01:09
	 */
	@LogOp(method = "dashboardSensitive",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "敏感文字列表页")
	@RequestMapping(value = "dashboardsensitive", method = RequestMethod.GET)
	public String dashboardSensitive(Model model) {
		try {
			List<BashMap> words = sensitiveWordMapper.findWords();
			model.addAttribute("words", words);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.ADMIN_SENSITIVE + "sensitive-word-list";
	}
	
	/**
	 * @Title: addSensitiveWord
	 * @Description: 添加敏感文字
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月10日 上午10:31:27
	 */
	@LogOp(method = "addSensitiveWord",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加敏感文字")
	@RequestMapping(value = "addsensitiveword", method = RequestMethod.POST)
	public @ResponseBody ResultBean addSensitiveWord(String word) {
		if(word == null || word.trim().isEmpty()) {
			return new ResultBean("501", ResultMsg.C_501);
		}
		word = word.trim();
		try {
			//检测文字是否已添加
			int count = sensitiveWordMapper.checkSensitiveWord(word);
			if(count > 0) {
				//文字已存在
				return new ResultBean("020", ResultMsg.C_020);
			}
			//正常添加
			BashMap bm = sensitiveWordService.addSensitiveWord(word);
			return new ResultBean(bm);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: stopSensitiveWord
	 * @Description: 停用敏感文字
	 * @param word
	 * @return
	 * @throws:
	 * @time: 2018年3月10日 上午10:42:39
	 */
	@LogOp(method = "stopSensitiveWord",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "停用敏感文字")
	@RequestMapping(value = "stopsensitiveword", method = RequestMethod.POST)
	public @ResponseBody ResultBean stopSensitiveWord(String wordid) {
		try {
			sensitiveWordService.stopSensitiveWord(wordid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/* **************************合规设置************************ */
	/**
	 * @Title: dashboardCompliance
	 * @Description: 合规列表页
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午11:28:58
	 */
	@LogOp(method = "dashboardCompliance",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "合规列表页")
	@RequestMapping(value = "dashboardcompliance", method = RequestMethod.GET)
	public String dashboardCompliance(Model model) {
		return ConstantIF.ADMIN_SENSITIVE + "compliance-list";
	}
	
	/**
	 * @Title: complianceList
	 * @Description: 合规列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 上午11:31:06
	 */
	@LogOp(method = "complianceList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "合规列表")
	@RequestMapping(value = "compliancelist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DataTable complianceList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			// 根据条件查询用户列表
			table = sensitiveWordService.findCompliance(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: addCompliance
	 * @Description: 添加合规处罚项
	 * @param type
	 * @param money
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午12:06:15
	 */
	@LogOp(method = "addCompliance",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加合规处罚项")
	@RequestMapping(value = "addcompliance", method = RequestMethod.POST)
	public @ResponseBody ResultBean addCompliance(Integer type, BigDecimal money) {
		try {
			sensitiveWordService.addCompliance(type, money);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: editCompliance
	 * @Description: 修改合规处罚项
	 * @param id
	 * @param type
	 * @param money
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午1:48:43
	 */
	@LogOp(method = "editCompliance",  logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改合规处罚项")
	@RequestMapping(value = "editcompliance", method = RequestMethod.POST)
	public @ResponseBody ResultBean editCompliance(String id, Integer type, BigDecimal money) {
		try {
			sensitiveWordService.editCompliance(id, type, money);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: delCompliance
	 * @Description: 删除合规
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年3月26日 下午1:58:21
	 */
	@LogOp(method = "delCompliance", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除合规")
	@RequestMapping(value = "delcompliance")
	public @ResponseBody ResultBean delCompliance(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		try {
			sensitiveWordService.delCompliance(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
