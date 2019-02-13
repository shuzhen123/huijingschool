package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName CourseCommentInfo
 * @Description 课程评论
 * @author cjy
 * @date 2017年12月25日 上午9:34:32
 */
public class CourseCommentInfo {

	/*t_course_comment*/
	private String userid; //用户id
	private String commentcontent; //评价内容
	private Integer coursescore; //课程分数
	private Timestamp createtime; //创建时间
	/*t_userinfo*/
	private String nickname; //昵称
	private String iconurl; //头像
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCommentcontent() {
		return commentcontent;
	}
	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	public Integer getCoursescore() {
		return coursescore;
	}
	public void setCoursescore(Integer coursescore) {
		this.coursescore = coursescore;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	@Override
	public String toString() {
		return "CourseCommentInfo [userid=" + userid + ", commentcontent=" + commentcontent + ", coursescore="
				+ coursescore + ", createtime=" + createtime + ", nickname=" + nickname + ", iconurl=" + iconurl + "]";
	}
	
}
