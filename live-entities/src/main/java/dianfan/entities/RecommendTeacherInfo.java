package dianfan.entities;
/**
 * @ClassName RecommendTeacherInfo
 * @Description 名师推荐
 * @author cjy
 * @date 2018年1月23日 下午4:31:53
 */
public class RecommendTeacherInfo  implements Comparable<RecommendTeacherInfo>{
	private String userid; //讲师id
	private String teacherurl; //讲师展示图
	private Integer famousteacherord; //名师推荐顺序
	private Integer course_count; //课程数
	private Integer video_count; //公开课数
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTeacherurl() {
		return teacherurl;
	}
	public void setTeacherurl(String teacherurl) {
		this.teacherurl = teacherurl;
	}
	public Integer getFamousteacherord() {
		return famousteacherord;
	}
	public void setFamousteacherord(Integer famousteacherord) {
		this.famousteacherord = famousteacherord;
	}
	public Integer getCourse_count() {
		return course_count;
	}
	public void setCourse_count(Integer course_count) {
		this.course_count = course_count;
	}
	
	public Integer getVideo_count() {
		return video_count;
	}
	public void setVideo_count(Integer video_count) {
		this.video_count = video_count;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_count == null) ? 0 : course_count.hashCode());
		result = prime * result + ((famousteacherord == null) ? 0 : famousteacherord.hashCode());
		result = prime * result + ((teacherurl == null) ? 0 : teacherurl.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime * result + ((video_count == null) ? 0 : video_count.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecommendTeacherInfo other = (RecommendTeacherInfo) obj;
		if (course_count == null) {
			if (other.course_count != null)
				return false;
		} else if (!course_count.equals(other.course_count))
			return false;
		if (famousteacherord == null) {
			if (other.famousteacherord != null)
				return false;
		} else if (!famousteacherord.equals(other.famousteacherord))
			return false;
		if (teacherurl == null) {
			if (other.teacherurl != null)
				return false;
		} else if (!teacherurl.equals(other.teacherurl))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (video_count == null) {
			if (other.video_count != null)
				return false;
		} else if (!video_count.equals(other.video_count))
			return false;
		return true;
	}
	@Override
	public int compareTo(RecommendTeacherInfo o) {
		if (this.famousteacherord == null) this.famousteacherord = 0;
		if (o.famousteacherord == null) o.famousteacherord = 0;
		
		if (this.famousteacherord - o.famousteacherord > 0) {
			return -1;
		}else {
			return 1;
		}
	}
	@Override
	public String toString() {
		return "RecommendTeacherInfo [userid=" + userid + ", teacherurl=" + teacherurl + ", famousteacherord="
				+ famousteacherord + ", course_count=" + course_count + ", video_count=" + video_count + "]";
	}
}
