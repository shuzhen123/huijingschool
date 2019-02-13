package dianfan.util.wx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WeixinOrderData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appid; // 微信开放平台审核通过的应用APPID
	private String bank_type; // 付款银行
	private Integer cash_fee; // 现金支付金额订单现金支付金额(分)
	private String fee_type; // 货币种类
	private String is_subscribe; // 是否关注公众账号
	private String mch_id; // 商户号
	private String nonce_str; // 随机字符串
	private String openid; // 用户标识
	private String out_trade_no; // 商户订单号
	private String result_code; // 业务结果
	private String return_code; // 通信标识
	private String sign; // 签名
	private String time_end; // 支付完成时间
	private Integer total_fee; // 订单总金额，单位为分
	private String trade_type; // 交易类型
	private String transaction_id; // 微信支付订单号
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public Integer getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	@Override
	public String toString() {
		return "WeixinOrderData [appid=" + appid + ", bank_type=" + bank_type
				+ ", cash_fee=" + cash_fee + ", fee_type=" + fee_type
				+ ", is_subscribe=" + is_subscribe + ", mch_id=" + mch_id
				+ ", nonce_str=" + nonce_str + ", openid=" + openid
				+ ", out_trade_no=" + out_trade_no + ", result_code="
				+ result_code + ", return_code=" + return_code + ", sign="
				+ sign + ", time_end=" + time_end + ", total_fee=" + total_fee
				+ ", trade_type=" + trade_type + ", transaction_id="
				+ transaction_id + "]";
	}
	
	
}
