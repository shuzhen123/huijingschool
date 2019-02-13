package dianfan.pay.alipay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipayUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.alipay.AlipayQueryBean;
import dianfan.entities.alipay.AppPayBean;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.entities.alipay.WapPayBean;

/**
 * @ClassName AlipayCore
 * @Description 支付宝支付
 * @author cjy
 * @date 2018年5月25日 下午4:30:45
 */
public class AlipayCore {
	/*
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(getClass());
	private ObjectMapper mapper = new ObjectMapper();
	/**
	 * 支付请求client
	 */
	private AlipayClient client;
	/**
	 * 
	 */
	private String return_url;
	/**
	 * 
	 */
	private String notify_url;
	/**
	 * <p>Title: 有参构造函数</p>
	 * <p>Description: 支付宝支付核心类</p>
	 * @param base 支付宝支付请求公共参数
	 */
	public AlipayCore (PublicPayParam base) {
		this.client = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", 
				base.getApp_id(), //商户appid
				base.getApp_private_key(), //商户私钥
				AlipayConstants.FORMAT_JSON, 
				AlipayConstants.CHARSET_UTF8, 
				base.getAlipay_public_key(), //支付宝公钥
				AlipayConstants.SIGN_TYPE_RSA2); //获得初始化的AlipayClient
		this.return_url = base.getReturn_url();
		this.notify_url = base.getNotify_url();
	}
	
	/**
	 * @Title: alipayWapTrade
	 * @Description: 手机网站支付
	 * @param biz
	 * @return
	 * @throws AlipayApiException
	 * @throws JsonProcessingException
	 * @throws:
	 * @time: 2018年5月29日 上午11:20:53
	 */
	public String alipayWapTrade (WapPayBean biz) throws AlipayApiException, JsonProcessingException {
		AlipayTradeWapPayRequest req = new AlipayTradeWapPayRequest ();
		req.setReturnUrl(this.return_url);//在公共参数中设置回跳地址
		req.setNotifyUrl(this.notify_url);//在公共参数中设置通知地址
		//业务参数转换成map
		BeanMap map = new BeanMap(biz);
		AlipayHashMap biz_map = new AlipayHashMap();
		for(Entry<Object, Object> m : map.entrySet()) {
			if("class".equals(m.getKey()) || m.getValue() == null) {
				continue;
			}
			biz_map.put(String.valueOf(m.getKey()), String.valueOf(m.getValue()));
		}
		//参数去null去空字符串处理
		AlipayUtils.cleanupMap(biz_map);
		log.debug("支付宝手机网站支付业务参数去空处理：" + biz_map);
		//生成业务字符串
		String bizContent = mapper.writeValueAsString(biz_map);
		log.debug("支付宝手机网站支付业务参数字符串：" + bizContent);
		
		req.setBizContent(bizContent);//填充业务参数
		log.debug("支付宝手机网站支付业务待签名字符串：" + mapper.writeValueAsString(req));
		
		return client.pageExecute(req).getBody(); //调用SDK生成表单
	}
	
	/**
	 * @Title: alipayAppTrade
	 * @Description: app支付
	 * @param biz
	 * @return
	 * @throws AlipayApiException
	 * @throws JsonProcessingException
	 * @throws:
	 * @time: 2018年5月29日 上午11:21:08
	 * 
	 * 更新时间：2018年6月6日 上午11:07:23
	 * 更新备注：BeanMap 去null修改
	 */
	public String alipayAppTrade (AppPayBean biz) throws AlipayApiException, JsonProcessingException {
		AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest ();
		req.setNotifyUrl(this.notify_url);//在公共参数中设置通知地址
		
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setSubject(biz.getSubject());
		model.setOutTradeNo(biz.getOut_trade_no());
		model.setTotalAmount(biz.getTotal_amount());
		model.setProductCode(biz.getProduct_code());
		
		req.setBizModel(model);//填充业务参数
		
		String body = client.sdkExecute(req).getBody(); //调用SDK生成表单
		return body;
	}
	public String alipayAppTrade_old (AppPayBean biz) throws AlipayApiException, JsonProcessingException {
		AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest ();
		req.setNotifyUrl(this.notify_url);//在公共参数中设置通知地址
		
		//业务参数转换成map
		BeanMap map = new BeanMap(biz);
		AlipayHashMap biz_map = new AlipayHashMap();
		for(Entry<Object, Object> m : map.entrySet()) {
			if("class".equals(m.getKey())  || m.getValue() == null) {
				continue;
			}
			biz_map.put(String.valueOf(m.getKey()), String.valueOf(m.getValue()));
		}
		//参数去null去空字符串处理
		AlipayUtils.cleanupMap(biz_map);
		log.debug("支付宝app支付业务参数去空处理：" + biz_map);
		//生成业务字符串
		String bizContent = mapper.writeValueAsString(biz_map);
		log.debug("支付宝app支付业务参数字符串：" + bizContent);
		
		req.setBizContent(bizContent);//填充业务参数
		log.debug("支付宝app支付业务待签名字符串：" + mapper.writeValueAsString(req));
		
		String body = client.sdkExecute(req).getBody(); //调用SDK生成表单
		return body;
	}
	
	/**
	 * @Title: tradeQuery
	 * @Description: 统一收单线下交易查询
	 * @param queryData 订单号数据
	 * @return
	 * @throws AlipayApiException
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年5月30日 上午9:37:35
	 */
	public String tradeQuery(AlipayQueryBean queryData) throws AlipayApiException, IOException {
		AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();
		Map<String, String> query_data = new HashMap<>();
		//业务参数转换成map
		BeanMap map = new BeanMap(queryData);
		for(Entry<Object, Object> m : map.entrySet()) {
			if("class".equals(m.getKey()) || m.getValue() == null) {
				continue;
			}
			query_data.put(String.valueOf(m.getKey()), String.valueOf(m.getValue()));
		}
		String bizContent = mapper.writeValueAsString(query_data);
		req.setBizContent(bizContent);
				
		String ret = client.execute(req).getBody();
		return ret;
	}
}
