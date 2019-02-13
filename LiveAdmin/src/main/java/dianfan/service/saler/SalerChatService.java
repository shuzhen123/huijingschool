package dianfan.service.saler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.saler.SalerChatMapper;
import dianfan.entities.ChatWord;
import dianfan.entities.DataTable;
import dianfan.entities.Reperecs;
import dianfan.entities.SalerCall;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName SalerChatService
 * @Description 业务员通话记录服务
 * @author cjy
 * @date 2018年3月12日 下午12:13:32
 */
@Service
public class SalerChatService {
	@Autowired
	private SalerChatMapper salerChatMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * @Title: findSalerChatList
	 * @Description: 客户通话记录管理列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 上午11:54:25
	 */
	public DataTable findSalerPhoneList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = salerChatMapper.findSalerPhoneCount(param);
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
		List<SalerCall> customerUser = salerChatMapper.findSalerPhoneList(param);

		for (SalerCall sc : customerUser) {
			sc.setVoicepath(ConstantIF.PROJECT + sc.getVoicepath());
		}

		// 设置数据
		dt.setData(customerUser);
		return dt;
	}

	/**
	 * @Title: ChangePhoneChatCollect
	 * @Description: 更改收藏状态
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月12日 下午3:54:46
	 */
	@Transactional
	public void ChangePhoneChatCollect(Map<String, Object> param) throws SQLExecutorException {
		salerChatMapper.updatePhoneChatCollect(param);
	}

	/**
	 * @Title: findSalerChatList
	 * @Description: 户聊天记录管理列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午3:18:55
	 */
	public DataTable findSalerChatList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = salerChatMapper.findSalerChatCount(param);
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
		List<ChatWord> customerUser = salerChatMapper.findSalerChatList(param);
		// 设置数据
		dt.setData(customerUser);
		return dt;
	}

	/**
	 * @Title: addChatInfo
	 * @Description: 添加客户聊天记录
	 * @param salerid
	 * @param customerid
	 * @param content
	 * @throws SQLExecutorException
	 * @throws UnsupportedEncodingException 
	 * @throws:
	 * @time: 2018年3月14日 下午6:20:21
	 */
	@Transactional
	public void addChatInfo(String salerid, String customerid, String content) throws SQLExecutorException{
		Map<String, String> data = new HashMap<>();
		String wordid = UUIDUtil.getUUID();
		data.put("id", wordid);
		data.put("saleid", salerid);
		data.put("customerid", customerid);
		// 写入主记录表
		salerChatMapper.addChatWordInfo(data);
		
		// 写入历史记录表
		data.clear();
		data.put("id", UUIDUtil.getUUID());
		data.put("wordid", wordid);
		data.put("words", content);
		data.put("saleid", salerid);
		data.put("customerid", customerid);
		salerChatMapper.addChatHistoryInfo(data);

		// 敏感文字文字检测（新线程执行）
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					// 获取敏感文字数组
					String[] words = salerChatMapper.findSensitiveWord();

					if (words != null && words.length > 0) {
						// 敏感文字已设置，开始检测
						List<String> sen_word = new ArrayList<>();

						String check_word = StringUtils.deleteWhitespace(content);
						for (int i = 0; i < words.length; i++) {
							if (check_word.contains(words[i])) {
								// 有敏感文字
								sen_word.add(words[i]);
							}
						}

						if (sen_word.size() > 0) {
							// 上传的文字记录已违规
							Reperecs rep = new Reperecs();
							rep.setId(UUIDUtil.getUUID());
							rep.setType(1);
							rep.setDetail(String.join(",", sen_word));
							rep.setWordsid(wordid);
							rep.setSaleid(salerid);
							// 添加违规记录
							salerChatMapper.addReperece(rep);
						}
					}
				} catch (SQLExecutorException e) {
					// TODO 异常处理
				}
			}
		});
	}

	/**
	 * @Title: findSalerChatList
	 * @Description: 单客户跟踪记录列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月13日 下午3:18:55
	 */
	public DataTable findSalerAndUserChatList(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = salerChatMapper.findSalerChatAndUserCount(param);
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
		List<ChatWord> customerUser = salerChatMapper.findSalerChatAndUserList(param);
		// 设置数据
		dt.setData(customerUser);
		return dt;
	}

}