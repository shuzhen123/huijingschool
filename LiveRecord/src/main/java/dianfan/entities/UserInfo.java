package dianfan.entities;

import java.sql.Timestamp;
/**
 * @ClassName UserInfo
 * @Description 用户信息表
 * @author cjy
 * @date 2017年12月25日 上午9:33:02
 */
public class UserInfo {
	private String userid; // varchar(50) not null comment '用户id',
	private String username; // varchar(50) comment '用户名',
	private String introduction; // varchar(400) comment '简介',
	private String password; // varchar(64) comment '密码',
	private String realname; // varchar(50) comment '真实姓名',
	private String cardid; // varchar(20) comment '身份证号码',
	private String telno; // varchar(15) comment '电话号码',
	private String openid; // varchar(100) comment '微信openid',
	private String iconurl; // varchar(100) comment '头像',
	private String nickname; // varchar(50) comment '昵称',
	private Timestamp registertime; // datetime comment '注册时间',
	private Integer registerstatus; // int(1) comment '注册状态（1：未实名注册2：已实名注册）',
	private Integer entkbn; // int(1) comment '数据有效区分（0：正常数据1:无效数据9：逻辑删除）',
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Timestamp getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}
	public Integer getRegisterstatus() {
		return registerstatus;
	}
	public void setRegisterstatus(Integer registerstatus) {
		this.registerstatus = registerstatus;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "UserInfo [userid=" + userid + ", username=" + username + ", introduction=" + introduction
				+ ", password=" + password + ", realname=" + realname + ", cardid=" + cardid + ", telno=" + telno
				+ ", openid=" + openid + ", iconurl=" + iconurl + ", nickname=" + nickname + ", registertime="
				+ registertime + ", registerstatus=" + registerstatus + ", entkbn=" + entkbn + "]";
	}
}
