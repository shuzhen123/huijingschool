package dianfan.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.admin.AdminRoleMapper;
import dianfan.entities.Role;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName RoleService
 * @Description 权限服务
 * @author cjy
 * @date 2018年1月16日 上午10:47:58
 */
@Service 
public class RoleService {

	@Autowired
	private AdminRoleMapper adminRoleMapper;
	
	/**
	 * @Title: findRoleByStatus
	 * @Description: 根据status获取所有权限
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月16日 下午4:10:06
	 */
	public List<Role> findRoleByStatus(Integer status) throws SQLExecutorException {
		Role role = new Role();
		role.setFlag(status);
		return adminRoleMapper.findRoleByStatus(role);
	}
	
	/**
	 * @Title: addRole
	 * @Description: 添加权限
	 * @param rolename
	 * @param rolevalue
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月16日 上午11:06:51
	 */
	@Transactional
	public String addRole(String rolename, String rolevalue) throws SQLExecutorException {
		Role role = new Role();
		role.setJurisdictionid(UUIDUtil.getUUID());
		role.setJurisdictionname(rolename);
		role.setFunctionname(rolevalue);
		role.setFlag(1);
		//持久化权限数据
		adminRoleMapper.addNewRole(role);
		return role.getJurisdictionid();
	}

	/**
	 * @Title: changeRoleStatus
	 * @Description: 更改权限状态
	 * @param roleid
	 * @param status 状态值（1开启， 0关闭）
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月16日 下午1:54:43
	 */
	@Transactional
	public void changeRoleStatus(String roleid, String status) throws SQLExecutorException {
		Role role = new Role();
		role.setJurisdictionid(roleid);
		role.setFlag(Integer.valueOf(status));
		adminRoleMapper.changeRoleStatus(role);
	}

	/**
	 * @Title: getAdminUserRole
	 * @Description: 获取管理员已拥有的权限列表
	 * @param userid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月16日 下午4:51:44
	 */
	public Map<String, List<Role>> getAdminUserRole(String userid) throws SQLExecutorException {
		//已拥有的权限
		List<Role> own = adminRoleMapper.findRoleByAdminId(userid);
		System.out.println(own);
		//全部可用权限
		List<Role> allRole = findRoleByStatus(1);
		System.out.println(allRole);
		
		if(own != null && own.size() > 0) {
			//已拥有的权限id集合
			List<String> roleids = new ArrayList<>();
			
			for(Role role : own) {
				roleids.add(role.getJurisdictionid());
			}
			
			ListIterator<Role> iterator = allRole.listIterator();
			//检索未拥有权限
			while(iterator.hasNext()) {
				Role role = iterator.next();
				if(roleids.contains(role.getJurisdictionid())) {
					iterator.remove();
				}
			}
			
		}
		
		Map<String, List<Role>> result = new HashMap<>();
		
		result.put("own", own);
		result.put("none", allRole);
		return result;
	}

	/**
	 * @Title: authRole
	 * @Description: 授权/撤权限
	 * @param userid
	 * @param roleid
	 * @param status 动作（）
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月16日 下午6:13:06
	 */
	@Transactional
	public void authRole(String userid, String roleid, String action) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		
		param.put("userid", userid);
		param.put("roleid", roleid);
		
		if("auth".equals(action.trim())) {
			//授权
			param.put("id", UUIDUtil.getUUID());
			adminRoleMapper.authRoleByUserid(param);
		}else if("cancel".equals(action.trim())) {
			//撤销权限
			adminRoleMapper.cancelRoleByUserid(param);
		}
		
	}

	


	
}












