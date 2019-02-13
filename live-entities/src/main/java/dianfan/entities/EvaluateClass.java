package dianfan.entities;

import java.util.List;

/**
 * @ClassName EvaluateClass
 * @Description 风险评测题目大类
 * @author cjy
 * @date 2018年5月7日 上午9:31:10
 */
public class EvaluateClass {
	private String classid; //id
	private String classname; //大类类名称
	private List<EvaluateQuestion> question; //问题题目
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public List<EvaluateQuestion> getQuestion() {
		return question;
	}
	public void setQuestion(List<EvaluateQuestion> question) {
		this.question = question;
	}
	
	
	@Override
	public String toString() {
		return "EvaluateClass [classid=" + classid + ", classname=" + classname + ", question=" + question + "]";
	}
}