package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName CourseOrder
 * @Description 课程订单
 * @author cjy
 * @date 2018年4月9日 下午3:16:40
 */
public class CourseOrder {
	private String orderno; //varchar(50) NOT NULL COMMENT '订单编号',
	private String trannum; //varchar(50) NOT NULL COMMENT '交易号',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '用户id',
	private String cashcouponid; //varchar(50) DEFAULT NULL COMMENT '代金券id',
	private String cpname; //varchar(50) DEFAULT NULL COMMENT '代金券名称',
	private BigDecimal price; //varchar(50) DEFAULT NULL COMMENT '代金券金额',
	private Integer source; //int(1) DEFAULT NULL COMMENT '来源【1:app客户端2：微信公众号】',
	private BigDecimal money; //double(15,2) DEFAULT NULL COMMENT '支付金额',
	private Integer paystatus; //varchar(5) DEFAULT NULL COMMENT '支付状态(0未支付，1已支付，2已取消)',
	private String paytype; //varchar(5) DEFAULT NULL COMMENT '支付类型',
	private Timestamp paytime; //datetime DEFAULT NULL COMMENT '支付时间',
	private Timestamp endpaytime; //datetime DEFAULT NULL COMMENT '支付截止时间',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private List<CartCourse> courselist; //varchar(50) DEFAULT NULL COMMENT '课程列表',
	private Long second; //支付截止时间(秒),
	private Object handler;
	
	public Object getHandler() {
		return handler;
	}
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	public Long getSecond() {
		return second;
	}
	public void setSecond(Long second) {
		this.second = second;
	}
	public String getOrderno() {
		return orderno;
	}
	public String getTrannum() {
		return trannum;
	}
	public void setTrannum(String trannum) {
		this.trannum = trannum;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public List<CartCourse> getCourselist() {
		return courselist;
	}
	public void setCourselist(List<CartCourse> courselist) {
		this.courselist = courselist;
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
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
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
	@Override
	public String toString() {
		return "CourseOrder [orderno=" + orderno + ", trannum=" + trannum + ", userid=" + userid + ", cashcouponid="
				+ cashcouponid + ", cpname=" + cpname + ", price=" + price + ", source=" + source + ", money=" + money
				+ ", paystatus=" + paystatus + ", paytype=" + paytype + ", paytime=" + paytime + ", endpaytime="
				+ endpaytime + ", createtime=" + createtime + ", courselist=" + courselist + ", second=" + second + "]";
	}
}
