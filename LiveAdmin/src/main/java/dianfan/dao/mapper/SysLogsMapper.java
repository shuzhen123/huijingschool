package dianfan.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import dianfan.entities.SysLogs;
/**
 * @ClassName SysLogsMapper
 * @Description api日志服务
 * @author cjy
 * @date 2018年1月2日 上午9:15:50
 */
@Repository
public interface SysLogsMapper {
	/**
	 * @Title: saveSysLogs
	 * @Description: 记录API接口请求日志
	 * @param logsInfo
	 * @throws:
	 * @time: 2018年1月2日 上午9:15:36
	 */
	@Insert("insert into t_syslogs  values (replace(uuid(),'-',''), #{description}, #{method}, #{logtype}, #{requestip}, #{createid}, #{createtime})")
	void saveSysLogs(SysLogs logsInfo);
}
