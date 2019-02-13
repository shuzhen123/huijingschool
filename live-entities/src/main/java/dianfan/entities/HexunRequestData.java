package dianfan.entities;
/**
 * @ClassName HexunRequestData
 * @Description 和讯支付请求参数
 * @author cjy
 * @date 2017年12月25日 上午11:24:04
 */
public class HexunRequestData {

	private String service; //接口名称(不可空)
	private String version; //接口版本，目前只有固定值1.0(不可空)
	private String partner_id; //合作者身份ID,签约合作方的钱包唯一用户号(不可空)
	private String server_id; //调用者身份ID主要用来控制权限，如果是平台方可为空(可空)
	private String _input_charset; //式，如utf-8、gbk、gb2312等(不可空)
	private String sign ; //签名(不可空)
	private String sign_type; //签名方式只支持RSA、MD5(不可空)
	private String return_url; //页面跳转同步返回页面路径(可空)
	private String memo; //备注(可空)
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getServer_id() {
		return server_id;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
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
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "HexunRequestData [service=" + service + ", version=" + version + ", partner_id=" + partner_id
				+ ", server_id=" + server_id + ", _input_charset=" + _input_charset + ", sign=" + sign + ", sign_type="
				+ sign_type + ", return_url=" + return_url + ", memo=" + memo + "]";
	}
	
	
}
