package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.entities.DataTable;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AdminSelfService
 * @Description 管理员信息服务
 * @author cjy
 * @date 2018年1月9日 下午3:43:23
 */
@Service 
public class AdminUserService {
	@Autowired
	private AdminUserMapper adminUserMapper;

	/**
	 * @Title: checkLogin
	 * @Description: 用户登录账号密码验证
	 * @param account 账号
	 * @param password 密码
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月2日 下午4:02:44
	 */
	public UserInfo checkLogin(String account, String password) throws SQLExecutorException {
		//基础验证
		if(account == null || "".equals(account.trim()) || password == null || "".equals(password.trim())) {
			return null;
		}
		//根据账号密码查询数据库
		Map<String, String> param = new HashMap<>();
		param.put("username", account);
		param.put("password", password);
		UserInfo info = adminUserMapper.findUserByAccount(param);
		return info;
	}
	
	/**
	 * @Title: editAdminUserInfo
	 * @Description: 管理员信息修改
	 * @param info
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月9日 下午4:31:30
	 */
	@Transactional
	public void editAdminUserInfo(UserInfo info) throws SQLExecutorException {
		adminUserMapper.updateAdminUser(info);
	}
	
	/**
	 * @Title: updateAdminUserPwd
	 * @Description: 修改密码
	 * @param userInfo
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月9日 下午5:07:12
	 */
	@Transactional
	public void updateAdminUserPwd(UserInfo userInfo) throws SQLExecutorException {
		adminUserMapper.updateAdminUserPwd(userInfo);
	}

	/*-------------------------------------------------*/
	
	/**
	 * @Title: findAdminUser
	 * @Description: 管理员列表
	 * @param userid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月15日 上午10:05:49
	 */
	public DataTable findAdminUser(String userid, int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();

		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("start", start);
		param.put("length", length);
		
		// 1、获取总条数
		int count = adminUserMapper.findAdminUserCount(param);
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
		List<UserInfo> users = adminUserMapper.findAdminUsers(param);

		// 设置数据
		dt.setData(users);
		return dt;
	}

	/**
	 * @Title: addAdminUser
	 * @Description: 添加管理员
	 * @param userInfo
	 * @param roles
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月15日 上午11:42:54
	 */
	@Transactional
	public void addAdminUser(UserInfo userInfo, String[] roles) throws SQLExecutorException {
		//管理员id
		String userid = UUIDUtil.getUUID();
		//保存管理员数据
		userInfo.setUserid(userid);
		adminUserMapper.addAdminUser(userInfo);
		
		//权限添加
		if(roles != null && roles.length > 0){
			Map<String, Object> param = new HashMap<>();
			param.put("userid", userid);
			param.put("roles", roles);
			//权限持久化
			adminUserMapper.addAdminUserRole(param);
		}
	}

	/**
	 * @Title: editAdminUser
	 * @Description: 管理员修改
	 * @param user
	 * @param roles
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月9日 下午4:23:14
	 */
	public void editAdminUser(UserInfo user, String[] roles) throws SQLExecutorException {
		adminUserMapper.editAdminUser(user);
		
		//清空权限
		adminUserMapper.cleanAdminUserRole(user.getUserid());
		//权限添加
		if(roles != null && roles.length > 0){
			Map<String, Object> param = new HashMap<>();
			param.put("userid", user.getUserid());
			param.put("roles", roles);
			//权限持久化
			adminUserMapper.addAdminUserRole(param);
		}
	}

	/**
	 * @Title: delAdminUser
	 * @Description: 删除管理员账号
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月18日 上午9:54:52
	 */
	@Transactional
	public void delAdminUser(List<String> lids) throws SQLExecutorException {
		adminUserMapper.delAdminUser(lids);
	}
	
}












