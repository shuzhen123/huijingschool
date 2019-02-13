package dianfan.pay.alipay;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import dianfan.entities.alipay.PublicPayParam;

/**
 * @ClassName AlipayNotify
 * @Description 支付宝异步通知验签
 * @author cjy
 * @date 2018年5月26日 下午1:36:58
 */
public class AlipayNotify {
	
	private PublicPayParam pub_data;
	
	public AlipayNotify(PublicPayParam pub_data){
		this.pub_data = pub_data;
	}

	/**
	 * @Title: signature
	 * @Description: 异步通知验签
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 * @throws:
	 * @time: 2018年5月26日 下午1:42:32
	 */
	public boolean signature(Map<String, String> params) throws AlipayApiException {
		return AlipaySignature.rsaCheckV1(params, pub_data.getAlipay_public_key(), pub_data.getCharset(), pub_data.getSign_type());
	}
}
