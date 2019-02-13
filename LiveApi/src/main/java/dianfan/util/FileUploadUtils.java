package dianfan.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
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
	 * @param absolutePath
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年1月5日 上午10:43:39
	 */
	public static String uploadOne(MultipartFile file, String absolutePath) throws IOException {
		//文件名
        String filename = file.getOriginalFilename();
    	//文件后缀名
    	String suffix = filename.substring(filename.lastIndexOf("."));
    	//新文件名称
    	String newfilename = GenRandomNumUtil.getOrderNo() + suffix;
    	//上传
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absolutePath, newfilename));
		return newfilename;
	}
}
