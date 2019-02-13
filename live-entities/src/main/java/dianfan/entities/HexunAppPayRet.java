package dianfan.entities;

public class HexunAppPayRet {
	private String app_id; //
	private String biz_content; //
	private String charset; //
	private String method; //
	private String notify_url; //
	private String sign; //
	private String sign_type; //
	private String timestamp; //
	private String version; //
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "HexunAppPayRet [app_id=" + app_id + ", biz_content=" + biz_content + ", charset=" + charset
				+ ", method=" + method + ", notify_url=" + notify_url + ", sign=" + sign + ", sign_type=" + sign_type
				+ ", timestamp=" + timestamp + ", version=" + version + "]";
	}
}
