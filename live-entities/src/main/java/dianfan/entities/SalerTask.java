package dianfan.entities;

import java.sql.Date;

/**
 * @ClassName SalerTask
 * @Description 业务统计表【业务员】
 * @author cjy
 * @date 2018年2月24日 上午11:42:30
 */
public class SalerTask implements Comparable<SalerTask>{
	private String userid; //varchar(50) DEFAULT NULL COMMENT '业务员id',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '业务员名称',
	private Date createtime; //date DEFAULT NULL COMMENT '创建时间',
	private Integer standpeople; //varchar(50) DEFAULT NULL COMMENT '标准值',
	private Integer actualpeople; //varchar(50) DEFAULT NULL COMMENT '实际值',
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getStandpeople() {
		return standpeople;
	}
	public void setStandpeople(Integer standpeople) {
		this.standpeople = standpeople;
	}
	public Integer getActualpeople() {
		return actualpeople;
	}
	public void setActualpeople(Integer actualpeople) {
		this.actualpeople = actualpeople;
	}
	@Override
	public String toString() {
		return "SalerTask [userid=" + userid + ", realname=" + realname + ", createtime=" + createtime
				+ ", standpeople=" + standpeople + ", actualpeople=" + actualpeople + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualpeople == null) ? 0 : actualpeople.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((realname == null) ? 0 : realname.hashCode());
		result = prime * result + ((standpeople == null) ? 0 : standpeople.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalerTask other = (SalerTask) obj;
		if (actualpeople == null) {
			if (other.actualpeople != null)
				return false;
		} else if (!actualpeople.equals(other.actualpeople))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (realname == null) {
			if (other.realname != null)
				return false;
		} else if (!realname.equals(other.realname))
			return false;
		if (standpeople == null) {
			if (other.standpeople != null)
				return false;
		} else if (!standpeople.equals(other.standpeople))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(SalerTask o) {
		if(this.createtime == null) {
			return 1;
		}
		int nowday = this.createtime.getDay();
		int thisday = o.getCreatetime().getDay();
		return thisday - nowday;
	}
}
