package dianfan.entities;
/**
 * @ClassName HexunOrderData
 * @Description 和讯支付必须参数
 * @author cjy
 * @date 2017年12月28日 下午3:40:40
 */
public class HexunOrderData {
	private String orderid; //商品订单号
	private String unit_price; //商品单价
	private Integer number; //商品数量
	private String price; //交易金额
	private String product_name; //商品名称
	
	private String pay_type; //支付方式（alipay 支付宝，wxpay 微信）
	
	
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
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
}
