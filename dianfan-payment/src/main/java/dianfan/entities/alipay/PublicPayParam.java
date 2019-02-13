package dianfan.entities.alipay;

/**
 * @ClassName PublicPayParam
 * @Description 支付宝支付公共参数
 * @author cjy
 * @date 2018年5月25日 下午3:00:59
 */
public class PublicPayParam {
	private String app_id; //必填：支付宝分配给开发者的应用ID
	private String app_private_key; //必填：商户私钥
	private String alipay_public_key; //必填：支付宝公钥
	private String return_url; //自动跳转回商户页面(仅wap支付有效),HTTP/HTTPS开头字符串
	private String charset; //必填：请求使用的编码格式，如utf-8,gbk,gb2312等
	private String sign_type; //必填：商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
	private String notify_url; //支付宝服务器主动通知商户服务器里指定的页面http/https路径。
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_private_key() {
		return app_private_key;
	}
	public void setApp_private_key(String app_private_key) {
		this.app_private_key = app_private_key;
	}
	public String getAlipay_public_key() {
		return alipay_public_key;
	}
	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	@Override
	public String toString() {
		return "PublicPayParam [app_id=" + app_id + ", app_private_key=" + app_private_key + ", alipay_public_key="
				+ alipay_public_key + ", return_url=" + return_url + ", charset=" + charset + ", sign_type=" + sign_type
				+ ", notify_url=" + notify_url + "]";
	}
}
