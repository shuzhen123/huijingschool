package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName BranchProfit
 * @Description 分润
 * @author cjy
 * @date 2018年2月26日 上午9:35:09
 */
public class BranchProfit {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String orderno; //varchar(50) DEFAULT NULL COMMENT '订单号',
	private Integer kind; //varchar(1) DEFAULT NULL COMMENT '种类（1课程，2礼物）',
	private String agentuserid; //varchar(50) DEFAULT NULL COMMENT '分润代理商',
	private String saleruserid; //varchar(50) DEFAULT NULL COMMENT '分润业务员',
	private Timestamp bprofitdate; //datetime DEFAULT NULL COMMENT '分润日期',
	private BigDecimal bagentmoney; //double(13,2) DEFAULT NULL COMMENT '分润代理商金额',
	private BigDecimal bsalermoney; //double(13,2) DEFAULT NULL COMMENT '分润业务员金额',
	private Integer bagentresult; //varchar(1) DEFAULT NULL COMMENT '分润代理商结果',
	private Integer bsalerresult; //varchar(1) DEFAULT NULL COMMENT '分润业务员结果',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	public String getAgentuserid() {
		return agentuserid;
	}
	public void setAgentuserid(String agentuserid) {
		this.agentuserid = agentuserid;
	}
	public String getSaleruserid() {
		return saleruserid;
	}
	public void setSaleruserid(String saleruserid) {
		this.saleruserid = saleruserid;
	}
	public Timestamp getBprofitdate() {
		return bprofitdate;
	}
	public void setBprofitdate(Timestamp bprofitdate) {
		this.bprofitdate = bprofitdate;
	}
	public BigDecimal getBagentmoney() {
		return bagentmoney;
	}
	public void setBagentmoney(BigDecimal bagentmoney) {
		this.bagentmoney = bagentmoney;
	}
	public BigDecimal getBsalermoney() {
		return bsalermoney;
	}
	public void setBsalermoney(BigDecimal bsalermoney) {
		this.bsalermoney = bsalermoney;
	}
	public Integer getBagentresult() {
		return bagentresult;
	}
	public void setBagentresult(Integer bagentresult) {
		this.bagentresult = bagentresult;
	}
	public Integer getBsalerresult() {
		return bsalerresult;
	}
	public void setBsalerresult(Integer bsalerresult) {
		this.bsalerresult = bsalerresult;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "BranchProfit [id=" + id + ", orderno=" + orderno + ", kind=" + kind + ", agentuserid=" + agentuserid
				+ ", saleruserid=" + saleruserid + ", bprofitdate=" + bprofitdate + ", bagentmoney=" + bagentmoney
				+ ", bsalermoney=" + bsalermoney + ", bagentresult=" + bagentresult + ", bsalerresult=" + bsalerresult
				+ ", createtime=" + createtime + "]";
	}
}
