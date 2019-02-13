package dianfan.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.VipANewsMapper;
import dianfan.entities.DataTable;
import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName VipNewsService
 * @Description vip咨讯服务
 * @author cjy
 * @date 2018年2月26日 下午5:33:12
 */
@Service
public class VipANewsService {
	@Autowired
	private VipANewsMapper vipNewsMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: findNewsModel
	 * @Description: vip咨讯列表
	 * @param agentid
	 * @param searchMap
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月10日 上午10:52:46
	 */
	public DataTable findVipNews(Map<String, Object> searchMap) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 1、获取总条数
		int count = vipNewsMapper.findNewsCount(searchMap);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<VipInfomation> model = vipNewsMapper.findNews(searchMap);

		for(VipInfomation info : model) {
			if(info.getPicurl() != null && !info.getPicurl().isEmpty()) {
				info.setPicurl(ConstantIF.PROJECT + info.getPicurl());
			}
		}
		// 设置数据
		dt.setData(model);
		return dt;
	}

	/**
	 * @Title: checkVipNews
	 * @Description: vip咨讯审核
	 * @param checkMap
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月27日 下午2:50:26
	 */
	@Transactional
	public void checkVipNews(Map<String, Object> checkMap) throws SQLExecutorException {
		vipNewsMapper.checkVipNews(checkMap);
	}

	
}