package dianfan.entities;

import java.sql.Timestamp;

import antlr.StringUtils;

/**
 * @ClassName UserInfo
 * @Description 用户信息表
 * @author cjy
 * @date 2017年12月25日 上午9:33:02
 */
public class UserInfo {
	private String userid; // varchar(50) not null comment '用户id',
	private String username; // varchar(50) comment '用户名',
	private String introduction; // varchar(400) comment '简介',
	private String password; // varchar(64) comment '密码',
	private String realname; // varchar(50) comment '真实姓名',
	private Integer sex; // varchar(50) comment '性别（0未填写，1男，2女）',
	private String prov; // varchar(50) comment '省份',
	private String cardid; // varchar(20) comment '身份证号码',
	private String telno; // varchar(15) comment '电话号码',
	private String openid; // varchar(100) comment '微信openid',
	private String cid; // varchar(100) comment '房间号',
	private String cidpassword; // varchar(100) comment '房间密码',
	private String tokenid; // varchar(100) comment '网易云token',
	private String teacherurl; // varchar(100) comment '讲师图片',
	private String iconurl; // varchar(100) comment '头像',
	private String nickname; // varchar(50) comment '昵称',
	private String invitecode; // varchar(24) comment '邀请码',
	private Timestamp registertime; // datetime comment '注册时间',
	private Integer registerstatus; // int(1) comment '注册状态（1：未实名注册2：已实名注册）',
	private Timestamp endtime; // datetime comment '角色1和2存在该字段有用',
	private Integer role; // int(1) comment '角色【1：用户 2：后台管理3：业务员4：主播代理商】',
	private String riskratingfalg; // varchar(1) comment '风险测评通过flag',
	private String agentcode; // varchar(1) comment '代理商简称编码',

	private String remark; // 用户备注,
	private String restype; // 用户资源类型（x后台导入，y智能机器人导入，z媒体推广）,

	private String trecflag; // varchar(1) comment '教师直播推荐【1：免费课程2：VIP收费】',
	private String famousteacherflag; // varchar(1) comment '名师推荐flag(0不推荐，1推荐)',

	private Timestamp createtime; // datetime comment '创建时间',
	private String createid; // varchar(50) comment '创建者id',
	private String updateid; // varchar(50) comment '更新者id',
	private Integer entkbn; // int(1) comment '数据有效区分（0：正常数据1:无效数据9：逻辑删除）',

	private String levelid; // varchar(50) comment '等级id',
	private String levelname; // varchar(50) comment '等级名称',
	
	private String agentid; // varchar(50) comment '代理商id',
	private String agentname; // varchar(50) comment '代理商名称',

	private Integer count; // 课程数量

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		if(telno != null) {
			telno = telno.trim();
		}
		this.telno = telno;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCidpassword() {
		return cidpassword;
	}

	public void setCidpassword(String cidpassword) {
		this.cidpassword = cidpassword;
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getTeacherurl() {
		return teacherurl;
	}

	public void setTeacherurl(String teacherurl) {
		this.teacherurl = teacherurl;
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public Timestamp getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}

	public Integer getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(Integer registerstatus) {
		this.registerstatus = registerstatus;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getRiskratingfalg() {
		return riskratingfalg;
	}

	public void setRiskratingfalg(String riskratingfalg) {
		this.riskratingfalg = riskratingfalg;
	}

	public String getAgentcode() {
		return agentcode;
	}

	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRestype() {
		return restype;
	}

	public void setRestype(String restype) {
		this.restype = restype;
	}

	public String getTrecflag() {
		return trecflag;
	}

	public void setTrecflag(String trecflag) {
		this.trecflag = trecflag;
	}

	public String getFamousteacherflag() {
		return famousteacherflag;
	}

	public void setFamousteacherflag(String famousteacherflag) {
		this.famousteacherflag = famousteacherflag;
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

	public String getLevelid() {
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "UserInfo [userid=" + userid + ", username=" + username + ", introduction=" + introduction
				+ ", password=" + password + ", realname=" + realname + ", sex=" + sex + ", prov=" + prov + ", cardid="
				+ cardid + ", telno=" + telno + ", openid=" + openid + ", cid=" + cid + ", cidpassword=" + cidpassword
				+ ", tokenid=" + tokenid + ", teacherurl=" + teacherurl + ", iconurl=" + iconurl + ", nickname="
				+ nickname + ", invitecode=" + invitecode + ", registertime=" + registertime + ", registerstatus="
				+ registerstatus + ", endtime=" + endtime + ", role=" + role + ", riskratingfalg=" + riskratingfalg
				+ ", agentcode=" + agentcode + ", remark=" + remark + ", restype=" + restype + ", trecflag=" + trecflag
				+ ", famousteacherflag=" + famousteacherflag + ", createtime=" + createtime + ", createid=" + createid
				+ ", updateid=" + updateid + ", entkbn=" + entkbn + ", levelid=" + levelid + ", levelname=" + levelname
				+ ", agentid=" + agentid + ", agentname=" + agentname + ", count=" + count + "]";
	}


}
