package dianfan.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.app.GoodsNotifyMapper;
import dianfan.dao.mapper.app.PayNotifyMapper;
import dianfan.entities.BranchProfit;
import dianfan.entities.Goods;
import dianfan.entities.GoodsOrder;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName GoodsOrderService
 * @Description 支付宝、微信支付响应服务
 * @author cjy
 * @date 2018年4月16日 下午3:06:43
 */
@Service
public class GoodsNotifyService {
	/**
	 * dao
	 */
	@Autowired
	private GoodsNotifyMapper goodsNotifyMapper;
	@Autowired
	private PayNotifyMapper payNotifyMapper;

	/**
	 * @Title: updateGoodsOrder
	 * @Description: 更新订单
	 * @param order
	 *            订单数据
	 * @throws SQLExecutorException
	 *             sql异常
	 * @throws:
	 * @time: 2018年4月16日 下午3:48:40
	 */
	@Transactional
	public void updateGoodsOrder(String orderid) throws SQLExecutorException {
		goodsNotifyMapper.updateGoodsOrder(orderid);

		// 获取礼物订单数据
		GoodsOrder order = goodsNotifyMapper.getGoodsOrderInfo(orderid);

		// 获取用户对应的代理商及业务员
		BranchProfit bp = payNotifyMapper.findAgentAndSalerRelation(order.getUserid());
		bp.setId(UUIDUtil.getUUID());
		bp.setOrderno(order.getOrderno());
		bp.setKind(2);
		bp.setBagentresult(1);
		bp.setBsalerresult(1);

		// 获取当前礼物的分润比例
		Goods goods = goodsNotifyMapper.getGoodsProfit(order.getGoodsid());

		BigDecimal agent = new BigDecimal(goods.getAgentprofit());
		// 获取代理商分润比率结果
		bp.setBagentmoney(order.getMoney().multiply(agent));
		BigDecimal saler = new BigDecimal(goods.getSalerprofit());
		// 获取业务员课程分润
		bp.setBsalermoney(order.getMoney().multiply(saler));
		// 插入分润数据
		payNotifyMapper.addBranchProfit(bp);
	}

}
