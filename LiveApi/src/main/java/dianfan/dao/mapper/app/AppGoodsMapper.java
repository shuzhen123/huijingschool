package dianfan.dao.mapper.app;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Goods;
import dianfan.entities.GoodsOrder;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppGoodsMapper
 * @Description 礼物相关dao
 * @author cjy
 * @date 2018年3月2日 下午1:00:50
 */
@Repository
public interface AppGoodsMapper {

	/**
	 * @Title: findGoods
	 * @Description: 获取礼物列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午1:02:56
	 */
	@Select("select goodsid, goodsname, price, icon from t_goods where entkbn = 0 order by price asc")
	List<Goods> findGoods() throws SQLExecutorException;

	/**
	 * @Title: getGoodsPrice
	 * @Description: 获取礼物价格
	 * @param gid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午1:55:15
	 */
	@Select("select price from t_goods where goodsid = #{gid}")
	BigDecimal getGoodsPrice(String gid) throws SQLExecutorException;

	/**
	 * @Title: createGoodsOrder
	 * @Description: 生成礼物未支付订单
	 * @param order
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午2:01:38
	 */
	@Insert("insert into t_giftorders (orderno, goodsid, userid, source, money, counts, remark, paystatus, paytype, createtime, entkbn) values"
			+ "(#{orderno}, #{goodsid}, #{userid}, 1, #{money}, #{counts}, #{remark}, 0, #{paytype}, now(), 0)")
	void createGoodsOrder(GoodsOrder order) throws SQLExecutorException;

	/**
	 * @Title: findGoodsOrderDataById
	 * @Description: 根据订单id查询订单数据
	 * @param orderid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午5:14:41
	 */
	@Select("select * from t_giftorders where orderno = #{orderid}")
	GoodsOrder findGoodsOrderDataById(String orderid) throws SQLExecutorException;

	/**
	 * @Title: updateGoodsOrder
	 * @Description: 更新礼物订单
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午5:20:47
	 */
	@Update("update t_giftorders set paystatus=#{paystatus}, paytime = #{paytime} where orderno = #{orderno}")
	void updateGoodsOrder(GoodsOrder data) throws SQLExecutorException;

	/**
	 * @Title: getGoodsProfit
	 * @Description: 获取礼物分润比例
	 * @param orderid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 下午5:29:08
	 */
	@Select("select agentprofit, salerprofit from t_giftorders go, t_goods g where "
			+ "go.goodsid=g.goodsid and go.orderno = #{orderid}")
	Goods getGoodsProfit(String orderid) throws SQLExecutorException;

	/**
	 * @Title: getUserOpenid
	 * @Description:
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月17日 上午11:54:29
	 */
	@Select("select openid from t_userinfo where userid=#{userid}")
	String getUserOpenid(String userid) throws SQLExecutorException;

}
