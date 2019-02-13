package dianfan.nimsdk;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

import common.propertymanager.PropertyUtil;

/**
 * @ClassName WXPayConfigImpl
 * @Description 微信公众号支付配置
 * @author cjy
 * @date 2018年4月16日 上午11:55:26
 */
public class WXPayConfigImpl implements WXPayConfig {

	private byte[] certData;

	/*
	 * public WXPayConfigImpl() throws Exception { String certPath =
	 * "classpath:apiclient_cert.p12"; File file = new File(certPath); InputStream
	 * certStream = new FileInputStream(file); this.certData = new byte[(int)
	 * file.length()]; certStream.read(this.certData); certStream.close();
	 * 
	 * }
	 */
	public String getAppID() {
		return PropertyUtil.getProperty("appid");
	}

	public String getMchID() {
		return PropertyUtil.getProperty("goods_macid");
	}

	public String getKey() {
		return PropertyUtil.getProperty("goods_key");
	}

	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}
}