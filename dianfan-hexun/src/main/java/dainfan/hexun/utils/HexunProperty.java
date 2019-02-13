package dainfan.hexun.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * @ClassName HexunProperty
 * @Description 和讯配置参数
 * @author cjy
 * @date 2018年2月5日 上午10:29:37
 */
public class HexunProperty {
	private static Properties props = null;
	static {
		props = new Properties();
		Reader reader = null;
		try {
			reader = new InputStreamReader(HexunProperty.class.getClassLoader().getResourceAsStream("hexun.properties"), "utf-8");
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
	public static String getValue(String key) {
		return props.getProperty(key);
	}

}
