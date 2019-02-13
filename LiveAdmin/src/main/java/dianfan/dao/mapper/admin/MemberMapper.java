package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName MemberMapper
 * @Description 用户管理dao
 * @author cjy
 * @date 2018年5月9日 上午9:36:06
 */
@Repository
public interface MemberMapper {
	/**
	 * @Title: findUsersCount
	 * @Description: 根据条件查询用户总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月3日 下午1:41:56
	 */
	@Select("select count(*) from t_userinfo where entkbn!=9 and role=1")
	int findUserCount(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findUsers
	 * @Description: 根据条件查询用户列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月3日 下午1:41:56
	 */
	List<UserInfo> findUsers(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findMemberClassifyCount
	 * @Description: 获取用户分类总数量
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午1:35:17
	 */
	@Select("select count(*) from t_csclassify")
	int findMemberClassifyCount() throws SQLExecutorException;
	
	/**
	 * @Title: findMemberClassify
	 * @Description: 获取用户分类
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午1:37:18
	 */
	@Select("select id, name from t_csclassify limit #{start}, #{length}")
	List<BashMap> findMemberClassify(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: checkClassifyName
	 * @Description: 检测新增分类名是否重复
	 * @param classname
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午2:00:43
	 */
	@Select("select count(*) from t_csclassify where name=#{classname}")
	Boolean checkClassifyName(String classname) throws SQLExecutorException;
	
	/**
	 * @Title: checkClassifyNameById
	 * @Description: 检测非id下的分类名是否重复
	 * @param id
	 * @param classname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 上午10:41:13
	 */
	@Select("select count(*) from t_csclassify where name=#{classname} and id <> #{id}")
	Boolean checkClassifyNameById(@Param(value="id") String id, @Param(value="classname") String classname) throws SQLExecutorException;
	
	/**
	 * @Title: addMemberClassify
	 * @Description: 添加用户分类
	 * @param classname
	 * @throws:
	 * @time: 2018年3月15日 下午1:53:25
	 */
	@Insert("insert into t_csclassify (id, name) values (replace(uuid(),'-',''), #{classname})")
	void addMemberClassify(String classname) throws SQLExecutorException;
	
	/**
	 * @Title: editMemberClassify
	 * @Description: 修改用户分类
	 * @param id
	 * @param classname
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 上午10:43:28
	 */
	@Update("update t_csclassify set name=#{classname} where id=#{id}")
	void editMemberClassify(@Param(value="id") String id, @Param(value="classname") String classname) throws SQLExecutorException;
	
	/**
	 * @Title: delClassify
	 * @Description: 删除用户分类
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午2:28:05
	 */
	void delClassify(List<String> lids) throws SQLExecutorException;
	
	/**
	 * @Title: changeUserClassify
	 * @Description: 修改分类下的客户类型
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月15日 下午2:31:14
	 */
	void changeUserClassify(List<String> lids) throws SQLExecutorException;

	
}
