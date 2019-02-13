package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Infomation
 * @Description 资讯
 * @author cjy
 * @date 2018年1月10日 上午10:58:55
 */
public class Infomation {
	private String id; // varchar(50) NOT NULL COMMENT '资讯id',
	private String informationmodelid; // varchar(50) NOT NULL COMMENT '模块id',
	private String newskindname; // varchar(50) NOT NULL COMMENT '模块名称',
	private String userid; // varchar(50) DEFAULT NULL COMMENT '用户id',
	private String realname; // varchar(50) DEFAULT NULL COMMENT '用户名称',
	private String infomationtitle; // varchar(100) DEFAULT NULL COMMENT '资讯标题',
	private String picurl; // varchar(200) DEFAULT NULL COMMENT '缩略图url',
	private String content; // text COMMENT '内容',
	private String descrption; // varchar(200) DEFAULT NULL COMMENT '描述',
	private String tag; // varchar(200) DEFAULT NULL COMMENT '标签',
	private String singname; // varchar(200) DEFAULT NULL COMMENT '作者',
	private Integer readcounts; // varchar(200) DEFAULT NULL COMMENT '阅读数量',
	private Integer thumbsupcounts; // varchar(10) DEFAULT NULL COMMENT '点赞数',
	private Timestamp createtime; // datetime DEFAULT NULL COMMENT '创建时间',
	private String createid; // varchar(50) DEFAULT NULL COMMENT '创建者ID',
	private String updateid; // varchar(50) DEFAULT NULL COMMENT '更新者id',
	private Integer entkbn; // int(1) DEFAULT NULL COMMENT '数据有效区分',
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInformationmodelid() {
		return informationmodelid;
	}
	public void setInformationmodelid(String informationmodelid) {
		this.informationmodelid = informationmodelid;
	}
	public String getNewskindname() {
		return newskindname;
	}
	public void setNewskindname(String newskindname) {
		this.newskindname = newskindname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getInfomationtitle() {
		return infomationtitle;
	}
	public void setInfomationtitle(String infomationtitle) {
		this.infomationtitle = infomationtitle;
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
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSingname() {
		return singname;
	}
	public void setSingname(String singname) {
		this.singname = singname;
	}
	public Integer getReadcounts() {
		return readcounts;
	}
	public void setReadcounts(Integer readcounts) {
		this.readcounts = readcounts;
	}
	public Integer getThumbsupcounts() {
		return thumbsupcounts;
	}
	public void setThumbsupcounts(Integer thumbsupcounts) {
		this.thumbsupcounts = thumbsupcounts;
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
		return "Infomation [id=" + id + ", informationmodelid=" + informationmodelid + ", newskindname=" + newskindname
				+ ", userid=" + userid + ", infomationtitle=" + infomationtitle + ", picurl=" + picurl + ", content="
				+ content + ", descrption=" + descrption + ", tag=" + tag + ", singname=" + singname + ", readcounts="
				+ readcounts + ", thumbsupcounts=" + thumbsupcounts + ", createtime=" + createtime + ", createid="
				+ createid + ", updateid=" + updateid + ", entkbn=" + entkbn + "]";
	}
}