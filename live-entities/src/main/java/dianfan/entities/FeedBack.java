package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Feedback
 * @Description 用户反馈表
 * @author cjy
 * @date 2018年1月11日 下午4:26:42
 */
public class FeedBack {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '用户id【可不相关】',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '用户真实姓名',
	private String content; //varchar(300) DEFAULT NULL COMMENT '内容',
	private String mail; //varchar(50) DEFAULT NULL COMMENT '邮件',
	private String picurl; //varchar(300) DEFAULT NULL COMMENT '图片url',
	private String teloremail; //varchar(50) DEFAULT NULL COMMENT '电话或手机',
	private Timestamp createtime; //varchar(50) DEFAULT NULL COMMENT '创建时间',
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private String updateid; //varchar(50) DEFAULT NULL COMMENT '更新者id',
	private Integer entkbn; //int(1) DEFAULT NULL COMMENT '数据有效区分',
	
	public String getId() {
		return id;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getTeloremail() {
		return teloremail;
	}
	public void setTeloremail(String teloremail) {
		this.teloremail = teloremail;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getUpdateid() {
		return updateid;
	}
	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "FeedBack [id=" + id + ", userid=" + userid + ", realname=" + realname + ", content=" + content
				+ ", mail=" + mail + ", picurl=" + picurl + ", teloremail=" + teloremail + ", createtime=" + createtime
				+ ", createid=" + createid + ", updateid=" + updateid + ", entkbn=" + entkbn + "]";
	}
}
