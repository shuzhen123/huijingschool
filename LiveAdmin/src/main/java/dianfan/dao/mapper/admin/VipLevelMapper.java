package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.VipLevel;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName VipLevelMapper
 * @Description vip赠送dao
 * @author cjy
 * @date 2018年4月4日 下午5:08:45
 */
@Repository
public interface VipLevelMapper {


	/**
	 * @Title: findVipLevelCount
	 * @Description: 获取vip赠送总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午5:35:09
	 */
	@Select("select count(*) from t_viplevel where entkbn=0")
	int findVipLevelCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findVipLevel
	 * @Description: 获取vip赠送数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午5:37:31
	 */
	@Select("select * from t_viplevel where entkbn=0 limit #{start}, #{length}")
	List<VipLevel> findVipLevel(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkVipLevelName
	 * @Description: 类型名称重复性检测
	 * @param name
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午6:02:15
	 */
	@Select("select count(*) from t_viplevel where levelname=#{name} and entkbn=0")
	Boolean checkVipLevelName(String name) throws SQLExecutorException;

	/**
	 * @Title: addVipLevel
	 * @Description: vip等级添加
	 * @param level
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月4日 下午6:04:30
	 */
	@Insert("insert into t_viplevel (id, levelname, money, days, createtime, entkbn) values (replace(uuid(),'-',''), #{levelname}, #{money}, #{days}, now(), 0)")
	void addVipLevel(VipLevel level) throws SQLExecutorException;

	/**
	 * @Title: checkVipLevelNameById
	 * @Description: 类型名称重复性检测，根据id
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 上午9:27:44
	 */
	@Select("select count(*) from t_viplevel where levelname=#{levelname} and id != #{id} and entkbn=0")
	Boolean checkVipLevelNameById(VipLevel level) throws SQLExecutorException;

	/**
	 * @Title: editVipLevel
	 * @Description: vip等级修改
	 * @param level
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 上午9:29:52
	 */
	@Update("update t_viplevel set levelname = #{levelname}, money = #{money}, days = #{days} where id=#{id}")
	void editVipLevel(VipLevel level) throws SQLExecutorException;

	/**
	 * @Title: delVipLevel
	 * @Description: 批量删除vip等级
	 * @param ids_str
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 上午10:31:15
	 */
	void delVipLevel(String[] ids) throws SQLExecutorException;

	
}