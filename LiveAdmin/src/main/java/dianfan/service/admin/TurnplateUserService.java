package dianfan.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.TurnplateUserMapper;
import dianfan.entities.DataTable;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName TurnplateUserService
 * @Description 大转盘提交用户服务
 * @author cjy
 * @date 2018年5月3日 上午10:57:16
 */
@Service
public class TurnplateUserService {
	@Autowired
	private TurnplateUserMapper adminUserMapper;

	/**
	 * @Title: findTurnplateUsers
	 * @Description: 大转盘提交用户列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午11:02:33
	 */
	public DataTable findTurnplateUsers(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		int count = adminUserMapper.findTurnplateUserCount(param);
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
		List<UserInfo> users = adminUserMapper.findTurnplateUsers(param);
		// 设置数据
		dt.setData(users);
		return dt;
	}

}
