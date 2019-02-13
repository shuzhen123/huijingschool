package dianfan.controller.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.ResultBean;
import dianfan.service.RedisService;

/**
 * @ClassName BaseDataManage
 * @Description 基础数据管理
 * @author cjy
 * @date 2018年1月12日 下午3:09:04
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class BaseDataManage {
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: dashboardBaseData
	 * @Description: 基础数据管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午3:09:20
	 */
	@LogOp(method = "dashboardBaseData", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "基础数据管理页")
	@RequestMapping(value = "basedata")
	public String dashboardBaseData(Model model) {
		String supervision_phone = redisService.get(ConstantIF.SUPERVISION_PHONE);
		model.addAttribute("supervision_phone", supervision_phone);
		String contact_phone = redisService.get(ConstantIF.CONTACT_PHONE);
		model.addAttribute("contact_phone", contact_phone);
		return ConstantIF.ADMIN_BASEDATA + "basedata-index";
	}

	/**
	 * @Title: companyIntro
	 * @Description: 基础数据页
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年1月12日 下午3:57:47
	 */
	@LogOp(method = "companyIntro", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "基础数据页")
	@RequestMapping(value = "basedatapage")
	public String baseDataPage(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");

		if (type == null || "".equals(type)) {
			return ConstantIF.ERROR_500;
		}

		// 对应文件绝对路径
		String realPath = PropertyUtil.getProperty("domain");
		if ("intro".equals(type)) {
			// 公司简介
			realPath += PropertyUtil.getProperty("company.intro");
		} else if ("protocol".equals(type)) {
			// 服务协议
			realPath += PropertyUtil.getProperty("company.protocol");
		} else if ("other".equals(type)) {
			// 其它设置
			realPath += PropertyUtil.getProperty("company.other");
		}
		// 读取对应文件
		File file = new File(realPath);
		if (file.exists()) {
			// 文件存在
			BufferedReader br = null;
			try {
				// 读取公司简介文件
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String str = new String();
				StringBuffer content = new StringBuffer();
				while ((str = br.readLine()) != null) {
					content.append(str);
				}
				model.addAttribute("data", content.toString());
			} catch (FileNotFoundException e) {
				return ResultMsg.ADMIN_500;
			} catch (IOException e) {
				return ResultMsg.ADMIN_500;
			} finally {
				if (br != null) {
					try {
						// 关流
						br.close();
					} catch (IOException e) {
						br = null;
						return ResultMsg.ADMIN_500;
					}
				}
			}
		}
		model.addAttribute("type", type);
		return ConstantIF.ADMIN_BASEDATA + "basedata-edit";
	}

	/**
	 * @Title: saveCompanyIntro
	 * @Description: 保存基础数据
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年1月12日 下午4:56:03
	 */
	@LogOp(method = "saveCompanyIntro", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "公司简介页")
	@RequestMapping(value = "savebasedata")
	public @ResponseBody ResultBean saveBaseData(HttpServletRequest request) {
		String type = request.getParameter("type");
		// 检测保存的数据类型
		if (type == null || "".equals(type)) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		// 对应文件绝对路径
		String realPath = PropertyUtil.getProperty("domain");
		if ("intro".equals(type)) {
			// 公司简介
			realPath += PropertyUtil.getProperty("company.intro");
		} else if ("protocol".equals(type)) {
			// 服务协议
			realPath += PropertyUtil.getProperty("company.protocol");
		} else if ("other".equals(type)) {
			// 其它设置
			realPath += PropertyUtil.getProperty("company.other");
		}
		// 读取对应文件
		File file = new File(realPath);
		BufferedWriter bw = null;
		// 读取公司简介文件
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			// 基础数据
			String data = "<!DOCTYPE HTML>" + "<html>" + "<head>" + "<meta charset=\"utf-8\">"
					+ "<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">"
					+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">"
					+ "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\" />"
					+ "<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />" + "</head>" + "<body>";
			data += request.getParameter("data");
			data += "</body></html>";
			bw.write(data);
		} catch (FileNotFoundException e) {
			return new ResultBean("500", ResultMsg.C_500);
		} catch (IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
					return new ResultBean("500", ResultMsg.C_500);
				}
			}
		}
		return new ResultBean();
	}

	/**
	 * @Title: changeCompanyPhone
	 * @Description: 电话修改
	 * @param phone
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年3月29日 上午11:56:55
	 */
	@LogOp(method = "changeCompanyPhone", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "电话修改")
	@RequestMapping(value = "changecompanyphone", method = RequestMethod.POST)
	public @ResponseBody ResultBean changeCompanyPhone(String phone, String type) {
		/*
		 * //正则检测手机号码 boolean b = RegexUtils.phoneRegex(phone); if(!b) { return new
		 * ResultBean("001", ResultMsg.C_001); } //正则检测固话号码 boolean b1 =
		 * RegexUtils.telphoneRegex(phone); if(!b1) { return new ResultBean("001",
		 * ResultMsg.C_001); }
		 */

		if ("contact".equals(type)) {
			// 更新联系电话
			redisService.set(ConstantIF.CONTACT_PHONE, phone);
		} else if ("supervision".equals(type)) {
			// 更新监督电话
			redisService.set(ConstantIF.SUPERVISION_PHONE, phone);
		} else {
			return new ResultBean("024", ResultMsg.C_024);
		}

		return new ResultBean();
	}
}
