package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName VipLevel
 * @Description vip等级
 * @author cjy
 * @date 2018年4月4日 下午5:35:44
 */
public class VipLevel {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String levelname; //varchar(50) DEFAULT NULL COMMENT '等级名称',
	private BigDecimal money; //DECIMAL(10,2) DEFAULT NULL COMMENT '等级金额',
	private Integer days; //int DEFAULT NULL COMMENT '时效',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "VipLevel [id=" + id + ", levelname=" + levelname + ", money=" + money + ", days=" + days
				+ ", createtime=" + createtime + "]";
	}
}
