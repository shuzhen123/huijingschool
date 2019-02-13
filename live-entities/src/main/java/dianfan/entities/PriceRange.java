package dianfan.entities;

import java.math.BigDecimal;
/**
 * @ClassName PriceRange
 * @Description 价格区间
 * @author cjy
 * @date 2018年1月10日 上午10:59:26
 */
public class PriceRange implements Comparable<PriceRange> {

	private String id; //
	private BigDecimal lprice; // 最低价
	private BigDecimal hprice; // 最高价


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getLprice() {
		return lprice;
	}

	public void setLprice(BigDecimal lprice) {
		this.lprice = lprice;
	}

	public BigDecimal getHprice() {
		return hprice;
	}

	public void setHprice(BigDecimal hprice) {
		this.hprice = hprice;
	}

	@Override
	public String toString() {
		return "PriceRange [id=" + id + ", lprice=" + lprice + ", hprice=" + hprice + "]";
	}

	@Override
	public int compareTo(PriceRange pr) {
		if(this.lprice == null) {
			this.lprice = new BigDecimal(0);
		}
		if(this.hprice == null) {
			this.hprice = new BigDecimal(0);
		}
		
		if(pr.lprice == null) {
			pr.setLprice(new BigDecimal(0));
		}
		if(pr.hprice == null) {
			pr.setHprice(new BigDecimal(0));
		}
		
		BigDecimal this_val = this.lprice.add(this.hprice).divide(new BigDecimal(2));
		BigDecimal tag_val = pr.lprice.add(pr.hprice).divide(new BigDecimal(2));
		return this_val.compareTo(tag_val);
	}

}
