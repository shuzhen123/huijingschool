package dianfan.entities.alipay;
/**
 * @ClassName AlipayQueryBean
 * @Description 统一收单线下交易查询
 * @author cjy
 * @date 2018年5月30日 上午9:29:52
 */
public class AlipayQueryBean {
	private String out_trade_no; //订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
	private String trade_no; //支付宝交易号，和商户订单号不能同时为空 
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	@Override
	public String toString() {
		return "AlipayQueryBean [out_trade_no=" + out_trade_no + ", trade_no=" + trade_no + "]";
	}
}
