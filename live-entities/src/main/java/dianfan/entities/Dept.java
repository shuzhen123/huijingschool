package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Dept
 * @Description 部门bean
 * @author cjy
 * @date 2018年2月7日 上午9:58:53
 */
public class Dept {
	private String deptid; //varchar(50) NOT NULL COMMENT '部门id',
	private String deptname; //varchar(50) DEFAULT NULL COMMENT '部门名称',
	private Integer deptenable; //1启用/0禁用,
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp createtime; //varchar(50) DEFAULT NULL COMMENT '创建时间',
	private Integer count; //部门人数,
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Integer getDeptenable() {
		return deptenable;
	}
	public void setDeptenable(Integer deptenable) {
		this.deptenable = deptenable;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "Dept [deptid=" + deptid + ", deptname=" + deptname + ", deptenable=" + deptenable + ", createid="
				+ createid + ", createtime=" + createtime + "]";
	}
}
