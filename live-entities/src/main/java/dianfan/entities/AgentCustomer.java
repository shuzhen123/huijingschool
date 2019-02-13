package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName AgentCustomer
 * @Description 代理商-用户分流数据
 * @author cjy
 * @date 2018年2月6日 下午5:29:59
 */
public class AgentCustomer {
	private String userid; // 用户id
	private String telno; // 用户手机号码
	private String nickname; // 用户昵称
	private String realname; // 用户姓名
	private Integer sex; // 用户性别
	private String prov; // 用户省份
	private String iconurl; // 用户头像
	private Integer riskratingfalg; // 风险测评通过flag（0未通过，1已通过）
	private String invitecode; // 邀请码
	private Timestamp registertime; // 注册时间
	private Integer entkbn; //

	private String remark; // 邀请码

	private String levelid; // 用户等级id
	private String levelname; // 用户等级名称

	private String salerid; // 业务员id
	private String salername; // 业务员姓名
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public Integer getRiskratingfalg() {
		return riskratingfalg;
	}
	public void setRiskratingfalg(Integer riskratingfalg) {
		this.riskratingfalg = riskratingfalg;
	}
	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	public Timestamp getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLevelid() {
		return levelid;
	}
	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public String getSalerid() {
		return salerid;
	}
	public void setSalerid(String salerid) {
		this.salerid = salerid;
	}
	public String getSalername() {
		return salername;
	}
	public void setSalername(String salername) {
		this.salername = salername;
	}
	@Override
	public String toString() {
		return "AgentCustomer [userid=" + userid + ", telno=" + telno + ", nickname=" + nickname + ", realname="
				+ realname + ", sex=" + sex + ", prov=" + prov + ", iconurl=" + iconurl + ", riskratingfalg="
				+ riskratingfalg + ", invitecode=" + invitecode + ", registertime=" + registertime + ", entkbn="
				+ entkbn + ", remark=" + remark + ", levelid=" + levelid + ", levelname=" + levelname + ", salerid="
				+ salerid + ", salername=" + salername + "]";
	}
}
