package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName HandleMethod
 * @Description 合规处理方式
 * @author cjy
 * @date 2018年3月26日 上午11:36:55
 */
public class HandleMethod {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private Integer counts; //int(4) DEFAULT NULL COMMENT '第几次',
	private Integer type; //int(4) DEFAULT NULL COMMENT '处理方式[1：罚款2：封号]',
	private BigDecimal money; //decimal(12,2) DEFAULT NULL COMMENT '罚款金额',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "HandleMethod [id=" + id + ", counts=" + counts + ", type=" + type + ", money=" + money + ", createtime="
				+ createtime + "]";
	}
}
