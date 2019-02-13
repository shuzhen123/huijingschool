package dianfan.entities;
/**
 * @ClassName TalkTime
 * @Description 通话时长
 * @author zhusun
 * @date 2018年3月26日 下午3:18:14
 */
public class TalkTime {
	private String userid; //业务员id
	private String username; //业务员账号
	private String realname; //业务员名称
	private String count; //通话次数
	private String calltimes; //通话时长
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
	public String getCalltimes() {
		return calltimes;
	}
	public void setCalltimes(String calltimes) {
		this.calltimes = calltimes;
	}
	@Override
	public String toString() {
		return "TalkTime [userid=" + userid + ", username=" + username + ", realname=" + realname + ", count=" + count
				+ ", calltimes=" + calltimes + "]";
	}
}
