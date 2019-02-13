package dianfan.entities;

import java.util.List;

/**
 * @ClassName EvaluateQuestion
 * @Description 风险评测问题
 * @author cjy
 * @date 2018年5月7日 上午9:31:10
 */
public class EvaluateQuestion {
	private String questionid; //id
	private List<EvaluateAnswer> answer; //问题答案
	private String questionname; //问题名称
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
	public List<EvaluateAnswer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<EvaluateAnswer> answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "EvaluateQuestion [questionid=" + questionid + ", questionname=" + questionname + ", answer=" + answer
				+ "]";
	}
}