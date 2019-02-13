package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName LiveCourse
 * @Description 直播课程bean
 * @author cjy
 * @date 2018年3月8日 上午11:31:59
 */
public class LiveCourse {
	private String courseid; //varchar(50) NOT NULL COMMENT '课程id',
	private String agentid; //varchar(50) DEFAULT NULL COMMENT '代理商id',
	private String agentname; //varchar(50) DEFAULT NULL COMMENT '代理商名称',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '教师id',
	private String realname; //varchar(50) DEFAULT NULL COMMENT '教师名称',
	private String coursetypecode; //varchar(50) DEFAULT NULL COMMENT '课程分类id',
	private String coursetypename; //varchar(50) DEFAULT NULL COMMENT '课程分类名称',
	private Integer coursekind; //int(1) DEFAULT NULL COMMENT '课程类型【1：免费直播课程2：vip实战直播课程3：精品课4：私教课】',
	private String coursename; //varchar(100) DEFAULT NULL COMMENT '课程名称',
	private String coursepic; //varchar(200) DEFAULT NULL COMMENT '课程展示图',
	private String coursedes; //text COMMENT '课程描述',
	private Integer auth; //varchar(2) DEFAULT NULL COMMENT '审核状态（1待审核，2审核通过，3审核失败）',
	private String cause; //varchar(2) DEFAULT NULL COMMENT '审核失败原因',
	private Integer bannerflag; //varchar(2) DEFAULT NULL COMMENT '上bannerflag（0不上，1上）',
	private Integer thumbsupcount; //int(10) DEFAULT NULL COMMENT '点赞数量',
	private Integer flag; //tinyint(1) DEFAULT NULL COMMENT '课程上下架(0下架，1上架)',
	private Integer liveflag; //tinyint(1) DEFAULT NULL COMMENT '直播课程flag(1正在直播，2往期直播，3预告直播)',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private Timestamp starttime; //datetime DEFAULT NULL COMMENT '预告开始时间',
	private String starttime_fmt; //varchar DEFAULT NULL COMMENT '预告开始时间-转格式',
	private Timestamp endtime; //datetime DEFAULT NULL COMMENT '预告结束时间',
	private String endtime_fmt; //varchar DEFAULT NULL COMMENT '预告结束时间-转格式',
	
	private String videoid; //视频id,
	private String videourl; //text COMMENT '视频链接地址【网易云直播】',
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
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
	public Integer getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		this.auth = auth;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
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
	public Integer getLiveflag() {
		return liveflag;
	}
	public void setLiveflag(Integer liveflag) {
		this.liveflag = liveflag;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public String getStarttime_fmt() {
		return starttime_fmt;
	}
	public void setStarttime_fmt(String starttime_fmt) {
		this.starttime_fmt = starttime_fmt;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public String getEndtime_fmt() {
		return endtime_fmt;
	}
	public void setEndtime_fmt(String endtime_fmt) {
		this.endtime_fmt = endtime_fmt;
	}
	public String getVideoid() {
		return videoid;
	}
	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	@Override
	public String toString() {
		return "LiveCourse [courseid=" + courseid + ", agentid=" + agentid + ", agentname=" + agentname + ", userid="
				+ userid + ", realname=" + realname + ", coursetypecode=" + coursetypecode + ", coursetypename="
				+ coursetypename + ", coursekind=" + coursekind + ", coursename=" + coursename + ", coursepic="
				+ coursepic + ", coursedes=" + coursedes + ", auth=" + auth + ", cause=" + cause + ", bannerflag="
				+ bannerflag + ", thumbsupcount=" + thumbsupcount + ", flag=" + flag + ", liveflag=" + liveflag
				+ ", createtime=" + createtime + ", starttime=" + starttime + ", starttime_fmt=" + starttime_fmt
				+ ", endtime=" + endtime + ", endtime_fmt=" + endtime_fmt + ", videoid=" + videoid + ", videourl="
				+ videourl + "]";
	}
}
