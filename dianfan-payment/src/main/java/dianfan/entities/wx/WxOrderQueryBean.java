package dianfan.entities.wx;
/**
 * @ClassName WxOrderQueryBean
 * @Description 微信查询订单
 * @author cjy
 * @date 2018年5月29日 下午5:21:50
 */
public class WxOrderQueryBean {
	private String transaction_id; //微信的订单号，建议优先使用
	private String out_trade_no; //商户系统内部订单号
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
}
