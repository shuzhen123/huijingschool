package dianfan.controller.saler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;

/**
 * @ClassName SalerPopuManage
 * @Description 推广管理
 * @author cjy
 * @date 2018年3月6日 下午3:49:47
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/saler")
public class SalerPopuManage {

	/**
	 * @Title: dashboardAgnetPopu
	 * @Description: 推广管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月30日 下午2:26:10
	 */
	@LogOp(method = "dashboardAgnetPopu", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "推广管理页")
	@RequestMapping(value = "salerpopu", method = { RequestMethod.GET })
	public String dashboardAgnetPopu() {
		return "forward:/agent/agentpopu";
	}

	/**
	 * @Title: createQRCode
	 * @Description: 更新二维码
	 * @param type 类型（1二维码推广，2直播间推广）
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年2月2日 下午12:02:19
	 */
	@LogOp(method = "updateQRCode", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更新二维码")
	@RequestMapping(value = "updateqr", method = RequestMethod.GET)
	public String updateQRCode() {
		return "forward:/agent/updateqr";
	}

	/**
	 * @Title: downloadQRCode
	 * @Description: 下载二维码
	 * @param type
	 * @param session
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年1月30日 下午5:11:32
	 */
	@LogOp(method = "downloadQRCode", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "下载二维码")
	@RequestMapping(value = "downloadqr", method = RequestMethod.GET)
	public String downloadQRCode() throws Exception {
		return "forward:/agent/downloadqr";
	}
	
}
