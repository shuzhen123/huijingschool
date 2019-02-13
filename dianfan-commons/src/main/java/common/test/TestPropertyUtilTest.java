package common.test;

import common.propertymanager.PropertyUtil;

public class TestPropertyUtilTest {

	public static void main(String[] args) {
		String config = PropertyUtil.getProperty("config");
		System.out.println(config);
	}

}
