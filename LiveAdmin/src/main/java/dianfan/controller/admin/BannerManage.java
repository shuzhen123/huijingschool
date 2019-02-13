package dianfan.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.BannerInfo;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.BannerService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName BannerManage
 * @Description 公众号轮播图管理
 * @author cjy
 * @date 2018年1月25日 下午5:22:16
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class BannerManage {

	@Autowired
	private BannerService bannerService;

	/**
	 * @Title: dashboardAdmin
	 * @Description: 公众号轮播图管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午2:46:06
	 */
	@LogOp(method = "dashboardMpBanner", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "公众号轮播图管理首页")
	@RequestMapping(value = "mpbanner", method = { RequestMethod.GET })
	public String dashboardMpBanner() {
		return ConstantIF.ADMIN_BANNER + "banner-list";
	}

	/**
	 * @Title: bannerList
	 * @Description: 公众号轮播图列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月25日 下午5:26:36
	 */
	@LogOp(method = "mpBannerList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "公众号轮播图列表")
	@RequestMapping(value = "mpbannerlist", method = { RequestMethod.POST })
	public @ResponseBody DataTable mpBannerList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			String search = request.getParameter(ConstantIF.DT_SEARCH).trim();
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 获取轮播图列表
			table = bannerService.findBanners(search, start, length);
			
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addMpBannerPage
	 * @Description: 公众号轮播图添加页
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 上午10:59:27
	 */
	@LogOp(method = "addMpBannerPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "公众号轮播图添加页")
	@RequestMapping(value = "addmpmpbannerpage", method = { RequestMethod.GET })
	public String addMpBannerPage() {
		return ConstantIF.ADMIN_BANNER + "banner-add";
	}

	/**
	 * @Title: addBanner
	 * @Description: 添加轮播图
	 * @param avator
	 * @param user
	 * @param request
	 * @throws:
	 * @time: 2018年1月26日 上午9:33:58
	 */
	@LogOp(method = "bannerList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加轮播图")
	@RequestMapping(value = "addmpbanner", method = { RequestMethod.POST })
	public @ResponseBody ResultBean addMpBanner(MultipartFile vfile, BannerInfo bi, HttpSession session) {
		try {
			String relativePath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
			// 上传
			String newfilename = FileUploadUtils.uploadOne(vfile, relativePath);
			bi.setPicurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			bannerService.addBanner(bi);
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: editMpBanner
	 * @Description: 修改轮播图
	 * @param vfile
	 * @param bi
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 上午11:49:22
	 */
	@LogOp(method = "editMpBanner", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改轮播图")
	@RequestMapping(value = "editmpbanner", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editMpBanner(MultipartFile vfile, BannerInfo bi, HttpSession session) {
		if (vfile != null && !vfile.isEmpty()) {
			try {
				// 相对路径
				String relativePath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				// 上传
				String newfilename = FileUploadUtils.uploadOne(vfile, relativePath);
				bi.setPicurl(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			} catch (IOException e) {
				// 上传失败
				return new ResultBean("500", ResultMsg.C_500);
			}
		}

		try {
			// 修改轮播图
			bannerService.editBanner(bi);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: editMpBannerPage
	 * @Description: 公众号图文轮播图修改页
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 下午12:23:58
	 */
	@LogOp(method = "editMpBannerPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "公众号图文轮播图修改页")
	@RequestMapping(value = "editmpbannerpage", method = { RequestMethod.GET })
	public String editMpBannerPage(@RequestParam String id, Model model) {
		// 获取图文详情
		try {
			BannerInfo bannerInfo = bannerService.getBannerInfo(id);
			model.addAttribute("info", bannerInfo);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.ADMIN_BANNER + "banner-edit";
	}

	/**
	 * @Title: mpBannerInfoPage
	 * @Description: 获取公众号图文轮播图数据页
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 下午12:23:58
	 */
	@LogOp(method = "editMpBannerPage", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取公众号图文轮播图数据页")
	@RequestMapping(value = "mpbannerinfopage", method = { RequestMethod.GET })
	public String mpBannerInfoPage(@RequestParam String id, Model model) {
		// 获取图文详情
		try {
			BannerInfo bannerInfo = bannerService.getBannerInfo(id);
			model.addAttribute("info", bannerInfo);
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.ADMIN_BANNER + "banner-edit";
	}

	/**
	 * @Title: delMpBanner
	 * @Description: 批量删除轮播图
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年1月26日 上午11:20:39
	 */
	@LogOp(method = "delMpBanner", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "批量删除轮播图")
	@RequestMapping(value = "delmpbanner", method = { RequestMethod.POST })
	public @ResponseBody ResultBean delMpBanner(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		// 根据轮播图id批量删除轮播图
		try {
			bannerService.delBanner(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
