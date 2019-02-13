package dianfan.entities.wx;
/**
 * @ClassName WxTradeType
 * @Description 微信交易类型
 * @author cjy
 * @date 2018年5月25日 上午10:17:46
 */
public interface WxTradeType {
	/** JSAPI 公众号支付 */
	public static final String JSAPI = "JSAPI";
	/** NATIVE 扫码支付 */
	public static final String NATIVE  = "NATIVE";
	/** APP支付 */
	public static final String APP  = "APP";
	/** h5支付 */
	public static final String WEB  = "MWEB";
}
