package dianfan.util;

import common.propertymanager.PropertyUtil;

public class RadiusMath {
	/**
	 * 经纬度范围
	 */
	public static Object[] findNeighPosition(double longitude, double latitude) {
		// 先计算查询点的经纬度范围
		double r = 6371;// 地球半径千米
		double dis = Double.parseDouble(PropertyUtil.getProperty("radiuslength"));// 5.0千米距离
		double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
		dlng = dlng * 180 / Math.PI;// 角度转为弧度
		double dlat = dis / r;
		dlat = dlat * 180 / Math.PI;
		double minlat = latitude - dlat;
		double maxlat = latitude + dlat;
		double minlng = longitude - dlng;
		double maxlng = longitude + dlng;

		Object[] values = { minlng, maxlng, minlat, maxlat };
		return values;
	}
}
