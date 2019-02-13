package dainfan.hexun.core;

import java.util.Map;

import org.apache.log4j.Logger;

import dainfan.hexun.entity.HexunBatchTradeData;
import dainfan.hexun.entity.HexunInstantTradeData;
import dainfan.hexun.utils.HexunProperty;
import dainfan.hexun.utils.HexunSignature;
import dainfan.hexun.utils.MD5Utils;


public class HexunPay {
	public static Logger log = Logger.getLogger(HexunPay.class);
	/**
	 * @Title: getPayData
	 * @Description: 即时到账交易(新支付)
	 * @param data
	 * @param config
	 * @return
	 * @throws:
	 * @time: 2017年12月29日 上午10:41:42
	 */
	public static Map<String, String> getPayData(HexunInstantTradeData instantTradeData) {
		HexunPayModels hpm = new HexunPayModels();
		//获取即时到账交易数据
		Map<String, String> pay_data = hpm.instantTradeModel(instantTradeData);
		pay_data.put("utm", instantTradeData.getUtm());
		//获取待签字符串
		String signContent = HexunSignature.createSignContent(pay_data);
		log.error("获取待签字符串: "+ signContent);
		//签名
		String sign = MD5Utils.getMD5(signContent + HexunProperty.getValue("hexun.sign.key"));
		//组合支付数据
		pay_data.put("sign", sign);
		pay_data.put("sign_type", HexunProperty.getValue("hexun.sign.type"));
		return pay_data;
	}
	
	/**
	 * @Title: getPayData
	 * @Description: 继续支付、批量支付
	 * @param batchTradeData
	 * @param config
	 * @return
	 * @throws:
	 * @time: 2017年12月29日 下午12:23:13
	 */
	public static Map<String, String> getPayData(HexunBatchTradeData batchTradeData) {
		HexunPayModels hpm = new HexunPayModels();
		//获取继续支付的数据
		Map<String, String> batchPay = hpm.batchPayModel(batchTradeData);
		//获取待签字符串
		String signContent = HexunSignature.createSignContent(batchPay);
		//签名
		String sign = MD5Utils.getMD5(signContent + HexunProperty.getValue("hexun.sign.key"));
		//组合支付数据
		batchPay.put("sign", sign);
		batchPay.put("sign_type", HexunProperty.getValue("hexun.sign.type"));
		return batchPay;
	}
	
	
}
