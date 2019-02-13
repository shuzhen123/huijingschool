package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppDiagnoseStockMapper;
import dianfan.entities.DiagnoseStock;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppDiagnoseStockService
 * @Description 诊股服务
 * @author cjy
 * @date 2018年3月8日 下午3:18:29
 */
@Service
public class AppDiagnoseStockService {
	@Autowired
	private AppDiagnoseStockMapper diagnoseStockMapper;

	/**
	 * @Title: findStockListByType
	 * @Description: 按类型获取诊股列表
	 * @param type
	 * @param page
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月2日 上午9:26:31
	 */
	public Map<String, Object> findStockListByType(int type, int page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		// 根据条件获取总条数
		int count = diagnoseStockMapper.findStockCount(param);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", page);

		// 查看是否已超总页数
		if (totalPage < page) {
			data.put("stocklist", new ArrayList<>());
			return data;
		}

		// 设置请求条件
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);

		// 获取诊股列表
		List<DiagnoseStock> stocklist = diagnoseStockMapper.findStockByType(param);

		data.put("stocklist", stocklist);
		return data;
	}

	/**
	 * @Title: sendStockQuestion
	 * @Description: 诊股提问
	 * @param userId
	 * @param title
	 * @param des
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月2日 上午10:02:37
	 */
	@Transactional
	public void sendStockQuestion(String userId, String title, String des) throws SQLExecutorException {
		DiagnoseStock ds = new DiagnoseStock();
		ds.setQuestiontitle(title);
		ds.setQuestiondes(des);
		ds.setCreateid(userId);
		diagnoseStockMapper.sendStockQuestion(ds);
	}


}
