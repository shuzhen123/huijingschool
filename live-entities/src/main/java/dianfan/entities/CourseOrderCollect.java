package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName CourseOrderCollect
 * @Description 课程订单汇总
 * @author cjy
 * @date 2018年4月11日 下午4:15:25
 */
public class CourseOrderCollect {
	private String orderno; //订单id
	private BigDecimal money; //支付金额
	private Integer paystatus; //支付状态(0未支付，1已支付，2已失效)
	private Integer paytype; //支付类型(1支付宝，2微信，3银联)
	private Timestamp paytime;//支付时间
	private Timestamp endpaytime;//支付截止时间
	private Timestamp createtime;//订单创建时间
	private Timestamp validitytime;//订单有效期
	private Integer entkbn; //订单状态
	
	private String coursename; //课程名称
	
	private String userid; //用户id
	private String telno; //用户手机号
	private String nickname; //用户昵称
	
	private String cashcouponid; //代金券id
	private String cpname; //代金券名称
	private BigDecimal price; //面值
	
	private String salerid; //业务员id
	private String realname; //业务员名称
	
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
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
	public Timestamp getEndpaytime() {
		return endpaytime;
	}
	public void setEndpaytime(Timestamp endpaytime) {
		this.endpaytime = endpaytime;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getValiditytime() {
		return validitytime;
	}
	public void setValiditytime(Timestamp validitytime) {
		this.validitytime = validitytime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
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
	public String getCashcouponid() {
		return cashcouponid;
	}
	public void setCashcouponid(String cashcouponid) {
		this.cashcouponid = cashcouponid;
	}
	public String getCpname() {
		return cpname;
	}
	public void setCpname(String cpname) {
		this.cpname = cpname;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	@Override
	public String toString() {
		return "CourseOrderCollect [orderno=" + orderno + ", money=" + money + ", paystatus=" + paystatus + ", paytype="
				+ paytype + ", paytime=" + paytime + ", endpaytime=" + endpaytime + ", createtime=" + createtime
				+ ", validitytime=" + validitytime + ", entkbn=" + entkbn + ", coursename=" + coursename + ", userid="
				+ userid + ", telno=" + telno + ", nickname=" + nickname + ", cashcouponid=" + cashcouponid
				+ ", cpname=" + cpname + ", price=" + price + ", salerid=" + salerid + ", realname=" + realname + "]";
	}
}
