package dainfan.hexun.entity;

public interface HexunServiceType {

	//即时到账 交易网关接口
	public static String INSTANT_TRADE = "instant_trade";
	//退款网关接口
	public static String REFUND = "refund";
	//交易查询网关接口
	public static String QUERY = "query_trade";
	//继续支付/批量支付操作网关接口
	public static String GO_ON_PAY = "create_pay";
	
	
	
}
