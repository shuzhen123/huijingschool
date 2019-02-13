package dianfan.entities;
/**
 * @ClassName CartCourse
 * @Description 购物车中的课程
 * @author cjy
 * @date 2018年4月9日 下午12:40:46
 */
public class CartCourse {

	private String id; //购物车课程列表id
	private String userid; //用户id
	private String courseid; //课程id
	private String coursename; //课程名称
	private String coursepic; //课程展示图
	private String coursemoney; //课程金额
	
	public String getId() {
		return id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCoursemoney() {
		return coursemoney;
	}
	public void setCoursemoney(String coursemoney) {
		this.coursemoney = coursemoney;
	}
	@Override
	public String toString() {
		return "CartCourse [id=" + id + ", userid=" + userid + ", courseid=" + courseid + ", coursename=" + coursename
				+ ", coursepic=" + coursepic + ", coursemoney=" + coursemoney + "]";
	}
}
