package dianfan.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CompareBeans {
	/**
	 * 列出两个对象之间相同不相同的值
	 * 
	 * @param obj1
	 * @param Obj2
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, String> compare(T obj1, T Obj2) throws Exception {

		Map<String, String> result = new HashMap<String, String>();

		Field[] fs = obj1.getClass().getDeclaredFields();// 获取所有属性
		for (Field f : fs) {
			f.setAccessible(true);// 设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
			Object v1 = f.get(obj1);
			Object v2 = f.get(Obj2);
			result.put(f.getName(), String.valueOf(equals(v1, v2)));
		}
		return result;
	}

	/**
	 * 两个对象间值得比较(false 相同,true 不相同)
	 * 
	 * @param obj1
	 * @param Obj2
	 * @return
	 * @throws Exception
	 */
	public static <T> boolean compare2(T obj1, T Obj2) throws Exception {

		boolean result = false;

		Field[] fs = obj1.getClass().getDeclaredFields();// 获取所有属性
		for (Field f : fs) {
			f.setAccessible(true);// 设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
			Object v1 = f.get(obj1);
			Object v2 = f.get(Obj2);
			if (!equals(v1, v2)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 比较两个对象
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {

		if (obj1 == obj2) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}

	/*
	 * public static void main(String[] args) throws Exception { UserInfoPC user1 =
	 * new UserInfoPC(); UserInfoPC user2 = new UserInfoPC();
	 * 
	 * Map<String, String> result = compare(user1, user2);
	 * 
	 * System.out.println(result); }
	 */
}
