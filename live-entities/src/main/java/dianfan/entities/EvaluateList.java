package dianfan.entities;
/**
 * @ClassName: EvaluateList
 * @Description: 风险评测题目页面展示list
 * @author sz
 * @time 2018年5月8日 下午12:25:39
 */
public class EvaluateList {
	private String id; //id 大类id
	private String classname; //大类类型
	private String questionid; //问题id
	private String questionname; //问题名称
	private String answerid; //答案id
	private String answername; //答案答案
	private Integer score; //分数
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getQuestionname() {
		return questionname;
	}
	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}
	public String getAnswerid() {
		return answerid;
	}
	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}
	public String getAnswername() {
		return answername;
	}
	public void setAnswername(String answername) {
		this.answername = answername;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "EvaluateList [id=" + id + ", classname=" + classname + ", questionid=" + questionid + ", questionname="
				+ questionname + ", answerid=" + answerid + ", answername=" + answername + ", score=" + score + "]";
	}


}
