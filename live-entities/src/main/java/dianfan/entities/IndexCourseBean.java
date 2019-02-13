package dianfan.entities;

import java.util.List;

/**
 * @ClassName IndexCourseBean
 * @Description 首页课程分类推荐列表
 * @author cjy
 * @date 2018年2月5日 上午9:17:04
 */
public class IndexCourseBean {

	private String kindid;
	private String coursetypename;
	private List<AppRecommendCourse> courses;
	public String getKindid() {
		return kindid;
	}
	public void setKindid(String kindid) {
		this.kindid = kindid;
	}
	public String getCoursetypename() {
		return coursetypename;
	}
	public void setCoursetypename(String coursetypename) {
		this.coursetypename = coursetypename;
	}
	public List<AppRecommendCourse> getCourses() {
		return courses;
	}
	public void setCourses(List<AppRecommendCourse> courses) {
		this.courses = courses;
	}
	@Override
	public String toString() {
		return "IndexCourseBean [kindid=" + kindid + ", coursetypename=" + coursetypename + ", courses=" + courses
				+ "]";
	}
	
}
