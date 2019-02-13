package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName VipInfomation
 * @Description vip资讯
 * @author cjy
 * @date 2018年2月26日 下午5:50:06
 */
public class VipInfomation {
	private String articalid; //varchar(50) NOT NULL COMMENT '主键id',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '用户id',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '用户名称',
	private Timestamp tdays; //datetime DEFAULT NULL COMMENT '日期',
	private String kind; //varchar(300) DEFAULT NULL COMMENT '文章类型id',
	private String kindname; //varchar(300) DEFAULT NULL COMMENT '文章类型名称',
	private String articaltitle; //varchar(300) DEFAULT NULL COMMENT '文章标题',
	private String picurl; //varchar(300) DEFAULT NULL COMMENT '缩略图url',
	private String content; //text COMMENT '内容',
	private Integer readcount; //int(5) DEFAULT NULL COMMENT '阅读量',
	private Integer showflag; //varchar(1) DEFAULT NULL COMMENT '显示flag',
	private Integer checkflag; //varchar(1) DEFAULT NULL COMMENT '审核(0待审核， 1审核通过，2审核不通过)',
	private String cause; //text COMMENT '审核原因',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	
	public String getKindname() {
		return kindname;
	}
	public void setKindname(String kindname) {
		this.kindname = kindname;
	}
	public String getArticalid() {
		return articalid;
	}
	public void setArticalid(String articalid) {
		this.articalid = articalid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Timestamp getTdays() {
		return tdays;
	}
	public void setTdays(Timestamp tdays) {
		this.tdays = tdays;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getArticaltitle() {
		return articaltitle;
	}
	public void setArticaltitle(String articaltitle) {
		this.articaltitle = articaltitle;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReadcount() {
		return readcount;
	}
	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
	public Integer getShowflag() {
		return showflag;
	}
	public void setShowflag(Integer showflag) {
		this.showflag = showflag;
	}
	public Integer getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "VipInfomation [articalid=" + articalid + ", userid=" + userid + ", realname=" + realname + ", tdays="
				+ tdays + ", kind=" + kind + ", kindname=" + kindname + ", articaltitle=" + articaltitle + ", picurl="
				+ picurl + ", content=" + content + ", readcount=" + readcount + ", showflag=" + showflag
				+ ", checkflag=" + checkflag + ", cause=" + cause + ", createtime=" + createtime + "]";
	}
}