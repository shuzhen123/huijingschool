package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.Infomation;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppGeniusViewMapper
 * @Description 
 * @author cjy
 * @date 2018年4月2日 上午11:01:22
 */
@Repository
public interface AppGeniusViewMapper {

	/**
	 * @Title: findGeniusViewCount
	 * @Description: 牛人观点总数量
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午9:32:07
	 */
	@Select("select count(*) from t_infomation where informationmodelid='-1' and entkbn=0")
	int findGeniusViewCount() throws SQLExecutorException;

	/**
	 * @Title: findGeniusViewList
	 * @Description: 牛人观点数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午9:34:13
	 */
	@Select("select info.id, info.infomationtitle, info.descrption, info.createtime, tea.realname from t_infomation info, t_userinfo tea where " + 
			"info.userid=tea.userid and info.informationmodelid='-1' and info.entkbn=0 order by info.createtime desc limit #{start}, #{offset}")
	List<Infomation> findGeniusViewList(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: appGeniusViewMapper
	 * @Description: 牛人观点详情
	 * @param id
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 下午12:00:10
	 */
	@Select("select info.id, info.infomationtitle, info.content, info.createtime, tea.realname " + 
			"from t_infomation info, t_userinfo tea where info.userid=tea.userid and info.id=#{id}")
	Infomation appGeniusViewMapper(String id) throws SQLExecutorException;

}
