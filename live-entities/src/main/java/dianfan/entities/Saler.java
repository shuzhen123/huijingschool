package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Saler
 * @Description 业务员
 * @author cjy
 * @date 2018年2月7日 下午2:57:49
 */
public class Saler {

	private String userid; //业务员id
	private String username; //业务员账号
	private String realname; //业务员姓名
	private String telno; //业务员手机号
	private String deptid; //所属部门id
	private String deptname; //所属部门名称
	private String positionid; //岗位id
	private String positionname; //岗位名称
	private String levelname; //等级名称
	private Timestamp registertime; //注册时间
	private Integer entkbn; //账号状态
	
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public Timestamp getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}
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
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid =positionid;
	}
	public String getPositionname() {
		return positionname;
	}
	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}
	
}
