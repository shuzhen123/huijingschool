package dianfan.service.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;

import common.propertymanager.PropertyUtil;
import dianfan.dao.mapper.admin.AdminUserMapper;
import dianfan.dao.mapper.admin.AgentMapper;
import dianfan.dao.mapper.agent.AgentQRMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Popu;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.agent.AgentQRService;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AgentService
 * @Description 代理商管理服务
 * @author cjy
 * @date 2018年3月15日 下午3:02:44
 */
@Service
public class AgentService {
	@Autowired
	private AgentMapper agentMapper;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AgentQRMapper agentQRMapper;
	@Autowired
	private AgentQRService agentQRService;

	/**
	 * @Title: findAgentUsers
	 * @Description: 根据条件查询代理商列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午3:08:26
	 */
	public DataTable findAgentUsers(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();

		// 1、根据条件查询代理商总条数
		Integer count = agentMapper.findAgentCount(param);
		if (count == null || count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、根据条件查询代理商数据
		List<UserInfo> users = agentMapper.findAgentInfo(param);

		// 设置数据
		dt.setData(users);
		return dt;
	}

	/**
	 * @Title: addAgentInfo
	 * @Description: 添加新代理商
	 * @param user
	 * @throws SQLExecutorException
	 * @throws IOException
	 * @throws WriterException
	 * @throws NumberFormatException
	 * @throws:
	 * @time: 2018年3月15日 下午4:32:18
	 */
	@Transactional
	public void addAgentInfo(UserInfo user)
			throws SQLExecutorException, NumberFormatException, WriterException, IOException {
		user.setUserid(UUIDUtil.getUUID());
		agentMapper.addAgentInfo(user);

		// 微信推广二维码
		String ticket = agentQRService.createWxQRCode(user.getUserid());
		Popu wxqr = new Popu();
		wxqr.setId(UUIDUtil.getUUID());
		wxqr.setUserid(user.getUserid());
		wxqr.setQrurl(PropertyUtil.getProperty("ticket_qr_url") + ticket);
		wxqr.setType(3);
		wxqr.setCreateid(user.getCreateid());

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
		qr.setUserid(user.getUserid());
		qr.setInvitecode(invitecode);
		qr.setType(1);
		qr.setCreateid(user.getCreateid());
		Popu qrCode = agentQRService.createQRCode(qr, user.getUserid());
		// 直播间推广数据
		Popu live = new Popu();
		live.setId(UUIDUtil.getUUID());
		live.setUserid(user.getUserid());
		live.setInvitecode(invitecode);
		live.setType(2);
		live.setCreateid(user.getCreateid());
		Popu liveCode = agentQRService.createQRCode(live, user.getUserid());

		List<Popu> popus = new ArrayList<>();
		popus.add(wxqr);
		popus.add(qrCode);
		popus.add(liveCode);
		agentQRMapper.addPopuInfo(popus);
	}

	/**
	 * @Title: delAgent
	 * @Description: 删除代理商
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午4:42:48
	 */
	@Transactional
	public void delAgent(List<String> lids) throws SQLExecutorException {
		adminUserMapper.deleteUserById(lids);
	}

	/**
	 * @Title: updateAgentInfo
	 * @Description: 代理商数据修改
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午5:52:59
	 */
	@Transactional
	public void updateAgentInfo(UserInfo user) throws SQLExecutorException {
		agentMapper.updateAgentInfo(user);
	}

	/**
	 * @Title: updateSelfAgentInfo
	 * @Description: 代理商个人信息修改
	 * @param user
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午5:52:59
	 */
	@Transactional
	public void updateSelfAgentInfo(UserInfo user) throws SQLExecutorException {
		agentMapper.updateSelfAgentInfo(user);
	}

	/**
	 * @Title: agentTeacherCommend
	 * @Description: 代理商教师推荐修改
	 * @param teacherid
	 * @param type
	 *            推荐修改类型（trecflag 名师推荐, untrecflag 名师不推荐, famous 直播推荐, unfamous
	 *            直播不推荐）
	 * @throws:
	 * @time: 2018年3月28日 下午4:27:30
	 */
	@Transactional
	public void agentTeacherCommend(String teacherid, String type) throws SQLExecutorException {
		Map<String, Object> data = new HashMap<>();
		data.put("teacherid", teacherid);

		if ("trecflag".equals(type)) {
			// 名师推荐
			data.put("trecflag", 1);
		} else if ("untrecflag".equals(type)) {
			// 名师不推荐
			data.put("trecflag", 0);
		} else if ("famous".equals(type)) {
			// 直播推荐
			data.put("famousteacherflag", 1);
		} else if ("unfamous".equals(type)) {
			// 直播不推荐
			data.put("famousteacherflag", 0);
		}
		agentMapper.changeCommend(data);
	}

}
