package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName NewsList
 * @Description app首页资讯列表
 * @author cjy
 * @date 2018年1月24日 上午10:51:20
 */
public class NewsList {
	private String id; // 资讯id
	private String picurl; // 缩略图url
	private String infomationtitle; // 资讯标题
	private Integer readcounts; // 阅读数量
	private Timestamp createtime; // 创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getInfomationtitle() {
		return infomationtitle;
	}
	public void setInfomationtitle(String infomationtitle) {
		this.infomationtitle = infomationtitle;
	}
	public Integer getReadcounts() {
		return readcounts;
	}
	public void setReadcounts(Integer readcounts) {
		this.readcounts = readcounts;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "NewsList [id=" + id + ", picurl=" + picurl + ", infomationtitle=" + infomationtitle + ", readcounts="
				+ readcounts + ", createtime=" + createtime + "]";
	}
}
