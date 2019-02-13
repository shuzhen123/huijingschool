package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName PhoneRecord
 * @Description 通话记录
 * @author cjy
 * @date 2018年3月14日 上午11:17:51
 */
public class PhoneRecordInfo {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String customerid; //varchar(50) DEFAULT NULL COMMENT '客户id',
	private String nickname; //varchar(50) DEFAULT NULL COMMENT '客户昵称',
	private String classid; //varchar(200) DEFAULT NULL COMMENT '客户分类id',
	private String classname; //varchar(200) DEFAULT NULL COMMENT '客户分类名称',
	private String telno; //varchar(50) DEFAULT NULL COMMENT '电话号码',
	private Integer status; //varchar(1) DEFAULT NULL COMMENT '通话状态（0未接听，1已接通，未上传录音，2已接通，已上传录音）',
	private Integer calltimes; //varchar(200) DEFAULT NULL COMMENT '通话时长',
	private String voicepath; //varchar(200) DEFAULT NULL COMMENT '通话声音服务器地址',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	
	private String memo; //客户备注,
	private Integer contacted; //0:未联系；1:已联系,
	private Integer collected; //0:未收藏，1:已收藏,
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getContacted() {
		return contacted;
	}
	public void setContacted(Integer contacted) {
		this.contacted = contacted;
	}
	public Integer getCollected() {
		return collected;
	}
	public void setCollected(Integer collected) {
		this.collected = collected;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCalltimes() {
		return calltimes;
	}
	public void setCalltimes(Integer calltimes) {
		this.calltimes = calltimes;
	}
	public String getVoicepath() {
		return voicepath;
	}
	public void setVoicepath(String voicepath) {
		this.voicepath = voicepath;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "PhoneRecordInfo [id=" + id + ", customerid=" + customerid + ", nickname=" + nickname + ", classid="
				+ classid + ", classname=" + classname + ", telno=" + telno + ", status=" + status + ", calltimes="
				+ calltimes + ", voicepath=" + voicepath + ", createtime=" + createtime + "]";
	}
}
