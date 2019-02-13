package dianfan.entities;
/**
 * @ClassName CourseAndStudyCount
 * @Description 课程学习量
 * @author cjy
 * @date 2017年12月25日 上午9:34:53
 */
public class CourseAndStudyCount {

	private String courseid; //课程id
	private Integer studyCount; // 课程对应的学习量
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public Integer getStudyCount() {
		return studyCount;
	}
	public void setStudyCount(Integer studyCount) {
		this.studyCount = studyCount;
	}
	@Override
	public String toString() {
		return "CourseAndStudyCount [courseid=" + courseid + ", studyCount=" + studyCount + "]";
	}
}
