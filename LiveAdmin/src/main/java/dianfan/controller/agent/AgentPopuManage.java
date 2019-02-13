package dianfan.controller.agent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
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

import com.google.zxing.WriterException;

import dianfan.annotations.LogOp;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.entities.Popu;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentQRService;

/**
 * @ClassName AgentPopuManage
 * @Description 推广管理
 * @author cjy
 * @date 2018年1月30日 下午2:25:10
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/agent")
public class AgentPopuManage {

	@Autowired
	private AgentQRService agentQRService;

	/**
	 * @Title: dashboardAgnetPopu
	 * @Description: 推广管理页
	 * @return
	 * @throws:
	 * @time: 2018年1月30日 下午2:26:10
	 */
	@LogOp(method = "dashboardAgnetPopu", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "推广管理页")
	@RequestMapping(value = "agentpopu", method = { RequestMethod.GET })
	public String dashboardAgnetPopu(@RequestParam int type, HttpSession session, Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		try {
			Popu popu = agentQRService.findUserQR(userInfo.getUserid(), type);
			if (popu != null && popu.getQrurl() != null) {
				popu.setQrurl(ConstantIF.PROJECT + popu.getQrurl());
			}
			model.addAttribute("popu", popu);

			if (type == 1) {
				Popu popu1 = agentQRService.findUserQR(userInfo.getUserid(), 3);
				model.addAttribute("popu_wx", popu1);
			}
		} catch (SQLExecutorException e) {
			return ResultMsg.ADMIN_500;
		}
		return ConstantIF.AGENT_POPU + "popu-qr";
	}

	/**
	 * @Title: createQRCode
	 * @Description: 更新二维码
	 * @param type
	 *            类型（1二维码推广，2直播间推广）
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年2月2日 下午12:02:19
	 */
	@LogOp(method = "updateQRCode", logtype = ConstantIF.LOG_TYPE_2, userid = "", description = "更新二维码")
	@RequestMapping(value = "updateqr", method = RequestMethod.GET)
	public @ResponseBody ResultBean updateQRCode(@RequestParam("type") int type, HttpSession session) {
		try {
			UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
			Popu popu = agentQRService.updateQRCode(userInfo.getUserid(), type);
			return new ResultBean(popu);
		} catch (SQLExecutorException | WriterException | IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
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
	public void downloadQRCode(@RequestParam int type, HttpSession session, HttpServletResponse response)
			throws Exception {
		UserInfo userInfo = (UserInfo) session.getAttribute(ConstantIF.PC_SESSION_KEY);
		Popu popu = agentQRService.findUserQR(userInfo.getUserid(), type);
		if (type == 3) {
			// new一个URL对象
			URL url = new URL(popu.getQrurl());
			// 打开链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 关闭输入流
			inStream.close();
			// 把outStream里的数据写入内存
			byte[] data = outStream.toByteArray();

			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + popu.getId() + ".png");
			response.addHeader("Content-Length", "" + conn.getContentLength());
			response.setContentType("image/png");

			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			// 写入数据
			outputStream.write(data);
			// 关闭输出流
			outputStream.close();

		} else {
			// 读取对应文件
			File file = new File(session.getServletContext().getRealPath("/") + popu.getQrurl());
			if (file.exists()) {
				/* 第二步：根据已存在的文件，创建文件输入流 */
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				/* 第三步：创建缓冲区，大小为流的最大字符数 */
				byte[] buffer = new byte[inputStream.available()]; // int available() 返回值为流中尚未读取的字节的数量
				/* 第四步：从文件输入流读字节流到缓冲区 */
				inputStream.read(buffer);
				/* 第五步： 关闭输入流 */
				inputStream.close();

				String fileName = file.getName();// 获取文件名
				response.reset();
				response.addHeader("Content-Disposition",
						"attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				response.setContentType("image/png ");

				/* 第六步：创建文件输出流 */
				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
				/* 第七步：把缓冲区的内容写入文件输出流 */
				outputStream.write(buffer);
				/* 第八步：刷空输出流，并输出所有被缓存的字节 */
				outputStream.flush();
				/* 第九步：关闭输出流 */
				outputStream.close();
			}
		}
	}

}
