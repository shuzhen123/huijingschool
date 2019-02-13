package dainfan.hexun.core;
/**
 * @ClassName HexunPayModels
 * @Description 支付模块
 * @author cjy
 * @date 2017年12月29日 上午10:19:33
 */

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dainfan.hexun.entity.HexunBatchTradeData;
import dainfan.hexun.entity.HexunInstantTradeData;
import dainfan.hexun.utils.HexunProperty;

public class HexunPayModels {

	/**
	 * @Title: baseModel
	 * @Description: 和讯支付基本参数
	 * @param service
	 * @param orderType
	 * @return
	 * @throws:
	 * @time: 2018年3月2日 下午4:20:19
	 */
	private Map<String, String> baseModel(String service, String orderType){
		Map<String, String> base_data = new HashMap<>();
		/* ************** 基本参数 ***************/
		//接口名称(不可空)
		base_data.put("service", service);
		//接口版本(不可空)
		base_data.put("version", HexunProperty.getValue("hexun.version"));
		//合作者身份ID(不可空)
		base_data.put("partner_id", HexunProperty.getValue("hexun.partnerid"));
		//调用者身份ID(可空)
		base_data.put("server_id", HexunProperty.getValue("hexun.serverid"));
		//参数编码字符集(不可空)
		base_data.put("_input_charset", HexunProperty.getValue("hexun.charse"));
		//页面跳转同步返回页面路径(可空)
		base_data.put("return_url", HexunProperty.getValue("hexun.return.url."+orderType));
		//备注(可空)
		base_data.put("memo", HexunProperty.getValue("hexun.memo."+orderType));
		
		return base_data;
	}
	
	/**
	 * @Title: instantTradeModel
	 * @Description: 即时到账 交易 
	 * @param baseModel
	 * @param order_data
	 * @param config
	 * @return
	 * @throws:
	 * @time: 2017年12月29日 上午10:34:25
	 */
	public Map<String, String> instantTradeModel(HexunInstantTradeData order_data){
		Map<String, String> baseModel = baseModel(HexunProperty.getValue("hexun.instant.transfer.service"), order_data.getOrder_type());
		/* ************** 业务参数 ***************/
		//商户网站请求号(不可空)
		baseModel.put("request_no", order_data.getOrderid());
		//操作员 Id(可空)
		//pay_data.put("operator_id", null);
		//买家 ID(不可空)
		baseModel.put("buyer_id", HexunProperty.getValue("hexun.buyerid"));
		//买家ID类型:UID/MEMBER_ID/MOBILE(不可空)
		baseModel.put("buyer_id_type", HexunProperty.getValue("hexun.buyerid.type"));
		//买家手机号(可空)
		//pay_data.put("buyer_mobile", null);
		//用户在商户平台下单时候的 ip地址(可空)
		//pay_data.put("buyer_ip", null);
		//返回数据类型,回数据类型，默认是可以请求的form数据，需要返回 json请填写该参数(必填)
		if("app".equals(order_data.getMch())) {
			baseModel.put("result_type", "json");
		}
		
		/* ************** 支付相关参数 ***************/
		//支付方式(必填)（alipay 支付宝，wxpay 微信）
//		String key = "hexun.pay.method."+order_data.getPay_type();
//		String pay_method  = HexunProperty.getValue(key);
		String pay_method  = HexunProperty.getValue("hexun.pay.method."+order_data.getPay_type());
		baseModel.put("pay_method", HexunProperty.getValue("hexun.pay.method.header") + order_data.getPrice().toString().length()+":"+order_data.getPrice() + pay_method);
		// 是否转收银台标识(必填)
		baseModel.put("go_cashier", HexunProperty.getValue("hexun.go.cashier"));
		// 是否是 WEB 访问(必填)
		baseModel.put("is_web_access", HexunProperty.getValue("hexun.web.access"));
		
		//交易字符串
		baseModel.put("trade_list", tradeStrBuiled(order_data, baseModel));
				
		return baseModel;
	}
	
	/**
	 * @Title: batchPay
	 * @Description:   继续支付/ 批量支付操作
	 * @param batch_data
	 * @param config
	 * @return
	 * @throws:
	 * @time: 2017年12月29日 上午11:57:37
	 */
	public Map<String, String> batchPayModel(HexunBatchTradeData batch_data){
		Map<String, String> batch_map_data = baseModel(HexunProperty.getValue("hexun.create.pay.service"), "vip");
		//商户网站请求号
		batch_map_data.put("request_no", UUID.randomUUID().toString().replace("-", ""));
		//商户网站唯一订单号集合,多订单使用"^"连接
		batch_map_data.put("outer_trade_no_list", batch_data.getOuter_trade_no());
		
		if(batch_data.getOperator_id() != null) {
			//操作员 Id
			batch_map_data.put("operator_id", batch_data.getOperator_id());
		}
		
		//支付方式(必填)（alipay 支付宝，wxpay 微信）
		String pay_method = HexunProperty.getValue("hexun.pay.method."+batch_data.getPay_method());
		batch_map_data.put("pay_method", HexunProperty.getValue("hexun.pay.method.header") + batch_data.getPrice().toString().length()+":"+batch_data.getPrice() + pay_method);
		
		batch_map_data.put("is_web_access", HexunProperty.getValue("hexun.web.access"));
		
		return batch_map_data;
	}
	
	
	/**
	 * @Title: TradeStrBuiled
	 * @Description: 构造交易字符串
	 * @param data
	 * @param hexunCnf
	 * @return
	 * @throws:
	 * @time: 2017年12月27日 下午3:50:54
	 */
	private static String tradeStrBuiled(HexunInstantTradeData data, Map<String, String> pay_data) {
		//交易字符串
		StringBuffer str = new StringBuffer();
		str.append(data.getOrderid().length()+":"+data.getOrderid()).append("~");//商户网站唯一订单号
		str.append(data.getProduct_name().length()+":"+data.getProduct_name()).append("~");//商品名称
		str.append(data.getUnit_price().toString().length()+":"+data.getUnit_price()).append("~");//商品单价
		str.append(data.getNumber().toString().length()+":"+data.getNumber()).append("~");//商品数量
		str.append(data.getPrice().toString().length()+":"+data.getPrice()).append("~");//交易金额
		str.append("0:~");//交易金额分润账号集
		str.append(HexunProperty.getValue("hexun.sellerid").length()+":"+HexunProperty.getValue("hexun.sellerid")).append("~");//卖家标示 ID(不可空)
		str.append("9:MEMBER_ID~");//卖家标示 ID 类型
		str.append("0:~");//卖家手机号
		str.append("1:0~");//使用订金金额
		str.append("0:~");//订金下订的商户网站唯一订单号
		str.append(HexunProperty.getValue("hexun.describe."+data.getOrder_type()).length()+":"+HexunProperty.getValue("hexun.describe."+data.getOrder_type())).append("~");//商品描述
		str.append("0:~");//商品展示 URL
		str.append("0:~");//商户订单提交时间
		str.append(HexunProperty.getValue("hexun.notify.url."+data.getOrder_type()).length()+":"+HexunProperty.getValue("hexun.notify.url."+data.getOrder_type())).append("~");//务器异步通知页面路径
		str.append("0:~");//支付过期时间
		str.append("0:");//店铺名称
		return str.toString();
	}
}
