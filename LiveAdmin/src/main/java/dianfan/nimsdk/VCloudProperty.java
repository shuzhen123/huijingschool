package dianfan.nimsdk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class VCloudProperty {
	
	private static final String PROPERTIES = "vcloud.properties";
	
	private static Properties props = null;
	
	private VCloudProperty() {}
	
	static {
		props = new Properties();
		InputStream in = null;
		try {
			in = VCloudProperty.class.getClassLoader().getResourceAsStream(PROPERTIES);
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

	public static String getValue(String key) {
		return props.getProperty(key);
	}

}