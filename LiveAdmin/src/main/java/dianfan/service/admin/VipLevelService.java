package dianfan.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.VipLevelMapper;
import dianfan.entities.DataTable;
import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName VipLevelService
 * @Description vip赠送服务
 * @author cjy
 * @date 2018年4月4日 下午5:07:34
 */
@Service
public class VipLevelService {

	@Autowired
	private VipLevelMapper vipLevelMapper;
	
	/**
	 * @Title: findVipLevelList
	 * @Description: vip赠送列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午5:15:29
	 */
	public DataTable findVipLevelList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 根据条件获取总条数
		int count = vipLevelMapper.findVipLevelCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、获取需求数据
		List<VipLevel> level = vipLevelMapper.findVipLevel(param);
		//设置数据
		dt.setData(level);
		return dt;
	}

	/**
	 * @Title: addVipLevel
	 * @Description: vip等级添加
	 * @param name
	 * @param money
	 * @param days
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月4日 下午6:03:21
	 */
	@Transactional
	public void addVipLevel(String name, BigDecimal money, Integer days) throws SQLExecutorException {
		VipLevel level = new VipLevel();
		level.setLevelname(name);
		level.setMoney(money);
		level.setDays(days);
		vipLevelMapper.addVipLevel(level);
	}
	
	/**
	 * @Title: editVipLevel
	 * @Description: vip等级修改
	 * @param id
	 * @param name
	 * @param money
	 * @param days
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月9日 上午9:28:58
	 */
	@Transactional
	public void editVipLevel(VipLevel level) throws SQLExecutorException {
		vipLevelMapper.editVipLevel(level);
	}

	/**
	 * @Title: delVipLevel
	 * @Description: 批量删除vip等级
	 * @param ids_str
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月9日 上午10:30:44
	 */
	public void delVipLevel(String[] ids) throws SQLExecutorException {
		vipLevelMapper.delVipLevel(ids);
	}


	
}
