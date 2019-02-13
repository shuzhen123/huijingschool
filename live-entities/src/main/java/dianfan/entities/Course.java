package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName Course
 * @Description 课程
 * @author cjy
 * @date 2018年1月10日 上午11:00:35
 */
public class Course {
	private String courseid; //varchar(50) NOT NULL COMMENT '课程id',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '教师id',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '教师姓名',
	
	private String agentid; //varchar(50) DEFAULT NULL COMMENT '代理商id',
	private String agentname; //varchar(50) DEFAULT NULL COMMENT '代理商姓名',
	
	private Integer recommendflag; //tinyint(1) DEFAULT '0' COMMENT '推荐flag(0不推荐，1推荐)',
	private Integer recommendindex; //tinyint(1) DEFAULT '0' COMMENT '推荐序号',
	private String coursetypecode; //varchar(32) DEFAULT NULL COMMENT '课程分类',
	private String coursetypename; //varchar(32) DEFAULT NULL COMMENT '课程分类名称',
	private Integer coursekind; //int(1) DEFAULT NULL COMMENT '课程类型【1：免费直播课程2：vip实战直播课程3：精品课4：私教课】',
	private String coursename; //varchar(100) DEFAULT NULL COMMENT '课程名称',
	private BigDecimal coursemoney; //double(15,2) DEFAULT NULL COMMENT '课程金额(原价)',
	private String discountrate; //double(3,2) DEFAULT NULL COMMENT '折扣率',
	private String coursepic; //varchar(200) DEFAULT NULL COMMENT '课程展示图',
	private String coursedes; //text COMMENT '课程描述',
	private Integer courselimit; //int(10) DEFAULT NULL COMMENT '课程限制(1:免费2:付费)',
	private Integer auth; //varchar(2) DEFAULT NULL COMMENT '审核状态(1待审核，2审核通过，3审核失败)',
	private String cause; //varchar(200) DEFAULT NULL COMMENT '审核失败原因',
	private String authuserid; //varchar(50) DEFAULT NULL COMMENT '审核用户id',
	private Integer bannerflag; //varchar(2) DEFAULT NULL COMMENT '上bannerflag(0不上，1上)',
	private Integer thumbsupcount; //int(10) DEFAULT NULL COMMENT '点赞数量',
	private Integer flag; //tinyint(1) DEFAULT NULL COMMENT '课程上下架(0下架，1上架)',
	private Integer liveflag; //tinyint(1) DEFAULT NULL COMMENT '直播课程flag(1正在直播，2往期直播，3预告直播)',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Integer entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分（0：正常数据1:无效数据9：逻辑删除）',
	
	public Integer getLiveflag() {
		return liveflag;
	}
	public void setLiveflag(Integer liveflag) {
		this.liveflag = liveflag;
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
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public Integer getRecommendindex() {
		return recommendindex;
	}
	public void setRecommendindex(Integer recommendindex) {
		this.recommendindex = recommendindex;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
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
	public Integer getRecommendflag() {
		return recommendflag;
	}
	public void setRecommendflag(Integer recommendflag) {
		this.recommendflag = recommendflag;
	}
	public String getCoursetypecode() {
		return coursetypecode;
	}
	public void setCoursetypecode(String coursetypecode) {
		this.coursetypecode = coursetypecode;
	}
	public String getCoursetypename() {
		return coursetypename;
	}
	public void setCoursetypename(String coursetypename) {
		this.coursetypename = coursetypename;
	}
	public Integer getCoursekind() {
		return coursekind;
	}
	public void setCoursekind(Integer coursekind) {
		this.coursekind = coursekind;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public BigDecimal getCoursemoney() {
		return coursemoney;
	}
	public void setCoursemoney(BigDecimal coursemoney) {
		this.coursemoney = coursemoney;
	}
	public String getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(String discountrate) {
		this.discountrate = discountrate;
	}
	public String getCoursepic() {
		return coursepic;
	}
	public void setCoursepic(String coursepic) {
		this.coursepic = coursepic;
	}
	public String getCoursedes() {
		return coursedes;
	}
	public void setCoursedes(String coursedes) {
		this.coursedes = coursedes;
	}
	public Integer getCourselimit() {
		return courselimit;
	}
	public void setCourselimit(Integer courselimit) {
		this.courselimit = courselimit;
	}
	public Integer getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		this.auth = auth;
	}
	public String getAuthuserid() {
		return authuserid;
	}
	public void setAuthuserid(String authuserid) {
		this.authuserid = authuserid;
	}
	public Integer getBannerflag() {
		return bannerflag;
	}
	public void setBannerflag(Integer bannerflag) {
		this.bannerflag = bannerflag;
	}
	public Integer getThumbsupcount() {
		return thumbsupcount;
	}
	public void setThumbsupcount(Integer thumbsupcount) {
		this.thumbsupcount = thumbsupcount;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
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
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "Course [courseid=" + courseid + ", userid=" + userid + ", realname=" + realname + ", agentid=" + agentid
				+ ", agentname=" + agentname + ", recommendflag=" + recommendflag + ", recommendindex=" + recommendindex
				+ ", coursetypecode=" + coursetypecode + ", coursetypename=" + coursetypename + ", coursekind="
				+ coursekind + ", coursename=" + coursename + ", coursemoney=" + coursemoney + ", discountrate="
				+ discountrate + ", coursepic=" + coursepic + ", coursedes=" + coursedes + ", courselimit="
				+ courselimit + ", auth=" + auth + ", cause=" + cause + ", authuserid=" + authuserid + ", bannerflag="
				+ bannerflag + ", thumbsupcount=" + thumbsupcount + ", flag=" + flag + ", liveflag=" + liveflag
				+ ", createtime=" + createtime + ", createid=" + createid + ", entkbn=" + entkbn + "]";
	}
}