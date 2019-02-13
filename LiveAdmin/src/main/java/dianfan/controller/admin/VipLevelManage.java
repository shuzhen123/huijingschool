package dianfan.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.VipLevelMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.VipLevelService;

/**
 * @ClassName VipLevelManage
 * @Description vip赠送管理
 * @author cjy
 * @date 2018年4月4日 下午5:06:27
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class VipLevelManage {
	@Autowired
	private VipLevelService vipLevelService;
	@Autowired
	private VipLevelMapper vipLevelMapper;
	
	/**
	 * @Title: dashboardVipLevelType
	 * @Description: vip赠送管理页
	 * @return
	 * @throws:
	 * @time: 2018年4月4日 下午5:11:08
	 */
	@LogOp(method = "dashboardVipLevelType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip赠送管理页")
	@RequestMapping(value = "viplevelpage", method = {RequestMethod.GET})
	public String dashboardVipLevelType() {
		return ConstantIF.ADMIN_VIP_LEVEL + "vip-level-list";
	}
	
	/**
	 * @Title: vipLevelList
	 * @Description: vip赠送列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月31日 上午10:24:05
	 */
	@LogOp(method = "vipLevelList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip赠送列表")
	@RequestMapping(value = "viplevellist", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody DataTable vipLevelList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));
			// 根据条件查询列表
			table = vipLevelService.findVipLevelList(param);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: addVipLevelType
	 * @Description: vip等级添加
	 * @param name
	 * @param money
	 * @param days
	 * @return
	 * @throws:
	 * @time: 2018年4月4日 下午6:00:53
	 */
	@LogOp(method = "addVipLevelType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip等级添加")
	@RequestMapping(value = "addviplevel", method=RequestMethod.POST)
	public @ResponseBody ResultBean addVipLevelType(String name, BigDecimal money, Integer days) {
		try {
			//类型名称重复性检测
			Boolean bool = vipLevelMapper.checkVipLevelName(name);
			if(bool) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			vipLevelService.addVipLevel(name, money, days);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: editVipLevelType
	 * @Description: vip等级修改
	 * @param id
	 * @param name
	 * @param money
	 * @param days
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 上午9:23:42
	 */
	@LogOp(method = "editVipLevelType", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "vip等级添加")
	@RequestMapping(value = "editviplevel", method=RequestMethod.POST)
	public @ResponseBody ResultBean editVipLevelType(VipLevel level) {
		try {
			//类型名称重复性检测
			Boolean bool = vipLevelMapper.checkVipLevelNameById(level);
			if(bool) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			vipLevelService.editVipLevel(level);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: delVipModel
	 * @Description: 批量删除vip等级
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年4月9日 上午10:17:12
	 */
	@LogOp(method = "delVipModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除vip等级")
	@RequestMapping(value = "delvipmodel")
	public @ResponseBody ResultBean delVipModel(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		// 根据id删除模块
		try {
			vipLevelService.delVipLevel(ids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
