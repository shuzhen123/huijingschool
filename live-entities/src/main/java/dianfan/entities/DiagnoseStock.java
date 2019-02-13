package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName DiagnoseStock
 * @Description 诊股
 * @author cjy
 * @date 2018年4月2日 上午9:24:49
 */
public class DiagnoseStock {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String questiontitle; //varchar(100) DEFAULT NULL COMMENT '问题标题',
	private String questiondes; //varchar(500) DEFAULT NULL COMMENT '问题描述',
	private String answer; //varchar(3000) DEFAULT NULL COMMENT '回答',
	private Integer flag; //varchar(1) DEFAULT NULL COMMENT '回复flag（0未回复，1已回复）',
	private Timestamp answertime; //datetime DEFAULT NULL COMMENT '回答时间',
	private Timestamp createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createid; //创建者id,
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
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
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
	@Override
	public String toString() {
		return "DiagnoseStock [id=" + id + ", questiontitle=" + questiontitle + ", questiondes=" + questiondes
				+ ", answer=" + answer + ", flag=" + flag + ", answertime=" + answertime + ", createtime=" + createtime
				+ ", createid=" + createid + "]";
	}
}
