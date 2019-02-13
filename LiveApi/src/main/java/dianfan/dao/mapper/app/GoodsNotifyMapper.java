package dianfan.dao.mapper.app;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Goods;
import dianfan.entities.GoodsOrder;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName GoodsNotifyMapper
 * @Description 支付宝、微信支付响应dao
 * @author cjy
 * @date 2018年4月16日 下午3:10:34
 */
@Repository
public interface GoodsNotifyMapper {

	/**
	 * @Title: findCourseOrderDataById
	 * @Description: 根据交易号查询订单数据
	 * @param trade_no
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月11日 上午9:38:45
	 */
	@Select("select money from t_giftorders where orderno=#{orderno} and paystatus=0")
	BigDecimal findGoodsOrderDataById(String orderno) throws SQLExecutorException;

	/**
	 * @Title: updateGoodsOrder
	 * @Description: 更新订单
	 * @param order
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月16日 下午3:38:40
	 */
	@Update("update t_giftorders set paystatus=1, paytime=now() where orderno=#{orderid}")
	void updateGoodsOrder(String orderid) throws SQLExecutorException;

	/**
	 * @Title: getGoodsOrderInfo
	 * @Description: 根据交易号查询订单数据
	 * @param orderid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月23日 上午10:07:11
	 */
	@Select("select * from t_giftorders where orderno=#{orderno}")
	GoodsOrder getGoodsOrderInfo(String orderid) throws SQLExecutorException;

	/**
	 * @Title: getGoodsProfit
	 * @Description: 根据礼物id获取分润比例
	 * @param goodsid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月23日 上午10:13:13
	 */
	@Select("select agentprofit, salerprofit from t_goods where goodsid=#{goodsid}")
	Goods getGoodsProfit(String goodsid) throws SQLExecutorException;

}
