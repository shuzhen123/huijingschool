package dianfan.wxcloseorder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dianfan.wxpay.MessageUtil;
import dianfan.wxpay.PayUtil;
import dianfan.wxpay.PaymentPo;
import dianfan.wxpay.UUIDHexGenerator;

public class CloseWeiXinOrder {

	/**
	 * 订单关闭
	 * @param appid 小程序id
	 * @param mch_id 商户Id
	 * @param out_trade_no 商户订单号
	 * @param partnerkey 商户平台里自己设的密钥
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException
	 */
	public boolean closeWeiXinOrder(String appid,String mch_id,String out_trade_no,String partnerkey) throws UnsupportedEncodingException, DocumentException {
		 

		PaymentPo paymentPo = new PaymentPo();
		paymentPo.setAppid(appid);//小程序ID
		paymentPo.setMch_id(mch_id);// 商户号
		paymentPo.setNonce_str(UUIDHexGenerator.generate());// 随机字符串
		paymentPo.setOut_trade_no(out_trade_no);// 商户订单号

		// 把请求参数打包成Map
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", paymentPo.getNonce_str());
		packageParams.put("out_trade_no", out_trade_no);
		// 除去Map中的空值和签名参数
		Map<String, String> sPara = PayUtil.paraFilter(packageParams);
		String prestr = PayUtil.createLinkString(sPara); // 把Map所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String key = "&key=" + partnerkey; // 商户支付密钥
		// MD5运算生成签名
		String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
		paymentPo.setSign(mysign);
		// 打包要发送的xml
		String respXml = MessageUtil.messageToXML(paymentPo);
		// 打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
		respXml = respXml.replace("__", "_");
		String url = "https://api.mch.weixin.qq.com/pay/closeorder";// 关闭订单接口
		String param = respXml;
		String result = PayUtil.httpRequest(url, "POST", param);
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		InputStream in = new ByteArrayInputStream(result.getBytes("UTF-8"));
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		for (Element element : elementList) {
			map.put(element.getName(), element.getText());
		}
		// 返回信息
		String return_code = map.get("return_code");// 返回状态码
		// 请求成功
		if (return_code == "SUCCESS") {
			return true;
		}
		return false;
	}
}
