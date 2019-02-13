package dianfan.entities;
/**
 * @ClassName EvaluateAnswer
 * @Description 风险评测题目答案
 * @author cjy
 * @date 2018年5月7日 上午9:31:10
 */
public class EvaluateAnswer {
	private String answerid; //id
	private String answername; //答案
	private Integer score; //分数
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
		return "EvaluateAnswer [answerid=" + answerid + ", answername=" + answername + ", score=" + score + "]";
	}
}