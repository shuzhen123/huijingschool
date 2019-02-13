package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Shares
 * @Description 诊股
 * @author cjy
 * @date 2018年3月28日 下午1:30:48
 */
public class Shares {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String questiontitle; //varchar(100) DEFAULT NULL COMMENT '问题标题',
	private String questiondes; //varchar(500) DEFAULT NULL COMMENT '问题描述',
	private String answer; //varchar(3000) DEFAULT NULL COMMENT '回答',
	private String flag; //varchar(1) DEFAULT NULL COMMENT '回复flag',
	private Timestamp answertime; //datetime DEFAULT NULL COMMENT '回答时间',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createid; //datetime DEFAULT NULL COMMENT '用户id',
	private String telno; //datetime DEFAULT NULL COMMENT '用户手机号码',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestiontitle() {
		return questiontitle;
	}
	public void setQuestiontitle(String questiontitle) {
		this.questiontitle = questiontitle;
	}
	public String getQuestiondes() {
		return questiondes;
	}
	public void setQuestiondes(String questiondes) {
		this.questiondes = questiondes;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Timestamp getAnswertime() {
		return answertime;
	}
	public void setAnswertime(Timestamp answertime) {
		this.answertime = answertime;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	@Override
	public String toString() {
		return "Shares [id=" + id + ", questiontitle=" + questiontitle + ", questiondes=" + questiondes + ", answer="
				+ answer + ", flag=" + flag + ", answertime=" + answertime + ", createtime=" + createtime
				+ ", createid=" + createid + ", telno=" + telno + "]";
	}
}
