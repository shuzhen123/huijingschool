package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName CourseType
 * @Description 课程分类
 * @author cjy
 * @date 2018年1月31日 上午10:28:52
 */
public class CourseType {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String coursetypename; //varchar(50) DEFAULT NULL COMMENT '课程分类名称',
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCoursetypename() {
		return coursetypename;
	}
	public void setCoursetypename(String coursetypename) {
		this.coursetypename = coursetypename;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "CourseType [id=" + id + ", coursetypename=" + coursetypename + ", createid=" + createid
				+ ", createtime=" + createtime + "]";
	}
}
