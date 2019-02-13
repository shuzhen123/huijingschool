package dianfan.service.saler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.bouncycastle.asn1.cms.KEKIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.saler.SalerCustomerMapper;
import dianfan.entities.DataTable;
import dianfan.entities.ResultBean;
import dianfan.entities.TrackRecord;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.UUIDUtil;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName SalerCustomerService
 * @Description 业务员客户服务
 * @author cjy
 * @date 2018年3月6日 下午2:01:37
 */
@Service
public class SalerCustomerService {
	@Autowired
	private SalerCustomerMapper salerCustomerMapper;

	/**
	 * @Title: findUnbindCustomer
	 * @Description: 客户资源池列表
	 * @param salerid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午2:56:42
	 */
	public DataTable findUnbindCustomer(String salerid, int start, int length) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("start", start);
		param.put("length", length);

		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = salerCustomerMapper.findUnbindCustomerCount(salerid);
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
		List<UserInfo> customer = salerCustomerMapper.findUnbindCustomer(param);

		for (UserInfo ui : customer) {
			StringBuilder sb = new StringBuilder(ui.getTelno());
			sb.replace(3, 7, "****");
			ui.setTelno(sb.toString());
		}

		// 设置数据
		dt.setData(customer);
		return dt;
	}
	
	/**
	 * @Title: collectCustomer
	 * @Description: 批量拾取用户资源
	 * @param lids
	 * @param salerid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月2日 下午3:29:42
	 */
	@Transactional
	public synchronized void collectCustomer(List<String> lids, String salerid) throws SQLExecutorException {
		// 批量拾取
		Map<String, Object> param = new HashMap<>();
		param.put("salerid", salerid);
		param.put("ids", lids);
		salerCustomerMapper.collectCustomer(param);
	}
	
	/**
	 * @Title: findCustomerUser
	 * @Description: 代理商维护用户分流列表
	 * @param agentid
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月6日 下午5:19:05
	 */
	public DataTable findCustomerUser(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 根据业务员条件获取用户总条数
		int count = salerCustomerMapper.findCustomerUserCount(param);
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
		List<UserInfo> customerUser = salerCustomerMapper.findCustomerUser(param);
		for (UserInfo ui : customerUser) {
			//手机号码中间4位替换为“*”
//			StringBuffer te = new StringBuffer(ui.getTelno());
//			te.replace(3, 7, "****");
			//重新赋给Telno
//			ui.setTelno(te.toString());
			
			ui.setIconurl(ConstantIF.PROJECT + ui.getIconurl());
			
		}
		// 设置数据
		dt.setData(customerUser);
		return dt;
	}

	/**
	 * @Title: updateUserInfo
	 * @Description: 更新用户信息
	 * @param info
	 * @throws SQLExecutorException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws:
	 * @time: 2018年4月19日 下午5:44:31
	 */
	@Transactional
	public void updateUserInfo(UserInfo info) throws SQLExecutorException, ClientProtocolException, IOException {
		salerCustomerMapper.updateUserInfo(info);
		// 更新用户等级
		if(info.getRemark().isEmpty()) {
			info.setRemark(null);
		}
		salerCustomerMapper.updateUserLevel(info);
		
		//同步网易云id数据
		Map<String, String> param = new HashMap<>();
		param.put("accid", info.getUserid());
		param.put("name", info.getNickname());
		if(info.getIconurl() != null) {
			param.put("icon", ConstantIF.PROJECT + info.getIconurl());
		}
		VCloudManage.im("vcloud.userinfo.update", param);
	}

	/**
	 * @Title: addUserInfo
	 * @Description: 新增用户
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws:
	 * @time: 2018年5月11日 下午3:15:52
	 */
	@Transactional
	public void addUserInfo(UserInfo info) throws SQLExecutorException, ClientProtocolException, IOException {
		//1、添加用户信息
		info.setUserid(UUIDUtil.getUUID());
		info.setNickname(GenRandomNumUtil.getRandomName());
		info.setTokenid(UUIDUtil.getUUID());
		salerCustomerMapper.addUserInfo(info);
		
		//2、开通网易云id
		Map<String, String> im_crt_param = new HashMap<>();
		im_crt_param.put("accid", info.getUserid()); //网易云通信ID
		im_crt_param.put("name", info.getNickname()); //网易云通信ID昵称
		im_crt_param.put("icon", ConstantIF.PROJECT + info.getIconurl()); //网易云通信ID头像URL
		im_crt_param.put("token", info.getTokenid()); //网易云通信token
		VCloudManage.im("vcloud.mi.createid", im_crt_param);
		
		//3、添加代理商-用户关系
		//获取业务员对应的代理商信息
		String agentid = salerCustomerMapper.getAgentBySalerid(info.getCreateid());
		//添加关系
		salerCustomerMapper.addAgentUserRelation(info.getUserid(), agentid, info.getCreateid());
		
		//4、添加用户等级关系
		if(info.getLevelid() == null || info.getLevelid().isEmpty()) {
			info.setLevelid(null);
		}
		salerCustomerMapper.addUserLevelRelation(info);
	}

	/**
	 * @Title: userBatchDel
	 * @Description: 根据id批量丢弃用户
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月14日 上午9:41:14
	 */
	@Transactional
	public void userBatchDel(List<String> lids) throws SQLExecutorException {
		salerCustomerMapper.userBatchDel(lids);
	}

	/**
	 * @Title: addTrackRecordInfo
	 * @Description: 添加客户跟踪记录
	 * @param userid
	 * @param salerid
	 * @param record
	 * @author: sz
	 * @throws SQLExecutorException 
	 * @date 2018年5月16日 下午4:37:01
	 */
	@Transactional
	public void addTrackRecordInfo(TrackRecord record) throws SQLExecutorException {
		salerCustomerMapper.addTrackRecord(record);
	}

	/**
	 * @Title: fildTrackRecordlist
	 * @Description: 客户跟踪显示列表
	 * @param userid
	 * @param salerid
	 * @return
	 * @author: sz
	 * @throws SQLExecutorException 
	 * @date 2018年5月16日 下午5:59:14
	 */
	public DataTable fildTrackRecordlist(String userid) throws SQLExecutorException {
		//1.返回参数模型
		DataTable tb = new DataTable();
		//2.查询用户对应的跟踪记录条数
		int count = salerCustomerMapper.trackRecordCount(userid);
		if (count == 0) {
			//无跟踪记录
			tb.setRecordsFiltered(0);
			tb.setRecordsTotal(0);
			return tb;
		}
		//3.设置请求条数和总条数
		tb.setRecordsFiltered(count);
		tb.setRecordsTotal(count);
		//4.查询客户对应的跟踪数据
		List<TrackRecord> list = salerCustomerMapper.fildtRackRecord(userid);
		tb.setData(list);
		return tb;
	}
}