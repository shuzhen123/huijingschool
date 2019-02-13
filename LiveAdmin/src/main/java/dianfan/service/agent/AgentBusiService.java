package dianfan.service.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.agent.AgentBusiMapper;
import dianfan.entities.DataTable;
import dianfan.entities.SalerLevel;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName BusiService
 * @Description 业务服务
 * @author cjy
 * @date 2018年2月23日 下午3:33:19
 */
@Service
public class AgentBusiService {
	@Autowired
	private AgentBusiMapper agentBusiMapper;

	/**
	 * @Title: findSalerLevel
	 * @Description: 等级列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午3:34:04
	 */
	public DataTable findSalerLevel(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 根据条件获取总条数
		int count = agentBusiMapper.findSalerLevelCount(param);
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
		List<SalerLevel> levels = agentBusiMapper.findSalerLevel(param);
		
		//设置数据
		dt.setData(levels);
		return dt;
	}

	/**
	 * @Title: addSalerLevel
	 * @Description: 添加等级
	 * @param salerLevel
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 下午3:54:48
	 */
	@Transactional
	public void addSalerLevel(SalerLevel salerLevel) throws SQLExecutorException {
		salerLevel.setId(UUIDUtil.getUUID());
		agentBusiMapper.addSalerLevel(salerLevel);
	}

	/**
	 * @Title: editSalerLevel
	 * @Description: 修改等级
	 * @param salerLevel
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 下午4:39:24
	 */
	@Transactional
	public void editSalerLevel(SalerLevel salerLevel) throws SQLExecutorException {
		agentBusiMapper.editSalerLevel(salerLevel);
	}

	/**
	 * @Title: delSalerLevel
	 * @Description: 删除等级
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 下午4:45:18
	 */
	@Transactional
	public void delSalerLevel(List<String> lids) throws SQLExecutorException {
		//删除等级
		agentBusiMapper.delSalerLevel(lids);
		//删除对应的业务员-等级关系
		agentBusiMapper.delSalerLevelRelation(lids);
	}


}