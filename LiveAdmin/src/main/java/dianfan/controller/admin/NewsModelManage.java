package dianfan.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.NewsService;

/**
 * @ClassName NewsManage
 * @Description 咨讯模块管理
 * @author cjy
 * @date 2018年1月9日 下午5:29:03
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class NewsModelManage {

	@Autowired
	private NewsService newsService;
	/**
	 * @Title: dashboardNewsModel
	 * @Description: 咨讯模块管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:29:58
	 */
	@LogOp(method = "dashboardNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "咨讯模块管理页")
	@RequestMapping(value = "newsmodel")
	public String dashboardNewsModel() {
		return ConstantIF.ADMIN_NEWS + "news-model-list";
	}
	
	/**
	 * @Title: newsModelList
	 * @Description: 咨讯模块列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:41:19
	 */
	@LogOp(method = "newsModelList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "咨讯模块列表")
	@RequestMapping(value = "newsmodellist")
	public @ResponseBody DataTable newsModelList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据卡券列表
			table = newsService.findNewsModel(search, start, length);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	/**
	 * @Title: delNewsModel
	 * @Description: 批量删除模块
	 * @param modelids
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午6:04:58
	 */
	@LogOp(method = "delNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除模块")
	@RequestMapping(value = "delnewsmodel")
	public @ResponseBody ResultBean delNewsModel(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		List<String> lids = new ArrayList<>();
		
		for(String id : ids) {
			lids.add(id);
		}
		
		// 根据id删除模块
		try {
			newsService.delNewsModel(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: addNewsModel
	 * @Description: 添加新模块
	 * @param modelname
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午9:31:21
	 */
	@LogOp(method = "addNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加新模块")
	@RequestMapping(value = "addnewsmodel")
	public @ResponseBody ResultBean addNewsModel(@RequestParam(value = "modelname") String modelname) {
		if (modelname == null || "".equals(modelname.trim())) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		try {
			//检测重复名称
			boolean boo = newsService.findModelByName(null, modelname);
			if(boo) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			//满足添加条件
			newsService.addNewsModel(modelname);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: editNewsModel
	 * @Description: 修改模块
	 * @param modelname
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午10:07:29
	 */
	@LogOp(method = "editNewsModel", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改模块")
	@RequestMapping(value = "editnewsmodel")
	public @ResponseBody ResultBean editNewsModel(@RequestParam(value = "modelname") String modelname, @RequestParam(value = "id") String id) {
		if (modelname == null || "".equals(modelname.trim())) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		try {
			//检测重复名称
			boolean boo = newsService.findModelByName(id, modelname);
			if(boo) {
				return new ResultBean("020", ResultMsg.C_020);
			}
			//满足修改条件
			newsService.editNewsModel(id, modelname);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
}
