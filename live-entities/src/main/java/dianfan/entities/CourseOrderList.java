package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName CourseOrderList
 * @Description 课程订单列表
 * @author cjy
 * @date 2018年4月9日 下午5:37:20
 */
public class CourseOrderList {
	private String orderno; //varchar(50) NOT NULL COMMENT '订单编号',
	private Integer paystatus; //varchar(5) DEFAULT NULL COMMENT '支付状态(0未支付，1已支付，2已取消)',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private Timestamp endpaytime; //datetime DEFAULT NULL COMMENT '支付截止时间',
	private Timestamp paytime; //datetime DEFAULT NULL COMMENT '支付成功时间',
	private BigDecimal money; //double(15,2) DEFAULT NULL COMMENT '支付金额',
	
	private Long second; //支付截止时间(秒),
	
	private String coursepic; //课程展示图
	private String coursename; //课程名称
	
	public Long getSecond() {
		return second;
	}
	public void setSecond(Long second) {
		this.second = second;
	}
	public Timestamp getPaytime() {
		return paytime;
	}
	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Integer getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getEndpaytime() {
		return endpaytime;
	}
	public void setEndpaytime(Timestamp endpaytime) {
		this.endpaytime = endpaytime;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getCoursepic() {
		return coursepic;
	}
	public void setCoursepic(String coursepic) {
		this.coursepic = coursepic;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	@Override
	public String toString() {
		return "CourseOrderList [orderno=" + orderno + ", paystatus=" + paystatus + ", createtime=" + createtime
				+ ", endpaytime=" + endpaytime + ", paytime=" + paytime + ", money=" + money + ", second=" + second
				+ ", coursepic=" + coursepic + ", coursename=" + coursename + "]";
	}
}
