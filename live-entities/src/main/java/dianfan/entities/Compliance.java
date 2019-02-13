package dianfan.entities;
/**
 * @ClassName Compliance
 * @Description 合规
 * @author cjy
 * @date 2018年3月26日 下午3:18:14
 */
public class Compliance {
	private String userid; //业务员id
	private String username; //业务员账号
	private String realname; //业务员名称
	private String count; //违规次数
	private String money; //罚款金额
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Compliance [userid=" + userid + ", username=" + username + ", realname=" + realname + ", count=" + count
				+ ", money=" + money + "]";
	}
}
