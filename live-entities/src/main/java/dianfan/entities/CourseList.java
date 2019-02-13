package dianfan.entities;

import java.math.BigDecimal;

/**
 * @ClassName CourseList
 * @Description 课程列表实体类
 * @author cjy
 * @date 2017年12月21日 下午4:30:32
 */
public class CourseList {
	/*t_course*/
	private String courseid ; //课程id
	private String coursename; // 课程名称
	private Integer courselimit; //课程限制(1:免费2:付费)
	private BigDecimal coursemoney; //课程价格
	private String coursepic; //课程展示图
	private Integer thumbsupcount; //点赞数量
	
	/*t_studyprogress*/
	private Integer browsingcount; //浏览量(学习量)
	/*t_course_thumbsup*/
	private String praise; //是否点过赞（n未赞， y已赞）
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
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
	public String getCoursepic() {
		return coursepic;
	}
	public void setCoursepic(String coursepic) {
		this.coursepic = coursepic;
	}
	public Integer getThumbsupcount() {
		return thumbsupcount;
	}
	public void setThumbsupcount(Integer thumbsupcount) {
		this.thumbsupcount = thumbsupcount;
	}
	public Integer getBrowsingcount() {
		return browsingcount;
	}
	public void setBrowsingcount(Integer browsingcount) {
		this.browsingcount = browsingcount;
	}
	public String getPraise() {
		return praise;
	}
	public void setPraise(String praise) {
		this.praise = praise;
	}
	@Override
	public String toString() {
		return "CourseList [courseid=" + courseid + ", coursename=" + coursename + ", courselimit=" + courselimit
				+ ", coursemoney=" + coursemoney + ", coursepic=" + coursepic + ", thumbsupcount=" + thumbsupcount
				+ ", browsingcount=" + browsingcount + ", praise=" + praise + "]";
	}
}
