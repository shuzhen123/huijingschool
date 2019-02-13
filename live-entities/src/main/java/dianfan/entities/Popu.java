package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName popu
 * @Description 推广
 * @author cjy
 * @date 2018年2月2日 上午11:03:17
 */
public class Popu {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String userid; //varchar(50) NOT NULL COMMENT '用户id',
	private String invitecode; //int(6) NOT NULL COMMENT '邀请码',
	private String qrurl; //varchar(500) DEFAULT NULL COMMENT '二维码url',
	private String popuurl; //varchar(500) DEFAULT NULL COMMENT '推广链接url',
	private Integer type; //tinyint(1) DEFAULT NULL COMMENT '类型（1二维码推广，2直播间推广）',
	private String checkcode; //varchar(50) DEFAULT NULL COMMENT '校验字符串',
	private Timestamp updatetime; //timestamp NOT NULL COMMENT '更新时间',
	private String createid; //varchar(50) NOT NULL COMMENT '创建者ID',
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	public String getId() {
		return id;
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
	public String getQrurl() {
		return qrurl;
	}
	public void setQrurl(String qrurl) {
		this.qrurl = qrurl;
	}
	public String getPopuurl() {
		return popuurl;
	}
	public void setPopuurl(String popuurl) {
		this.popuurl = popuurl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String check) {
		this.checkcode = check;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "Popu [id=" + id + ", userid=" + userid + ", invitecode=" + invitecode + ", qrurl=" + qrurl
				+ ", popuurl=" + popuurl + ", type=" + type + ", checkcode=" + checkcode + ", updatetime=" + updatetime
				+ "]";
	}
}
