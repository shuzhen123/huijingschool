package dianfan.service.admin;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dianfan.dao.mapper.admin.RiskEvaluateMapper;
import dianfan.entities.DataTable;
import dianfan.entities.EvaluateAnswer;
import dianfan.entities.EvaluateList;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName: RiskEvaluateService
 * @Description: 风险测评管理 service
 * @author sz
 * @time 2018年5月8日 上午9:36:41
 */
@Service
public class RiskEvaluateService {

	/**
	 * 注入RiskEvaluateMapper
	 */
	@Autowired
	private RiskEvaluateMapper riskEvaluateMapper;
	
	/**
	 * @Title: fildEvaluateList
	 * @Description: 风险测评列表显示
	 * @param param
	 * 			请求入参
	 * @return DataTable
	 * @author: sz
	 * @date 2018年5月8日 上午9:38:25
	 */
	public DataTable fildEvaluateList(Map<String, Object> param) {
		//返回数据模型
		DataTable tb = new DataTable();
		try {
			//获取总条数
			int count = riskEvaluateMapper.riskEvaluateCount(param);
			//判断count是否为0
			if (count == 0) {
				//没有可用的数据，返回0
				tb.setRecordsFiltered(0);
				tb.setRecordsTotal(0);
				return tb;
			} else {
				//总条数不为0时，设置总条数和请求条数
				tb.setRecordsTotal(count);
				tb.setRecordsFiltered(count);
			}
		} catch (SQLExecutorException e) {
			e.printStackTrace();
		}
		
		try {
			//1.获取风险测评题目列表
			List<EvaluateList> list = riskEvaluateMapper.riskEvaluateList(param);
			//2.添加到tb中返回上层
			tb.setData(list);
		} catch (SQLExecutorException e) {
			e.printStackTrace();
		}
		return tb;
	}

	/**
	 * @Title: editEvaluateScore
	 * @Description: 修改测评答案对应的分数
	 * @param answer
	 * @author: sz
	 * @throws SQLExecutorException 
	 * @date 2018年5月8日 上午11:12:00
	 */
	@Transactional
	public void editEvaluateScore(EvaluateAnswer score) throws SQLExecutorException {
		// 修改测评答案对应的分数
		riskEvaluateMapper.editEvaluateScore(score);
	}
	
}
