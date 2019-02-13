package dianfan.entities;

import java.math.BigDecimal;

/**
 * @ClassName AppCourseList
 * @Description app首页推荐课程
 * @author cjy
 * @date 2018年1月24日 上午10:15:28
 */
public class AppRecommendCourse {
	private String courseid; //课程id
	private String coursename; //课程名称
	private String coursepic; //课程展示图
	private BigDecimal coursemoney; //价格
	private Integer type; //课程类型（1:免费2:付费）
	private Integer study_count; //学习量
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
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
	public String getCoursepic() {
		return coursepic;
	}
	public void setCoursepic(String coursepic) {
		this.coursepic = coursepic;
	}
	public BigDecimal getCoursemoney() {
		return coursemoney;
	}
	public void setCoursemoney(BigDecimal coursemoney) {
		this.coursemoney = coursemoney;
	}
	public Integer getStudy_count() {
		return study_count;
	}
	public void setStudy_count(Integer study_count) {
		this.study_count = study_count;
	}
	@Override
	public String toString() {
		return "AppCourseList [courseid=" + courseid + ", coursename=" + coursename + ", coursepic=" + coursepic
				+ ", coursemoney=" + coursemoney + ", study_count=" + study_count + "]";
	}
}
