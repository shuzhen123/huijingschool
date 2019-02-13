package dianfan.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.SensitiveWordMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.HandleMethod;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AdminSelfService
 * @Description 管理员信息服务
 * @author cjy
 * @date 2018年1月9日 下午3:43:23
 */
@Service 
public class SensitiveWordService {
	@Autowired
	private SensitiveWordMapper sensitiveWordMapper;

	/**
	 * @Title: addSensitiveWord
	 * @Description: 添加敏感文字
	 * @param word
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月10日 上午10:37:47
	 */
	@Transactional
	public BashMap addSensitiveWord(String word) throws SQLExecutorException {
		BashMap bm = new BashMap();
		bm.setId(UUIDUtil.getUUID());
		bm.setName(word);
		sensitiveWordMapper.addSensitiveWord(bm);
		return bm;
	}

	/**
	 * @Title: stopSensitiveWord
	 * @Description: 停用敏感文字
	 * @param wordid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月26日 上午11:32:36
	 */
	@Transactional
	public void stopSensitiveWord(String wordid) throws SQLExecutorException {
		sensitiveWordMapper.stopSensitiveWord(wordid);
	}

	/**
	 * @Title: findCompliance
	 * @Description: 合规列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 上午11:32:15
	 */
	public DataTable findCompliance(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		//1、根据条件查询合规总条数
		Integer count = sensitiveWordMapper.findComplianceCount(param);
		if(count == null || count == 0) {
			//无数据
			return dt;
		}else {
			//设置总条数
			dt.setRecordsTotal(count);
			//设置请求条数
			dt.setRecordsFiltered(count);
		}
		
		//2、根据条件查询合规数据
		List<HandleMethod> users = sensitiveWordMapper.findCompliance(param);
		
		//设置数据
		dt.setData(users);
		return dt;
	}

	/**
	 * @Title: addCompliance
	 * @Description: 添加合规处罚项
	 * @param type
	 * @param money
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 下午12:05:54
	 */
	@Transactional
	public void addCompliance(Integer type, BigDecimal money) throws SQLExecutorException {
		HandleMethod hm = new HandleMethod();
		hm.setType(type);
		if(type != null && type == 1) {
			hm.setMoney(money);
		}
		sensitiveWordMapper.addCompliance(hm);
	}

	/**
	 * @Title: editCompliance
	 * @Description: 修改合规处罚项
	 * @param id
	 * @param type
	 * @param money
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 下午1:49:44
	 */
	@Transactional
	public void editCompliance(String id, Integer type, BigDecimal money) throws SQLExecutorException {
		HandleMethod hm = new HandleMethod();
		hm.setId(id);
		hm.setType(type);
		if(type != null && type == 1) {
			hm.setMoney(money);
		}
		sensitiveWordMapper.editCompliance(hm);
	}

	/**
	 * @Title: delCompliance
	 * @Description: 删除合规
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月26日 下午1:59:19
	 */
	@Transactional
	public void delCompliance(List<String> lids) throws SQLExecutorException {
		sensitiveWordMapper.delCompliance(lids);
	}

	
}












