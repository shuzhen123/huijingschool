package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName SalerCall
 * @Description 业务员通话记录
 * @author cjy
 * @date 2018年3月12日 下午12:11:00
 */
public class SalerCall {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String salerid; //varchar(50) DEFAULT NULL COMMENT '业务员id',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '业务员名称',
	private String customerid; //varchar(50) DEFAULT NULL COMMENT '客户id',
	
	private String account; //varchar(50) DEFAULT NULL COMMENT '客户账号',
	private String nickname; //varchar(50) DEFAULT NULL COMMENT '客户昵称',
	
	private String telno; //varchar(50) DEFAULT NULL COMMENT '电话号码',
	private Integer status; //varchar(1) DEFAULT NULL COMMENT '客户状态',
	private Integer calltimes; //varchar(200) DEFAULT NULL COMMENT '通话时长',
	private String voicetitle; //varchar(50) DEFAULT NULL COMMENT '通话声音标题【某某-日期.MP3】',
	private String voicepath; //varchar(200) DEFAULT NULL COMMENT '通话声音服务器地址',
	private Integer collectionflag; //varchar(1) DEFAULT NULL COMMENT '收藏flag',
	private Timestamp starttime; //datetime DEFAULT NULL COMMENT '通话开始时间',
	private Timestamp endtime; //datetime DEFAULT NULL COMMENT '通话结束时间',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	
	private Integer count; //varchar(1) DEFAULT NULL COMMENT '记录违规条数',

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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getVoicetitle() {
		return voicetitle;
	}

	public void setVoicetitle(String voicetitle) {
		this.voicetitle = voicetitle;
	}

	public String getVoicepath() {
		return voicepath;
	}

	public void setVoicepath(String voicepath) {
		this.voicepath = voicepath;
	}

	public Integer getCollectionflag() {
		return collectionflag;
	}

	public void setCollectionflag(Integer collectionflag) {
		this.collectionflag = collectionflag;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "SalerCall [id=" + id + ", salerid=" + salerid + ", realname=" + realname + ", customerid=" + customerid
				+ ", account=" + account + ", nickname=" + nickname + ", telno=" + telno + ", status=" + status
				+ ", calltimes=" + calltimes + ", voicetitle=" + voicetitle + ", voicepath=" + voicepath
				+ ", collectionflag=" + collectionflag + ", starttime=" + starttime + ", endtime=" + endtime
				+ ", createtime=" + createtime + ", count=" + count + "]";
	}
	
}
