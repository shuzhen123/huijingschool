package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * @ClassName Coupon
 * @Description 卡券
 * @author cjy
 * @date 2018年1月10日 上午11:00:43
 */
public class Coupon {
	private String id; //varchar(50) NOT NULL COMMENT '券id',
	private String cpname; //varchar(50) DEFAULT NULL COMMENT '代金券名称',
	private Integer type; //varchar(50) DEFAULT NULL COMMENT '类型(1:体验券2:代金券)',
	private BigDecimal price; //decimal(18,2) DEFAULT NULL COMMENT '面值',
	private Integer useplace; //varchar(1) DEFAULT NULL COMMENT '使用场所【1:直播2：购买课程】',
	private Integer servicedays; //int(11) DEFAULT NULL COMMENT '服务天数',
	private Integer validity; //int(11) DEFAULT NULL COMMENT '有效期(天数)',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private Timestamp expire; //datetime DEFAULT NULL COMMENT '到期时间',
	private Integer entkbn; //int(1) DEFAULT NULL COMMENT '数据有效区分（0有效，9删除）',	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getUseplace() {
		return useplace;
	}
	public void setUseplace(Integer useplace) {
		this.useplace = useplace;
	}
	public Integer getServicedays() {
		return servicedays;
	}
	public void setServicedays(Integer servicedays) {
		this.servicedays = servicedays;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getExpire() {
		return expire;
	}
	public void setExpire(Timestamp expire) {
		this.expire = expire;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", cpname=" + cpname + ", type=" + type + ", price=" + price + ", useplace="
				+ useplace + ", servicedays=" + servicedays + ", validity=" + validity + ", createtime=" + createtime
				+ ", expire=" + expire + ", entkbn=" + entkbn + "]";
	}
}
