package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName BannerInfo
 * @Description banner数据
 * @author cjy
 * @date 2018年1月25日 下午5:34:46
 */
public class BannerInfo {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String title; //varchar(100) DEFAULT NULL COMMENT '标题',
	private String picurl; //varchar(200) DEFAULT NULL COMMENT '缩略图url',
	private String content; //text COMMENT '内容',
	private String singname; //varchar(200) DEFAULT NULL COMMENT '作者',
	private Integer readcounts; //int(11) DEFAULT NULL COMMENT '阅读数量',
	private Integer link; //int(11) DEFAULT NULL COMMENT '是否外链（0否，1是）',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String entkbn; //int(1) DEFAULT NULL COMMENT '数据有效区分',
	public Integer getLink() {
		return link;
	}
	public void setLink(Integer link) {
		this.link = link;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(String entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "BannerInfo [id=" + id + ", title=" + title + ", picurl=" + picurl + ", content=" + content
				+ ", singname=" + singname + ", readcounts=" + readcounts + ", link=" + link + ", createtime="
				+ createtime + ", entkbn=" + entkbn + "]";
	}
}
