package dianfan.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import common.logger.Logger;
import common.propertymanager.PropertyUtil;

public class PropertyManagerHsql {
	private static final String PROPERTIES = "hsql.properties";
	/**
	 * @author
	 * @version 创建时间：2017年7月05日 下午7:45:06 类说明
	 */
	private static Properties props = null;
	static {
		loadProps();
	}

	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			// <!--第一种，通过类加载器进行获取properties文件流-->
			in = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTIES);
			// <!--第二种，通过类进行获取properties文件流-->
			// in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
			props.load(in);
		} catch (FileNotFoundException e) {
			System.out.println("文件找不到！");
		} catch (IOException e) {
			System.out.println("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				System.out.println(PROPERTIES + "文件流关闭出现异常");
			}
		}
		System.out.println("加载" + PROPERTIES + "文件内容完成...........");
		System.out.println(PROPERTIES + "文件内容：" + props);
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		Logger.error("SQLID:" + key + "\r\n" + "SQLTEXT:" + props.getProperty(key));
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		Logger.error("SQLID:" + key + "\r\n" + "SQLTEXT:" + props.getProperty(key) + "\r\n" + "defaultValue:"
				+ defaultValue);
		return props.getProperty(key, defaultValue);
	}
}
