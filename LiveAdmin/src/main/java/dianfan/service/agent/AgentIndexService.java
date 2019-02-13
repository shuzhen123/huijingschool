package dianfan.service.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.agent.AgentIndexMapper;
import dianfan.entities.ChartsBean;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AgentIndexService
 * @Description 首页数据服务
 * @author cjy
 * @date 2018年2月6日 上午9:24:15
 */
@Service
public class AgentIndexService {
	@Autowired
	private AgentIndexMapper agentIndexMapper;

	/**
	 * @Title: getResourceData
	 * @Description: 注册人数数据
	 * @param type
	 *            1今日，2近一周，3近一月
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 上午9:30:32
	 */
	public LinkedList<ChartsBean> getResourceData(int type, String agentid) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		// 当前时间
		param.put("endtime", new Timestamp(System.currentTimeMillis()));

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		if (type == 1) {
			// 今日注册人数
			// 今日00:00:00时间
			param.put("starttime", new Timestamp(calendar.getTime().getTime()));
		} else if (type == 2) {
			// 近一周注册人数
			// 一周前的00:00:00时间
			calendar.add(calendar.WEEK_OF_MONTH, -1);
			calendar.add(calendar.DAY_OF_MONTH, +1);
			param.put("starttime", new Timestamp(calendar.getTime().getTime()));
		} else if (type == 3) {
			// 近一月注册人数
			// 一月前的00:00:00时间
			calendar.add(Calendar.MONTH, -1);
			calendar.add(calendar.DAY_OF_MONTH, +1);
			param.put("starttime", new Timestamp(calendar.getTime().getTime()));
		}

		// 1、获取时间段内的注册人数
		LinkedList<ChartsBean> regdata = agentIndexMapper.findRegisterUserByTime(param);

		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
		// 整理数据
		if (type == 1) {
			// 今日00:00:00时间
			if (regdata != null && !regdata.isEmpty()) {
				ChartsBean bean = regdata.getFirst();
				bean.setStrtime(format.format(bean.getTime()));
			}
		} else {
			// 一周前的00:00:00时间

			// 注册用户 时间->数量
			Map<Timestamp, Integer> regdata_m = new HashMap<>();
			if (regdata != null && !regdata.isEmpty()) {
				ListIterator<ChartsBean> iterator = regdata.listIterator();
				while (iterator.hasNext()) {
					ChartsBean next = iterator.next();
					regdata_m.put(next.getTime(), next.getNum());
				}
				regdata = null;
			}

			// 转换桶
			LinkedList<ChartsBean> regdata_c = new LinkedList<>();

			// 相差天数
			int i = (int) ((new Date().getTime() - calendar.getTime().getTime()) / (1000 * 3600 * 24));
			Integer count = null;
			for (; i >= 0; i--) {
				count = regdata_m.get(new Timestamp(calendar.getTime().getTime()));
				if (count == null) {
					regdata_c.add(new ChartsBean(0, format.format(calendar.getTime())));
				} else {
					regdata_c.add(new ChartsBean(count, format.format(calendar.getTime())));
				}
				calendar.add(calendar.DAY_OF_MONTH, +1);
			}
			regdata = regdata_c;
		}
		return regdata;
	}

	/**
	 * @Title: getEarningsData
	 * @Description: 资金统计
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 * @throws SQLExecutorException
	 * @throws ParseException
	 * @throws:
	 * @time: 2018年2月12日 上午10:18:58
	 */
	public LinkedList<ChartsBean> getEarningsData(int year, int month, String agentid) throws SQLExecutorException, ParseException {
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate localDate = yearMonth.atDay(1);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		ZonedDateTime startTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
		// 月起始时间
		param.put("starttime", new Timestamp(Date.from(startTime.toInstant()).getTime()));

		LocalDate endOfMonth = yearMonth.atEndOfMonth();
		LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
		ZonedDateTime endTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
		// 月终止时间
		param.put("endtime", new Timestamp(Date.from(endTime.toInstant()).getTime()));

		// 1、获取时间段内的课程购买金额
		List<ChartsBean> vipdata = agentIndexMapper.findBuyCourseByTime(param);
		// 时间->金额
		Map<Timestamp, BigDecimal> vipdata_m = new HashMap<>();
		if (vipdata != null && !vipdata.isEmpty()) {
			ListIterator<ChartsBean> iterator = vipdata.listIterator();
			while (iterator.hasNext()) {
				ChartsBean next = iterator.next();
				vipdata_m.put(next.getTime(), next.getMoney());
			}
			vipdata = null;
		}

		// 2、获取时间段内的礼物购买金额
		List<ChartsBean> goodsdata = agentIndexMapper.findBuyGoodsByTime(param);
		// 时间->金额
		Map<Timestamp, BigDecimal> goodsdata_m = new HashMap<>();
		if (goodsdata != null && !goodsdata.isEmpty()) {
			ListIterator<ChartsBean> iterator = goodsdata.listIterator();
			while (iterator.hasNext()) {
				ChartsBean next = iterator.next();
				goodsdata_m.put(next.getTime(), next.getMoney());
			}
			goodsdata = null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");

		// 转换桶
		LinkedList<ChartsBean> data = new LinkedList<>();

		// 相差天数
		int i = (int) ((Date.from(endTime.toInstant()).getTime() - Date.from(startTime.toInstant()).getTime())
				/ (1000 * 3600 * 24));
		
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format1.parse(year + "-" + (month > 10 ? month : "0" + month) + "-01");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		for (; i >= 0; i--) {
			//vip money
			BigDecimal vipmoney = vipdata_m.get(new Timestamp(calendar.getTime().getTime()));
			if(vipmoney == null) {
				vipmoney = new BigDecimal(0);
			}
			//goods money
			BigDecimal goodsmoney = goodsdata_m.get(new Timestamp(calendar.getTime().getTime()));
			if(goodsmoney == null) {
				goodsmoney = new BigDecimal(0);
			}
			data.add(new ChartsBean(vipmoney.add(goodsmoney), format.format(calendar.getTime())));
			calendar.add(calendar.DAY_OF_MONTH, +1);
		}
		return data;
	}

}