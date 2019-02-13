package dianfan.controller.admin;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.MemberMapper;
import dianfan.entities.DataTable;
import dianfan.entities.UserAnswerList;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.admin.MemberService;

/**
 * @ClassName MemberManage
 * @Description 用户管理
 * @author cjy
 * @date 2018年1月3日 上午11:27:29
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class MemberManage {
	@Autowired
	private MemberService memberService;

	/**
	 * @Title: dashboardMember
	 * @Description: 用户管理首页
	 * @return
	 * @throws:
	 * @time: 2018年5月9日 上午9:38:26
	 */
	@LogOp(method = "dashboard", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "用户管理首页")
	@RequestMapping(value = "member", method = RequestMethod.GET)
	public String dashboardMember() {
		return ConstantIF.ADMIN_MEMBER + "member-list";
	}

	/**
	 * @Title: memberList
	 * @Description: 获取用户列表
	 * @return
	 * @throws:
	 * @time: 2018年1月3日 下午1:29:09
	 */
	@LogOp(method = "memberList", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "获取用户列表")
	@RequestMapping(value = "memberlist", method = RequestMethod.POST)
	public @ResponseBody DataTable memberList(HttpServletRequest request) {
		DataTable table = new DataTable();
		table.setDraw(request.getParameter(ConstantIF.DT_DRAW));
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("start", Integer.valueOf(request.getParameter(ConstantIF.DT_START)));
			param.put("length", Integer.valueOf(request.getParameter(ConstantIF.DT_LENGTH)));

			// 用户名
			param.put("nickname", request.getParameter("nickname"));
			// 作者
			param.put("phone", request.getParameter("phone"));
			// 使用时间搜索
			param.put("starttime", request.getParameter("starttime"));
			param.put("endtime", request.getParameter("endtime"));

			// 根据条件查询用户列表
			table = memberService.findUsers(param);
		} catch (SQLExecutorException e) {
			table.setError(ResultMsg.C_500);
		}
		return table;
	}
	
	

	
	/*=-==========================================================*/
	/**
	 * @Title: exportMemberToExcel
	 * @Description: 用户导出
	 * @return
	 * @throws IOException
	 * @throws ExcelException
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月9日 上午11:40:18
	 */
	/*
	 * @LogOp(method = "exportMemberToExcel", logtype = ConstantIF.LOG_TYPE_2,
	 * userid = "", description = "用户导出")
	 * 
	 * @RequestMapping(value = "exportmembertoexcel", method = RequestMethod.GET)
	 * public void exportMemberToExcel(HttpServletResponse response) { DataTable dt
	 * = null; try { dt = memberService.findUsers("", 0, -1, "1"); } catch
	 * (SQLExecutorException e1) { try { response.sendError(500); } catch
	 * (IOException e) {} }
	 * 
	 * if (dt == null || dt.getRecordsTotal() == 0) { try {
	 * response.sendError(ResultMsg.HEADER_900); } catch (IOException e) {} }else {
	 * HSSFWorkbook workbook = null; try { String[] headers = {"序号", "用户昵称", "手机号码",
	 * "用户等级", "注册时间"}; //获取POI对象 Map<String, Object> map =
	 * ExportExcel.getSheet("用户信息", "用户信息表", headers); workbook = (HSSFWorkbook)
	 * map.get("wb"); HSSFSheet sheet = (HSSFSheet) map.get("sheet");
	 * 
	 * List<UserInfo> users = (List<UserInfo>) dt.getData(); // 创建表中数据行-增加样式-赋值
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); for(int i
	 * = 0; i < users.size(); i++) { HSSFRow row3 = sheet.createRow(i + 2);
	 * row3.createCell(0).setCellValue(i + 1);
	 * row3.createCell(1).setCellValue(users.get(i).getNickname());
	 * row3.createCell(2).setCellValue(users.get(i).getTelno());
	 * if(users.get(i).getIs_vip()) { row3.createCell(3).setCellValue("vip"); }else
	 * { row3.createCell(3).setCellValue("普通用户"); }
	 * row3.createCell(4).setCellValue(sdf.format(users.get(i).getRegistertime()));
	 * } OutputStream output = response.getOutputStream(); response.reset(); //
	 * response.setHeader("Content-disposition", "attachment; filename=" + "用户信息" +
	 * format.format(new Date()) + GenRandomNumUtil.getRandomNum(4) + ".xls");
	 * response.setContentType("application/msexcel"); workbook.write(output);
	 * workbook.close(); output.flush(); output.close(); } catch (SecurityException
	 * | IllegalArgumentException | IOException e) { try { response.sendError(500);
	 * } catch (IOException e1) { }finally { try { workbook.close(); } catch
	 * (IOException e1) { workbook = null; } } }finally { if(workbook != null) { try
	 * { workbook.close(); } catch (IOException e) { workbook = null; } } }
	 * 
	 * }
	 * 
	 * 
	 * try { // 检测数据数量 DataTable dt = memberService.findUsers("", 0, -1, null); if
	 * (dt.getRecordsTotal() == 0) { response.sendError(ResultMsg.HEADER_900); }else
	 * { // 创建HSSFWorkbook对象(excel的文档对象) HSSFWorkbook wb = new HSSFWorkbook(); //
	 * 建立新的sheet对象（excel的表单） HSSFSheet sheet = wb.createSheet("用户信息"); //
	 * 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个 HSSFRow row1 =
	 * sheet.createRow(0); // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个 HSSFCell cell =
	 * row1.createCell(0); // 设置单元格内容 cell.setCellValue("用户信息表"); //
	 * 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列 sheet.addMergedRegion(new
	 * CellRangeAddress(0, 0, 0, 4)); //设置缺省列高
	 * sheet.setDefaultRowHeightInPoints(10); //设置缺省列宽
	 * sheet.setDefaultColumnWidth(20); //设置指定列的列宽，256 *
	 * 50这种写法是因为width参数单位是单个字符的256分之一 sheet.setColumnWidth(cell.getColumnIndex(),
	 * 256 * 50); // 在sheet里创建第二行 HSSFRow row2 = sheet.createRow(1); //
	 * 创建单元格并设置单元格内容 row2.createCell(0).setCellValue("序号");
	 * row2.createCell(1).setCellValue("用户昵称");
	 * row2.createCell(2).setCellValue("手机号码");
	 * row2.createCell(3).setCellValue("用户等级");
	 * row2.createCell(4).setCellValue("注册时间"); // 获取数据 List<UserInfo> users =
	 * (List<UserInfo>) dt.getData(); for(int i = 0; i < users.size(); i++) { //
	 * 在sheet里创建第i+2行 HSSFRow row3 = sheet.createRow(i + 2);
	 * row3.createCell(0).setCellValue(i + 1);
	 * row3.createCell(1).setCellValue(users.get(i).getNickname());
	 * row3.createCell(2).setCellValue(users.get(i).getTelno());
	 * row3.createCell(3).setCellValue(users.get(i).getIs_vip()?"vip":"普通用户");
	 * row3.createCell(4).setCellValue(users.get(i).getRegistertime()); }
	 * 
	 * OutputStream output = response.getOutputStream(); response.reset(); //
	 * response.setHeader("Content-disposition", "attachment; filename=" + "用户信息" +
	 * format.format(new Date()) + GenRandomNumUtil.getRandomNum(4) + ".xls");
	 * response.setContentType("application/msexcel"); wb.write(output); wb.close();
	 * output.flush(); output.close(); } } catch (SQLExecutorException | IOException
	 * e) { try { response.sendError(500); } catch (IOException e1) { } }
	 * 
	 * }
	 */

}
