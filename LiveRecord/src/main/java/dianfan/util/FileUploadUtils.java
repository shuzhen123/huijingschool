package dianfan.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import common.propertymanager.PropertyUtil;
/**
 * @ClassName FileUploadUtils
 * @Description 文件上传工具类
 * @author cjy
 * @date 2018年1月5日 上午10:43:21
 */
public class FileUploadUtils {

	/**
	 * @Title: uploadOne
	 * @Description: 单文件上传
	 * @param file
	 * @return String[0] 新文件名, String[1] 文件后缀
	 * @throws IOException
	 * @throws:
	 * @time: 2018年1月5日 上午10:43:39
	 */
	public static Map<String, String> uploadVoice(MultipartFile file) throws IOException {
		String domain = PropertyUtil.getProperty("domain");
		String path = PropertyUtil.getProperty("uploadvoicepath");
		//原文件名
        String filename = file.getOriginalFilename();
    	//文件后缀名
    	String suffix = filename.substring(filename.lastIndexOf("."));
    	//新文件名称
    	String newfilename = GenRandomNumUtil.getDateNo();
    	//上传
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(domain + path, newfilename  + suffix));
		
		Map<String, String> data = new HashMap<>();
		data.put("filename", path + newfilename);
		data.put("suffix", suffix);
		return data;
	}
}
