package dianfan.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.SharesMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Goods;
import dianfan.entities.Shares;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName SharesService
 * @Description 诊股服务
 * @author cjy
 * @date 2018年3月28日 下午1:25:37
 */
@Service 
public class SharesService {

	@Autowired
	private SharesMapper sharesMapper;
	
	public DataTable findShares(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 1、获取总条数
		int count = sharesMapper.findSharesCount(param);
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
		List<Shares> goods = sharesMapper.findShares(param);
		// 设置数据
		dt.setData(goods);
		return dt;
	}

	/**
	 * @Title: sharesAnswer
	 * @Description: 诊股设置已回答
	 * @param sharesid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月3日 下午5:42:45
	 */
	public void sharesAnswer(String sharesid) throws SQLExecutorException {
		sharesMapper.sharesAnswer(sharesid);
	}

	
}












