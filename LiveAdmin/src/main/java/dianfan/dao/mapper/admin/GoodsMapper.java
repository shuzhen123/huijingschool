package dianfan.dao.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Goods;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName GoodsMapper
 * @Description 礼物dao
 * @author cjy
 * @date 2018年2月12日 下午4:43:41
 */
@Repository
public interface GoodsMapper {

	/**
	 * @Title: findGoodsCount
	 * @Description: 获取礼物数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 下午4:44:20
	 */
	@Select("select count(*) from t_goods where entkbn = 0")
	int findGoodsCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findGoods
	 * @Description: 获取礼物数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年2月12日 下午4:46:00
	 */
	@Select("select * from t_goods where entkbn = 0 limit #{start}, #{length}")
	List<Goods> findGoods(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: checkGoodsNameById
	 * @Description: 根据礼物id进行名称重复性检测
	 * @param goods
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 上午10:09:17
	 */
	@Select("select count(*) from t_goods where goodsid != #{goodsid} and goodsname = #{goodsname} and entkbn = 0")
	int checkGoodsNameById(Goods goods) throws SQLExecutorException;
	
	/**
	 * @Title: checkGoodsName
	 * @Description: 礼物名称重复性检测
	 * @param goods
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 上午9:30:53
	 */
	@Select("select count(*) from t_goods where goodsname = #{goodsname} and entkbn = 0")
	int checkGoodsName(Goods goods) throws SQLExecutorException;
	
	/**
	 * @Title: addGoods
	 * @Description: 添加礼物
	 * @param goods
	 * @throws:
	 * @time: 2018年2月23日 上午9:19:08
	 */
	@Insert("insert into t_goods (goodsid, goodsname, price, icon, agentprofit, salerprofit, createtime, entkbn) values "
			+ "(#{goodsid}, #{goodsname}, #{price}, #{icon}, #{agentprofit}, #{salerprofit}, now(), 0)")
	void addGoods(Goods goods) throws SQLExecutorException;

	/**
	 * @Title: editGoods
	 * @Description: 修改礼物信息
	 * @param goods
	 * @throws:
	 * @time: 2018年2月23日 上午10:12:00
	 */
	void editGoods(Goods goods) throws SQLExecutorException;

	/**
	 * @Title: delGoods
	 * @Description: 删除礼物
	 * @param lids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月23日 上午10:46:35
	 */
	void delGoods(List<String> lids) throws SQLExecutorException;

	

	


	
	
}