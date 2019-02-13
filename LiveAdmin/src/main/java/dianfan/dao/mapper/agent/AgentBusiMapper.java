package dianfan.dao.mapper.agent;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.SalerLevel;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName BusiMapper
 * @Description 业务dao
 * @author cjy
 * @date 2018年2月23日 下午3:33:08
 */
@Repository
public interface AgentBusiMapper {

	/**
	 * @Title: findSalerLevelCount
	 * @Description: 根据条件获取等级列表数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午3:34:43
	 */
	@Select("select count(*) from t_level where agentid=#{agentid} and entkbn=0")
	int findSalerLevelCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findCoupons
	 * @Description: 根据条件获取等级列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月8日 下午2:58:31
	 */
	@Select("select * from t_level where agentid=#{agentid} and entkbn=0 order by levelname limit #{start}, #{length}")
	List<SalerLevel> findSalerLevel(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findSalerLevelById
	 * @Description: 获取业务员等级
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月24日 下午1:53:05
	 */
	@Select("select s.levelid id, l.levelname, l.limitpeople from t_level_saler s, t_level l where s.levelid = l.id and userid = #{userid} limit 0, 1")
	SalerLevel findSalerLevelById(String userid) throws SQLExecutorException;
	
	/**
	 * @Title: findCountByLevelName
	 * @Description: 等级名称重复性检测
	 * @param salerLevel
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午4:04:11
	 */
	@Select("select count(*) from t_level where levelname = #{levelname} and agentid=#{agentid} and entkbn=0")
	int findCountByLevelName(SalerLevel salerLevel) throws SQLExecutorException;
	
	/**
	 * @Title: findCountByLevelid
	 * @Description: 等级名称重复性检测
	 * @param salerLevel
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午4:38:00
	 */
	@Select("select count(*) from t_level where levelname = #{levelname} and id != #{id} and agentid=#{agentid} and entkbn=0")
	int findCountByLevelId(SalerLevel salerLevel) throws SQLExecutorException;
	
	/**
	 * @Title: addSalerLevel
	 * @Description: 添加等级
	 * @param salerLevel
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 下午3:56:18
	 */
	@Insert("insert into t_level (id, agentid, levelname, xcount, ycount, zcount , limitpeople)"
			+ " values (#{id}, #{agentid}, #{levelname},#{xcount}, #{ycount}, #{zcount}, #{limitpeople})")
	void addSalerLevel(SalerLevel salerLevel) throws SQLExecutorException;

	/**
	 * @Title: editSalerLevel
	 * @Description: 修改等级
	 * @param salerLevel
	 * @throws:
	 * @time: 2018年2月23日 下午4:39:55
	 */
	@Update("update t_level set levelname = #{levelname}, xcount = #{xcount}, "
			+ "ycount = #{ycount}, zcount = #{zcount}, limitpeople = #{limitpeople} where id = #{id}")
	void editSalerLevel(SalerLevel salerLevel) throws SQLExecutorException;

	/**
	 * @Title: delSalerLevel
	 * @Description: 删除等级
	 * @param lids
	 * @throws:
	 * @time: 2018年2月23日 下午4:45:46
	 */
	void delSalerLevel(List<String> lids) throws SQLExecutorException;

	/**
	 * @Title: delSalerLevelRelation
	 * @Description: 根据levelid删除对应的业务员-等级关系
	 * @param lids
	 * @throws:
	 * @time: 2018年2月23日 下午4:53:59
	 */
	void delSalerLevelRelation(List<String> lids) throws SQLExecutorException;

	/**
	 * @Title: delSalerLevelRelationBySalerid
	 * @Description: 根据salerid删除对应的业务员-等级关系
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月24日 下午2:14:42
	 */
	@Delete("delete from t_level_saler where userid = #{salerid}")
	void delSalerLevelRelationBySalerid(String salerid) throws SQLExecutorException;

	/**
	 * @Title: checkSalerLevelRelation
	 * @Description: 检测业务员有没有等级
	 * @param userid
	 * @throws:
	 * @time: 2018年2月24日 下午2:11:05
	 */
	@Select("select count(*) from t_level_saler where userid = #{userid}")
	int checkSalerLevelRelation(String userid) throws SQLExecutorException;

	/**
	 * @Title: addSalerLevelRelation
	 * @Description: 插入业务员等级关系
	 * @param map
	 * @throws:
	 * @time: 2018年2月24日 下午2:18:17
	 */
	@Insert("insert into t_level_saler (id, levelid, userid) values (replace(uuid(),'-',''), #{levelid}, #{salerid})")
	void addSalerLevelRelation(Map<String, String> map) throws SQLExecutorException;

	/**
	 * @Title: editSalerLevelRelation
	 * @Description: 修改业务员等级关系
	 * @param map
	 * @throws:
	 * @time: 2018年2月24日 下午2:20:58
	 */
	@Update("update t_level_saler set levelid = #{levelid} where userid = #{salerid}")
	void editSalerLevelRelation(Map<String, String> map) throws SQLExecutorException;
}