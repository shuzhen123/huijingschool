package dianfan.service.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WXPayConstants.SignType;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppGoodsMapper;
import dianfan.entities.GoodsOrder;
import dianfan.entities.ResultBean;
import dianfan.entities.alipay.AppPayBean;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.entities.alipay.WapPayBean;
import dianfan.entities.wx.UnifiedorderBean;
import dianfan.entities.wx.WxTradeType;
import dianfan.exception.PayException;
import dianfan.exception.SQLExecutorException;
import dianfan.nimsdk.WXPayConfigImpl;
import dianfan.pay.alipay.AlipayCore;
import dianfan.pay.config.wx.WxPayConfigImpl;
import dianfan.pay.wx.WxPayCore;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AppGoodsService
 * @Description 礼物相关服务
 * @author cjy
 * @date 2018年3月2日 下午12:58:45
 */
@Service
@Transactional
public class AppGoodsService {
	public static Logger log = Logger.getLogger(AppGoodsService.class);
	/**
	 * 注入：AppGoodsMapper
	 */
	@Autowired
	private AppGoodsMapper appGoodsMapper;

	/**
	 * @Title: createGoodsOrder
	 * @Description: 创建礼物订单
	 * @param order
	 *            礼物订单数据
	 * @return GoodsOrder 礼物订单数据
	 * @throws SQLExecutorException
	 *             sql异常
	 * @throws:
	 * @time: 2018年4月17日 上午9:29:39
	 */
	@Transactional
	public GoodsOrder createGoodsOrder(GoodsOrder order) throws SQLExecutorException {
		// 获取礼物价格
		BigDecimal price = appGoodsMapper.getGoodsPrice(order.getGoodsid());
		if (price == null) {
			// 礼物不存在
			return null;
		}
		// 订单总金额
		order.setMoney(price.multiply(new BigDecimal(order.getCounts())));
		// 生成礼物未支付订单
		appGoodsMapper.createGoodsOrder(order);
		return order;
	}

	/**
	 * @Title: payGoodsOrderByWx
	 * @Description: 微信支付(公众号/app)
	 * @param order
	 *            礼物订单数据
	 * @param type
	 *            JSAPI 公众号支付 ， APP APP支付
	 * @param ip
	 *            终端ip地址
	 * @return ResultBean ResultBean
	 * @throws SQLExecutorException
	 *             sql异常
	 * @throws PayException
	 *             支付数据异常
	 * @throws Exception
	 *             支付数据异常
	 * @throws:
	 * @time: 2018年4月16日 下午12:50:50
	 */
	@Transactional
	public ResultBean payGoodsOrderByWx(GoodsOrder order, String type, String ip) throws SQLExecutorException, PayException, Exception {
		// 创建礼物订单
		order = createGoodsOrder(order);
		if (order == null) {
			// 订单创建失败
			return new ResultBean("024", ResultMsg.C_024);
		}

		UnifiedorderBean biz = new UnifiedorderBean();
		biz.setBody(new String(PropertyUtil.getProperty("goods_body").getBytes("ISO-8859-1"), "utf-8"));
		biz.setOut_trade_no(order.getOrderno());
		biz.setTotal_fee(order.getMoney().multiply(new BigDecimal(100)).intValue());
		biz.setSpbill_create_ip(ip);
		biz.setNotify_url(PropertyUtil.getProperty("goods_mp_notify_url"));
		biz.setTrade_type(type);
		
		String appid, macid, key;
		if(StringUtils.equals(type, WxTradeType.JSAPI)) {
			//微信公众号支付
			//传入openid
			String openid = appGoodsMapper.getUserOpenid(order.getUserid());
			biz.setOpenid(openid);
			appid = PropertyUtil.getProperty("appid");
			macid = PropertyUtil.getProperty("goods_mp_macid");
			key = PropertyUtil.getProperty("goods_mp_key");
		}else {
			//微信app支付
			appid = PropertyUtil.getProperty("goods_app_appid");
			macid = PropertyUtil.getProperty("goods_macid");
			key = PropertyUtil.getProperty("goods_key");
		}
		WXPayConfig conf = new WxPayConfigImpl(appid, macid, key);
		// 微信支付公共请求数据
		Map<String, String> wpc = new WxPayCore(conf).unifiedOrder(biz, type);
		
		return new ResultBean(wpc);
	}
	
	public ResultBean payGoodsOrderByWx_old(GoodsOrder order, String type, String ip) throws SQLExecutorException, PayException, Exception {
		// 创建礼物订单
		order = createGoodsOrder(order);
		if (order == null) {
			// 订单创建失败
			return new ResultBean("024", ResultMsg.C_024);
		}
		
		// 2、组装支付请求数据
		WXPayConfigImpl config = new WXPayConfigImpl();
		WXPay wxpay = new WXPay(config);
		
		Map<String, String> data = new HashMap<String, String>();
		// 商品简单描述
		data.put("body", new String(PropertyUtil.getProperty("goods_body").getBytes("ISO-8859-1"), "utf-8"));
		// 商户系统内部订单号
		data.put("out_trade_no", order.getOrderno());
		// 订单总金额，单位为分
		data.put("total_fee", order.getMoney().multiply(new BigDecimal(100)).intValue() + "");
		// APP和网页支付提交用户端ip
		data.put("spbill_create_ip", ip);
		// 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
		data.put("notify_url", PropertyUtil.getProperty("goods_mp_notify_url"));
		// JSAPI 公众号支付 ， APP APP支付
		data.put("trade_type", type);
		
		if ("JSAPI".equals(type)) {
			// 数据库获取用户openid
			String openid = appGoodsMapper.getUserOpenid(order.getUserid());
			data.put("openid", openid);
		}
		// 支付请求
		Map<String, String> resp = wxpay.unifiedOrder(data);
		
		// return_code 检测
		if (resp == null || "FAIL".equals(resp.get("return_code").toUpperCase())) {
			// 支付异常
			throw new PayException(resp.get("return_msg"));
		}
		// result_code 检测
		if ("FAIL".equals(resp.get("result_code").toUpperCase())) {
			// 支付异常
			throw new PayException(resp.get("err_code") + ":" + resp.get("err_code_des"));
		}
		
		Map<String, String> data1 = new HashMap<>();
		data1.put("appId", PropertyUtil.getProperty("appid"));
		data1.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		data1.put("nonceStr", UUIDUtil.getUUID());
		data1.put("package", "prepay_id=" + resp.get("prepay_id"));
		data1.put("signType", "MD5");
		String sign = WXPayUtil.generateSignature(data1, PropertyUtil.getProperty("goods_key"));
		data1.put("paySign", sign);
		
		return new ResultBean(data1);
	}

	/**
	 * @Title: payGoodsOrderByAlipay
	 * @Description: 支付宝支付（app/wap）
	 * @param order
	 * @param dev
	 * @return
	 * @throws SQLExecutorException
	 * @throws AlipayApiException
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 * @throws:
	 * @time: 2018年5月30日 下午4:03:44
	 */
	public ResultBean payGoodsOrderByAlipay(GoodsOrder order, String dev) throws SQLExecutorException, AlipayApiException, JsonProcessingException, UnsupportedEncodingException {
		// 创建礼物订单
		order = createGoodsOrder(order);
		if (order == null) {
			// 订单创建失败
			return new ResultBean("024", ResultMsg.C_024);
		}
		
		PublicPayParam base = new PublicPayParam();
		base.setApp_id(PropertyUtil.getProperty("goods_alipay_appid"));
		base.setApp_private_key(PropertyUtil.getProperty("goods_alipay_private_key"));
		base.setAlipay_public_key(PropertyUtil.getProperty("goods_alipay_public_key"));
		base.setCharset("utf-8");
		base.setSign_type("RSA2");
		base.setNotify_url(PropertyUtil.getProperty("goods_alipay_notify_url"));
		
		String ret;
		if("app".equals(dev)) {
			//App支付
			AppPayBean biz = new AppPayBean();
			biz.setSubject(new String(PropertyUtil.getProperty("goods_body").getBytes("ISO-8859-1"), "utf-8"));
			biz.setOut_trade_no(order.getOrderno());
			biz.setTotal_amount(order.getMoney().toString());
			ret = new AlipayCore(base).alipayAppTrade(biz);
		}else {
			//wap支付
			WapPayBean biz = new WapPayBean();
			biz.setSubject(PropertyUtil.getProperty("goods_body"));
			biz.setOut_trade_no(order.getOrderno());
			biz.setTotal_amount(order.getMoney().toString());
			ret = new AlipayCore(base).alipayWapTrade(biz);
		}
		return new ResultBean(ret);
	}
}
