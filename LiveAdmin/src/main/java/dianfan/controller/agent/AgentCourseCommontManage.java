package dianfan.controller.agent;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import dianfan.service.admin.CourseCommontService;

/**
 * @ClassName CourseCommontManage
 * @Description 课程评论
 * @author cjy
 * @date 2018年1月12日 上午10:42:21
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentCourseCommontManage {

	@Autowired
	private CourseCommontService commontService;
	
	/**
	 * @Title: dashboardCourseCommont
	 * @Description: 课程评论管理页
	 * @param courseid
	 * @param model
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午10:43:51
	 */
	@LogOp(method = "dashboardNews", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "咨讯管理页")
	@RequestMapping(value = "coursecommentpage")
	public String dashboardCourseCommont(@RequestParam String courseid, Model model) {
		model.addAttribute("courseid", courseid);
		return ConstantIF.ADMIN_COURSE + "course-commont";
	}
	
	/**
	 * @Title: courseCommontList
	 * @Description: 课程评论列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 上午11:48:12
	 */
	@LogOp(method = "courseCommontList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "课程评论列表")
	@RequestMapping(value = "coursecommentlist")
	public @ResponseBody DataTable courseCommontList(HttpServletRequest request) {
		DataTable table = new DataTable();
		try {
			String id = request.getParameter("courseid").trim();
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 反馈列表
			table = commontService.findCourseCommontList(id, search, start, length);
			table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		} catch (SQLExecutorException e) {
			// TODO 错误处理
		}
		return table;
	}
	
	/**
	 * @Title: delCourseCommont
	 * @Description: 删除评论
	 * @param id 评论id
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午1:41:17
	 */
	@LogOp(method = "delCourseCommont", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除评论")
	@RequestMapping(value = "delcoursecomment")
	public @ResponseBody ResultBean delCourseCommont(@RequestParam String id) {
		if (id == null || "".equals(id)) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}
		
		try {
			//根据id删除评论
			commontService.delCourseCommont(id);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
	
	
}
