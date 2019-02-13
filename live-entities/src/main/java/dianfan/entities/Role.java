package dianfan.entities;
/**
 * @ClassName Role
 * @Description 权限
 * @author cjy
 * @date 2018年1月16日 上午10:55:37
 */
public class Role {
	private String jurisdictionid; //varchar(50) NOT NULL COMMENT '权限主键id',
	private String jurisdictionname; //varchar(50) DEFAULT NULL COMMENT '权限名称',
	private String functionname; //varchar(50) DEFAULT NULL COMMENT '功能名称',
	private Integer flag; //tinyint(1) DEFAULT NULL COMMENT '开启或关闭（1开，0关）',
	public String getJurisdictionid() {
		return jurisdictionid;
	}
	public void setJurisdictionid(String jurisdictionid) {
		this.jurisdictionid = jurisdictionid;
	}
	public String getJurisdictionname() {
		return jurisdictionname;
	}
	public void setJurisdictionname(String jurisdictionname) {
		this.jurisdictionname = jurisdictionname;
	}
	public String getFunctionname() {
		return functionname;
	}
	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Role [jurisdictionid=" + jurisdictionid + ", jurisdictionname=" + jurisdictionname + ", functionname="
				+ functionname + ", flag=" + flag + "]";
	}
	
}
