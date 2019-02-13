package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.FeedBackMapper;
import dianfan.entities.DataTable;
import dianfan.entities.FeedBack;
import dianfan.entities.NewsModel;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName FeedbackService
 * @Description 用户反馈服务
 * @author cjy
 * @date 2018年1月11日 下午4:28:50
 */
@Service
public class FeedBackService {
	@Autowired
	private FeedBackMapper feedBackMapper;

	/**
	 * @Title: findNewsModel
	 * @Description: 用户反馈列表
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月9日 下午5:40:57
	 */
	public DataTable findFeedBacks(String search, int start, int length) throws SQLExecutorException {

		DataTable dt = new DataTable();

		// 1、获取总条数
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		//分类搜索条件
		param.put("type", search);

		int count = feedBackMapper.findFeedBacksCount(param);
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
		List<FeedBack> feedBacks = feedBackMapper.findFeedBacks(param);

		// 设置数据
		dt.setData(feedBacks);
		return dt;
	}

	/**
	 * @Title: delFeedbackInfo
	 * @Description: 根据id批量删除用户反馈数据
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午5:27:04
	 */
	@Transactional
	public void delFeedbackInfo(List<String> ids) throws SQLExecutorException {
		feedBackMapper.delFeedbackInfoByIds(ids);
	}

	/**
	 * @Title: changeFeedbackStatus
	 * @Description: 根据id更新反馈信息状态
	 * @param id
	 * @param status 更改为的状态
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午6:11:01
	 */
	@Transactional
	public void changeFeedbackStatus(String id, String status) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("status", status);
		feedBackMapper.updateFeedbackStatus(param);
	}

	
}