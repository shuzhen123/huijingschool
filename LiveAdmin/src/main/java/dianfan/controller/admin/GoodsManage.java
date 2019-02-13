package dianfan.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
import dianfan.entities.DataTable;
import dianfan.entities.Goods;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.admin.GoodsService;
import dianfan.util.FileUploadUtils;

/**
 * @ClassName GoodsManage
 * @Description 礼物管理
 * @author cjy
 * @date 2018年2月12日 下午4:38:29
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class GoodsManage {

	@Autowired
	private GoodsService goodsService;

	/**
	 * @Title: dashboardGoods
	 * @Description: 礼物管理页
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午4:39:34
	 */
	@LogOp(method = "dashboardGoods", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "礼物管理页")
	@RequestMapping(value = "dashboardgoods", method = { RequestMethod.GET })
	public String dashboardGoods() {
		return ConstantIF.ADMIN_GOODS + "goods-list";
	}

	/**
	 * @Title: goodsList
	 * @Description: 礼物列表
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午4:41:27
	 */
	@LogOp(method = "goodsList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取礼物列表")
	@RequestMapping(value = "goodslist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody DataTable goodsList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			int start = Integer.valueOf(request.getParameter(ConstantIF.DT_START));
			int length = Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH));
			// 根据条件查询用户列表
			table = goodsService.findGoods(start, length);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}

	/**
	 * @Title: addGoods
	 * @Description: 添加礼物
	 * @param goods
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 上午9:23:42
	 */
	@LogOp(method = "addGoods", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "添加礼物")
	@RequestMapping(value = "addgoods", method = { RequestMethod.POST })
	public @ResponseBody ResultBean addGoods(MultipartFile img, Goods goods, HttpSession session) {
		if (goods.getGoodsname() == null || goods.getGoodsname().trim().isEmpty()) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		try {
			// 上传礼物icon
			if (img != null) {
				String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
				String newfilename = FileUploadUtils.uploadOne(img, realPath);
				goods.setIcon(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			}
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500);
		}

		goods.setGoodsname(goods.getGoodsname().trim());
		goods.setAgentprofit(goods.getAgentprofit() / 100);
		goods.setSalerprofit(goods.getSalerprofit() / 100);
		try {
			// 礼物名称重复性检测
			int count = goodsService.checkGoodsName(goods);
			if (count > 0) {
				// 名称重复
				return new ResultBean("020", ResultMsg.C_020);
			}
			// 添加礼物数据
			goodsService.addGoods(goods);
		} catch (SQLExecutorException e) {
			// TODO 错误处理
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: editGoods
	 * @Description: 修改礼物信息
	 * @param goods
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 上午9:52:05
	 */
	@LogOp(method = "editGoods", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "修改礼物信息")
	@RequestMapping(value = "editgoods", method = { RequestMethod.POST })
	public @ResponseBody ResultBean editGoods(MultipartFile img, Goods goods, HttpSession session) {
		if (goods.getGoodsname() == null || goods.getGoodsname().trim().isEmpty()) {
			return new ResultBean("501", ResultMsg.C_501);
		}

		try {
			String realPath = PropertyUtil.getProperty("domain") + PropertyUtil.getProperty("uploadimgpath");
			// 上传礼物icon
			if (img != null && !img.isEmpty()) {
				String newfilename = FileUploadUtils.uploadOne(img, realPath);
				goods.setIcon(PropertyUtil.getProperty("uploadimgpath") + newfilename);
			}
		} catch (IOException e) {
			// 上传失败
			return new ResultBean("500", ResultMsg.C_500);
		}

		goods.setGoodsname(goods.getGoodsname().trim());
		goods.setAgentprofit(goods.getAgentprofit() / 100);
		goods.setSalerprofit(goods.getSalerprofit() / 100);
		try {
			// 礼物名称重复性检测
			int count = goodsService.checkGoodsName(goods);
			if (count > 0) {
				// 名称重复
				return new ResultBean("020", ResultMsg.C_020);
			}
			// 添加礼物数据
			goodsService.editGoods(goods);
		} catch (SQLExecutorException e) {
			// TODO 错误处理
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}

	/**
	 * @Title: delGoods
	 * @Description: 删除礼物
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年2月23日 上午10:42:16
	 */
	@LogOp(method = "delGoods", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "删除礼物")
	@RequestMapping(value = "delgoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean delGoods(@RequestParam(value = "ids[]") String[] ids) {
		if (ids.length < 1) {
			// 参数错误
			return new ResultBean("501", ResultMsg.C_501);
		}

		List<String> lids = new ArrayList<>();

		for (String id : ids) {
			lids.add(id);
		}

		// 根据礼物id删除礼物
		try {
			goodsService.delGoods(lids);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
		return new ResultBean();
	}
}
