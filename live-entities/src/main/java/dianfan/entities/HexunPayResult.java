package dianfan.entities;
/**
 * @ClassName HexunPay
 * @Description 首次支付返回值
 * @author cjy
 * @date 2018年1月10日 下午3:15:30
 */
public class HexunPayResult {
	private String _input_charset; //编码字符集
	private String cashier_url; //实体数据
	private Object result; //实体数据
	private String is_success; //请求状态（T/F）
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getCashier_url() {
		return cashier_url;
	}
	public void setCashier_url(String cashier_url) {
		this.cashier_url = cashier_url;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	@Override
	public String toString() {
		return "HexunPayResult [_input_charset=" + _input_charset + ", cashier_url=" + cashier_url + ", result="
				+ result + ", is_success=" + is_success + "]";
	}
}
