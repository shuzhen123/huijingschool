package dianfan.wxfefund.test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import dianfan.wxorderquery.GetWxOrderno;
import dianfan.wxpay.PayUtil;
import dianfan.wxpay.UUIDHexGenerator;
import dianfan.wxrefund.ClientCustomSSL;
import dianfan.wxrefund.RequestHandler;

public class Refund {
	/**
	 * 微信退款
	 * 
	 * @param request
	 * @return
	 */
	public Map wxRefund(String appid, String mch_id, String out_trade_no, String partnerkey) {

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", UUIDHexGenerator.generate());
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_trade_no);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, null, partnerkey);

		// 把Map所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = PayUtil.createLinkString(packageParams); 
		// MD5运算生成签名
		String sign = PayUtil.sign(prestr, partnerkey, "utf-8").toUpperCase();
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ packageParams.get("nonce_str") + "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<out_refund_no>" + out_trade_no
				+ "</out_refund_no>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		Map<String, String> responseMap = new HashMap<>();
		try {
			String strxml = ClientCustomSSL.doRefund(createOrderURL, xml);
			responseMap = GetWxOrderno.doXMLParse(strxml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String returnCode = responseMap.get("return_code");
		// 通信正常
		// if (returnCode.equals("SUCCESS")) {
		// String resultCode = responseMap.get("result_code");
		// detail = new RefundDetail();
		// // 微信生成的退款ID
		// String tradeNo = responseMap.get("out_refund_no");
		//
		// detail.setTradeNo(tradeNo);
		// detail.setRefundId(IdWorker.unique());
		// detail.setType(PayType.wxPay.getStatusValue());
		// detail.setUserId(request.getUserId());
		// detail.setSubject(responseMap.get("err_code_des"));
		// detail.setorderOutId(request.getOutTradeNo());
		// detail.setRefundFee(request.getRefundFee());
		// detail.setTotalFee(request.getTotalFee());
		// detail.setOriginTradeNo(tradeNo);
		// // 退款成功
		// if (resultCode.equals(ReturnStatus.success)) {
		// detail.setStatus(PayStatus.paySuccess.getStatusValue());
		// payMapper.saveRefund(detail);
		// return ReturnStatus.success.getStatusValue();
		//
		// } else {
		// detail.setStatus(PayStatus.payFail.getStatusValue());
		// refundDetailMapper.insertRefund(detail);
		// return ReturnStatus.fail.getStatusValue();
		//
		// }

		// }
		return responseMap;
	}

}
