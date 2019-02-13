package dianfan.vcloud.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;


/**
 * @ClassName VcoludConf
 * @Description 网易云直播配置参数
 * @author cjy
 * @date 2018年1月23日 上午10:45:12
 */
public class VcoludConf {
	private static Properties props = null;
	
	private static VcoludConf yp = new VcoludConf();
	
	private VcoludConf() {
		props = new Properties();
		Reader reader = null;
		try {
			reader = new InputStreamReader(VcoludConf.class.getClassLoader().getResourceAsStream("vcloud.properties"), "utf-8");
			props.load(reader);
		} catch (FileNotFoundException e) {
			System.out.println("文件找不到！");
		} catch (IOException e) {
			System.out.println("出现IOException");
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("文件流关闭出现异常");
			}
		}

	}

	/*
	 * 获取类对象
	 */
	public static VcoludConf config() {
		return yp;
	}
	
	/**
	 * @Title: getConfig
	 * @Description: 根据key获取配置数据
	 * @param key
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 上午10:49:02
	 */
	public String getConfig(String key) {
		return props.getProperty(key);
	}

}
