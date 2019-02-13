package dianfan.pay.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;

import dianfan.entities.wx.UnifiedorderBean;
import dianfan.entities.wx.WxOrderQueryBean;
import dianfan.entities.wx.WxTradeType;

/**
 * @ClassName WxPayCore
 * @Description 微信支付统一下单
 * @author cjy
 * @date 2018年5月25日 上午11:11:54
 */
public class WxPayCore {
	/*
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 微信配置参数
	 */
	private final WXPayConfig conf;
	
	/**
	 * 沙盒测试
	 */
	private final boolean useSandbox;
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 微信支付核心类</p>
	 * @param conf
	 */
	public WxPayCore(WXPayConfig conf){
		this(conf, false);
	}
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 微信支付核心类, 沙盒测试</p>
	 * @param conf
	 */
	public WxPayCore(WXPayConfig conf, boolean useSandbox){
		//assertNotNull("微信支付配置参数不能为null", conf);
		log.debug("微信支付配置参数：" + conf);
		this.conf = conf;
		this.useSandbox = useSandbox;
	}
	
	public Map<String, String> unifiedOrder(UnifiedorderBean order, String payType) throws Exception {
		return unifiedOrder(order, payType, SignType.MD5);
	}
	
	/**
	 * @Title: 统一下单
	 * @Description: 场景：公共号支付、扫码支付、APP支付
	 * @param order 微信支付业务实体数据
	 * @param signType 签名类型，默认为MD5，支持HMAC-SHA256和MD5
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年5月29日 下午4:42:55
	 */
	public Map<String, String> unifiedOrder(UnifiedorderBean order, String payType, SignType signType) throws Exception {
		//assertNotNull("微信支付业务参数不能为null", order);
		log.debug("微信支付业务参数：" + order);
		WXPay wxpay; 
		if (SignType.MD5.equals(signType)) {
			wxpay = new WXPay(conf, SignType.MD5, this.useSandbox);
        } else if (SignType.HMACSHA256.equals(signType)) {
        	wxpay = new WXPay(conf, SignType.HMACSHA256, this.useSandbox);
        }else {
        	log.error("未知的签名类型，默认为MD5，支持HMAC-SHA256和MD5");
        	throw new RuntimeException("未知的签名类型，默认为MD5，支持HMAC-SHA256和MD5");
        }
		//业务参数转换成map
		BeanMap map = new BeanMap(order);
		Map<String, String> reqData = new HashMap<>();
		for(Entry<Object, Object> m : map.entrySet()) {
			if("class".equals(String.valueOf(m.getKey())) || m.getValue() == null) {
				continue;
			}
			reqData.put(String.valueOf(m.getKey()), String.valueOf(m.getValue()));
		}
		log.debug("微信支付处理后的业务参数：" + reqData);
		//发送支付请求
		Map<String, String> wpc = wxpay.unifiedOrder(reqData);
		log.error("微信支付请求：" + wpc);
		
		//判断是否调起成功
		if(StringUtils.equals("SUCCESS", StringUtils.upperCase(wpc.get("return_code")))  && 
				StringUtils.equals("SUCCESS", StringUtils.upperCase(wpc.get("result_code")))) {
			Map<String, String> signMap = new HashMap<>();
			if(StringUtils.equals(payType, WxTradeType.JSAPI)) {
				signMap.put("appId", conf.getAppID());
				signMap.put("nonceStr", WXPayUtil.generateNonceStr());
				signMap.put("timeStamp", System.currentTimeMillis() / 1000 + ""); //
				signMap.put("package", "prepay_id=" + wpc.get("prepay_id"));
				signMap.put("signType", (SignType.MD5.equals(signType)) ? WXPayConstants.MD5 : WXPayConstants.HMACSHA256);
				String sign = WXPayUtil.generateSignature(signMap, conf.getKey());
				signMap.put("paySign", sign);
			}else if(StringUtils.equals(payType, WxTradeType.APP)){
				signMap.put("appid", conf.getAppID());
				signMap.put("partnerid", conf.getMchID());
				signMap.put("prepayid", wpc.get("prepay_id"));
				signMap.put("package", "Sign=WXPay");
				signMap.put("noncestr", WXPayUtil.generateNonceStr());
				signMap.put("timestamp", System.currentTimeMillis() / 1000 + ""); //
				String sign = WXPayUtil.generateSignature(signMap, conf.getKey());
				signMap.put("sign", sign);
			}else if(StringUtils.equals(payType, WxTradeType.WEB)){
				signMap = wpc;
			}
			
			return signMap;
		}else {
			throw new Exception();
		}
	}
	
	/**
	 * @Title: orderQuery
	 * @Description: 微信订单查询
	 * @param orderno 微信的订单号 或 商户系统内部订单号（二选一，微信的订单号，建议优先使用）
	 * @param signType 签名类型，默认为MD5，支持HMAC-SHA256和MD5
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年5月29日 下午5:15:22
	 */
	public Map<String, String> orderQuery(WxOrderQueryBean orderno) throws Exception {
		return orderQuery(orderno, SignType.MD5);
	}
	public Map<String, String> orderQuery(WxOrderQueryBean orderno, SignType signType) throws Exception {
		//assertNotNull("微信订单查询订单号不能为空", orderno);
		log.debug("微信订单查询订单号：" + orderno);
		WXPay wxpay; 
		if (SignType.MD5.equals(signType)) {
			wxpay = new WXPay(conf, SignType.MD5, this.useSandbox);
        } else if (SignType.HMACSHA256.equals(signType)) {
        	wxpay = new WXPay(conf, SignType.HMACSHA256, this.useSandbox);
        }else {
        	log.error("未知的签名类型，默认为MD5，支持HMAC-SHA256和MD5");
        	throw new RuntimeException("未知的签名类型，默认为MD5，支持HMAC-SHA256和MD5");
        }
		//业务参数转换成map
		BeanMap map = new BeanMap(orderno);
		Map<String, String> reqData = new HashMap<>();
		for(Entry<Object, Object> m : map.entrySet()) {
			if("class".equals(String.valueOf(m.getKey())) || m.getValue() == null) {
				continue;
			}
			reqData.put(String.valueOf(m.getKey()), String.valueOf(m.getValue()));
		}
		//发送查询请求
		return wxpay.orderQuery(reqData);
	}
}
