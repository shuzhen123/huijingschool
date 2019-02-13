package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName Reperece
 * @Description 奖惩记录
 * @author cjy
 * @date 2018年3月15日 下午12:36:40
 */
public class Reperecs {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private Integer type; //varchar(1) DEFAULT NULL COMMENT '类型【1：敏感文字2：录音】',
	private String detail; //varchar(200) DEFAULT NULL COMMENT '详细敏感文字/录音内容',
	private String salecustomer_tellog_id; //varchar(200) DEFAULT NULL COMMENT '业务员客户通话记录表id',
	private String wordsid; //varchar(50) DEFAULT NULL COMMENT '文字表id',
	private String saleid; //varchar(50) DEFAULT NULL COMMENT '业务员id',
	private BigDecimal money; //decimal(12,2) DEFAULT NULL COMMENT '金额',
	private String handlemethodid; //varchar(50) DEFAULT NULL COMMENT '处理方式id',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private Integer entkbn; //int(1) DEFAULT NULL COMMENT '数据有效区分',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSalecustomer_tellog_id() {
		return salecustomer_tellog_id;
	}
	public void setSalecustomer_tellog_id(String salecustomer_tellog_id) {
		this.salecustomer_tellog_id = salecustomer_tellog_id;
	}
	public String getWordsid() {
		return wordsid;
	}
	public void setWordsid(String wordsid) {
		this.wordsid = wordsid;
	}
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getHandlemethodid() {
		return handlemethodid;
	}
	public void setHandlemethodid(String handlemethodid) {
		this.handlemethodid = handlemethodid;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "Reperece [id=" + id + ", type=" + type + ", detail=" + detail + ", salecustomer_tellog_id="
				+ salecustomer_tellog_id + ", wordsid=" + wordsid + ", saleid=" + saleid + ", money=" + money
				+ ", handlemethodid=" + handlemethodid + ", createtime=" + createtime + ", entkbn=" + entkbn + "]";
	}
}
