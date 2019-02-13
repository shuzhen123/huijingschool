package dianfan.wxrefund;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import dianfan.wxpay.PayUtil;
public class RefundUtil {
	
	/**
	 * main函数，实现非部署情况下退款（仅限于全额退款）
	 * @param args
	 */
	public static void main(String[] args) {
		
		wechatRefund("xxxxxx",1);//第一个参数是商户订单号，第二个参数是总金额（double型，以“元”为单位）
	}
	
	/**
	 * 退款函数，该方法可以对曾经部分退款的订单进行再次退款
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 退款对应的订单的总金额（以“元”为单位）
	 * @param refund_fee1 计划退款的金额（以“元”为单位）
	 * @return
	 */
	public static void wechatRefund1(String out_trade_no,double total_fee1,double refund_fee1){
		String out_refund_no = UUID.randomUUID().toString().substring(0, 32);// 退款单号，随机生成 ，但长度应该跟文档一样（32位）(卖家信息校验不一致，请核实后再试)
		int total_fee = (int) (total_fee1*100);//订单的总金额,以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		int refund_fee = (int) (refund_fee1*100);;// 退款金额，以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		String nonce_str = "4225543268";// 随机字符串
		//微信公众平台文档：“基本配置”--》“开发者ID”
		String appid ="XXXX";
		//微信公众平台文档：“基本配置”--》“开发者ID”
		String appsecret = "XXXX";
		//商户号
		//微信公众平台文档：“微信支付”--》“商户信息”--》“商户号”，将该值赋值给partner
		String mch_id = "xxxx";
		String op_user_id = mch_id;//就是MCHID
		//微信公众平台："微信支付"--》“商户信息”--》“微信支付商户平台”（登录）--》“API安全”--》“API密钥”--“设置密钥”（设置之后的那个值就是partnerkey，32位）
		String partnerkey = "xxxx";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee+"");
		packageParams.put("refund_fee", refund_fee+"");
		packageParams.put("op_user_id", op_user_id);
	
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		// 把Map所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = PayUtil.createLinkString(packageParams); 
		// MD5运算生成签名
		String sign = PayUtil.sign(prestr, partnerkey, "utf-8").toUpperCase();
		String xml = "<xml>" + 
				"<appid>" + appid + "</appid>" + 
				"<mch_id>" + mch_id + "</mch_id>" + 
				"<nonce_str>" + nonce_str + "</nonce_str>" + 
				"<sign><![CDATA[" + sign + "]]></sign>"	+ 
				"<out_trade_no>" + out_trade_no + "</out_trade_no>"	+ 
				"<out_refund_no>" + out_refund_no + "</out_refund_no>" + 
				"<total_fee>" + total_fee + "</total_fee>" + 
				"<refund_fee>" + refund_fee + "</refund_fee>" + 
				"<op_user_id>" + op_user_id + "</op_user_id>" + 
				"</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String refundResult= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.out.println("退款产生的json字符串："+ refundResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 该方法默认全额退款，但如果该订单曾经退款一部分，那么就不可使用该方法
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 总的退款金额（以“元”为单位）
	 */
	public static void wechatRefund(String out_trade_no,double total_fee1){
		
		wechatRefund1(out_trade_no,total_fee1,total_fee1);
	}
}
