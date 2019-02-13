package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName GoodsOrder
 * @Description 礼物订单
 * @author cjy
 * @date 2018年3月2日 下午1:10:13
 */
public class GoodsOrder {
	/**
	 * varchar(50) NOT NULL COMMENT '订单编号',
	 */
	private String orderno;
	/**
	 * //varchar(50) DEFAULT NULL COMMENT '礼物商品id',
	 */
	private String goodsid; 
	/**
	 * //varchar(50) DEFAULT NULL COMMENT '支付人员id',
	 */
	private String userid; 
	/**
	 * //decimal(15,2) DEFAULT NULL COMMENT '支付金额',
	 */
	private BigDecimal money; 
	/**
	 * //int(50) DEFAULT NULL COMMENT '件数',
	 */
	private Integer counts; 
	/**
	 * //varchar(200) DEFAULT NULL COMMENT '留言',
	 */
	private String remark; 
	/**
	 * //varchar(5) DEFAULT NULL COMMENT '支付状态(0未支付，1已支付，2已失效)',
	 */
	private Integer paystatus; 
	/**
	 * //varchar(5) DEFAULT NULL COMMENT '支付类型(1支付宝，2微信)',
	 */
	private Integer paytype; 
	/**
	 * //datetime DEFAULT NULL COMMENT '支付时间',
	 */
	private Timestamp paytime; 
	/**
	 * //datetime DEFAULT NULL COMMENT '创建时间',
	 */
	private Timestamp createtime; 
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "GoodsOrder [orderno=" + orderno + ", goodsid=" + goodsid + ", userid=" + userid + ", money=" + money
				+ ", counts=" + counts + ", remark=" + remark + ", paystatus=" + paystatus + ", paytype=" + paytype
				+ ", paytime=" + paytime + ", createtime=" + createtime + "]";
	}
}
