package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName GoodsOrderCollect
 * @Description 礼物订单
 * @author cjy
 * @date 2018年4月18日 上午11:17:05
 */
public class GoodsOrderCollect {
	private String orderno; // varchar(50) NOT NULL COMMENT '订单编号',
	private String goodsid; // varchar(50) DEFAULT NULL COMMENT '礼物商品id',
	private String goodsname; // varchar(50) DEFAULT NULL COMMENT '礼物商品名称',
	private String userid; // varchar(50) DEFAULT NULL COMMENT '支付人员id',
	private String telno; // varchar(50) DEFAULT NULL COMMENT '支付人员账号',
	private String nickname; // varchar(50) DEFAULT NULL COMMENT '支付人员昵称',
	private BigDecimal money; // decimal(15,2) DEFAULT NULL COMMENT '支付金额',
	private Integer counts; // int(50) DEFAULT NULL COMMENT '件数',
	private String remark; // varchar(200) DEFAULT NULL COMMENT '留言',
	private Integer paystatus; // varchar(5) DEFAULT NULL COMMENT '支付状态(0未支付，1已支付，2已失效)',
	private Integer paytype; // tinyint(1) DEFAULT NULL COMMENT '支付类型(1支付宝，2微信)',
	private Timestamp paytime; // datetime DEFAULT NULL COMMENT '支付时间',
	
	private String realname; // varchar(50) DEFAULT NULL COMMENT '代理商名称',

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

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Override
	public String toString() {
		return "GoodsOrderCollect [orderno=" + orderno + ", goodsid=" + goodsid + ", goodsname=" + goodsname
				+ ", userid=" + userid + ", telno=" + telno + ", nickname=" + nickname + ", money=" + money
				+ ", counts=" + counts + ", remark=" + remark + ", paystatus=" + paystatus + ", paytype=" + paytype
				+ ", paytime=" + paytime + ", realname=" + realname + "]";
	}

}
