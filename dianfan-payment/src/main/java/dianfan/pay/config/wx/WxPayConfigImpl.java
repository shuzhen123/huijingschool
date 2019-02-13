package dianfan.pay.config.wx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import com.github.wxpay.sdk.WXPayConfig;

/**
 * @ClassName WxBaseConfig
 * @Description 微信支付参数配置
 * @author cjy
 * @date 2018年5月25日 上午11:13:50
 */
public class WxPayConfigImpl implements WXPayConfig {
	
	private String appID; //公众账号ID(微信支付分配的公众账号ID（企业号corpid即为此appId）)
	private String mchID; //微信支付分配的商户号
	private String key; //API 密钥
	
	private String cert_file_url; //商户证书地址
	private int httpConnectTimeoutMs; //HTTP(S) 连接超时时间，单位毫秒
	private int httpReadTimeoutMs; //HTTP(S) 读数据超时时间，单位毫秒
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 无需证书的配置 </p>
	 * @param appid 公众账号ID(微信支付分配的公众账号ID（企业号corpid即为此appId）)
	 * @param mch_id 微信支付分配的商户号
	 * @param key API 密钥
	 */
	public WxPayConfigImpl(String appid, String mch_id, String key) {
		this.appID = appid;
		this.mchID = mch_id;
		this.key = key;
	}
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 无需证书的配置, 附加超时时间</p>
	 * @param appid 公众账号ID(微信支付分配的公众账号ID（企业号corpid即为此appId）)
	 * @param mch_id 微信支付分配的商户号
	 * @param key API 密钥
	 * @param httpConnectTimeoutMs 连接超时时间，单位毫秒
	 * @param httpReadTimeoutMs 读数据超时时间，单位毫秒
	 */
	public WxPayConfigImpl(String appid, String mch_id, String key, int httpConnectTimeoutMs, int httpReadTimeoutMs) {
		this(appid, mch_id, key);
		this.httpConnectTimeoutMs = httpConnectTimeoutMs;
		this.httpReadTimeoutMs = httpReadTimeoutMs;
	}
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 支持带有证书的配置</p>
	 * @param appid 公众账号ID(微信支付分配的公众账号ID（企业号corpid即为此appId）)
	 * @param mch_id 微信支付分配的商户号
	 * @param key API 密钥
	 * @param cert_file_url 商户证书地址
	 */
	public WxPayConfigImpl(String appid, String mch_id, String key, String cert_file_url) {
		this(appid, mch_id, key);
		this.cert_file_url = cert_file_url;
	}
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 支持带有证书的配置, 附加超时时间</p>
	 * @param appid 公众账号ID(微信支付分配的公众账号ID（企业号corpid即为此appId）)
	 * @param mch_id 微信支付分配的商户号
	 * @param key API 密钥
	 * @param cert_file_url 商户证书地址
	 * @param httpConnectTimeoutMs 连接超时时间，单位毫秒
	 * @param httpReadTimeoutMs 读数据超时时间，单位毫秒
	 */
	public WxPayConfigImpl(String appid, String mch_id, String key, String cert_file_url, int httpConnectTimeoutMs, int httpReadTimeoutMs) {
		this(appid, mch_id, key, httpConnectTimeoutMs, httpReadTimeoutMs);
		this.cert_file_url = cert_file_url;
	}

	/**
     * 获取 App ID
     *
     * @return App ID
     */
	public String getAppID() {
		return this.appID;
	}

	/**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
	public String getMchID() {
		return this.mchID;
	}

	/**
     * 获取 API 密钥
     *
     * @return API密钥
     */
	public String getKey() {
		return this.key;
	}

	/**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
	public InputStream getCertStream() {
		if(!StringUtils.isEmpty(cert_file_url)) {
			InputStream stream = getClass().getClassLoader().getResourceAsStream(cert_file_url);
			return stream;
		}else {
			return null;
		}
	}

	/**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
	public int getHttpConnectTimeoutMs() {
		return this.httpConnectTimeoutMs;
	}

	/**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
	public int getHttpReadTimeoutMs() {
		return this.httpReadTimeoutMs;
	}

}
