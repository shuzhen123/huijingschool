package dainfan.hexun.entity.notify;
/**
 * @ClassName InstantTradeNotifyData
 * @Description 
 * @author cjy
 * @date 2017年12月29日 下午1:42:13
 */

import java.math.BigDecimal;

public class InstantTradeNotifyData{
	private String notify_id; //通知 ID
	private String notify_type; //通知类型
	private String notify_time; //通知时间
	private String _input_charset; //参数字符集编码
	private String sign; //签名
	private String sign_type; //签名方式
	private String version; //版本号
	
	private String outer_trade_no; //商户网站唯一订单号
	private String inner_trade_no; //钱包交易号
	private String trade_status; //交易状态
	private BigDecimal trade_amount; //交易金额
	private String gmt_create; //交易创建时间
	private String gmt_payment; //交易支付时间
	private String gmt_close; //交易关闭时间
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
	public String getOuter_trade_no() {
		return outer_trade_no;
	}
	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}
	public String getInner_trade_no() {
		return inner_trade_no;
	}
	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public BigDecimal getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(BigDecimal trade_amount) {
		this.trade_amount = trade_amount;
	}
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getGmt_close() {
		return gmt_close;
	}
	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}
	@Override
	public String toString() {
		return "InstantTradeNotifyData [notify_id=" + notify_id + ", notify_type=" + notify_type + ", notify_time="
				+ notify_time + ", _input_charset=" + _input_charset + ", sign=" + sign + ", sign_type=" + sign_type
				+ ", version=" + version + ", outer_trade_no=" + outer_trade_no + ", inner_trade_no=" + inner_trade_no
				+ ", trade_status=" + trade_status + ", trade_amount=" + trade_amount + ", gmt_create=" + gmt_create
				+ ", gmt_payment=" + gmt_payment + ", gmt_close=" + gmt_close + "]";
	}
	
}