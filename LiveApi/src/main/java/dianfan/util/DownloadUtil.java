package dianfan.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载工具类
 * 
 * @author Administrator
 *
 */
public class DownloadUtil {
	// 下载
	public static boolean downLoadFile(String name, String type, String path, HttpServletResponse response)
			throws Exception {
		String fileName = name;
		String fileType = type;
		File file = new File(path + fileName); // 根据文件路径获得File文件
		// 设置文件类型(这样设置就不止是下Excel文件了，一举多得)
		if ("pdf".equals(fileType)) {
			response.setContentType("application/pdf;charset=GBK");
		} else if ("csv".equals(fileType)) {
			response.setContentType("application/msexcel;charset=GBK");
		} else if ("doc".equals(fileType)) {
			response.setContentType("application/msword;charset=GBK");
		} else if ("xls".equals(fileType)) {
			response.setContentType("application/msexcel;charset=GBK");
		}
		// 文件名
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
		response.setContentLength((int) file.length());
		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (Exception e) {
			// 异常自己捕捉
		} finally {
			// 关闭流，不可少
			if (input != null)
				input.close();
			if (output != null)
				output.close();
		}
		return false;
	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * @RequestMapping("/download")
	 * 
	 * @ResponseBody public void downLoadExcelModel(HttpServletRequest
	 * request,HttpServletResponse response) throws Exception { String download =
	 * request.getSession().getServletContext().getRealPath("/upload/"); //获取下载路劲
	 * ExcelAndCsvDownload.downLoadFile(moban.csv,csv,download,
	 * response);//依次传入需要下载的文件名，文件格式，路径，response参数 } }
	 */
}
