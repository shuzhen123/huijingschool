package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName SysLogs
 * @Description 接口访问日志表
 * @author cjy
 * @date 2017年12月25日 上午9:33:22
 */
public class SysLogs implements java.io.Serializable {

	/** @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String description;
	private String method;
	private Long logtype;
	private String requestip;
	private String createid;
	private Timestamp createtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Long getLogtype() {
		return logtype;
	}
	public void setLogtype(Long logtype) {
		this.logtype = logtype;
	}
	public String getRequestip() {
		return requestip;
	}
	public void setRequestip(String requestip) {
		this.requestip = requestip;
	}
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
}