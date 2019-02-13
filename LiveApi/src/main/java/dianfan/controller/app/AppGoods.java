package dianfan.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppGoodsMapper;
import dianfan.entities.Goods;
import dianfan.entities.GoodsOrder;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.entities.wx.WxTradeType;
import dianfan.exception.PayException;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.AppGoodsService;
import dianfan.util.GenRandomNumUtil;

/**
 * @ClassName AppGoods
 * @Description 打赏
 * @author cjy
 * @date 2018年3月2日 上午11:47:08
 */
@Scope("request")
@Controller
@RequestMapping(value = "/app")
public class AppGoods {
	public static Logger log = Logger.getLogger(AppGoods.class);
	/**
	 * 注入：AppGoodsService
	 */
	@Autowired
	private AppGoodsService appGoodsService;
	/**
	 * 注入：RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入：AppGoodsMapper
	 */
	@Autowired
	private AppGoodsMapper appGoodsMapper;

	/**
	 * @Title: goodsList
	 * @Description: 礼物列表
	 * @return ResultBean 结果集
	 * @time: 2018年4月16日 下午5:02:39
	 */
	@LogOp(method = "goodsList", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "礼物列表")
	@ApiOperation(value = "礼物列表", httpMethod = "GET", notes = "礼物列表", response = ResultBean.class)
	@RequestMapping(value = "goodslist", method = { RequestMethod.GET })
	@UnCheckedFilter
	public @ResponseBody ResultBean goodsList() {
		try {
			List<Goods> goods = appGoodsMapper.findGoods();
			for (Goods g : goods) {
				g.setIcon(ConstantIF.PROJECT + g.getIcon());
			}
			return new ResultBean(goods);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}

	/**
	 * @Title: payGoodsOrder
	 * @Description: 礼物订单支付
	 * @param accesstoken
	 *            accesstoken
	 * @param gid
	 *            礼物id
	 * @param count
	 *            数量
	 * @param remark
	 *            留言
	 * @param dev
	 *            支付设备（app：app支付 ， mp：公众号支付）
	 * @param paytype
	 *            支付类型（固定值：alipay 支付宝，wxpay 微信）
	 * @param code
	 *            换取openid的code
	 * @param request
	 *            request
	 * @return ResultBean 结果集
	 * @time: 2018年4月16日 下午12:45:45
	 */
	@LogOp(method = "payGoodsOrder", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "礼物订单支付")
	@ApiOperation(value = "礼物订单支付", httpMethod = "POST", notes = "礼物订单支付", response = ResultBean.class)
	@RequestMapping(value = "/paygoodsorder", method = { RequestMethod.POST })
	public @ResponseBody ResultBean payGoodsOrder(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken") String accesstoken,
			@ApiParam("礼物id") @RequestParam(value = "gid") String gid,
			@ApiParam("数量") @RequestParam(value = "count") String count,
			@ApiParam("留言") @RequestParam(value = "remark", required = false) String remark,
			@ApiParam("支付设备（app：app支付 ，  mp：公众号支付）") @RequestParam(value = "dev", defaultValue = "app") String dev,
			@ApiParam("支付类型（固定值：alipay 支付宝，wxpay 微信）") @RequestParam(value = "paytype") String paytype,
			HttpServletRequest request) {
		int counts = Integer.parseInt(count);
		if (counts < 1) {
			counts = 1;
		}

		ResultBean rb = null;
		try {
			// JSAPI 公众号支付 ， APP APP支付
			TokenModel token = redisTokenService.getToken(accesstoken);
			// 生成订单并请求支付
			// 构建订单数据
			GoodsOrder order = new GoodsOrder();
			order.setOrderno(GenRandomNumUtil.getOrderNo());
			order.setGoodsid(gid);
			order.setUserid(token.getUserId());
			order.setCounts(counts);
			order.setRemark(remark);
			if ("app".equals(dev) && ConstantIF.ALIPAY.equals(paytype)) {
				// app支付宝支付
				order.setPaytype(1);
				rb = appGoodsService.payGoodsOrderByAlipay(order, dev);
			} else if ("app".equals(dev) && ConstantIF.WXPAY.equals(paytype)) {
				// app微信支付
				order.setPaytype(1);
				rb = appGoodsService.payGoodsOrderByWx(order, WxTradeType.APP, request.getHeader("X-Real-IP"));
			} else if ("mp".equals(dev) && ConstantIF.ALIPAY.equals(paytype)) {
				// 公众号支付宝H5支付
				order.setPaytype(2);
				rb = appGoodsService.payGoodsOrderByAlipay(order, dev);
			} else if ("mp".equals(dev) && ConstantIF.WXPAY.equals(paytype)) {
				// 公众号微信支付
				order.setPaytype(2);
				rb = appGoodsService.payGoodsOrderByWx(order, WxTradeType.JSAPI, request.getHeader("X-Real-IP"));
			} else {
				// 未知的支付类型
				rb = new ResultBean("015", ResultMsg.C_015);
			}
		} catch (SQLExecutorException e) {
			rb = new ResultBean("500", ResultMsg.C_500);
		} catch (PayException e) {
			rb = new ResultBean("500", e.getMessage());
		} catch (Exception e) {
			rb = new ResultBean("500", ResultMsg.C_500);
		}
		return rb;
	}
}
