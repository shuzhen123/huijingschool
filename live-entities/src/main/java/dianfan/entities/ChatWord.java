package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName ChatWord
 * @Description 业务员聊天记录
 * @author cjy
 * @date 2018年3月13日 下午2:27:37
 */
public class ChatWord {
	private String id; 
	private String saleid; //业务员id
	private String realname; //业务员名称
	private String wordid; //文字记录id
	private String words; // 文字信息
	private String customerid; //客户id
	private String telno; //客户手机号
	private String nickname; //客户昵称
	private Timestamp createtime; //创建时间
	
	private Integer count; //奖惩次数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaleid() {
		return saleid;
	}

	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getWordid() {
		return wordid;
	}

	public void setWordid(String wordid) {
		this.wordid = wordid;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ChatWord [id=" + id + ", saleid=" + saleid + ", realname=" + realname + ", wordid=" + wordid
				+ ", words=" + words + ", customerid=" + customerid + ", telno=" + telno + ", nickname=" + nickname
				+ ", createtime=" + createtime + ", count=" + count + "]";
	}
}
