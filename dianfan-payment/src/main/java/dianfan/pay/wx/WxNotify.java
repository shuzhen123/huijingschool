package dianfan.pay.wx;

import java.util.Map;

import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WXPayConstants.SignType;

/**
 * @ClassName WxNotify
 * @Description 微信异步通知处理
 * @author cjy
 * @date 2018年5月29日 下午5:34:11
 */
public class WxNotify {

	/**
	 * @Title: xmlToMap
	 * @Description: xml转map
	 * @param respStr xml格式字符串
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年5月29日 下午6:05:53
	 */
	public Map<String, String> xmlToMap (String respStr) throws Exception {
		return WXPayUtil.xmlToMap(respStr);
	}
	
	/**
	 * @Title: checkSign
	 * @Description: 微信支付验签（签名类型默认MD5）
	 * @param respStr 微信响应xml格式的字符串
	 * @param key API 密钥
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年5月29日 下午6:06:20
	 */
	public boolean checkSign (String respStr, String key) throws Exception {
		return WXPayUtil.isSignatureValid(respStr, key);
	}
	
	/**
	 * @Title: checkSign
	 * @Description: 微信支付验签
	 * @param respStr 微信响应xml格式的字符串
	 * @param key API 密钥
	 * @param signType 指定签名类型
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年5月29日 下午6:07:30
	 */
	public boolean checkSign (String respStr, String key, SignType signType) throws Exception {
		return WXPayUtil.isSignatureValid(WXPayUtil.xmlToMap(respStr), key, signType);		
	}
}
