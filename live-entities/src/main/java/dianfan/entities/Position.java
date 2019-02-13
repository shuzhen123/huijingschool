package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Position
 * @Description 岗位bean
 * @author zhusun
 * @date 2018年2月7日 上午9:58:53
 */
public class Position {
	private String positionid; //varchar(50) NOT NULL COMMENT '岗位id',
	private String positionname; //varchar(50) DEFAULT NULL COMMENT '岗位名称',
	private Integer positionenable; //1启用/0禁用,
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp createtime; //varchar(50) DEFAULT NULL COMMENT '创建时间',
	
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
	public String getPositionname() {
		return  positionname;
	}
	public void setPositionname(String  positionname) {
		this.positionname =  positionname;
	}
	public Integer getPositionenable() {
		return positionenable;
	}
	public void setPositionenable(Integer positionenable) {
		this.positionenable = positionenable;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "Position [positionid=" + positionid + ", positionname=" + positionname + ", positionenable=" + positionenable + ", createid="
				+ createid + ", createtime=" + createtime + "]";
	}
}
