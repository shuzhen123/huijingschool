package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName CourseCommont
 * @Description 课程评论
 * @author cjy
 * @date 2018年1月12日 上午10:50:17
 */
public class CourseCommont {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '用户id',
	private String telno; //varchar(50) DEFAULT NULL COMMENT '用户手机号码',
	private String nickname; //varchar(50) DEFAULT NULL COMMENT '用户昵称',
	private String courseid; //varchar(50) DEFAULT NULL COMMENT '课程id',
	private String commentcontent; //varchar(200) DEFAULT NULL COMMENT '评价内容',
	private Integer coursescore; //varchar(50) DEFAULT NULL COMMENT '课程分数',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
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
	@Override
	public String toString() {
		return "CourseCommont [id=" + id + ", userid=" + userid + ", telno=" + telno + ", nickname=" + nickname
				+ ", courseid=" + courseid + ", commentcontent=" + commentcontent + ", coursescore=" + coursescore
				+ ", createtime=" + createtime + "]";
	}
}
