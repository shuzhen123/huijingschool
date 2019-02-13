package dianfan.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.syslog.SysLogsMapper;
import dianfan.entities.SysLogs;

@Service
public class LogInfoService {
	@Autowired
	private SysLogsMapper sysLogsMapper;

	/**
	 * @Title: writeLog
	 * @Description: 记录接口访问日志
	 * @param logtype
	 * @param description
	 * @param method
	 * @param userid
	 * @param params
	 * @param requestip
	 * @throws Exception
	 * @throws:
	 * @time: 2017年12月20日 下午4:47:34
	 */
	@Transactional
	public void writeLog(Long logtype, String description, String method, String userid, String requestip) throws Exception {
		SysLogs info = new SysLogs();
		info.setLogtype(logtype);
		info.setMethod(method);
		info.setCreatetime(new Timestamp(new Date().getTime()));
		info.setCreateid(userid);
		info.setDescription(description);
		info.setRequestip(requestip);
		sysLogsMapper.saveSysLogs(info);
	}
}
