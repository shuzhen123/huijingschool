package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName: TrackRecord
 * @Description: 客户跟踪记录表
 * @author sz
 * @time 2018年5月16日 下午4:12:29
 */
public class TrackRecord {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String salerid; //varchar(50) NOT NULL COMMENT '业务员id',
	private String salername; //业务员名字
	private String userid; //varchar(50) NOT NULL COMMENT '用户id',
	private String record; //text COMMENT '跟踪记录内容',
	private Timestamp createtime; //datetime NOT NULL COMMENT '记录创建时间',
	private String updateid; //varchar(50) DEFAULT NULL COMMENT '更新者id',
	private Timestamp updatetime; //timestamp NOT NULL DEFAULT  '更新时间',
	
	public String getSalername() {
		return salername;
	}
	public void setSalername(String salername) {
		this.salername = salername;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalerid() {
		return salerid;
	}
	public void setSalerid(String salerid) {
		this.salerid = salerid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getUpdateid() {
		return updateid;
	}
	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "TrackRecord [id=" + id + ", salerid=" + salerid + ", salername=" + salername + ", userid=" + userid
				+ ", record=" + record + ", createtime=" + createtime + ", updateid=" + updateid + ", updatetime="
				+ updatetime + "]";
	}

	
	
	
}
