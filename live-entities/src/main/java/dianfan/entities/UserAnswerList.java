package dianfan.entities;

/**
 * @ClassName: UserAnswerList
 * @Description: 用户测评结果列表
 * @author sz
 * @time 2018年5月15日 下午7:02:52
 */
public class UserAnswerList {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String userid; //varchar(50) NOT NULL COMMENT '用户id',
	private String classid; //大类的id
	private String classname; //大类类名称
	private String questionid; //问题的id
	private String questionname; //问题名称
	private String answerid; //varchar(50) NOT NULL COMMENT '答案id',
	private String answername; //答案内容
	private int score; //int(11) NOT NULL COMMENT '分数',
	private String riskratingfalg;//风险测评通过flag
	private String createtime; //datetime NOT NULL COMMENT '创建时间',
	private int allscore;
	
	
	
	public int getAllscore() {
		return allscore;
	}
	public void setAllscore(int allscore) {
		this.allscore = allscore;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRiskratingfalg() {
		return riskratingfalg;
	}
	public void setRiskratingfalg(String riskratingfalg) {
		this.riskratingfalg = riskratingfalg;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "UserAnswerList [id=" + id + ", userid=" + userid + ", classid=" + classid + ", classname=" + classname
				+ ", questionid=" + questionid + ", questionname=" + questionname + ", answerid=" + answerid
				+ ", answername=" + answername + ", score=" + score + ", riskratingfalg=" + riskratingfalg
				+ ", createtime=" + createtime + ", allscore=" + allscore + "]";
	}
	
	
	
	
	

}
