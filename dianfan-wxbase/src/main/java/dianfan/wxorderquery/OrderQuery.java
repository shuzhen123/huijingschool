package dianfan.wxorderquery;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import dianfan.wxpay.PayUtil;
import dianfan.wxpay.UUIDHexGenerator;
import dianfan.wxrefund.RequestHandler;

public class OrderQuery {

	/**
	 * 订单查询
	 * @param out_trade_no 商户订单号
	 * @param appid 小程序id
	 * @param mch_id 商户Id
	 * @param pkey 商户平台里自己设的密钥
	 */
	public Map orderQueryV(String out_trade_no, String appid, String mch_id, String pkey) {
 
		String url = "https://api.mch.weixin.qq.com/pay/orderquery";
		Map map = new HashMap();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", UUIDHexGenerator.generate());
		packageParams.put("out_trade_no", out_trade_no);
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, null, pkey);
		// 把Map所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = PayUtil.createLinkString(packageParams); 
		// MD5运算生成签名
		String sign = PayUtil.sign(prestr, pkey, "utf-8").toUpperCase();
		String xmlParam = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ packageParams.get("nonce_str") + "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>" + "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "</xml>";
		return map = GetWxOrderno.doXML(url, xmlParam);
	}
}
