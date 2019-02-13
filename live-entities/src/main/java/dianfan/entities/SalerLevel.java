package dianfan.entities;
/**
 * @ClassName SalerLevel
 * @Description 业务员等级
 * @author cjy
 * @date 2018年2月23日 下午3:39:15
 */
public class SalerLevel {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String agentid; //varchar(50) DEFAULT NULL COMMENT '代理商id',
	private String orderdes; //varchar(50) DEFAULT NULL COMMENT '顺序',
	private String levelname; //varchar(50) DEFAULT NULL COMMENT '等级名称',
	private Integer limitpeople; //varchar(50) DEFAULT NULL COMMENT '标准(拉人个数)',
	private Integer xcount; //varchar(50) DEFAULT NULL COMMENT 'x型资源',
	private Integer ycount; //varchar(50) DEFAULT NULL COMMENT 'y型资源',
	private Integer zcount; //varchar(50) DEFAULT NULL COMMENT 'z型资源',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getOrderdes() {
		return orderdes;
	}
	public void setOrderdes(String orderdes) {
		this.orderdes = orderdes;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public Integer getLimitpeople() {
		return limitpeople;
	}
	public void setLimitpeople(Integer limitpeople) {
		this.limitpeople = limitpeople;
	}
	public Integer getXcount() {
		return xcount;
	}
	public void setXcount(Integer xcount) {
		this.xcount = xcount;
	}
	public Integer getYcount() {
		return ycount;
	}
	public void setYcount(Integer ycount) {
		this.ycount = ycount;
	}
	public Integer getZcount() {
		return zcount;
	}
	public void setZcount(Integer zcount) {
		this.zcount = zcount;
	}
	@Override
	public String toString() {
		return "SalerLevel [id=" + id + ", agentid=" + agentid + ", orderdes=" + orderdes + ", levelname=" + levelname
				+ ", limitpeople=" + limitpeople + ", xcount=" + xcount + ", ycount=" + ycount + ", zcount=" + zcount
				+ "]";
	}
}
