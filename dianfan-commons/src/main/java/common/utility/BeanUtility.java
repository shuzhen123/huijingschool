package common.utility;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import common.logger.Logger;

public class BeanUtility {

	/** bean嵌套 */
	private static final String NESTED = ".";

	/**
	 * 复制bean的属性（支持嵌套属性，以点号分割）
	 * 
	 * @param source
	 *            拷贝属性的源对象
	 * 
	 * @param dest
	 *            拷贝属性的目的地对象
	 * 
	 * @param includeProperties
	 *            拷贝的属性列表
	 * 
	 * @author yang_qiao
	 * 
	 * @throws InvocationTargetException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @throws InstantiationException
	 * 
	 * @throws IntrospectionException
	 * 
	 * @date 2013-12-18
	 */
	public static final void copyIncludeProperties(final Object source, Object dest, final String[] includeProperties)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException,
			IntrospectionException {
		if (includeProperties == null || includeProperties.length == 0) {
			throw new IllegalArgumentException("未传入要拷贝的属性列表");
		}
		if (source == null) {
			throw new IllegalArgumentException("要拷贝的源对象为空");
		}
		if (dest == null) {
			throw new IllegalArgumentException("要拷贝的目的对象为空");
		}

		// 拷贝
		for (String property : includeProperties) {
			PropertyDescriptor sourcePropertyDescriptor = null;
			PropertyDescriptor destPropertyDescriptor = null;
			if (isSimpleProperty(property)) { // 简单属性
				sourcePropertyDescriptor = getProperty(property, source);
				destPropertyDescriptor = getProperty(property, dest);
				if (sourcePropertyDescriptor == null) {
					throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
				}
				if (destPropertyDescriptor == null) {
					throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
				}
				try {
					copyProperty(property, source, dest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // 嵌套bean属性
				Object target = dest;
				Object realSource = source;
				String[] nestedProperty = getNestedProperty(property);
				if (nestedProperty != null && nestedProperty.length > 1) {
					for (int i = 0; i < nestedProperty.length - 1; i++) {
						sourcePropertyDescriptor = getProperty(nestedProperty[i], realSource);
						destPropertyDescriptor = getProperty(nestedProperty[i], target);
						if (sourcePropertyDescriptor == null) {
							throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
						}
						if (destPropertyDescriptor == null) {
							throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
						}
						Method readMethod = sourcePropertyDescriptor.getReadMethod();
						realSource = readMethod.invoke(realSource);
						readMethod = destPropertyDescriptor.getReadMethod();
						Method writeMethod = destPropertyDescriptor.getWriteMethod();
						Object value = readMethod.invoke(target);
						if (value == null) {
							value = destPropertyDescriptor.getPropertyType().newInstance();
							writeMethod.invoke(target, value);
						}
						target = value;
					}
					final String prop = nestedProperty[nestedProperty.length - 1];
					sourcePropertyDescriptor = getProperty(prop, realSource);
					destPropertyDescriptor = getProperty(prop, target);
					if (sourcePropertyDescriptor == null) {
						throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
					}
					if (destPropertyDescriptor == null) {
						throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
					}
					try {
						copyProperty(prop, realSource, target);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 复制bean的属性（支持嵌套属性，以点号分割）
	 * 
	 * @param source
	 *            拷贝属性的源对象
	 * 
	 * @param dest
	 *            拷贝属性的目的地对象
	 * 
	 * @param excludeProperties
	 *            排除的属性列表
	 * 
	 * @author yang_qiao
	 * 
	 * 
	 * @throws Exception
	 *             异常
	 * @date 2013-12-18
	 */
	public static void copyProperties(final Object source, final Object dest, final String... excludeProperties)
			throws Exception {
		final Object backupSource = clone(dest);
		if (source == null) {
			throw new IllegalArgumentException("要拷贝的源对象为空");
		}
		if (dest == null) {
			throw new IllegalArgumentException("要拷贝的目的对象为空");
		}
		BeanUtility.copyProperties(dest, source);
		// 还原排除的属性值
		revertProperties(backupSource, dest, excludeProperties);
	}

	/**
	 * 从备份对象中还原属性
	 * 
	 * @param backup
	 *            备份bean
	 * 
	 * @param target
	 *            目标bean
	 * 
	 * @param properties
	 *            属性列表
	 * 
	 * @author yang_qiao
	 * 
	 * @throws Exception
	 *             异常
	 * 
	 * @date 2013-12-18
	 */
	private static void revertProperties(final Object backup, Object target, final String... properties)
			throws Exception {
		if (properties == null || properties.length == 0) {
			return;
		}
		if (backup == null) {
			throw new IllegalArgumentException("备份对象为空");
		}
		if (target == null) {
			throw new IllegalArgumentException("目的对象为空");
		}
		// 拷贝
		for (String property : properties) {
			PropertyDescriptor sourcePropertyDescriptor = null;
			PropertyDescriptor destPropertyDescriptor = null;
			if (isSimpleProperty(property)) { // 简单属性
				sourcePropertyDescriptor = getProperty(property, backup);
				destPropertyDescriptor = getProperty(property, target);
				if (sourcePropertyDescriptor == null) {
					throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
				}
				if (destPropertyDescriptor == null) {
					throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
				}
				copyProperty(property, backup, target);
			} else { // 嵌套bean属性
				Object targetObj = target;
				Object realBackup = backup;
				String[] nestedProperty = getNestedProperty(property);
				if (nestedProperty != null && nestedProperty.length > 1) {
					for (int i = 0; i < nestedProperty.length - 1; i++) {
						sourcePropertyDescriptor = getProperty(nestedProperty[i], realBackup);
						destPropertyDescriptor = getProperty(nestedProperty[i], targetObj);
						if (sourcePropertyDescriptor == null) {
							throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
						}
						if (destPropertyDescriptor == null) {
							throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
						}
						Method readMethod = sourcePropertyDescriptor.getReadMethod();
						realBackup = readMethod.invoke(realBackup);
						if (realBackup == null) { // 判断备份对象嵌套属性是否为空
							realBackup = sourcePropertyDescriptor.getPropertyType().newInstance();
						}
						Method writeMethod = destPropertyDescriptor.getWriteMethod();
						readMethod = destPropertyDescriptor.getReadMethod();
						Object value = readMethod.invoke(targetObj);
						if (value == null) {
							value = destPropertyDescriptor.getPropertyType().newInstance();
							writeMethod.invoke(targetObj, value);
						}
						targetObj = value;
					}
					final String prop = nestedProperty[nestedProperty.length - 1];
					sourcePropertyDescriptor = getProperty(prop, realBackup);
					destPropertyDescriptor = getProperty(prop, targetObj);
					if (sourcePropertyDescriptor == null) {
						throw new IllegalArgumentException("要拷贝的源对象不存在该属性");
					}
					if (destPropertyDescriptor == null) {
						throw new IllegalArgumentException("要拷贝到的目标对象不存在该属性");
					}
					copyProperty(prop, realBackup, targetObj);
				}
			}
		}
	}

	/**
	 * 对象克隆
	 * 
	 * @param value
	 *            值或对象
	 * @author yang_qiao
	 * @throws Exception
	 *             异常
	 * @return 对象或值
	 * 
	 * @date 2013-12-18
	 */
	private static Object clone(final Object value) throws Exception {
		// 字节数组输出流，暂存到内存中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 序列化
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(value);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		// 反序列化
		return ois.readObject();
	}

	/**
	 * 判断是否为简单属性，是，返回ture
	 * 
	 * @param property
	 *            属性
	 * @author yang_qiao
	 * @return 返回值
	 * @date 2013-12-18
	 */
	private static boolean isSimpleProperty(final String property) {
		return !property.contains(NESTED);
	}

	/**
	 * 获取目标bean的属性
	 * 
	 * @param propertyName
	 *            属性名
	 * @param target
	 *            目标
	 * @author yang_qiao
	 * 
	 * @return 属性描述
	 * 
	 * @date 2013-12-17
	 */
	private static PropertyDescriptor getProperty(final String propertyName, final Object target) {
		if (target == null) {
			new IllegalArgumentException("查询属性的对象为空");
		}
		if (propertyName == null || "".equals(propertyName)) {
			new IllegalArgumentException("查询属性不能为空值");
		}
		PropertyDescriptor propertyDescriptor = null;
		try {
			propertyDescriptor = new PropertyDescriptor(propertyName, target.getClass());
		} catch (IntrospectionException e) {
			Logger.error("不存在该属性");
			return null;
		}
		return propertyDescriptor;
	}

	/**
	 * 单个属性复制--原数据源和目的数据源必须要有该属性方可
	 * 
	 * @author yang_qiao
	 * @param propertyName
	 *            属性名称
	 * @param source
	 *            来源
	 * @param dest
	 *            目标
	 * 
	 * @throws Exception
	 *             异常
	 * 
	 * @date 2013-12-17
	 */
	public static void copyProperty(final String propertyName, final Object source, Object dest) throws Exception {
		PropertyDescriptor property;
		property = new PropertyDescriptor(propertyName, source.getClass());
		Method getMethod = property.getReadMethod();
		Object value = getMethod.invoke(source);
		property = new PropertyDescriptor(propertyName, dest.getClass());
		Method setMethod = property.getWriteMethod();
		setMethod.invoke(dest, value);
	}

	/**
	 * 获取嵌套Bean的属性
	 * 
	 * @param nestedProperty
	 *            本地属性
	 * @author yang_qiao
	 * 
	 * @return 返回值
	 * @date 2013-12-18
	 */
	public static String[] getNestedProperty(final String nestedProperty) {
		if (nestedProperty == null || "".equals(nestedProperty)) {
			new IllegalArgumentException("参数为空值");
		}
		return nestedProperty.split("\\" + NESTED);
	}

}
