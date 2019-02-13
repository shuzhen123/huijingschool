package dainfan.hexun.entity.notify;
/**
 * @ClassName BaseNotifyData
 * @Description 支付服务异步通知基本参数
 * @author cjy
 * @date 2017年12月29日 下午1:41:46
 */
public class BaseNotifyData {

	private String notify_id; //通知 ID
	private String notify_type; //通知类型
	private String notify_time; //通知时间
	private String _input_charset; //参数字符集编码
	private String sign; //签名
	private String sign_type; //签名方式
	private String version; //版本号
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "BaseNotifyData [notify_id=" + notify_id + ", notify_type=" + notify_type + ", notify_time="
				+ notify_time + ", _input_charset=" + _input_charset + ", sign=" + sign + ", sign_type=" + sign_type
				+ ", version=" + version + "]";
	}
	
}
