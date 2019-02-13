package dianfan.entities;
/**
 * @ClassName DynamicNews
 * @Description 最新动态
 * @author cjy
 * @date 2018年1月23日 下午3:20:34
 */
public class DynamicNews {

	private String phone;
	private Integer days;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return "DynamicNews [phone=" + phone + ", days=" + days + "]";
	}
	
}
