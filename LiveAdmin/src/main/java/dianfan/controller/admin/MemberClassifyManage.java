package dianfan.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import dianfan.dao.mapper.admin.MemberMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.MemberService;

/**
 * @ClassName MemberClassifyManage
 * @Description 用户分类管理
 * @author cjy
 * @date 2018年3月15日 下午1:18:58
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class MemberClassifyManage {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;

	/**
	 * @Title: dashboardMemberClassify
	 * @Description: 用户分类管理首页
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午1:20:10
	 */
	@LogOp(method = "dashboardMemberClassify", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "用户分类管理首页")
	@RequestMapping(value = "memberclassify", method = RequestMethod.GET)
	public String dashboardMemberClassify() {
		return ConstantIF.ADMIN_MEMBER + "member-classify-list";
	}

	/**
	 * @Title: memberClassifyList
	 * @Description: 获取用户分类列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午1:22:45
	 */
	@LogOp(method = "memberList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取用户分类列表")
	@RequestMapping(value = "memberclassifylist", method = RequestMethod.POST)
	public @ResponseBody DataTable memberClassifyList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询用户列表
			table = memberService.findMemberClassify(start, length);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addMemberClassify
	 * @Description: 添加用户分类
	 * @param classname
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午1:51:56
	 */
	@LogOp(method = "addMemberClassify", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加用户分类")
	@RequestMapping(value = "addmemberclassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean addMemberClassify(String classname) {
		try {
			//1.检测新增分类名是否重复
			Boolean ck = memberMapper.checkClassifyName(classname);
			if(ck != null && ck) {
				//名称以重复
				return new ResultBean("022", ResultMsg.C_022);
			}
			//2、添加用户分类
			memberService.addMemberClassify(classname);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
	}
	
	/**
	 * @Title: editMemberClassify
	 * @Description: 修改用户分类
	 * @param classname
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年5月9日 上午10:27:36
	 */
	@LogOp(method = "editMemberClassify", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改用户分类")
	@RequestMapping(value = "editmemberclassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean editMemberClassify(String classname, String id) {
		try {
			//1.检测新增分类名是否重复
			Boolean ck = memberMapper.checkClassifyNameById(id, classname);
			if(ck != null && ck) {
				//名称以重复
				return new ResultBean("022", ResultMsg.C_022);
			}
			//2、修改用户分类
			memberService.editMemberClassify(id, classname);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
	}
	
	/**
	 * @Title: delClassify
	 * @Description: 删除用户分类
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年3月15日 下午2:25:31
	 */
	@LogOp(method = "delClassify", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除用户分类")
	@RequestMapping(value = "delclassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean delClassify(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		// 根据用户id删除用户
		try {
			memberService.delClassify(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
