package dianfan.entities;
/**
 * @ClassName Customer
 * @Description 客户详情
 * @author cjy
 * @date 2018年3月14日 下午1:58:00
 */
public class Customer {
	private String nickname; //用户昵称
	private String telno; //手机号码
	private String remark; //备注
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Customer [nickname=" + nickname + ", telno=" + telno + ", remark=" + remark + "]";
	}
}
