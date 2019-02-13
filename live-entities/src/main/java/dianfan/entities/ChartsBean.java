package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ChartsBean {

	private Integer num;
	private BigDecimal money;
	private Timestamp time;
	private String strtime;
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public ChartsBean(Integer num, String strtime) {
		this.num = num;
		this.strtime = strtime;
	}
	public ChartsBean(BigDecimal money, String strtime) {
		this.money = money;
		this.strtime = strtime;
	}
	public ChartsBean() {
	}
	public String getStrtime() {
		return strtime;
	}
	public void setStrtime(String strtime) {
		this.strtime = strtime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "ChartsBean [num=" + num + ", money=" + money + ", time=" + time + ", strtime=" + strtime + "]";
	}
}
