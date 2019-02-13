package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName NewsComment
 * @Description 咨讯评论 
 * @author cjy
 * @date 2018年3月1日 下午1:01:07
 */
public class NewsComment {
	private String commentid; //varchar(50) NOT NULL COMMENT '评论id',
	private String infomationid; //varchar(50) DEFAULT NULL COMMENT '资讯id',
	private String commentuserid; //varchar(50) DEFAULT NULL COMMENT '评论人id',
	private String iconurl; //varchar(50) DEFAULT NULL COMMENT '评论人头像',
	private String nickname; //varchar(50) DEFAULT NULL COMMENT '评论人昵称',
	private String commentcontent; //varchar(400) DEFAULT NULL COMMENT '评论内容',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
	public String getInfomationid() {
		return infomationid;
	}
	public void setInfomationid(String infomationid) {
		this.infomationid = infomationid;
	}
	public String getCommentuserid() {
		return commentuserid;
	}
	public void setCommentuserid(String commentuserid) {
		this.commentuserid = commentuserid;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCommentcontent() {
		return commentcontent;
	}
	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "NewsComment [commentid=" + commentid + ", infomationid=" + infomationid + ", commentuserid="
				+ commentuserid + ", iconurl=" + iconurl + ", nickname=" + nickname + ", commentcontent="
				+ commentcontent + ", createtime=" + createtime + "]";
	}
}
