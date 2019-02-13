package common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IBaseDao
 * @Description: Dao封装接口
 * @author yz
 * @date 2014年6月16日17:05:17
 */
public interface IBaseDao<T, ID extends Serializable> {
	/**
	 * <findByProperty>
	 * 
	 * @param t
	 *            实体
	 * @see com.itv.launcher.util.IBaseDao#update(java.lang.Object)
	 */
	public List<T> findByProperty(String propertyName, String propertyValue, T t) throws Exception;

	/**
	 * <保存实体> <完整保存实体>
	 * 
	 * @param t
	 *            实体参数
	 */
	public abstract T save(T t) throws Exception;

	/**
	 * <保存或者更新实体>
	 * 
	 * @param t
	 *            实体
	 */
	public abstract void saveOrUpdate(T t) throws Exception;

	/**
	 * <load> <加载实体的load方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 */
	public abstract T load(ID id) throws Exception;

	/**
	 * <get> <查找的get方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 */
	public abstract T get(ID id) throws Exception;

	/**
	 * <contains>
	 * 
	 * @param t
	 *            实体
	 * @return 是否包含
	 */
	public abstract boolean contains(T t) throws Exception;

	/**
	 * <delete> <删除表中的t数据>
	 * 
	 * @param t
	 *            实体
	 */
	public abstract void delete(T t) throws Exception;

	/**
	 * <根据ID删除数据>
	 * 
	 * @param Id
	 *            实体id
	 * @return 是否删除成功
	 */
	public abstract boolean deleteById(ID Id) throws Exception;

	/**
	 * <删除所有>
	 * 
	 * @param entities
	 *            实体的Collection集合
	 */
	public abstract void deleteAll(Collection<T> entities) throws Exception;

	/**
	 * <执行Hql语句>
	 * 
	 * @param hqlString
	 *            hql
	 * @param values
	 *            不定参数数组
	 */
	public abstract void queryHql(String hqlString, Object... values) throws Exception;

	/**
	 * <执行Sql语句>
	 * 
	 * @param sqlString
	 *            sql
	 * @param values
	 *            不定参数数组
	 */
	public abstract void querySql(String sqlString, Object... values) throws Exception;

	/**
	 * <根据HQL语句查找唯一实体>
	 * 
	 * @param hqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询实体
	 */
	public abstract T getByHQL(String hqlString, Object... values) throws Exception;

	/**
	 * <根据SQL语句查找唯一实体>
	 * 
	 * @param sqlString
	 *            SQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询实体
	 */
	public abstract T getBySQL(String sqlString, Object... values) throws Exception;

	/**
	 * <根据HQL语句，得到对应的list>
	 * 
	 * @param hqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询多个实体的List集合
	 */
	public abstract List<T> getListByHQL(String hqlString, Object... values) throws Exception;

	/**
	 * <根据HQL语句，得到对应的List<Map>>
	 * 
	 * @param hqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询多个实体的Map集合
	 */
	public abstract List<Map<String, Object>> getMapByHQL(String hqlString, Object... values) throws Exception;

	/**
	 * <根据SQL语句，得到对应的list>
	 * 
	 * @param sqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询多个实体的List集合
	 */
	public abstract List<T> getListBySQL(String sqlString, Object... values) throws Exception;

	/**
	 * 由sql语句得到List
	 * 
	 * @param sql
	 * @param map
	 * @param values
	 * @return List
	 */
	public List findListBySql(final String sql, final RowMapper map, final Object... values) throws Exception;

	/**
	 * <refresh>
	 * 
	 * @param t
	 *            实体
	 */
	public abstract void refresh(T t) throws Exception;

	/**
	 * <update>
	 * 
	 * @param t
	 *            实体
	 */
	public abstract void update(T t) throws Exception;

	/**
	 * <根据HQL得到记录数>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 记录总数
	 */
	public abstract Integer countByHql(String hql, Object... values) throws Exception;

	/**
	 * <HQL分页查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param countHql
	 *            查询记录条数的HQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 */
	public abstract PageResults<T> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize,
			Object... values) throws Exception;
}
