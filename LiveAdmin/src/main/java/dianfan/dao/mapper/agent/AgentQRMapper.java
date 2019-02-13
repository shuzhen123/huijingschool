package dianfan.dao.mapper.agent;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.Popu;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AgentQRMapper
 * @Description 二维码推广dao
 * @author cjy
 * @date 2018年1月30日 下午3:58:56
 */
@Repository
public interface AgentQRMapper {

	/**
	 * @Title: checkInviteCode
	 * @Description: 邀请码重复性检测
	 * @param invitecode
	 * @return
	 * @throws:
	 * @time: 2018年2月11日 上午10:16:45
	 */
	@Select("select count(*) from t_popu where invitecode = #{invitecode}")
	boolean checkInviteCode(String invitecode) throws SQLExecutorException;

	/**
	 * @Title: getUserQR
	 * @Description: 获取二维码推广数据 
	 * @param userid
	 * @throws:
	 * @time: 2018年1月30日 下午4:38:42
	 */
	@Select("select * from t_popu where userid = #{userid} and type = #{type}")
	Popu getUserQR(Popu popu) throws SQLExecutorException;
	
	/**
	 * @Title: findAgentInfo
	 * @Description: 获取上级代理商信息
	 * @param userid
	 * @throws:
	 * @time: 2018年2月2日 上午11:12:08
	 */
	@Select("select agentuserid from t_agent_user where salesmanuserid = #{userid}")
	String findAgentInfo(String userid) throws SQLExecutorException;

	/**
	 * @Title: addPopuInfo
	 * @Description: 添加推广数据
	 * @param popus
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月11日 上午11:03:33
	 */
	void addPopuInfo(List<Popu> popus) throws SQLExecutorException;

	/**
	 * @Title: updatepopuInfo
	 * @Description: 更新推广链接
	 * @throws:
	 * @time: 2018年2月2日 下午1:25:25
	 */
	void updatePopuInfo(Popu popu) throws SQLExecutorException;

	/**
	 * @Title: getAgentIdBySalerId
	 * @Description: 根据业务员id获取代理商id
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年2月11日 下午12:32:46
	 */
	@Select("select agentuserid from t_agent_user where salesmanuserid = #{salerid}")
	String getAgentIdBySalerId(String salerid) throws SQLExecutorException;

}