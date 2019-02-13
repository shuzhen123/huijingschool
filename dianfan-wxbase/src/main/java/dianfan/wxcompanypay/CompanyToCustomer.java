package dianfan.wxcompanypay;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import common.propertymanager.PropertyUtil;
import dianfan.wxorderquery.GetWxOrderno;
import dianfan.wxpay.MessageUtil;
import dianfan.wxpay.PayUtil;
import dianfan.wxpay.UUIDHexGenerator;
import dianfan.wxpay.WxPayUtil;
import dianfan.wxrefund.ClientCustomSSL;
import dianfan.wxrefund.RequestHandler;

public class CompanyToCustomer {
	/**
	 * 商户付款给用户
	 * 
	 * @param request
	 * @return
	 */
	public Map compayPayMoney(String appid, String mch_id, String out_trade_no, String partnerkey, String openid,
			String name, String desc, String ip, int money) {

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_appid", appid);
		packageParams.put("mchid", mch_id);
		packageParams.put("nonce_str", UUIDHexGenerator.generate().toUpperCase());
		packageParams.put("partner_trade_no", out_trade_no);
		packageParams.put("openid", openid);
		packageParams.put("check_name","NO_CHECK");
		packageParams.put("re_user_name",name);
		packageParams.put("amount",String.valueOf(money));
		packageParams.put("desc",desc);
		packageParams.put("spbill_create_ip",ip);
		
		// 除去Map中的空值和签名参数
		Map<String, String> sPara = PayUtil.paraFilter(packageParams);
		// 把Map所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = PayUtil.createLinkString(sPara); 
		String key = "&key=" + partnerkey;// 商户支付密钥
		// MD5运算生成签名
		String sign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
		String xml = "<xml>" + "<mch_appid>" + appid + "</mch_appid>" + "<mchid>" + mch_id + "</mchid>" + "<nonce_str>"
				+ packageParams.get("nonce_str") + "</nonce_str>" + "<partner_trade_no>" + out_trade_no
				+ "</partner_trade_no>" + "<openid>" + openid + "</openid>" + "<check_name>NO_CHECK</check_name>"
				+ "<re_user_name>" + name + "</re_user_name>" + "<amount>" + money + "</amount>" + "<desc>" + desc
				+ "</desc>" + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<sign>" + sign
				+ "</sign>" + "</xml>";
		xml = xml.replace("__", "_");
		String createOrderURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
		Map<String, String> responseMap = new HashMap<>();
		try {
			String strxml = ClientCustomSSL.doCompanyPay(createOrderURL, xml);
			responseMap = GetWxOrderno.doXMLParse(strxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseMap;
	}
}
