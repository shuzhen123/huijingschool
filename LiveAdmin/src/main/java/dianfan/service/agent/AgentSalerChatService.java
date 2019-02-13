package dianfan.service.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.agent.AgentSalerChatMapper;
import dianfan.entities.BashMap;
import dianfan.entities.ChatWord;
import dianfan.entities.DataTable;
import dianfan.entities.Reperecs;
import dianfan.entities.SalerCall;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AgentCourseService
 * @Description 代理商课程服务
 * @author cjy
 * @date 2018年1月18日 下午5:18:10
 */
@Service 
public class AgentSalerChatService {
	@Autowired
	private AgentSalerChatMapper chatMapper;

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
			if(param.get("userid") != null && !param.get("userid").toString().isEmpty())
			{
				StringBuilder sb = new StringBuilder(sc.getAccount());
				sb.replace(3, 7, "****");
				sc.setAccount(sb.toString());
			}
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
		if(param.get("userid") != null && !param.get("userid").toString().isEmpty())
		{
			for(ChatWord cw : chatList) {
				StringBuilder sb = new StringBuilder(cw.getTelno());
				sb.replace(3, 7, "****");
				cw.setTelno(sb.toString());
			}
		}
		//设置数据
		dt.setData(chatList);
		return dt;
	}

	/**
	 * @Title: addViolatorInfo
	 * @Description: 添加通话违规记录
	 * @param reperecs
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月17日 上午10:09:30
	 */
	@Transactional
	public void addViolatorInfo(Reperecs reperecs) throws SQLExecutorException {
		chatMapper.addViolatorInfo(reperecs);
	}

	/**
	 * @Title: dealToViolator
	 * @Description: 处理通话违规记录
	 * @param id
	 * @param methodid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月20日 上午10:06:34
	 */
	@Transactional
	public void dealToViolator(String id, String methodid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("methodid", methodid);
		chatMapper.dealToViolator(param);
		//是否封号
		Integer type = chatMapper.getMethodType(methodid);
		if(type != null && type == 2) {
			chatMapper.setSalerAccountForbid(id);
		}
	}

	
}