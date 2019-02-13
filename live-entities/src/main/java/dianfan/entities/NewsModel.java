package dianfan.entities;
/**
 * @ClassName NewsModel
 * @Description 资讯模块
 * @author cjy
 * @date 2018年1月10日 上午10:58:47
 */
public class NewsModel {

	private String informationid; //资讯模块id
	private String newskindcode; //资讯模块code
	private String newskindname; //资讯模块名称
	private Integer entkbn; //数据有效区分
	public String getInformationid() {
		return informationid;
	}
	public void setInformationid(String informationid) {
		this.informationid = informationid;
	}
	public String getNewskindcode() {
		return newskindcode;
	}
	public void setNewskindcode(String newskindcode) {
		this.newskindcode = newskindcode;
	}
	public String getNewskindname() {
		return newskindname;
	}
	public void setNewskindname(String newskindname) {
		this.newskindname = newskindname;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "NewsModel [informationid=" + informationid + ", newskindcode=" + newskindcode + ", newskindname="
				+ newskindname + ", entkbn=" + entkbn + "]";
	}
	
}
