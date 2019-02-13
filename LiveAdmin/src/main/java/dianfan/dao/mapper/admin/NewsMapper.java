package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.Infomation;
import dianfan.entities.NewsModel;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName NewsMapper
 * @Description 咨讯dao
 * @author cjy
 * @date 2018年2月26日 下午5:43:02
 */
@Repository
public interface NewsMapper {

	/**
	 * @Title: findNewsCount
	 * @Description: 根据条件获取咨讯模块数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:44:32
	 */
	@Select("select count(*) from t_informationmodule where entkbn=0")
	int findNewsModelCount() throws SQLExecutorException;

	/**
	 * @Title: findNewsModel
	 * @Description: 根据条件获取咨讯模块数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月9日 下午5:46:08
	 */
	List<NewsModel> findNewsModel(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: delNewsModelByIds
	 * @Description: 批量删除模块
	 * @param ids
	 * @throws:
	 * @time: 2018年1月9日 下午6:06:56
	 */
	void delNewsModelByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: findCountByName
	 * @Description: 根据条件查找模块条数
	 * @param modelname
	 * @throws:
	 * @time: 2018年1月10日 上午9:45:35
	 */
	int findCountByName(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: addNewsModel
	 * @Description: 添加新模块
	 * @param model
	 * @throws:
	 * @time: 2018年1月10日 上午9:33:09
	 */
	@Insert("insert into t_informationmodule (informationid, newskindname, entkbn) values (#{informationid}, #{newskindname}, 0)")
	void addNewsModel(NewsModel model) throws SQLExecutorException;

	/**
	 * @Title: editNewsModel
	 * @Description: 修改模块
	 * @param nm
	 * @throws:
	 * @time: 2018年1月10日 上午10:23:13
	 */
	@Update("update t_informationmodule set newskindname = #{newskindname} where informationid = #{informationid}")
	void editNewsModel(NewsModel model) throws SQLExecutorException;

	/**
	 * @Title: findNewsCount
	 * @Description: 根据条件查找咨讯总条数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午10:54:57
	 */
	int findNewsCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findNews
	 * @Description: 根据条件查找咨讯列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年1月10日 上午10:56:09
	 */
	List<Infomation> findNews(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findAgentList
	 * @Description: 获取代理商列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月8日 上午9:40:39
	 */
	@Select("select userid id, realname name from t_userinfo where role = 4 and entkbn = 0 order by registertime asc")
	List<BashMap> findAgentList() throws SQLExecutorException;
	
	/**
	 * @Title: findTeacherByAgentid
	 * @Description: 根据代理商获取讲师列表
	 * @param agentid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 下午1:50:26
	 */
	@Select("select info.userid id, info.realname name from t_agent_teac teac, t_userinfo info where teac.teacherid=info.userid and teac.agentid=#{agentid}")
	List<BashMap> findTeacherByAgentid(String agentid) throws SQLExecutorException;
	
	/**
	 * @Title: addNewsInfo
	 * @Description: 添加咨讯数据
	 * @param info
	 * @throws:
	 * @time: 2018年1月11日 下午12:58:30
	 */
	void addNewsInfo(Infomation info) throws SQLExecutorException;

	/**
	 * @Title: delNewsByIds
	 * @Description: 根据id删除咨讯数据
	 * @param ids
	 * @throws:
	 * @time: 2018年1月11日 下午1:57:38
	 */
	void delNewsByIds(List<String> ids) throws SQLExecutorException;

	/**
	 * @Title: findNewsInfoById
	 * @Description: 获取咨讯数据
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年1月11日 下午2:21:57
	 */
	@Select("select * from t_infomation where id = #{id}")
	Infomation findNewsInfoById(String id) throws SQLExecutorException;

	/**
	 * @Title: findTeachersByTid
	 * @Description: 根据讲师id获取相同代理商下的讲师
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 下午2:27:58
	 */
	List<BashMap> findTeachersByTid(String teacherid) throws SQLExecutorException;
	
	/**
	 * @Title: findAgentidByTid
	 * @Description: 根据讲师id获取代理商id
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月22日 下午2:29:27
	 */
	@Select("select agentid from t_agent_teac where teacherid=#{teacherid}")
	String findAgentidByTid(String teacherid) throws SQLExecutorException;
	
	/**
	 * @Title: editNewsInfo
	 * @Description: 修改咨讯数据
	 * @param info
	 * @throws:
	 * @time: 2018年1月11日 下午2:44:25
	 */
	void editNewsInfo(Infomation info) throws SQLExecutorException;

	/**
	 * @Title: findViewpointCount
	 * @Description: 根据条件获取观点数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午9:57:00
	 */
	int findViewpointCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findViewpoint
	 * @Description: 根据条件获取观点数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午9:58:27
	 */
	List<Infomation> findViewpoint(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: editViewpointInfo
	 * @Description: 修改观点数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午10:13:24
	 */
	@Update("update t_infomation set infomationtitle = #{infomationtitle}, "
			+ "descrption = #{descrption}, content = #{content} where id =#{id}")
	void editViewpointInfo(Infomation info) throws SQLExecutorException;

}