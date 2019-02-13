package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName Goods
 * @Description 礼物
 * @author cjy
 * @date 2018年2月12日 下午4:49:51
 */
public class Goods {
	private String goodsid; // varchar(50) NOT NULL COMMENT '商品id',
	private String goodsname; // varchar(50) DEFAULT NULL COMMENT '商品名称',
	private BigDecimal price; // decimal(18,2) DEFAULT NULL COMMENT '单价',
	private String icon; // varchar(50) DEFAULT NULL COMMENT '礼物icon',
	private Float agentprofit = 0F; // float(3,2) DEFAULT NULL COMMENT '一级分润',
	private Float salerprofit = 0F; // float(3,2) DEFAULT NULL COMMENT '二级分润',
	private Timestamp createtime; // datetime DEFAULT NULL COMMENT '创建时间',

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Float getAgentprofit() {
		return agentprofit;
	}

	public void setAgentprofit(Float agentprofit) {
		this.agentprofit = agentprofit;
	}

	public Float getSalerprofit() {
		return salerprofit;
	}

	public void setSalerprofit(Float salerprofit) {
		this.salerprofit = salerprofit;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Goods [goodsid=" + goodsid + ", goodsname=" + goodsname + ", price=" + price + ", icon=" + icon
				+ ", agentprofit=" + agentprofit + ", salerprofit=" + salerprofit + ", createtime=" + createtime + "]";
	}
}
