package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.MemberMapper;
import dianfan.entities.BashMap;
import dianfan.entities.DataTable;
import dianfan.entities.UserAnswerList;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName MemberService
 * @Description 用户管理服务
 * @author cjy
 * @date 2018年1月3日 下午12:17:12
 */
@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;

	/**
	 * @Title: findUsers
	 * @Description: 根据条件查询用户列表
	 * @param search
	 * @param start
	 * @param length
	 * @param role
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月5日 下午3:21:57
	 */
	public DataTable findUsers(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		int count = memberMapper.findUserCount(param);
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
		List<UserInfo> users = memberMapper.findUsers(param);
		for (UserInfo ui : users) {
			ui.setIconurl(ConstantIF.PROJECT + ui.getIconurl());
		}
		// 设置数据
		dt.setData(users);
		return dt;
	}

	/**
	 * @Title: findMemberClassify
	 * @Description: 获取用户分类列表
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午1:35:23
	 */
	public DataTable findMemberClassify(int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 1、获取总条数
		int count = memberMapper.findMemberClassifyCount();
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
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		List<BashMap> classify = memberMapper.findMemberClassify(param);

		// 设置数据
		dt.setData(classify);
		return dt;
	}

	/**
	 * @Title: addMemberClassify
	 * @Description: 添加用户分类
	 * @param classname
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午1:52:24
	 */
	@Transactional
	public void addMemberClassify(String classname) throws SQLExecutorException {
		memberMapper.addMemberClassify(classname);
	}
	
	/**
	 * @Title: editMemberClassify
	 * @Description: 修改用户分类
	 * @param id
	 * @param classname
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月9日 上午10:42:26
	 */
	@Transactional
	public void editMemberClassify(String id, String classname) throws SQLExecutorException {
		memberMapper.editMemberClassify(id, classname);
	}

	/**
	 * @Title: delClassify
	 * @Description: 删除用户分类
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午2:27:33
	 */
	@Transactional
	public void delClassify(List<String> lids) throws SQLExecutorException {
		// 1、删除用户分类
		memberMapper.delClassify(lids);
		// 修改分类下的客户类型
		memberMapper.changeUserClassify(lids);
	}
}
