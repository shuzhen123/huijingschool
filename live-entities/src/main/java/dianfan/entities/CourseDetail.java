package dianfan.entities;

import java.math.BigDecimal;

/**
 * @ClassName CourseDetail
 * @Description 课程详情
 * @author cjy
 * @date 2017年12月25日 上午9:34:16
 */
public class CourseDetail {
	/* t_course */
	private String courseid; // 课程id
	private String coursedes; // 课程描述
	private Integer thumbsupcount; // 点赞数量
	private Integer courselimit; // 课程免、付费
	private BigDecimal coursemoney; // 课程金额
	/* t_userinfo */
	private String userid; // 教师id
	private String username; // 教师名
	private String introduction; // 简介
	private String iconurl; // 头像
	private Character loginstatus; // 登录状态（n未登录， y已登录）

	/* t_videos */
	private String videoid; // 视频id
	private String videoppicurl; // 课程展示图
	private String videourl; // 课程链接

	/* t_course_thumbsup */
	private Character praise; // 是否点过赞（n未赞， y已赞）

	private Character buy; // 是否已购买（n未购买， y已购买）

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCoursedes() {
		return coursedes;
	}

	public void setCoursedes(String coursedes) {
		this.coursedes = coursedes;
	}

	public Integer getThumbsupcount() {
		return thumbsupcount;
	}

	public void setThumbsupcount(Integer thumbsupcount) {
		this.thumbsupcount = thumbsupcount;
	}

	public Integer getCourselimit() {
		return courselimit;
	}

	public void setCourselimit(Integer courselimit) {
		this.courselimit = courselimit;
	}

	public BigDecimal getCoursemoney() {
		return coursemoney;
	}

	public void setCoursemoney(BigDecimal coursemoney) {
		this.coursemoney = coursemoney;
	}

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

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public Character getLoginstatus() {
		return loginstatus;
	}

	public void setLoginstatus(Character loginstatus) {
		this.loginstatus = loginstatus;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getVideoppicurl() {
		return videoppicurl;
	}

	public void setVideoppicurl(String videoppicurl) {
		this.videoppicurl = videoppicurl;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public Character getPraise() {
		return praise;
	}

	public void setPraise(Character praise) {
		this.praise = praise;
	}

	public Character getBuy() {
		return buy;
	}

	public void setBuy(Character buy) {
		this.buy = buy;
	}

	@Override
	public String toString() {
		return "CourseDetail [courseid=" + courseid + ", coursedes=" + coursedes + ", thumbsupcount=" + thumbsupcount
				+ ", courselimit=" + courselimit + ", coursemoney=" + coursemoney + ", userid=" + userid + ", username="
				+ username + ", introduction=" + introduction + ", iconurl=" + iconurl + ", loginstatus=" + loginstatus
				+ ", videoid=" + videoid + ", videoppicurl=" + videoppicurl + ", videourl=" + videourl + ", praise="
				+ praise + ", buy=" + buy + "]";
	}

}