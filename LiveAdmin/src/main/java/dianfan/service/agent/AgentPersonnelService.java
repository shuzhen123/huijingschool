package dianfan.service.agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.dao.mapper.agent.AgentBusiMapper;
import dianfan.dao.mapper.agent.AgentPersonnelMapper;
import dianfan.dao.mapper.agent.AgentQRMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Dept;
import dianfan.entities.Position;
import dianfan.entities.Popu;
import dianfan.entities.ResultBean;
import dianfan.entities.Saler;
import dianfan.entities.SalerTask;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AgentPersonnelService
 * @Description 代理商人事管理服务
 * @author cjy
 * @date 2018年2月7日 上午9:45:20
 */
@Service
public class AgentPersonnelService {
	@Autowired
	private AgentPersonnelMapper agentPersonnelMapper;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AgentQRMapper agentQRMapper;
	@Autowired
	private AgentQRService agentQRService;
	@Autowired
	private AgentBusiMapper agentBusiMapper;

	/**
	 * @Title: findDept
	 * @Description: 代理商部门列表
	 * @param agentid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午9:49:42
	 */
	public DataTable findDept(String agentid, int start, int length) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("start", start);
		param.put("length", length);

		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = agentPersonnelMapper.findDeptCount(param);
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
		List<Dept> depts = agentPersonnelMapper.findDept(param);

		// 设置数据
		dt.setData(depts);
		return dt;
	}

	/**
	 * @Title: addNewDept
	 * @Description: 添加新部门
	 * @param name
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午10:25:36
	 */
	@Transactional
	public ResultBean addNewDept(String name, String userid) throws SQLExecutorException {
		Dept dept = new Dept();
		dept.setDeptid(UUIDUtil.getUUID());
		dept.setDeptname(name);
		dept.setCreateid(userid);
		// 名称重复性检测
		boolean boo = checkDeptName(dept);
		if (boo) {
			// 名称已重复
			return new ResultBean("020", ResultMsg.C_020);
		}
		// 检测通过
		agentPersonnelMapper.addDept(dept);
		return new ResultBean();
	}

	/**
	 * @Title: editDept
	 * @Description: 部门修改
	 * @param dept
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午11:05:01
	 */
	@Transactional
	public ResultBean editDept(Dept dept) throws SQLExecutorException {
		// 部门名称重复性检测
		boolean boo = checkDeptName(dept);
		if (boo) {
			// 名称已重复
			return new ResultBean("020", ResultMsg.C_020);
		}
		// 检测通过
		agentPersonnelMapper.editDept(dept);
		return new ResultBean();
	}

	/**
	 * @Title: changeDeptStatus
	 * @Description: 部门停、启用
	 * @param userid
	 * @param status
	 *            1启用/0禁用
	 * @param deptid
	 *            当前部门id
	 * @param tardeptid
	 *            当禁用部门时，部门下的业务员移至当前部门下
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:50:47
	 */
	@Transactional
	public void changeDeptStatus(String userid, int status, String deptid, String tardeptid)
			throws SQLExecutorException {
		Dept dept = new Dept();
		dept.setCreateid(userid);
		dept.setDeptid(deptid);
		dept.setDeptenable(status);
		if (status == 0) {
			// 转移员工部门
			Map<String, String> param = new HashMap<>();
			param.put("userid", userid);
			param.put("olddeptid", deptid);
			if (tardeptid == null || tardeptid.trim().isEmpty()) {
				tardeptid = null;
			}
			param.put("newdeptid", tardeptid);
			agentPersonnelMapper.moveSalerToNewDept(param);
		}
		// 禁用部门
		agentPersonnelMapper.changeDeptStatus(dept);
	}

	/**
	 * @Title: delDept
	 * @Description: 删除部门
	 * @param ids
	 * @param agentid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午2:04:07
	 */
	@Transactional
	public void delDept(List<String> ids, String agentid) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("agentid", agentid);
		agentPersonnelMapper.delDept(param);
	}

	/**
	 * @Title: checkDeptName
	 * @Description: 部门名称重复性检测
	 * @param agent
	 * @param deptname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午11:06:27
	 */
	private boolean checkDeptName(Dept dept) throws SQLExecutorException {
		return agentPersonnelMapper.checkDeptName(dept);
	}

	/**
	 * @Title: findSaler
	 * @Description: 业务员列表
	 * @param userid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午2:19:37
	 */
	public DataTable findSaler(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = agentPersonnelMapper.findSalerCount(param);
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
		List<Saler> saler = agentPersonnelMapper.findSaler(param);

		// 设置数据
		dt.setData(saler);
		return dt;
	}

	/**
	 * @Title: addSaler
	 * @Description: 添加业务员
	 * @param agentid
	 * @param deptid
	 * @param levelid
	 * @param userInfo
	 * @param filepath
	 * @return
	 * @throws SQLExecutorException
	 * @throws IOException
	 * @throws WriterException
	 * @throws NumberFormatException
	 * @throws:
	 * @time: 2018年2月7日 下午3:50:05
	 */
	@Transactional
	public void addSaler(String agentid, String deptid, String levelid, String positionid, String[] depts, UserInfo userInfo)
			throws SQLExecutorException, NumberFormatException, WriterException, IOException {
		String salerid = UUIDUtil.getUUID();
		// 添加业务员数据
		userInfo.setUserid(salerid);
		if(positionid == null || "".equals(positionid.trim()))
		{
			positionid = null;
			userInfo.setRole(3);
		}
		else
		{
			userInfo.setRole(6);
		}
		userInfo.setCreateid(agentid);
		agentPersonnelMapper.addSalerInfo(userInfo);

		if (levelid != null && !levelid.trim().isEmpty()) {
			// 设置业务员等级
			Map<String, String> le = new HashMap<>();
			le.put("salerid", salerid);
			le.put("levelid", levelid);
			agentPersonnelMapper.addSalerLevel(le);
		}

		// 设置下属部门
		if(depts != null && depts.length > 0){
			Map<String, Object> param_d = new HashMap<>();
			param_d.put("userid", salerid);
			param_d.put("add_subdepts", depts);
			//权限持久化
			agentPersonnelMapper.addUserSubdepts(param_d);
		}

		// 绑定代理商 - 员工关系
		Map<String, String> param = new HashMap<>();
		param.put("id", UUIDUtil.getUUID());
		param.put("agentuserid", agentid);
		param.put("deptid", deptid);
		param.put("salesmanuserid", salerid);
		param.put("positionid", positionid);
		agentPersonnelMapper.addAgentUser(param);

		// 微信推广二维码
		String ticket = agentQRService.createWxQRCode(userInfo.getUserid());
		Popu wxqr = new Popu();
		wxqr.setId(UUIDUtil.getUUID());
		wxqr.setUserid(userInfo.getUserid());
		wxqr.setQrurl(PropertyUtil.getProperty("ticket_qr_url") + ticket);
		wxqr.setType(3);
		wxqr.setCreateid(userInfo.getCreateid());

		// 添加推广信息
		// 推广公共数据
		boolean boo = true;
		String invitecode = null;
		while (boo) {
			// 生成不重复的邀请码
			invitecode = GenRandomNumUtil.getRandomNum(6);
			// 邀请码重复性检测
			boo = agentQRMapper.checkInviteCode(invitecode);
		}

		// 注册推广数据
		Popu qr = new Popu();
		qr.setId(UUIDUtil.getUUID());
		qr.setUserid(userInfo.getUserid());
		qr.setInvitecode(invitecode);
		qr.setType(1);
		qr.setCreateid(userInfo.getCreateid());
		Popu qrCode = agentQRService.createQRCode(qr, agentid);
		// 直播间推广数据
		Popu live = new Popu();
		live.setId(UUIDUtil.getUUID());
		live.setUserid(userInfo.getUserid());
		live.setInvitecode(invitecode);
		live.setType(2);
		live.setCreateid(userInfo.getCreateid());
		Popu liveCode = agentQRService.createQRCode(live, agentid);

		List<Popu> popus = new ArrayList<>();
		popus.add(wxqr);
		popus.add(qrCode);
		popus.add(liveCode);
		agentQRMapper.addPopuInfo(popus);
	}

	/**
	 * @Title: getSalerData
	 * @Description: 获取业务员部门数据
	 * @param agentid
	 * @param salerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午4:55:37
	 */
	public Saler getSalerData(String agentid, String salerid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("salerid", salerid);
		return agentPersonnelMapper.findSalerDeptData(param);
	}

	/**
	 * @Title: getSalerPositionData
	 * @Description: 获取业务员岗位数据
	 * @param agentid
	 * @param salerid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午4:55:37
	 */
	public Saler getSalerPositionData(String agentid, String salerid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("salerid", salerid);
		return agentPersonnelMapper.findSalerPositionData(param);
	}

	/**
	 * @Title: delSaler
	 * @Description: 删除业务员
	 * @param lids
	 * @param agentid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午4:37:54
	 */
	@Transactional
	public void delSaler(List<String> ids, String agentid) throws SQLExecutorException {
		// 1、删除用户
		adminUserMapper.deleteUserById(ids);
		// 2、解绑代理商 - 员工关系
		Map<String, Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("agentid", agentid);
		agentPersonnelMapper.unbindAgentUser(param);
		//3、释放业务员-用户关系
		agentPersonnelMapper.unbindSalerUserRelation(param);
		
	}

	/**
	 * @Title: editSaler
	 * @Description: 修改业务员信息
	 * @param userid
	 * @param deptid
	 * @param levelid
	 * @param userinfo
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午5:29:35
	 */
	@Transactional
	public void editSaler(String deptid, String levelid, String positionid, String[] depts, UserInfo userinfo) throws SQLExecutorException {
		if(positionid == null || "".equals(positionid.trim()))
		{
			positionid = null;
			userinfo.setRole(3);
		}
		else
		{
			userinfo.setRole(6);
		}
		// 1、修改业务员信息
		agentPersonnelMapper.updateSalerInfo(userinfo);
		// 2、修改业务员部门信息
		Map<String, String> param = new HashMap<>();
		param.put("userid", userinfo.getUserid());
		if (deptid == null || "".equals(deptid.trim())) {
			deptid = null;
		}
		param.put("newdeptid", deptid);
		agentPersonnelMapper.changeSalerDept(param);

		// 2.1、修改业务员岗位
		param = new HashMap<>();
		param.put("userid", userinfo.getUserid());
		param.put("newpositionid", positionid);
		agentPersonnelMapper.changeSalerPosition(param);
		
		// 2.2、设置下属部门
		agentPersonnelMapper.delSalerSubdeptdBySalerid(userinfo.getUserid());
		if(depts != null && depts.length > 0){
			Map<String, Object> param_d = new HashMap<>();
			param_d.put("userid", userinfo.getUserid());
			param_d.put("add_subdepts", depts);
			//权限持久化
			agentPersonnelMapper.addUserSubdepts(param_d);
		}

		// 3、修改业务员等级
		if (levelid == null || levelid.trim().isEmpty()) {
			// 删除等级
			agentBusiMapper.delSalerLevelRelationBySalerid(userinfo.getUserid());
		} else {
			Map<String, String> map = new HashMap<>();
			map.put("levelid", levelid);
			map.put("salerid", userinfo.getUserid());
			// 检测业务员有没有等级
			int count = agentBusiMapper.checkSalerLevelRelation(userinfo.getUserid());
			if (count == 0) {
				// 插入等级关系
				agentBusiMapper.addSalerLevelRelation(map);
			} else {
				// 修改等级关系
				agentBusiMapper.editSalerLevelRelation(map);
			}
		}
	}

	/**
	 * @Title: findSalerTaskList
	 * @Description: 业务员任务列表
	 * @param agentid
	 * @param start
	 * @param length
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月24日 上午11:52:10
	 */
	public Map<String, List> findSalerTaskList(String agentid, int year, int month) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("date", year + "-" + month);
		// 2、获取需求数据
		List<SalerTask> saler = agentPersonnelMapper.findSalerTask(param);

		// 表头数据
		List<Object> htab = new ArrayList<>();
		// 表体数据
		List<List<String>> btab = new ArrayList<>();

		// 表体数据中间件
		List<String> cache = new ArrayList<>();

		// 总任务数，已完成任务数
		int count = 0, finish = 0;

		String lastsalerid = "";
		for (SalerTask st : saler) {
			if (!htab.contains(st.getCreatetime())) {
				htab.add(st.getCreatetime());
			}
			if (!lastsalerid.equals(st.getUserid())) {
				// 新建后添加
				if (!cache.isEmpty()) {
					cache.add(finish + "/" + count);
					List<String> cp = new ArrayList<>();
					cp.addAll(cache);
					btab.add(cp);
				}
				count = 0;
				finish = 0;
				cache.clear();
				cache.add(st.getRealname());
			}
			count += st.getStandpeople();
			finish += st.getActualpeople();

			cache.add(st.getActualpeople() + "/" + st.getStandpeople());
			lastsalerid = st.getUserid();
		}

		cache.add(finish + "/" + count);
		List<String> cp = new ArrayList<>();
		cp.addAll(cache);
		btab.add(cp);

		Map<String, List> data = new HashMap<>();
		data.put("htab", htab);
		data.put("btab", btab);
		return data;
	}

	/**
	 * @Title: findPosition
	 * @Description: 代理商岗位列表
	 * @param agentid
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午9:49:42
	 */
	public DataTable findPosition(String agentid, int start, int length) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("agentid", agentid);
		param.put("start", start);
		param.put("length", length);

		DataTable dt = new DataTable();

		// 根据条件获取总条数
		int count = agentPersonnelMapper.findPositionCount(param);
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
		List<Position> positions = agentPersonnelMapper.findPosition(param);

		// 设置数据
		dt.setData(positions);
		return dt;
	}
	
	/**
	 * @Title: addNewPosition
	 * @Description: 添加岗位
	 * @param name
	 * @param userid
	 * @param agentRoles
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午10:25:36
	 */
	@Transactional
	public ResultBean addNewPosition(String name, String userid, String[] agentRoles) throws SQLExecutorException {
		Position position = new Position();
		position.setPositionid(UUIDUtil.getUUID());
		position.setPositionname(name);
		position.setCreateid(userid);
		// 名称重复性检测
		boolean boo = checkPositionName(position);
		if (boo) {
			// 名称已重复
			return new ResultBean("020", ResultMsg.C_020);
		}
		// 检测通过
		agentPersonnelMapper.addPosition(position);
		
		//权限添加
		if(agentRoles != null && agentRoles.length > 0){
			Map<String, Object> param = new HashMap<>();
			param.put("positionid", position.getPositionid());
			param.put("add_roles", agentRoles);
			//权限持久化
			agentPersonnelMapper.addPositionRole(param);
		}
		
		return new ResultBean();
	}

	/**
	 * @Title: editPosition
	 * @Description: 岗位修改
	 * @param position
	 * @param agentRoles
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午11:05:01
	 */
	@Transactional
	public ResultBean editPosition(Position position, String oldName, String[] agentRoles) throws SQLExecutorException {
		if(!oldName.equals(position.getPositionname()))
		{
			// 部门名称重复性检测
			boolean boo = checkPositionName(position);
			if (boo) {
				// 名称已重复
				return new ResultBean("020", ResultMsg.C_020);
			}
		}
		// 检测通过
		agentPersonnelMapper.editPosition(position);
		
		agentPersonnelMapper.cleanPositionRole(position.getPositionid());
		//权限添加
		if(agentRoles != null && agentRoles.length > 0){
			Map<String, Object> param = new HashMap<>();
			param.put("positionid", position.getPositionid());
			param.put("add_roles", agentRoles);
			//权限持久化
			agentPersonnelMapper.addPositionRole(param);
		}
		return new ResultBean();
	}

	/**
	 * @Title: changePositionStatus
	 * @Description: 岗位停、启用
	 * @param userid
	 * @param status
	 *            1启用/0禁用
	 * @param positionid
	 *            当前岗位id
	 * @return
	 * @throws:
	 * @time: 2018年2月7日 上午11:50:47
	 */
	@Transactional
	public void changePositionStatus(String userid, int status, String positionid)
			throws SQLExecutorException {
		Position position = new Position();
		position.setCreateid(userid);
		position.setPositionid(positionid);
		position.setPositionenable(status);
		// 禁用部门
		agentPersonnelMapper.changePositionStatus(position);
	}

	/**
	 * @Title: delPosition
	 * @Description: 删除岗位
	 * @param ids
	 * @param agentid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 下午2:04:07
	 */
	@Transactional
	public void delPosition(List<String> ids, String agentid) throws SQLExecutorException {
		Map<String, Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("agentid", agentid);
		agentPersonnelMapper.delPosition(param);
	}

	/**
	 * @Title: checkPositionName
	 * @Description: 岗位名称重复性检测
	 * @param agent
	 * @param positionname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月7日 上午11:06:27
	 */
	private boolean checkPositionName(Position position) throws SQLExecutorException {
		return agentPersonnelMapper.checkPositionName(position);
	}
	
	/**
	 * @Title: addPositionRole
	 * @Description: 添加岗位权限
	 * @param userInfo
	 * @param roles
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月15日 上午11:42:54
	 */
	@Transactional
	public void addPositionRole(UserInfo userInfo, String[] roles) throws SQLExecutorException {
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

}