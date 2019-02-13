package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName StatementVip
 * @Description 报表——直播订单
 * @author cjy
 * @date 2018年2月12日 下午2:16:00
 */
public class StatementBean {
	private String orderno; //订单id
	private Integer source; //来源【1:app客户端2：微信公众号】
	private BigDecimal money; //支付金额
	private Integer paystatus; //支付状态(0未支付，1已支付，2已失效)
	private Integer paytype; //支付类型(1支付宝，2微信，3银联)
	private Timestamp paytime;//支付时间
	
	private String userid; //用户id
	private String telno; //用户登录手机号
	private String nickname; //用户昵称
	
	private String couponid; //代金券id
	private String cpname; //代金券名称
	private Integer type; //类型(1:体验券2:代金券)
	private Integer servicedays;//服务天数
	
	private BigDecimal price; //面值
	
	private Integer counts; //订单中礼物数量
	private String goodsid; //礼物id
	private String goodsname; //礼物名称
	
	private String salerid; //业务员id
	private String realname; //业务员姓名
	private BigDecimal aprofit; //代理商提成
	private BigDecimal profit; //业务员提成
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	public Timestamp getPaytime() {
		return paytime;
	}
	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public String getCpname() {
		return cpname;
	}
	public void setCpname(String cpname) {
		this.cpname = cpname;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getServicedays() {
		return servicedays;
	}
	public void setServicedays(Integer servicedays) {
		this.servicedays = servicedays;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getSalerid() {
		return salerid;
	}
	public void setSalerid(String salerid) {
		this.salerid = salerid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public BigDecimal getAprofit() {
		return aprofit;
	}
	public void setAprofit(BigDecimal aprofit) {
		this.aprofit = aprofit;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	@Override
	public String toString() {
		return "StatementBean [orderno=" + orderno + ", source=" + source + ", money=" + money + ", paystatus="
				+ paystatus + ", paytype=" + paytype + ", paytime=" + paytime + ", userid=" + userid + ", telno="
				+ telno + ", nickname=" + nickname + ", couponid=" + couponid + ", cpname=" + cpname + ", type=" + type
				+ ", servicedays=" + servicedays + ", price=" + price + ", counts=" + counts + ", goodsid=" + goodsid
				+ ", goodsname=" + goodsname + ", salerid=" + salerid + ", realname=" + realname + ", aprofit="
				+ aprofit + ", profit=" + profit + "]";
	}
}
