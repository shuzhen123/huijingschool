package dianfan.service.teacher;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.teacher.TeaVipNewsMapper;
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
public class TeaVipNewsService {
	@Autowired
	private TeaVipNewsMapper vipNewsMapper;
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
	 * @Title: addNewsInfo
	 * @Description: 添加咨讯数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午12:57:38
	 */
	@Transactional
	public void addVipNewsInfo(VipInfomation info) throws SQLExecutorException {
		info.setArticalid(UUIDUtil.getUUID());
		//获取vip策略审核需求
		String check = redisService.get(ConstantIF.DEF_VIP_NEWS_KEY);
		if(check != null && "1".equals(check)) {
			info.setCheckflag(1);
		}else {
			info.setCheckflag(0);
		}
		vipNewsMapper.addVipNewsInfo(info);
	}

	/**
	 * @Title: delVipNews
	 * @Description: 根据id删除咨讯数据
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午1:56:51
	 */
	@Transactional
	public void delVipNews(List<String> ids) throws SQLExecutorException {
		vipNewsMapper.delVipNewsByIds(ids);
	}

	/**
	 * @Title: editNewsInfo
	 * @Description: 修改咨讯数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午2:43:44
	 */
	@Transactional
	public void editVipNewsInfo(VipInfomation info) throws SQLExecutorException {
		vipNewsMapper.editVipNewsInfo(info);
	}
}