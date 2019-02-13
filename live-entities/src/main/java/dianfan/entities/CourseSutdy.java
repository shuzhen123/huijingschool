package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName CourseSutdy
 * @Description 课程学习进度
 * @author cjy
 * @date 2018年6月6日 上午11:28:50
 */
public class CourseSutdy {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String studyuserid; //varchar(50) DEFAULT NULL COMMENT '学习课程者id',
	private Timestamp studytime; //datetime DEFAULT NULL COMMENT '学习时间',
	
	private String courseid; //varchar(50) DEFAULT NULL COMMENT '课程id',
	private String coursename; // 课程名称
	private Integer coursekind; //课程类型【1：免费直播课程2：vip实战直播课程3：精品课4：私教课】
	private String coursepic; //课程展示图
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudyuserid() {
		return studyuserid;
	}
	public void setStudyuserid(String studyuserid) {
		this.studyuserid = studyuserid;
	}
	public Timestamp getStudytime() {
		return studytime;
	}
	public void setStudytime(Timestamp studytime) {
		this.studytime = studytime;
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
	public Integer getCoursekind() {
		return coursekind;
	}
	public void setCoursekind(Integer coursekind) {
		this.coursekind = coursekind;
	}
	public String getCoursepic() {
		return coursepic;
	}
	public void setCoursepic(String coursepic) {
		this.coursepic = coursepic;
	}
	@Override
	public String toString() {
		return "CourseSutdy [id=" + id + ", studyuserid=" + studyuserid + ", studytime=" + studytime + ", courseid="
				+ courseid + ", coursename=" + coursename + ", coursekind=" + coursekind + ", coursepic=" + coursepic
				+ "]";
	}
}
