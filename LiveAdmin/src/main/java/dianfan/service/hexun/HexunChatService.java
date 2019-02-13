package dianfan.service.hexun;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.hexun.HexunChatMapper;
import dianfan.entities.ChatWord;
import dianfan.entities.DataTable;
import dianfan.entities.SalerCall;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AgentCourseService
 * @Description 代理商课程服务
 * @author cjy
 * @date 2018年1月18日 下午5:18:10
 */
@Service 
public class HexunChatService {
	@Autowired
	private HexunChatMapper chatMapper;

	/**
	 * @Title: findSalerPhoneList
	 * @Description: 客户通话记录管理列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月16日 下午12:14:05
	 */
	public DataTable findSalerPhoneList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = chatMapper.findSalerPhoneCount(param);
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
		List<SalerCall> customerUser = chatMapper.findSalerPhoneList(param);
		
		for(SalerCall sc : customerUser) {
			sc.setVoicepath(ConstantIF.PROJECT + sc.getVoicepath());
		}
		//设置数据
		dt.setData(customerUser);
		return dt;
	}
	
	/**
	 * @Title: findSalerChatList
	 * @Description: 业务员聊天记录管理列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月17日 上午11:02:21
	 */
	public DataTable findSalerChatList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		
		//根据条件获取总条数
		int count = chatMapper.findSalerChatCount(param);
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
		List<ChatWord> chatList = chatMapper.findSalerChatList(param);
		//设置数据
		dt.setData(chatList);
		return dt;
	}

}