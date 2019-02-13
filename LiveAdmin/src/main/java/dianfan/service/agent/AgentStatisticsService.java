package dianfan.service.agent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.agent.AgentStatisticsMapper;
import dianfan.entities.BranchProfit;
import dianfan.entities.Compliance;
import dianfan.entities.TalkTime;
import dianfan.entities.UserInfo;
import dianfan.entities.CourseOrderCollect;
import dianfan.entities.DataTable;
import dianfan.entities.StatementBean;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AgentStatisticsService
 * @Description 代理商报表服务
 * @author cjy
 * @date 2018年2月12日 下午1:35:55
 */
@Service 
public class AgentStatisticsService {
	@Autowired
	private AgentStatisticsMapper agentStatisticsMapper;

	/**
	 * @Title: findCourseOrderList
	 * @Description: 课程消费订单列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月14日 上午9:21:58
	 */
	public DataTable findCourseOrderList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = agentStatisticsMapper.findCourseOrderCount(param);
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<CourseOrderCollect> vipOrder = agentStatisticsMapper.findCourseOrder(param);
		
		for(CourseOrderCollect coc : vipOrder) {
			if(coc.getPaystatus() == 1 && coc.getValiditytime().getTime() < System.currentTimeMillis()) {
				coc.setPaystatus(2);
			}
			if(param.get("userid") != null && !param.get("userid").toString().isEmpty())
			{
				StringBuilder sb = new StringBuilder(coc.getTelno());
				sb.replace(3, 7, "****");
				coc.setTelno(sb.toString());
			}
		}
		
		//设置数据
		dt.setData(vipOrder);
		return dt;
	}
	
	/**
	 * @Title: findGoodsOrderList
	 * @Description: 礼物消费订单列表
	 * @param agentid
	 * @param agentid
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 下午3:41:28
	 */
	public DataTable findGoodsOrderList(String searchdeptid, String searchsalerid,String userid, String agentid, String salerid,String search, int start, int length) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("searchdeptid", searchdeptid);
        param.put("searchsalerid", searchsalerid);
		param.put("userid", userid);
		param.put("agentid", agentid);
		param.put("salerid", salerid);
		param.put("start", start);
		param.put("length", length);
		
		//条件搜索
		if(!"".equals(search)) {
			String[] split = search.split("\\^\\*");
			if(split.length > 1) {
				for(int i = 0; i < split.length; i+=2) {
					param.put(split[i], split[i+1].trim());
				}
			}
		}
		
		DataTable dt = new DataTable();
		
		//根据条件获取礼物消费订单总条数
		int count = agentStatisticsMapper.findGoodsOrderCount(param);
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<StatementBean> goodsOrder = agentStatisticsMapper.findGoodsOrder(param);
		
		if(agentid != null) {
			//调整数据
			for(StatementBean st : goodsOrder) {
				if(agentid.equals(st.getSalerid())) {
					st.setRealname(null);
				}
			}
		}
		
		if(userid != null)
		{
			for(StatementBean st : goodsOrder) {
				StringBuilder sb = new StringBuilder(st.getTelno());
				sb.replace(3, 7, "****");
				st.setTelno(sb.toString());
			}
		}
		
		//设置数据
		dt.setData(goodsOrder);
		return dt;
	}

	/**
	 * @Title: wageStatistics
	 * @Description: 提成汇总
	 * @param agentid
	 * @param starttime
	 * @param endtime
	 * @param deptid
	 * @param salerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月27日 下午3:58:46
	 */
	public Map<String, Object> wageStatistics(String agentid, String userid, String starttime, String endtime, String deptid, String salerid) throws SQLExecutorException {
		Map<String, Object> data = new HashMap<>();
		//条件
		Map<String, String> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("userid", userid);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		
		//全部
		List<BranchProfit> all = agentStatisticsMapper.fingWageStatistics(param);
		
		//无数据
		if(all.size() == 0) {
			data.put("all", null);
			data.put("dept", null);
			data.put("saler", null);
			return data;
		}else {
			//计算总和 ，
			Map<String, String> cache = new HashMap<>();
			
			BigDecimal deptcount = new BigDecimal(0);
			BigDecimal salercount = new BigDecimal(0);
			
			for(BranchProfit bp : all) {
				if(bp.getKind() == 1) {
					//1课程
					deptcount = deptcount.add(bp.getBagentmoney()).add(bp.getBsalermoney());
				}else if(bp.getKind() == 2){
					//2礼物
					salercount = salercount.add(bp.getBagentmoney()).add(bp.getBsalermoney());
				}
			}
			
			cache.put("dept", deptcount.toString());
			cache.put("saler", salercount.toString());
			cache.put("count", deptcount.add(salercount).toString());
			
			data.put("all", cache);
		}
		
		if(deptid != null && !deptid.trim().isEmpty()) {
			//获取部门数据
			param.put("deptid", deptid);
			List<BranchProfit> dept = agentStatisticsMapper.fingWageStatistics(param);
			//计算总和
			Map<String, String> cache = new HashMap<>();
			
			BigDecimal deptcount = new BigDecimal(0);
			BigDecimal salercount = new BigDecimal(0);
			
			for(BranchProfit bp : dept) {
				if(bp.getKind() == 1) {
					//1课程
					deptcount = deptcount.add(bp.getBsalermoney());
				}else if(bp.getKind() == 2){
					//2礼物
					salercount = salercount.add(bp.getBsalermoney());
				}
			}
			
			cache.put("dept", deptcount.toString());
			cache.put("saler", salercount.toString());
			
			cache.put("count", deptcount.add(salercount).toString());
			
			data.put("dept", cache);
		}
		if(salerid != null && !salerid.trim().isEmpty()) {
			//获取人员数据
			param.put("salerid", salerid);
			List<BranchProfit> saler = agentStatisticsMapper.fingWageStatistics(param);
			//计算总和
			Map<String, String> cache = new HashMap<>();
			
			BigDecimal deptcount = new BigDecimal(0);
			BigDecimal salercount = new BigDecimal(0);
			
			for(BranchProfit bp : saler) {
				if(bp.getKind() == 1) {
					//1课程
					deptcount = deptcount.add(bp.getBsalermoney());
				}else if(bp.getKind() == 2){
					//2礼物
					salercount = salercount.add(bp.getBsalermoney());
				}
			}
			
			cache.put("dept", deptcount.toString());
			cache.put("saler", salercount.toString());
			
			cache.put("count", deptcount.add(salercount).toString());
			
			data.put("saler", cache);
		}
		
		return data;
	}
	
	/**
	 * @Title: wageSalerStatistics
	 * @Description: 业务员提成汇总
	 * @param salerid
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 上午9:13:12
	 */
	public Map<String, String> wageSalerStatistics(String salerid, String starttime, String endtime) throws SQLExecutorException {
		//条件
		Map<String, String> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		
		//全部
		List<BranchProfit> all = agentStatisticsMapper.fingWageSalerStatistics(param);
		
		//无数据
		if(all.size() == 0) {
			return null;
		}else {
			//计算总和 ，
			Map<String, String> cache = new HashMap<>();
			
			BigDecimal deptcount = new BigDecimal(0);
			BigDecimal salercount = new BigDecimal(0);
			
			for(BranchProfit bp : all) {
				if(bp.getKind() == 1) {
					//1课程
					deptcount = deptcount.add(bp.getBagentmoney()).add(bp.getBsalermoney());
				}else if(bp.getKind() == 2){
					//2礼物
					salercount = salercount.add(bp.getBagentmoney()).add(bp.getBsalermoney());
				}
			}
			
			cache.put("dept", deptcount.toString());
			cache.put("saler", salercount.toString());
			cache.put("count", deptcount.add(salercount).toString());
			
			return cache;
		}
	}

	/**
	 * @Title: findSalerviolatorList
	 * @Description: 业务员合规处理汇总列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 下午2:43:24
	 */
	public DataTable findSalerViolatorList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取业务员合规处理总条数
		int count = agentStatisticsMapper.findSalerViolatorCount(param);
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、根据条件获取业务员合规处理数据
		List<Compliance> list = agentStatisticsMapper.findSalerViolator(param);
		
		//设置数据
		dt.setData(list);
		return dt;
	}

	/**
	 * @Title: findTalktimestatisticsList
	 * @Description: 业务员通话时长汇总列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 下午2:43:24
	 */
	public DataTable findTalktimestatisticsList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取业务员通话时长总条数
		int count = agentStatisticsMapper.findTalktimestatisticsCount(param);
		if(count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、根据条件获取业务员通话时长数据
		List<TalkTime> list = agentStatisticsMapper.findTalktimestatistics(param);
		
		for (TalkTime tt : list) {
			Integer second = Integer.parseInt(tt.getCalltimes());
			Integer minitue = second / 60;
			second -= minitue * 60;
			Integer hour = minitue / 60;
			minitue -= hour * 60;
			tt.setCalltimes(hour.toString() + "时" + minitue.toString() + "分" + second.toString() + "秒");
		}
		
		//设置数据
		dt.setData(list);
		return dt;
	}


}