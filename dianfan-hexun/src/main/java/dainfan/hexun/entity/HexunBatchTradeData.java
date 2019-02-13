package dainfan.hexun.entity;

import java.math.BigDecimal;

/**
 * @ClassName HexunBatchTradeData
 * @Description 继续支付/ 批量支付操作 参数
 * @author cjy
 * @date 2017年12月29日 上午11:59:38
 */
public class HexunBatchTradeData {

	private String outer_trade_no; // 订单号
	private String operator_id; // 操作员 Id 
	private String pay_method; // 支付方式
	private String price; //交易金额
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOuter_trade_no() {
		return outer_trade_no;
	}
	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
}
