package dainfan.hexun.entity;
/**
 * @ClassName HexunInstantTradeData
 * @Description 即时到账 交易参数
 * @author cjy
 * @date 2017年12月29日 上午9:57:30
 */
public class HexunInstantTradeData {

	private String orderid; //商品订单号交易号
	private String unit_price; //商品单价
	private Integer number; //商品数量
	private String price; //交易金额
	private String product_name; //商品名称
	
	private String pay_type; //支付方式（alipay 支付宝，wxpay 微信）
	
	private String order_type; //订单类型（vip、goods）
	
	private String mch; //支付设备（固定值：app）
	private String utm; //代理商识别码
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getMch() {
		return mch;
	}
	public void setMch(String mch) {
		this.mch = mch;
	}
	public String getUtm() {
		return utm;
	}
	public void setUtm(String utm) {
		this.utm = utm;
	}
	@Override
	public String toString() {
		return "HexunInstantTradeData [orderid=" + orderid + ", unit_price=" + unit_price + ", number=" + number
				+ ", price=" + price + ", product_name=" + product_name + ", pay_type=" + pay_type + ", order_type="
				+ order_type + ", mch=" + mch + ", utm=" + utm + "]";
	}
}
