package dianfan.service.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.PhoneRecordMapper;
import dianfan.entities.CallVoice;
import dianfan.entities.PhoneRecordInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName PhoneRecordService
 * @Description 业务员通话记录服务
 * @author cjy
 * @date 2018年3月14日 上午10:22:25
 */
@Service
public class PhoneRecordService {
	@Autowired
	private PhoneRecordMapper phoneRecordMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * @Title: findPhoneRecord
	 * @Description: 获取业务员通话记录列表
	 * @param salerid 业务员id
	 * @param page 请求的页数
	 * @param classid 业务分区id
	 * @param contacted 联系状态（0:未联系；1:已联系）
	 * @param collected 收藏状态（0:未收藏，1:已收藏）
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午2:41:16
	 */
	public List<PhoneRecordInfo> findPhoneRecord(String salerid, Integer page, String classid, Integer contacted, Integer collected) throws SQLExecutorException {
		//根据业务员id获取业务员通话记录列表
		Map<String, Object> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("classid", classid);
		param.put("contacted", contacted);
		param.put("collected", collected);
		
		//根据业务员id获取业务员通话记录总条数
		int count = phoneRecordMapper.findPhoneRecordCount(param);
		
		//总页数
		int totalPage;
		if(count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count/ConstantIF.PAGE_OFFSET + 1;
		}else {
			totalPage = count/ConstantIF.PAGE_OFFSET;
		}
		
		//查看是否已超总页数
		if(totalPage < page) {
			return new ArrayList();
		}
		
		//设置请求起始位置
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("length", ConstantIF.PAGE_OFFSET);
		
		//根据条件获取业务员通话记录
		List<PhoneRecordInfo> recordList = phoneRecordMapper.findPhoneRecord(param);
		return recordList;
	}
	public Map<String, Object> findPhoneRecord1(String salerid, Integer page, String classid, Integer contacted, Integer collected) throws SQLExecutorException {
		//响应数据
		Map<String, Object> data = new HashMap<>();
		
		//根据业务员id获取业务员通话记录列表
		Map<String, Object> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("classid", classid);
		param.put("contacted", contacted);
		param.put("collected", collected);
		
		//根据业务员id获取业务员通话记录总条数
		int count = phoneRecordMapper.findPhoneRecordCount(param);
		
		//总页数
		int totalPage;
		if(count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count/ConstantIF.PAGE_OFFSET + 1;
		}else {
			totalPage = count/ConstantIF.PAGE_OFFSET;
		}
		
		//总页数
		data.put("tpage", totalPage);
		//当前页
		data.put("cpage", page);
		
		//查看是否已超总页数
		if(totalPage < page) {
			data.put("data", null);
			return data;
		}
		
		//设置请求起始位置
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("length", ConstantIF.PAGE_OFFSET);
		
		//根据条件获取业务员通话记录
		List<PhoneRecordInfo> recordList = phoneRecordMapper.findPhoneRecord(param);
		data.put("data", recordList);
		
		return data;
	}

	/**
	 * @Title: updateCustomerNickname
	 * @Description: 更改客户昵称
	 * @param customerid
	 * @param nickname
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月14日 下午2:08:43
	 */
	@Transactional
	public void updateCustomerNickname(String customerid, String nickname) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("customerid", customerid);
		param.put("nickname", nickname);
		phoneRecordMapper.updateCustomerNickname(param);
	}
	
	/**
	 * @Title: updateCustomerRemark
	 * @Description: 更改客户备注
	 * @param customerid
	 * @param remark
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月14日 下午2:12:53
	 */
	@Transactional
	public void updateCustomerRemark(String customerid, String remark) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("customerid", customerid);
		param.put("remark", remark);
		phoneRecordMapper.updateCustomerRemark(param);
	}
	
	/**
	 * @Title: updateCustomerClass
	 * @Description: 更改客户类型
	 * @param customerid
	 * @param classid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月9日 上午11:52:00
	 */
	@Transactional
	public void updateCustomerClass(String customerid, String classid) throws SQLExecutorException {
		phoneRecordMapper.updateCustomerClass(customerid, classid);
	}

	/**
	 * @Title: addCallVoice
	 * @Description: 新增通话记录
	 * @param call
	 * @throws:
	 * @time: 2018年3月14日 下午2:42:29
	 */
	@Transactional
	public void addCallVoice(CallVoice call) throws SQLExecutorException {
		if(call.getStatus() == 1) {
			//通话已接通
			//根据客户id获取客户昵称
			String nickname = phoneRecordMapper.getNickNameById(call.getCustomerid());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String date = sdf.format(new Date());
			//录音标题
			call.setVoicetitle(nickname+"-"+date);
		}
		phoneRecordMapper.addCallVoice(call);
	}

	/**
	 * @Title: uploadPhoneVoice
	 * @Description: 上传通话录音
	 * @param callid
	 * @param newfilename
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月14日 下午3:48:21
	 */
	@Transactional
	public void uploadPhoneVoice(String callid, Map<String, String> voice) throws SQLExecutorException {
//		上传通话录音
		Map<String, String> param = new HashMap<>();
		param.put("callid", callid);
		param.put("voicepath", voice.get("filename") + PropertyUtil.getProperty("voice_suffix"));
		phoneRecordMapper.updateCallVoice(param);
		
		//音频转码(新线程操作)
		taskExecutor.execute(new Runnable() {
			public void run() {
				String cmd = PropertyUtil.getProperty("ffmpeg_prefix") + 
						PropertyUtil.getProperty("domain") + voice.get("filename") + voice.get("suffix") + " " +
						PropertyUtil.getProperty("domain") + voice.get("filename") + PropertyUtil.getProperty("voice_suffix");
				try {
					Runtime.getRuntime().exec(cmd);
					//转码成功，删除原音频文件
//					File file= new File(realPath + voice.get("filename") + voice.get("suffix"));
//					file.delete();
				} catch (IOException e) {
					//转码失败
				}
			}
		});
	}

	/**
	 * @Title: collectAction
	 * @Description: 收藏/取消收藏（0:取消收藏，1:收藏）
	 * @param salerid
	 * @param customerid
	 * @param type
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月20日 下午2:12:21
	 */
	@Transactional
	public void collectAction(String salerid, String customerid, int type) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("customerid", customerid);
		if(type == 1) {
			//检测是否已收藏过
			Integer ck = phoneRecordMapper.checkCollect(param);
			if(ck == 0) {
				//收藏动作
				phoneRecordMapper.collectAction(param);
			}
		}else {
			//取消收藏
			phoneRecordMapper.uncollectAction(param);
		}
	}

}
