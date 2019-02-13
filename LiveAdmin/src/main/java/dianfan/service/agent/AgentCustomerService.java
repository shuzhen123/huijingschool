package dianfan.service.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.agent.AgentCustomerMapper;
import dianfan.entities.AgentCustomer;
import dianfan.entities.DataTable;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.RegexUtils;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AgentCustomerService
 * @Description 代理商维护用户分流服务
 * @author cjy
 * @date 2018年2月6日 下午5:17:51
 */
@Service
public class AgentCustomerService {
	@Autowired
	private AgentCustomerMapper agentCustomerMapper;

	/**
	 * @Title: findUnbindCustomer
	 * @Description: 客户资源池列表
	 * @param agentid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月28日 下午6:01:15
	 */
	public DataTable findUnbindCustomer(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = agentCustomerMapper.findUnbindCustomerCount((String) param.get("agentid"));
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
		List<UserInfo> customer = agentCustomerMapper.findUnbindCustomer(param);

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
	 * @Title: telnoBatchImport
	 * @Description: 批量导入手机号
	 * @param tels
	 * @param agentid
	 * @param restype
	 * @throws:
	 * @time: 2018年5月2日 上午11:29:12
	 */
	@SuppressWarnings("null")
	@Transactional
	public Map<String, Object> telnoBatchImport(List<List<String>> tels, String agentid, String restype) throws SQLExecutorException {
		//可用号码数据集合
		Map<String, List<String>> tels_map = new HashMap<>();
		
		//有效号码数组
		List<String> valid = new ArrayList<>();
		//无效号码数组
		List<String> unvalid = new ArrayList<>();
		//成功的号码
		List<String> success = new ArrayList<>();
		//失败的号码
		List<String> failde = new ArrayList<>();
		
		for(List<String> tel : tels) {
			if(tel != null && tel.get(0) != null) {
				if(RegexUtils.phoneRegex(tel.get(0))) {
					//号码有效
					tels_map.put(tel.get(0), tel);
					//有效号码数组
					valid.add(tel.get(0));
				}else {
					//号码无效数组
					unvalid.add(tel.get(0));
				}
			}
		}
		
		//1、查找telnos中已被注册的手机号码
		List<String> registerTelno = agentCustomerMapper.checkRegisterTelno(valid);
		
		//2、过滤已注册号码
		List<UserInfo> data = new ArrayList<>();
		for(Map.Entry<String, List<String>> tel : tels_map.entrySet()) {
			String telno = tel.getKey(); //手机号码
			List<String> tel_data = tel.getValue(); //单条号码数据
			
			if(registerTelno.contains(telno)) {
				//号码已注册
				failde.add(telno);
			} else {
				//号码可插入
				UserInfo user = new UserInfo();
				user.setUserid(UUIDUtil.getUUID());
				user.setTelno(telno);//电话
				user.setRealname(tel_data.get(1));//姓名
				user.setNickname(GenRandomNumUtil.getRandomName());//昵称
				if("男".equals(tel_data.get(2))) {
					user.setSex(1);//性别
				}else if("女".equals(tel_data.get(2))) {
					user.setSex(2);//性别
				}else {
					user.setSex(0);//性别
				}
				user.setProv(tel_data.get(3));//省份
				user.setRestype(restype);
				user.setCreateid(agentid);
				data.add(user);
				success.add(telno);
			}
		}
		
		//返回数据
		Map<String, Object> res = new HashMap<>();
		if(!data.isEmpty()) {
			//有未注册的号码
			//3、批量插入号码
			agentCustomerMapper.telnoBatchImport(data);
			
			//4、建立用户与代理商关系
			agentCustomerMapper.createAgentUserRelation(data);
			
			//5、建立用户等级关系
			agentCustomerMapper.createUserClassRelation(data);
		}
		res.put("unvalid", unvalid);//无效手机号码
		res.put("valid", valid);//有效手机号码
		res.put("success", success);//成功手机号码
		res.put("failed", registerTelno);//失败手机号码
		return res;
	}
	
	/**
	 * @Title: changeUserRestype
	 * @Description: 更改用户资源类型
	 * @param userid
	 * @param restype
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月14日 上午11:05:13
	 */
	public void changeUserRestype(String userid, String restype) throws SQLExecutorException {
		agentCustomerMapper.changeUserRestype(userid, restype);
	}
	
	/**
	 * @Title: findCustomerUser
	 * @Description: 代理商维护用户列表
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

		// 根据条件获取代理商下用户总条数
		int count = agentCustomerMapper.findCustomerUserCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、根据条件获取代理商下用户数据
		List<AgentCustomer> customerUser = agentCustomerMapper.findCustomerUser(param);
		for (AgentCustomer ac : customerUser) {
			if(param.get("userid") != null && !param.get("userid").toString().isEmpty())
			{
				StringBuilder sb = new StringBuilder(ac.getTelno());
				sb.replace(3, 7, "****");
				ac.setTelno(sb.toString());
			}

			ac.setIconurl(ConstantIF.PROJECT + ac.getIconurl());
		}
		// 设置数据
		dt.setData(customerUser);
		return dt;
	}

	/**
	 * @Title: moveCustomer
	 * @Description: 批量转移用户资源
	 * @param lids
	 * @param salerid
	 * @param agentid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月8日 下午4:42:52
	 */
	@Transactional
	public void moveCustomer(List<String> lids, String salerid, String agentid) throws SQLExecutorException {
		// 批量转移用户资源
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("salerid", salerid);
		param.put("ids", lids);
		agentCustomerMapper.moveCustomer(param);
	}


}